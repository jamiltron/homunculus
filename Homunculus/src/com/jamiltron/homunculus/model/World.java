package com.jamiltron.homunculus.model;

import java.util.Random;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.jamiltron.homunculus.Assets;
import com.jamiltron.homunculus.util.JArray;

public class World {
  public static final float BOX_X = 13.70f;
  public static final float BOX_Y = 20.5f;
  public static final float BOTTOM = 5.75f;
  public static final float ENTRY_X = 6f;
  public static final float ENTRY_Y = 20.75f;
  public static final float COLS = 8;
  public static final float ROWS = 17;
  public static final float ROW_RAND_MIN = 13;
  public static final float X_MIN = 3f;
  public static final float X_MAX = X_MIN + COLS - 1;
  public static final float Y_MIN = 4.75f;
  public static final float Y_MAX = Y_MIN + ROWS - 1;
  public int highScore;
  public boolean scoreBroken;
  
  private final Color[] colors = Color.values();
  public int numHomunculi;
  public Array<Homunculus> homunculi = new Array<Homunculus>();
  public Array<Spell> setSpells = new Array<Spell>();
  public Array<Homunculus> deadHomunculi = new Array<Homunculus>();
  public Array<Spell> deadSpells = new Array<Spell>();
  public JArray<Color> colorGrid = new JArray<Color>((int)COLS, (int)ROWS);
  private Pool<Component> componentPool;
  private Pool<Spell> spellPool;
  private Pool<Homunculus> homunculusPool;
  private Homunculus tmpHomunculus;
  public boolean paused;
  public boolean won;
  public boolean lost;
  public boolean switchingSpells;
  public boolean activeEntering;
  public float activeEnteringTime;
  public float switchingTime;
  public int score;
  private Spell activeSpell = null;
  private Spell nextSpell = null;
  private final Random random = new Random();

  public void update(float dt) {
    for (Homunculus h : homunculi) {
      if (h != null) {
        h.stateTime += dt;
      }
    }
    
    if (activeEntering) {
      activeEnteringTime -= dt;
      if (activeEnteringTime <= 0f) {
        activeEntering = false;
        activeEnteringTime = 0f;
      }
    }
    
    if (!switchingSpells) {
      if (nextSpell != null) {
        nextSpell.updateAnimation(dt);
      }
    } else {
      for (Spell spell : setSpells) {
        spell.updateAnimation(dt);
      }
      switchingTime += dt;
      if (switchingTime >= Assets.wizardTime * Assets.wizardFrames) {
        switchingTime = 0;
        switchingSpells = false;
        activeEntering = true;
        activeEnteringTime = 0.3f;
        spellPool.free(nextSpell);
        nextSpell = generateSpell();
      }
    }
  }
  
  public void updateHighScore() {
    if (score > highScore) {
      highScore = score;
    }
  }
  
  public void setSwitching() {
    restSpell();
    switchingSpells = true;
    activeSpell = copySpell(nextSpell);
    activateSpell(activeSpell);
  }
  
  public Color getGrid(float x, float y) {
    return colorGrid.get(x, y);
  }

  public Color getGrid(Component component) {
    return colorGrid.get(component.pos.x, component.pos.y);
  }

  public void putGrid(float x, float y, Color c) {
    colorGrid.set(x, y, c);
  }

  public World(int numHomunculi) {
    this(numHomunculi, 0);
  }
  
  public World() {
    this(3, 0);
  }
  
  public void setProps(int numHomunculi, int score) {
    for (Homunculus h : deadHomunculi) {
      homunculusPool.free(h);
    }
    for (Homunculus h : homunculi) {
      homunculusPool.free(h);
    }
    for (Spell s : setSpells) {
      componentPool.free(s.component1);
      componentPool.free(s.component2);
      spellPool.free(s);
    }
    deadHomunculi.clear();
    homunculi.clear();
    setSpells.clear();
    activeSpell = null;
    nextSpell = null;
    colorGrid.clear();
    createWorld(numHomunculi);
    this.score = score;
  }

  public World(int numHomunculi, int s) {
    componentPool = new Pool<Component>() {
      @Override
      protected Component newObject() {
        return new Component();
      }
    };
    spellPool = new Pool<Spell>() {
      @Override
      protected Spell newObject() {
        return new Spell();
      }
    };
    homunculusPool = new Pool<Homunculus>() {
      @Override
      protected Homunculus newObject() {
        return new Homunculus();
      }
    };
    createWorld(numHomunculi);
    score = s;
  }

  public Spell getNextSpell() {
    return nextSpell;
  }

  public Spell getActiveSpell() {
    return activeSpell;
  }

  public void cleanUp() {
    homunculi.removeAll(deadHomunculi, true);
    setSpells.removeAll(deadSpells, true);
    for (Spell spell : deadSpells) {
      componentPool.free(spell.component1);
      componentPool.free(spell.component2);
      spellPool.free(spell);
    }
    for (Homunculus homunculus : deadHomunculi) {
      homunculusPool.free(homunculus);
    }
    deadHomunculi.clear();
    deadSpells.clear();
  }

  public Spell generateSpell() {
    int i = colors.length;
    Color color1 = colors[random.nextInt(i)];
    Color color2 = colors[random.nextInt(i)];
    Component component1 = componentPool.obtain();
    Component component2 = componentPool.obtain();
    component1.setProps(BOX_X, BOX_Y, color1);
    component2.setProps(BOX_X + 1, BOX_Y, color2);
    
    Spell spell = spellPool.obtain();
    spell.setProps(component1, component2);
    return spell;
  }
  
  public Spell copySpell(Spell spell) {
    Component component1 = componentPool.obtain();
    Component component2 = componentPool.obtain();
    component1.setProps(spell.component1.pos.x, spell.component1.pos.y, spell.component1.color);
    component2.setProps(spell.component2.pos.x, spell.component2.pos.y, spell.component2.color);
    
    Spell newSpell = spellPool.obtain();
    newSpell.setProps(component1, component2);
    return newSpell;
  }

  public void restSpell() {
    if (activeSpell != null) {
      putGrid(activeSpell.component1.pos.x, activeSpell.component1.pos.y,
          activeSpell.component1.color);
      putGrid(activeSpell.component2.pos.x, activeSpell.component2.pos.y,
          activeSpell.component2.color);
      setSpells.add(activeSpell);
    }
  }

  public void activateSpell(Spell spell) {
    spell.component1.pos.x = ENTRY_X;
    spell.component1.pos.y = ENTRY_Y;

    spell.component2.pos.x = ENTRY_X + 1;
    spell.component2.pos.y = ENTRY_Y;
  }
  
  private void createWorld(int numHomunculi) {
    switchingTime = 0;
    activeEntering = false;
    activeEnteringTime = 0;
    switchingSpells = false;
    this.numHomunculi = numHomunculi;
    paused = false;
    won = false;
    lost = false;
    Boolean inserted;
    Color color;
    float x, y;
    //colorGrid = new JArray<Color>((int)COLS, (int)ROWS);

    // create random homunculi
    for (int i = 0; i < numHomunculi; i++) {
      inserted = false;
      do {
        x = (random.nextInt((int)COLS) + X_MIN);
        y = (random.nextInt((int)ROW_RAND_MIN) + Y_MIN);
        int j = 0;

        if (getGrid(x, y) == null) {
          tmpHomunculus = homunculusPool.obtain();
          do {
            inserted = true;
            color = colors[(i + j) % colors.length];
            tmpHomunculus.setProps(x, y, color);
            putGrid(x, y, color);
            if (insertCausesMatches()) {
              j += 1;
              inserted = false;
            }
          } while (j < 3 && !inserted);
        }
      } while (!inserted);
      homunculi.add(tmpHomunculus);
    }
    nextSpell = generateSpell();
  }
  
  private boolean insertCausesMatches() {
    // check for rows to destroy
    for (float y = World.Y_MIN; y <= World.Y_MAX; y++) {
      for (float x = World.X_MIN; x <= World.X_MAX - 3; x++) {
        if ((getGrid(x, y) != null)
            && getGrid(x, y) == (getGrid(x + 1, y))
            && getGrid(x, y) == (getGrid(x + 2, y))
            && getGrid(x, y) == (getGrid(x + 3, y))) {
          return true;
        }
      }
    }

    // check for columns to destroy
    for (float y = World.Y_MIN; y <= World.Y_MAX - 3; y++) {
      for (float x = World.X_MIN; x <= World.X_MAX; x++) {
        if ((getGrid(x, y) != null)
            && getGrid(x, y) == (getGrid(x, y + 1))
            && getGrid(x, y) == (getGrid(x, y + 2))
            && getGrid(x, y) == (getGrid(x, y + 3))) {
          return true;
        }
      }
    }
    return false;
  }
}
