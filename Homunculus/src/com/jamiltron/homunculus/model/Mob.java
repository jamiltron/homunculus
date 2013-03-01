package com.jamiltron.homunculus.model;

import com.badlogic.gdx.math.Vector2;

public class Mob extends Entity {
  public Vector2 vel;
  public Vector2 acc;
  
  public Mob(float x, float y, float w, float h) {
    super(x, y, w, h);
    vel = new Vector2(0, 0);
    acc = new Vector2(0, 0);
  }
  
  public void update(float dt) {
    pos.add(vel.cpy().mul(dt));
  }
}


