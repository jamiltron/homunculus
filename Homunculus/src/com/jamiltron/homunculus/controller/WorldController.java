package com.jamiltron.homunculus.controller;

import java.util.HashMap;
import java.util.Map;

import com.jamiltron.homunculus.HomunculusGame;
import com.jamiltron.homunculus.Assets;
import com.jamiltron.homunculus.Settings;
import com.jamiltron.homunculus.model.Component;
import com.jamiltron.homunculus.model.Homunculus;
import com.jamiltron.homunculus.model.Spell;
import com.jamiltron.homunculus.model.World;
import com.jamiltron.homunculus.screen.MainMenu;
import com.jamiltron.homunculus.util.JArray;

public class WorldController {
  enum Keys {
    LEFT, RIGHT, ROTR, ROTL, DROP, PAUSE, ANY;
  }

  public boolean nextLevel;
  private final HomunculusGame game;
  private World world;
  private Spell activeSpell;
  private final Settings settings;
  private boolean unpausable;
  private boolean destroying;
  float currentTime;
  float dropTime;
  float fastTime;
  public float normalTime;
  float pauseTime;
  public static final float rotateDelay = 0.25f;
  int drops;

  JArray<Boolean> toDestroy;

  static Map<Keys, Boolean> keys = new HashMap<WorldController.Keys, Boolean>();
  static {
    keys.put(Keys.LEFT, false);
    keys.put(Keys.RIGHT, false);
    keys.put(Keys.ROTR, false);
    keys.put(Keys.ROTL, false);
    keys.put(Keys.DROP, false);
    keys.put(Keys.PAUSE, false);
    keys.put(Keys.ANY, false);
  };

  public void resetKeys() {
    keys.put(Keys.LEFT, false);
    keys.put(Keys.RIGHT, false);
    keys.put(Keys.ROTR, false);
    keys.put(Keys.ROTL, false);
    keys.put(Keys.DROP, false);
    keys.put(Keys.PAUSE, false);
    keys.put(Keys.ANY, false);
  }

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

  public void pausePress() {
    keys.put(Keys.PAUSE, true);
  }

  public void anyPress() {
    keys.put(Keys.ANY, true);
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

  public void pauseRelease() {
    keys.put(Keys.PAUSE, false);
  }

  public void anyRelease() {
    keys.put(Keys.ANY, false);
  }

  public void resetController(World w) {
    world = w;
    nextLevel = false;
    pauseTime = 0.15f;
    drops = 0;
    activeSpell = world.getActiveSpell();
    currentTime = 0f;

    // grab the game speed from the settings
    if (settings.getSpeed().ordinal() == 0) {
      normalTime = 0.75f;
      fastTime = 0.1f;
    } else if (settings.getSpeed().ordinal() == 1) {
      normalTime = 0.5f;
      fastTime = 0.1f;
    } else if (settings.getSpeed().ordinal() == 2) {
      normalTime = 0.25f;
      fastTime = 0.08f;
    } else {
      throw new IllegalArgumentException("Invalid speed setting");
    }

    dropTime = normalTime;
    for (float y = World.Y_MIN; y <= World.Y_MAX; y++) {
      for (float x = World.X_MIN; x <= World.X_MAX; x++) {
        toDestroy.set(x, y, false);
      }
    }
  }

  public WorldController(World w, HomunculusGame g) {
    nextLevel = false;
    game = g;
    unpausable = true;
    settings = g.settings;
    pauseTime = 0.15f;
    drops = 0;
    world = w;
    activeSpell = w.getActiveSpell();
    currentTime = 0f;
    destroying = false;

    // grab the game speed from the settings
    if (settings.getSpeed().ordinal() == 0) {
      normalTime = 0.75f;
      fastTime = 0.1f;
    } else if (settings.getSpeed().ordinal() == 1) {
      normalTime = 0.5f;
      fastTime = 0.1f;
    } else if (settings.getSpeed().ordinal() == 2) {
      normalTime = 0.25f;
      fastTime = 0.08f;
    } else {
      throw new IllegalArgumentException("Invalid speed setting");
    }

    dropTime = normalTime;
    toDestroy = new JArray<Boolean>(World.COLS, World.ROWS);
    for (float y = World.Y_MIN; y <= World.Y_MAX; y++) {
      for (float x = World.X_MIN; x <= World.X_MAX; x++) {
        toDestroy.set(x, y, false);
      }
    }
  }

  public void updateOver() {
    if (!world.lost && !world.won
        && (world.getGrid(World.ENTRY_X, World.ENTRY_Y) != null || 
        world.getGrid(World.ENTRY_X + 1, World.ENTRY_Y) != null)) {
      world.lost = true;
      resetKeys();
    }

    if (world.lost && keys.get(Keys.ANY)) {
      game.setScreen(new MainMenu(game));
    }
  }
  public void updatePaused() {
    // if the game is not paused, pause it
    if (unpausable && !world.paused && keys.get(Keys.PAUSE)) {
      world.paused = true;
      unpausable = false;
      // if the game is paused, unpause it
    } else if (unpausable && world.paused && keys.get(Keys.ANY)) {
      world.paused = false;
      unpausable = false;
      resetKeys();
    } else if (keys.get(Keys.PAUSE) == false) {
      unpausable = true;
    }
  }

  public void updateWon() {
    if (!world.won && world.homunculi.size == 0) {
      world.won = true;
      resetKeys();
    }

    if (world.won && keys.get(Keys.ANY)) {
      activeSpell = null;
      nextLevel = true;
    }
  }

  public void update(float dt) {
    updateOver();
    if (!world.lost) {
      updateWon();
      if (!world.won) {
        updatePaused();
        if (!world.paused) {
          world.update(dt);
          if (destroying) {
            destroying = false;
            
            for (Spell spell : world.setSpells) {
              if (!spell.component1.isDead) {
                if (spell.component1.isDying && spell.component1.stateTime > Component.DYING_TIME) {
                  world.putGrid(spell.component1.pos.x, spell.component1.pos.y, null);
                  spell.component1.isDead = true;
                } else if (spell.component1.isDying){
                  destroying = true;
                }
              }
              
              if (!spell.component2.isDead) {
                if (spell.component2.isDying && spell.component2.stateTime > Component.DYING_TIME) {
                  world.putGrid(spell.component2.pos.x, spell.component2.pos.y, null);
                  spell.component2.isDead = true;
                } else if (spell.component2.isDying) {
                  destroying = true;
                }
              }
              
              spell.update(dt);
              
              if ((spell != null) && (spell.component1.isDead)
                  && spell.component2.isDead) {
                world.deadSpells.add(spell);
              }
            }
            
            for (Homunculus hom : world.homunculi) {
              if (hom != null) {
                if (hom.isDying && hom.stateTime > Homunculus.DYING_TIME) {
                  world.deadHomunculi.add(hom);
                  world.putGrid(hom.pos.x, hom.pos.y, null);
                } else if (hom.isDying) {
                  destroying = true;
                }
              }
            }
            scoreUp();
            world.cleanUp();
          } else {
            
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
        }
      }
    }
  }
  
  private boolean updateDrops(float dt) {
    boolean check1;
    boolean check2;
    boolean keepChecking = false;

    for (Spell spell : world.setSpells) {
      check1 = true;
      check2 = true;
      if (spell.component1.isDead) {
        check1 = false;
      }
      if (spell.component2.isDead) {
        check2 = false;
      }

      if (check1 && check2) {
        if (spell.component1.pos.y > World.Y_MIN
            && spell.component2.pos.y > World.Y_MIN
            && world.getGrid(spell.component1.pos.x, spell.bottom() - 1f) == null
            && world.getGrid(spell.component2.pos.x, spell.bottom() - 1f) == null) {
          world.putGrid(spell.component1.pos.x, spell.component1.pos.y, null);
          world.putGrid(spell.component2.pos.x, spell.component2.pos.y, null);
          spell.setVel(null, -1f);
          keepChecking = true;
        } else {
          world.putGrid(spell.component1.pos.x, spell.component1.pos.y,
              spell.component1.color);
          world.putGrid(spell.component2.pos.x, spell.component2.pos.y,
              spell.component2.color);
        }
      } else if (check1) {
        if ((spell.component1.pos.y != World.Y_MIN)
            && world.getGrid(spell.component1.pos.x,
                spell.component1.pos.y - 1f) == null) {
          world.putGrid(spell.component1.pos.x, spell.component1.pos.y, null);
          spell.setVel(null, -1f);
          keepChecking = true;
        } else {
          world.putGrid(spell.component1.pos.x, spell.component1.pos.y,
              spell.component1.color);
        }

      } else if (check2) {
        if ((spell.component2.pos.y != World.Y_MIN)
            && world.getGrid(spell.component2.pos.x,
                spell.component2.pos.y - 1f) == null) {
          world.putGrid(spell.component2.pos.x, spell.component2.pos.y, null);
          spell.setVel(null, -1f);
          keepChecking = true;
        } else {
          world.putGrid(spell.component2.pos.x, spell.component2.pos.y,
              spell.component2.color);
        }
      } else {
        throw new Error("Error condition in updateDrops.");
      }
      spell.update(dt);
      spell.setVel(null, 0f);
    }

    return keepChecking;
  }

  private void updateMatches() {
    boolean playSound = false;

    // check for rows to destroy
    for (float y = World.Y_MIN; y <= World.Y_MAX; y++) {
      for (float x = World.X_MIN; x <= World.X_MAX - 3; x++) {
        if ((world.getGrid(x, y) != null)
            && world.getGrid(x, y) == (world.getGrid(x + 1, y))
            && world.getGrid(x, y) == (world.getGrid(x + 2, y))
            && world.getGrid(x, y) == (world.getGrid(x + 3, y))) {
          for (float j = x; j <= x + 3; j++) {
            destroying = true;
            toDestroy.set(j, y, true);
            playSound = true;
          }
        }
      }
    }

    // check for columns to destroy
    for (float y = World.Y_MIN; y <= World.Y_MAX - 3; y++) {
      for (float x = World.X_MIN; x <= World.X_MAX; x++) {
        if ((world.getGrid(x, y) != null)
            && world.getGrid(x, y) == (world.getGrid(x, y + 1))
            && world.getGrid(x, y) == (world.getGrid(x, y + 2))
            && world.getGrid(x, y) == (world.getGrid(x, y + 3))) {
          for (float j = y; j <= y + 3; j++) {
            destroying = true;
            toDestroy.set(x, j, true);
            playSound = true;
          }
        }
      }
    }

    // go through each spell, and destroy it if it matches an entry in toDestroy
    for (Spell spell : world.setSpells) {
      float x, y;
      if ((spell != null) && (!spell.component1.isDead)
          && toDestroy.get(spell.component1.pos.x, spell.component1.pos.y)) {
        x = spell.component1.pos.x;
        y = spell.component1.pos.y;
        spell.component1.isDying = true;
        spell.component1.stateTime = 0f;
        toDestroy.set(x, y, false);
      }
      if ((spell != null) && (!spell.component2.isDead)
          && toDestroy.get(spell.component2.pos.x, spell.component2.pos.y)) {
        x = spell.component2.pos.x;
        y = spell.component2.pos.y;
        spell.component2.isDying = true;
        spell.component2.stateTime = 0f;
        world.putGrid(x, y, null);
        toDestroy.set(x, y, false);
      }
    }

    // go through each homunculi and destroy it need be
    for (Homunculus homunculi : world.homunculi) {
      float x, y;
      if ((homunculi != null)
          && toDestroy.get(homunculi.pos.x, homunculi.pos.y)) {
        x = homunculi.pos.x;
        y = homunculi.pos.y;
        homunculi.isDying = true;
        homunculi.stateTime = 0f;
        toDestroy.set(x, y, false);
      }
    }

    scoreUp();

    if (playSound) playMatch();
      
  }
  
  private void playDrop() {
    if (game.settings.getSoundOn()) {
      Assets.playSound(Assets.drop);
    }
  }
  
  public void playMatch() {
    if (settings.getSoundOn()) {
      Assets.playSound(Assets.match);
    }
  }
  
  public void playRotate() {
    if (settings.getSoundOn()) {
      Assets.playSound(Assets.rotate);
    }
  }

  private void scoreUp() {
    if (world.deadHomunculi.size > 0) {
      world.score += settings.getScoreModifier()
          * Math.pow(2, world.deadHomunculi.size - 1);
    }
  }
  private void updateSpell(float dt) {
    if (activeSpell != null) {
      boolean canMove = true;
      boolean playRotate = false;

      // check if rotating right and can rotate
      if (keys.get(Keys.ROTR) && activeSpell.getRotateTime() <= 0) {
        if (activeSpell.isFlat() && ((activeSpell.topComponent().pos.y < World.Y_MAX - 1) ||
            activeSpell.topComponent().pos.y < World.Y_MAX && 
            (activeSpell.leftComponent().pos.x == World.ENTRY_X || 
            activeSpell.leftComponent().pos.x == World.ENTRY_X + 1))) {
          if (world.getGrid(activeSpell.leftComponent().pos.x,
              activeSpell.leftComponent().pos.y + 1f) == null) {
            Component left = activeSpell.leftComponent();
            Component right = activeSpell.rightComponent();
            right.pos.x -= 1f;
            left.pos.y += 1f;
            activeSpell.setRotateTime(rotateDelay + dt);
            playRotate = true;
          }
        } else if (activeSpell.isStanding()
            && activeSpell.topComponent().pos.y <= World.Y_MAX
            && activeSpell.rightComponent().pos.x < World.X_MAX - 1f) {
          if (world.getGrid(activeSpell.bottomComponent().pos.x + 1f,
              activeSpell.bottomComponent().pos.y) == null) {
            Component top = activeSpell.topComponent();
            top.pos.x += 1f;
            top.pos.y -= 1f;
            activeSpell.setRotateTime(rotateDelay + dt);
            playRotate = true;
          }
        } else if (activeSpell.isStanding()
            && activeSpell.topComponent().pos.y <= World.Y_MAX
            && activeSpell.rightComponent().pos.x < World.X_MAX) {
          if (world.getGrid(activeSpell.bottomComponent().pos.x - 1f,
              activeSpell.bottomComponent().pos.y) == null) {
            Component top = activeSpell.topComponent();
            Component bot = activeSpell.bottomComponent();
            top.pos.y -= 1f;
            bot.pos.x -= 1f;
            activeSpell.setRotateTime(rotateDelay + dt);
            playRotate = true;
            
          }
        }
      }

      // check if rotating left and can rotate
      if (keys.get(Keys.ROTL) && activeSpell.getRotateTime() <= 0) {
        if (activeSpell.isFlat() && ((activeSpell.topComponent().pos.y < World.Y_MAX - 1) ||
            activeSpell.topComponent().pos.y < World.Y_MAX && 
            (activeSpell.leftComponent().pos.x == World.ENTRY_X || 
            activeSpell.leftComponent().pos.x == World.ENTRY_X + 1))) {
          if (world.getGrid(activeSpell.leftComponent().pos.x,
              activeSpell.leftComponent().pos.y + 1f) == null) {
            Component right = activeSpell.rightComponent();
            right.pos.y += 1f;
            right.pos.x -= 1f;
            activeSpell.setRotateTime(rotateDelay + dt);
            playRotate = true;
          }
        } else if (activeSpell.isStanding()
            && activeSpell.topComponent().pos.y <= World.Y_MAX
            && activeSpell.leftComponent().pos.x < World.X_MAX - 1f) {
          if (world.getGrid(activeSpell.bottomComponent().pos.x + 1f,
              activeSpell.bottomComponent().pos.y) == null) {
            Component top = activeSpell.topComponent();
            Component bottom = activeSpell.bottomComponent();
            top.pos.y -= 1f;
            bottom.pos.x += 1f;
            activeSpell.setRotateTime(rotateDelay + dt);
            playRotate = true;
          }
        }
      }

      // check if we can move left
      if (keys.get(Keys.LEFT) && activeSpell.getPauseTime() <= 0f
          && ((activeSpell.leftComponent().pos.x >= World.X_MIN
          && activeSpell.topComponent().pos.y < World.Y_MAX) ||
          (activeSpell.leftComponent().pos.x == World.ENTRY_X + 1f 
          && activeSpell.topComponent().pos.y == World.Y_MAX))) {

        if ((world.getGrid(activeSpell.topComponent().pos.x - 1f,
            activeSpell.topComponent().pos.y) != null)
            || (world.getGrid(activeSpell.bottomComponent().pos.x - 1f,
                activeSpell.bottomComponent().pos.y) != null)
            || (world.getGrid(activeSpell.leftComponent().pos.x - 1f,
                activeSpell.leftComponent().pos.y) != null)) {
          canMove = false;
        }

        if (canMove) {
          activeSpell.setPauseTime(pauseTime + dt);
          activeSpell.setVel(-Component.SPEED, null);
        }
        canMove = true;
      }

      // check if we can move right
      if (keys.get(Keys.RIGHT) && activeSpell.getPauseTime() <= 0f
          && ((activeSpell.rightComponent().pos.x < World.X_MAX - 1
          && activeSpell.topComponent().pos.y < World.Y_MAX) ||
          (activeSpell.leftComponent().pos.x == World.ENTRY_X 
          && activeSpell.topComponent().pos.y == World.Y_MAX
          && activeSpell.rightComponent().pos.x == World.ENTRY_X))) {

        if ((world.getGrid(activeSpell.topComponent().pos.x + 1f,
            activeSpell.topComponent().pos.y) != null)
            || (world.getGrid(activeSpell.bottomComponent().pos.x + 1f,
                activeSpell.bottomComponent().pos.y) != null)
            || (world.getGrid(activeSpell.rightComponent().pos.x + 1f,
                activeSpell.rightComponent().pos.y) != null)) {
          canMove = false;
        }

        if (canMove) {
          activeSpell.setPauseTime(pauseTime + dt);
          activeSpell.setVel(Component.SPEED, null);
        }
        canMove = true;
      }

      // nullify movement if the player is holding down both
      if (keys.get(Keys.LEFT) && keys.get(Keys.RIGHT)) {
        activeSpell.setVel(0f, null);
      }

      // check if we can move down
      if (activeSpell.bottomComponent().pos.y == World.Y_MIN && activeSpell.getVel().y == -Component.SPEED) {
        canMove = false;
      } else if (activeSpell.bottomComponent().pos.y == World.Y_MIN) {
    	  // do nothing
      } else {
        if (((world.getGrid(activeSpell.leftComponent().pos.x,
            activeSpell.leftComponent().pos.y - 1f) != null)
            || (world.getGrid(activeSpell.rightComponent().pos.x,
                activeSpell.rightComponent().pos.y - 1f) != null) || (world
            .getGrid(activeSpell.bottomComponent().pos.x,
                activeSpell.bottomComponent().pos.y - 1f) != null))
            && (activeSpell.getVel().y == -Component.SPEED)) {
          canMove = false;
        }
      }
        
      if (canMove) {
        activeSpell.update(dt);
        // after having moved, see if we are occupying some other entity
        if ((world.getGrid(activeSpell.component1.pos.x,
            activeSpell.component1.pos.y) != null)
            || (world.getGrid(activeSpell.component2.pos.x,
                activeSpell.component2.pos.y) != null)) {
          activeSpell.setVel(activeSpell.component1.vel.x * -1f, 0f);
          activeSpell.update(0f);
        }
        activeSpell.setVel(0f, 0f);
      } else if (activeSpell.getVel().y == -Component.SPEED) {
        drops += 1;
        if (drops % 10 == 0 && drops != 0) {
          normalTime -= 0.01f;
          fastTime -= 0.01f;
          if (normalTime < 0.01f)
            normalTime = 0.01f;
          if (fastTime < 0.01f)
            fastTime = 0.01f;
        }
        
        playDrop();
        activeSpell.setVel(0f, 0f);
        world.restSpell();
        activeSpell = world.getActiveSpell();
      }

      if (playRotate) {
        playRotate();
      }
    }
  }

}