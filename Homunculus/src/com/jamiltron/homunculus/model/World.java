package com.jamiltron.homunculus.model;

import com.badlogic.gdx.utils.Array;
import com.jamiltron.homunculus.model.Color;
import com.jamiltron.homunculus.util.JArray;
import java.util.Random;

public class World {
  private Color[] colors = Color.values();
  public int numHomunculi;
  public Array<Homunculus> homunculi = new Array<Homunculus>();
  public Array<Spell> setSpells = new Array<Spell>();
  public Array<Homunculus> deadHomunculi = new Array<Homunculus>();
  public Array<Spell> deadSpells = new Array<Spell>();
  public JArray<Color> colorGrid;
  public boolean paused;
  public boolean won;
  public boolean lost;
  private Spell activeSpell = null;
  private Spell nextSpell   = null;
  private Random random = new Random();

  
  public Color getGrid(float x, float y) {
    return colorGrid.get((int)x, (int)y);
  }
  
  public Color getGrid(Component component) {
    return colorGrid.get((int) component.pos.x, (int) component.pos.y);
  }
  
  public void putGrid(float x, float y, Color c) {
    colorGrid.set((int)x, (int)y, c);
  }
  

  public World(int numHomunculi) {
    createWorld(numHomunculi);
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
    Component component1 = new Component(12, 19, color1);
    Component component2 = new Component(13, 19, color2);
    return new Spell(component1, component2);
  }
  
  public void restSpell() {
    if (activeSpell != null) {
      putGrid(activeSpell.component1.pos.x, 
          activeSpell.component1.pos.y, activeSpell.component1.color);
      putGrid(activeSpell.component2.pos.x, 
          activeSpell.component2.pos.y, activeSpell.component2.color);
      setSpells.add(activeSpell);
    }
    activateSpell(nextSpell);
    activeSpell = nextSpell;
    nextSpell = generateSpell();
  }
  

  public void activateSpell(Spell spell) {
    spell.component1.pos.x = 5;
    spell.component1.pos.y = 18;
    
    spell.component2.pos.x = 6;
    spell.component2.pos.y = 18;
  }
  
  private void createWorld(int nh) {
    numHomunculi = nh;
    paused = false;
    won = false;
    lost = false;
    Boolean inserted;
    Color color;
    float x, y;
    colorGrid = new JArray<Color>(8, 17);
    
    // create random homunculi
    for (int i = 0; i < numHomunculi; i++) {
      inserted = false;
      do {
        x = (float)(random.nextInt(8)  + 2);
        y = (float)(random.nextInt(12) + 2);

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
