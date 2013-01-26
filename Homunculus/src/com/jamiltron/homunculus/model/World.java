package com.jamiltron.homunculus.model;

import java.util.Random;

import com.badlogic.gdx.utils.Array;
import com.jamiltron.homunculus.util.JArray;

public class World {
  public static final float BOX_X = 13.70f;
  public static final float BOX_Y = 20.5f;
  public static final float BOTTOM = 5.75f;
  public static final float ENTRY_X = 6f;
  public static final float ENTRY_Y = 20.75f;
  public static final float COLS = 8;
  public static final float ROWS = 17;
  public static final float ROW_RAND_MIN = 12;
  public static final float X_MIN = 4f;
  public static final float X_MAX = X_MIN + COLS - 1;
  public static final float Y_MIN = 4.75f;
  public static final float Y_MAX = Y_MIN + ROWS - 1;
  
  private final Color[] colors = Color.values();
  public int numHomunculi;
  public Array<Homunculus> homunculi = new Array<Homunculus>();
  public Array<Spell> setSpells = new Array<Spell>();
  public Array<Homunculus> deadHomunculi = new Array<Homunculus>();
  public Array<Spell> deadSpells = new Array<Spell>();
  public JArray<Color> colorGrid;
  public boolean paused;
  public boolean won;
  public boolean lost;
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
    
    if (activeSpell != null) {
      activeSpell.updateAnimation(dt);
    }
    
    if (nextSpell != null) {
      nextSpell.updateAnimation(dt);
    }
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
    createWorld(numHomunculi);
    score = 0;
  }

  public World(int numHomunculi, int s) {
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
    deadHomunculi.clear();
    deadSpells.clear();
  }

  public Spell generateSpell() {
    int i = colors.length;
    Color color1 = colors[random.nextInt(i)];
    Color color2 = colors[random.nextInt(i)];
    Component component1 = new Component(BOX_X, BOX_Y, color1);
    Component component2 = new Component(BOX_X + 1, BOX_Y, color2);
    return new Spell(component1, component2);
  }

  public void restSpell() {
    if (activeSpell != null) {
      putGrid(activeSpell.component1.pos.x, activeSpell.component1.pos.y,
          activeSpell.component1.color);
      putGrid(activeSpell.component2.pos.x, activeSpell.component2.pos.y,
          activeSpell.component2.color);
      setSpells.add(activeSpell);
    }
    activateSpell(nextSpell);
    activeSpell = nextSpell;
    nextSpell = generateSpell();
  }

  public void activateSpell(Spell spell) {
    spell.component1.pos.x = ENTRY_X;
    spell.component1.pos.y = ENTRY_Y;

    spell.component2.pos.x = ENTRY_X + 1;
    spell.component2.pos.y = ENTRY_Y;
  }
  
  private void createWorld(int nh) {
    numHomunculi = nh;
    paused = false;
    won = false;
    lost = false;
    Boolean inserted;
    Color color;
    float x, y;
    colorGrid = new JArray<Color>((int)COLS, (int)ROWS);

    // create random homunculi
    for (int i = 0; i < numHomunculi; i++) {
      inserted = false;
      do {
        x = (random.nextInt((int)COLS - 1) + X_MIN);
        y = (random.nextInt((int)ROW_RAND_MIN) + Y_MIN);

        if (getGrid(x, y) == null) {
          inserted = true;
          color = colors[i % colors.length];
          homunculi.add(new Homunculus(x, y, color));
          putGrid(x, y, color);
        }
      } while (!inserted);
    }
    nextSpell = generateSpell();
  }
}
