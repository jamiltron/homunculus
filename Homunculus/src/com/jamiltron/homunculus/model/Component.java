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
  public boolean isDead;
  
  public Component() {
    this(-1f, -1f, null);
  }

  public Component(float x, float y, Color c) {
    super(x, y, WIDTH, HEIGHT);
    color = c;
    isFalling = false;
    stateTime = Assets.random.nextFloat();
    isDying = false;
    isDead  = false;
  }
  
  public void setProps(float x, float y, Color c) {
    pos.x = x;
    pos.y = y;
    color = c;
    isFalling = false;
    stateTime = Assets.random.nextFloat();
    isDying = false;
    isDead  = false;
  }
}
