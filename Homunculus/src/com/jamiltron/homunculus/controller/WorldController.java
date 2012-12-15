package com.jamiltron.homunculus.controller;

import com.jamiltron.homunculus.model.World;
import com.jamiltron.homunculus.model.Spell;
import com.jamiltron.homunculus.model.Component;
import com.jamiltron.homunculus.model.Homunculus;
import com.jamiltron.homunculus.util.JArray;
import java.util.HashMap;
import java.util.Map;

public class WorldController {
  enum Keys {
    LEFT, RIGHT, ROTR, ROTL, DROP;
  }
  
  private World world;
  private Spell activeSpell;
  float currentTime;
  float dropTime;
  float fastTime;
  float normalTime;
  
  JArray<Boolean> toDestroy;
  static Map<Keys, Boolean> keys = new HashMap<WorldController.Keys, Boolean>();
  static {
    keys.put(Keys.LEFT,   false);
    keys.put(Keys.RIGHT,  false);
    keys.put(Keys.ROTR, false);
    keys.put(Keys.ROTL, false);
    keys.put(Keys.DROP,   false);
  };
  
  public void leftPress() {
    keys.put(Keys.LEFT, true);
  }
  
  public void rightPress() {
    keys.put(Keys.RIGHT, true);
  }
  
  public void rotrPress() {
    keys.put(Keys.ROTR, true);
  }
  
  public void rotlPress() {
    keys.put(Keys.ROTL, true);
  }
  
  public void dropPress() {
    keys.put(Keys.DROP, true);
  }
  
  public void leftRelease() {
    keys.put(Keys.LEFT, false);
  }
  
  public void rightRelease() {
    keys.put(Keys.RIGHT, false);
  }
  
  public void rotrRelease() {
    keys.put(Keys.ROTR, false);
  }
  
  public void rotlRelease() {
    keys.put(Keys.ROTL, false);
  }
  
  public void dropRelease() {
    keys.put(Keys.DROP, false);
  }
  
  public WorldController(World w) {
    world = w;
    activeSpell = w.getActiveSpell();
    currentTime = 0f;
    fastTime = 0.1f;
    normalTime = 0.5f;
    dropTime = normalTime;
    toDestroy = new JArray<Boolean>(8, 17);
    for (int y = 2; y <= 18; y++) {
      for (int x = 2; x <= 9; x++) {
        toDestroy.set(x,  y, false);
      }
    }
  }

  
  public void update(float dt) {
    if (keys.get(Keys.DROP) && activeSpell != null) {
      dropTime = fastTime;
    } else {
      dropTime = normalTime;
    }
    currentTime += dt;
    if (currentTime >= dropTime) {
      currentTime -= dropTime;
      if (activeSpell == null) {
        world.restSpell();
        activeSpell = world.getActiveSpell();
      } else {
        activeSpell.setVel(null, -Component.SPEED);
      }
    }
    updateSpell(dt, world);
    updateMatches(world);
  }

  private void updateMatches(World world) {
    for (int y = 2; y < 18; y++) {
      for (int x = 2; x <= 5; x++) {
        if ((world.getGrid(x, y) != null) &&
            world.getGrid(x, y) == (world.getGrid(x + 1, y)) &&
            world.getGrid(x, y) == (world.getGrid(x + 2, y)) &&
            world.getGrid(x, y) == (world.getGrid(x + 3, y))) {
          for (int j = x; j < x + 4; j++) {
            toDestroy.set(j, y, true);
            
          }
        }
      }
    }
    
    for (int y = 2; y <= 14; y++) {
      for (int x = 2; x < 8; x++) {
        if ((world.getGrid(x, y) != null) &&
            world.getGrid(x, y) == (world.getGrid(x, y + 1)) &&
            world.getGrid(x, y) == (world.getGrid(x, y + 2)) &&
            world.getGrid(x, y) == (world.getGrid(x, y + 3))){
          for (int j = y; j < y + 4; j++) {
            toDestroy.set(x, j, true);
          }
        }
      }
    }
    
    for (Spell spell : world.getSetSpells()) {

      float x, y;
      if ((spell != null) && 
          (spell.component1 != null) &&
          toDestroy.get(spell.component1.pos.x, spell.component1.pos.y)) {
        x = spell.component1.pos.x;
        y = spell.component1.pos.y;
        spell.component1 = null;
        world.putGrid(x, y, null);
        toDestroy.set(x, y, false);
      }
      if ((spell != null) && 
          (spell.component2 != null) &&
          toDestroy.get(spell.component2.pos.x, spell.component2.pos.y)){
        x = spell.component2.pos.x;
        y = spell.component2.pos.y;
        spell.component2 = null;
        world.putGrid(x, y, null);
        toDestroy.set(x, y, false);
      }
    
    
      if ((spell != null) &&
          (spell.component1 == null) && spell.component2 == null) {
        world.getSetSpells().removeValue(spell, true);
      }
    }
    
    for (Homunculus homunculi : world.getHomunculi()) {
      float x, y;
      if ((homunculi != null) && 
          toDestroy.get(homunculi.pos.x, homunculi.pos.y)) {

        x = homunculi.pos.x;
        y = homunculi.pos.y;
        world.getHomunculi().removeValue(homunculi, true);
        world.putGrid(x, y, null);
        toDestroy.set(x, y, false);
      }
    }
    
    world.getHomunculi().removeValue(null, true);
    world.getSetSpells().removeValue(null, true);
  }
  
  private void updateSpell(float dt, World world) {
    if (activeSpell != null) {
      boolean canMove = true;
      
      // check if rotating right and can rotate
      if (keys.get(Keys.ROTR) && activeSpell.getRotateTime() <= 0) {
        if (activeSpell.isFlat() && activeSpell.topComponent().pos.y < 18) {
          if (world.getGrid(activeSpell.leftComponent().pos.x, activeSpell.leftComponent().pos.y + 1f) == null) {
            Component left = activeSpell.leftComponent();
            Component right = activeSpell.rightComponent();
            right.pos.x -= 1f;
            left.pos.y  += 1f;
            activeSpell.setRotateTime(0.25f + dt);
          }
        } else if (activeSpell.isStanding() && 
            activeSpell.topComponent().pos.y < 18 && 
            activeSpell.rightComponent().pos.x < 9) {
            if (world.getGrid(activeSpell.bottomComponent().pos.x + 1f, activeSpell.bottomComponent().pos.y)== null) {
              Component top = activeSpell.topComponent();
              top.pos.x += 1f;
              top.pos.y -= 1f;
              activeSpell.setRotateTime(0.25f + dt);
            }
        }
      }
      
      // check if rotating left and can rotate
      if (keys.get(Keys.ROTL) && activeSpell.getRotateTime() <= 0) {
        if (activeSpell.isFlat() && activeSpell.topComponent().pos.y < 18) {
          if (world.getGrid(activeSpell.leftComponent().pos.x, activeSpell.leftComponent().pos.y + 1f) == null) {
            Component right = activeSpell.rightComponent();
            right.pos.y += 1f;
            right.pos.x -= 1f;
            activeSpell.setRotateTime(0.25f + dt);
          }
        } else if (activeSpell.isStanding() && 
            activeSpell.topComponent().pos.y < 18 && 
            activeSpell.leftComponent().pos.x < 9) {
            if (world.getGrid(activeSpell.bottomComponent().pos.x + 1f, activeSpell.bottomComponent().pos.y)== null) {
              Component top = activeSpell.topComponent();
              Component bottom = activeSpell.bottomComponent();
              top.pos.y -= 1f;
              bottom.pos.x += 1f;
              activeSpell.setRotateTime(0.25f + dt);
            }
        }
      }
      
      
      // check if we can move left
      if (keys.get(Keys.LEFT) && 
          activeSpell.getPauseTime() <= 0f &&
          activeSpell.leftComponent().pos.x > 2 &&
          activeSpell.topComponent().pos.y < 18) {
        
        if ((world.getGrid(activeSpell.topComponent().pos.x - 1f, activeSpell.topComponent().pos.y) != null) ||
            (world.getGrid(activeSpell.bottomComponent().pos.x - 1f, activeSpell.bottomComponent().pos.y) != null) ||
            (world.getGrid(activeSpell.leftComponent().pos.x - 1f, activeSpell.leftComponent().pos.y)!= null)) {
          canMove = false;
        }
            
        if (canMove) {
          activeSpell.setPauseTime(0.25f + dt);
          activeSpell.setVel(-Component.SPEED, null);
        }
        canMove = true;
      } 
      
      // check if we can move right
      if (keys.get(Keys.RIGHT) && 
          activeSpell.getPauseTime() <= 0f &&
          activeSpell.rightComponent().pos.x < 9 &&
          activeSpell.topComponent().pos.y < 18) {
        
        if ((world.getGrid(activeSpell.topComponent().pos.x + 1f, activeSpell.topComponent().pos.y) != null) ||
            (world.getGrid(activeSpell.bottomComponent().pos.x + 1f, activeSpell.bottomComponent().pos.y) != null) ||
            (world.getGrid(activeSpell.rightComponent().pos.x + 1f, activeSpell.rightComponent().pos.y) != null)) {
          canMove = false;
        }
        
        if (canMove) {
          activeSpell.setPauseTime(0.25f + dt);
          activeSpell.setVel(Component.SPEED, null);
        }
        canMove = true;
      }
      
      // nullify movement if the player is holding down both
      if (keys.get(Keys.LEFT) && keys.get(Keys.RIGHT)) {
        activeSpell.setVel(0f, null);
      }
      
      // check if we can move down
      if (canMove && activeSpell.bottomComponent().pos.y <= 2f) {
        canMove = false;
      } else {
        if ((world.getGrid(activeSpell.leftComponent().pos.x,  activeSpell.leftComponent().pos.y -1f) != null) ||
            (world.getGrid(activeSpell.rightComponent().pos.x, activeSpell.rightComponent().pos.y - 1f) != null) ||
            (world.getGrid(activeSpell.bottomComponent().pos.x, activeSpell.bottomComponent().pos.y - 1f) != null)) {
          canMove = false;
        }
      }
      
      if (canMove) {
        activeSpell.update(dt);
        activeSpell.setVel(0f, 0f);
      } else {
        activeSpell.setVel(0f, 0f);
        world.restSpell();
        activeSpell = world.getActiveSpell();
      }
      
    }
  }
  
}
