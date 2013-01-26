package com.jamiltron.homunculus.model;

import com.jamiltron.homunculus.Assets;

public class Component extends Mob {
  public static final Float WIDTH  = 1f;
  public static final Float HEIGHT = 1f;
  public static final Float SPEED  = 1f;
  public float stateTime;
  public static final float DYING_TIME = 0.3f;
  
  public Color color;
  
  public boolean isDying;
  public boolean isFalling;

  public Component(float x, float y, Color c) {
    super(x, y, WIDTH, HEIGHT);
    color = c;
    isFalling = false;
    stateTime = Assets.random.nextFloat();
    isDying = false;
  }
  
  public void setFalling(boolean fall) {
    isFalling = fall;
  }
}
