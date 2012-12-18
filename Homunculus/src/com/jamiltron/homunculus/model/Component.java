package com.jamiltron.homunculus.model;

public class Component extends Mob {
  private static final Float WIDTH  = 1f;
  private static final Float HEIGHT = 1f;
  public static final Float SPEED  = 1f;
  
  public Color color;
  
  public boolean isFalling;

  public Component(float x, float y, Color c) {
    super(x, y, WIDTH, HEIGHT);
    color = c;
    isFalling = false;
  }
  
  public void setFalling(boolean fall) {
    isFalling = fall;
  }
}
