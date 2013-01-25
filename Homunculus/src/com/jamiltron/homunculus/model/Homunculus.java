package com.jamiltron.homunculus.model;

import com.jamiltron.homunculus.Assets;

public class Homunculus extends Entity {
  public static final Float WIDTH = 1f;
  public static final Float HEIGHT = 1f;
  public float stateTime;
  
  public Color color;
  
  public Homunculus(Float x, Float y, Color c) {
    super(x, y, WIDTH, HEIGHT);
    color = c;
    stateTime = Assets.random.nextFloat(); 
  }
}
