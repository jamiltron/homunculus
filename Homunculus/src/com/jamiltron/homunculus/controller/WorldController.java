package com.jamiltron.homunculus.controller;

import com.jamiltron.homunculus.Assets;
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
  JArray<Boolean> maybeDrop;
  
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
        toDestroy.set(x, y, false);
      }
    }
  }

  
  public void update(float dt) {
    if (!updateDrops(dt)) {
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
    updateSpell(dt);
    updateMatches();
    }
  }
  
  private boolean updateDrops(float dt) {
    boolean check1;
    boolean check2;
    boolean keepChecking = false;
    
    for (Spell spell : world.setSpells) {
      check1 = true;
      check2 = true;
      if (spell.component1 == null) check1 = false;
      if (spell.component2 == null) check2 = false;

      if (check1 && check2) {
        
        if (spell.component1.pos.y > 2 && 
            spell.component2.pos.y > 2 &&
            world.getGrid(spell.component1.pos.x, spell.bottom() - 1f) == null &&
            world.getGrid(spell.component2.pos.x, spell.bottom() - 1f) == null) {
          world.putGrid(spell.component1.pos.x, spell.component1.pos.y, null);
          world.putGrid(spell.component2.pos.x, spell.component2.pos.y, null);
          spell.setVel(null, -1f);
          keepChecking = true;
        } else {
          world.putGrid(spell.component1.pos.x, spell.component1.pos.y, spell.component1.color);
          world.putGrid(spell.component2.pos.x, spell.component2.pos.y, spell.component2.color);
        }
      } else if (check1) {
        if ((spell.component1.pos.y != 2) && 
            world.getGrid(spell.component1.pos.x, spell.component1.pos.y - 1f) == null) {
          world.putGrid(spell.component1.pos.x, spell.component1.pos.y, null);
          spell.setVel(null, -1f);
          keepChecking = true;
        } else {
          world.putGrid(spell.component1.pos.x, spell.component1.pos.y, spell.component1.color);
        }
        
      } else if (check2) {
        if ((spell.component2.pos.y != 2) && 
            world.getGrid(spell.component2.pos.x, spell.component2.pos.y - 1f) == null) {
          world.putGrid(spell.component2.pos.x, spell.component2.pos.y, null);
          spell.setVel(null, -1f);
          keepChecking = true;
        } else {
          world.putGrid(spell.component2.pos.x, spell.component2.pos.y, spell.component2.color);
        }
      } else {
        System.out.println("Error condition in updateDrops.");
      }
      spell.update(dt);
      spell.setVel(null, 0f);
    }
    
    return keepChecking;
  }
  
  private void updateMatches() {
    boolean playSound = false;
    
    // check for rows to destroy
    for (int y = 2; y <= 18; y++) {
      for (int x = 2; x <= 6; x++) {
        if ((world.getGrid(x, y) != null) &&
            world.getGrid(x, y) == (world.getGrid(x + 1, y)) &&
            world.getGrid(x, y) == (world.getGrid(x + 2, y)) &&
            world.getGrid(x, y) == (world.getGrid(x + 3, y))) {
          for (int j = x; j <= x + 3; j++) {
            toDestroy.set(j, y, true);
            playSound = true;
            
          }
        }
      }
    }
    
    // check for columns to destroy
    for (int y = 2; y <= 15; y++) {
      for (int x = 2; x <= 9; x++) {
        if ((world.getGrid(x, y) != null) &&
            world.getGrid(x, y) == (world.getGrid(x, y + 1)) &&
            world.getGrid(x, y) == (world.getGrid(x, y + 2)) &&
            world.getGrid(x, y) == (world.getGrid(x, y + 3))){
          for (int j = y; j <= y + 3; j++) {
            toDestroy.set(x, j, true);
            playSound = true;
          }
        }
      }
    }
    
    // go through each spell, and destroy it if it matches an entry in toDestroy
    for (Spell spell : world.setSpells) {
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
          toDestroy.get(spell.component2.pos.x, spell.component2.pos.y)) {
        x = spell.component2.pos.x;
        y = spell.component2.pos.y;
        spell.component2 = null;
        world.putGrid(x, y, null);
        toDestroy.set(x, y, false);
      }
    
      if ((spell != null) &&
          (spell.component1 == null) && spell.component2 == null) {
        world.deadSpells.add(spell);
      }
    }
    
    // go through each homunculi and destroy it need be
    for (Homunculus homunculi : world.homunculi) {
      float x, y;
      if ((homunculi != null) && 
          toDestroy.get(homunculi.pos.x, homunculi.pos.y)) {

        x = homunculi.pos.x;
        y = homunculi.pos.y;
        world.deadHomunculi.add(homunculi);
        world.putGrid(x, y, null);
        toDestroy.set(x, y, false);
      }
    }
    
    world.cleanUp();

    if (playSound) Assets.playSound(Assets.match);
  }
  
  private void updateSpell(float dt) {
    if (activeSpell != null) {
      boolean canMove = true;
      boolean playRotate = false;
      
      // check if rotating right and can rotate
      if (keys.get(Keys.ROTR) && activeSpell.getRotateTime() <= 0) {
        if (activeSpell.isFlat() && activeSpell.topComponent().pos.y < 17) {
          if (world.getGrid(activeSpell.leftComponent().pos.x, 
              activeSpell.leftComponent().pos.y + 1f) == null) {
            Component left = activeSpell.leftComponent();
            Component right = activeSpell.rightComponent();
            right.pos.x -= 1f;
            left.pos.y  += 1f;
            activeSpell.setRotateTime(0.25f + dt);
            playRotate = true;
          }
        } else if (activeSpell.isStanding() && 
            activeSpell.topComponent().pos.y < 18 && 
            activeSpell.rightComponent().pos.x < 9) {
            if (world.getGrid(activeSpell.bottomComponent().pos.x + 1f, 
                activeSpell.bottomComponent().pos.y)== null) {
              Component top = activeSpell.topComponent();
              top.pos.x += 1f;
              top.pos.y -= 1f;
              activeSpell.setRotateTime(0.25f + dt);
              playRotate = true;
              
            }
        }
      }
      
      // check if rotating left and can rotate
      if (keys.get(Keys.ROTL) && activeSpell.getRotateTime() <= 0) {
        if (activeSpell.isFlat() && activeSpell.topComponent().pos.y < 17) {
          if (world.getGrid(activeSpell.leftComponent().pos.x, 
              activeSpell.leftComponent().pos.y + 1f) == null) {
            Component right = activeSpell.rightComponent();
            right.pos.y += 1f;
            right.pos.x -= 1f;
            activeSpell.setRotateTime(0.25f + dt);
            playRotate = true;
          }
        } else if (activeSpell.isStanding() && 
            activeSpell.topComponent().pos.y < 18 && 
            activeSpell.leftComponent().pos.x < 9) {
            if (world.getGrid(activeSpell.bottomComponent().pos.x + 1f, 
                activeSpell.bottomComponent().pos.y)== null) {
              Component top = activeSpell.topComponent();
              Component bottom = activeSpell.bottomComponent();
              top.pos.y -= 1f;
              bottom.pos.x += 1f;
              activeSpell.setRotateTime(0.25f + dt);
              playRotate = true;
            }
        }
      }
      
      // check if we can move left
      if (keys.get(Keys.LEFT) && 
          activeSpell.getPauseTime() <= 0f &&
          activeSpell.leftComponent().pos.x > 2 &&
          activeSpell.topComponent().pos.y < 18) {
        
        if ((world.getGrid(activeSpell.topComponent().pos.x - 1f, 
                activeSpell.topComponent().pos.y) != null) ||
            (world.getGrid(activeSpell.bottomComponent().pos.x - 1f, 
                activeSpell.bottomComponent().pos.y) != null) ||
            (world.getGrid(activeSpell.leftComponent().pos.x - 1f, 
                activeSpell.leftComponent().pos.y)!= null)) {
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
        
        if ((world.getGrid(activeSpell.topComponent().pos.x + 1f, 
                activeSpell.topComponent().pos.y) != null) ||
            (world.getGrid(activeSpell.bottomComponent().pos.x + 1f,
                activeSpell.bottomComponent().pos.y) != null) ||
            (world.getGrid(activeSpell.rightComponent().pos.x + 1f, 
                activeSpell.rightComponent().pos.y) != null)) {
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
        if ((world.getGrid(activeSpell.leftComponent().pos.x, 
                activeSpell.leftComponent().pos.y -1f) != null) ||
            (world.getGrid(activeSpell.rightComponent().pos.x, 
                activeSpell.rightComponent().pos.y - 1f) != null) ||
            (world.getGrid(activeSpell.bottomComponent().pos.x, 
                activeSpell.bottomComponent().pos.y - 1f) != null)) {
          canMove = false;
        }
      }
      
      
      if (canMove) {
        activeSpell.update(dt);
        // after having moved, see if we are occupying some other entity
        if ((world.getGrid(activeSpell.component1.pos.x, activeSpell.component1.pos.y) != null) ||
            (world.getGrid(activeSpell.component2.pos.x, activeSpell.component2.pos.y) != null)) {
          activeSpell.setVel(activeSpell.component1.vel.x * -1f, 0f);
          activeSpell.update(0f);
        }
        activeSpell.setVel(0f, 0f);
      } else {
        Assets.playSound(Assets.drop);
        activeSpell.setVel(0f, 0f);
        world.restSpell();
        activeSpell = world.getActiveSpell();
      }
      
      if (playRotate) {
        Assets.playSound(Assets.rotate);
      }
    }
  }
  
}
