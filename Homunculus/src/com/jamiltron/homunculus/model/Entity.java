package com.jamiltron.homunculus.model;

import com.badlogic.gdx.math.Vector2;

public class Entity {
  public Vector2 pos;
  
  public Entity(float x, float y, float w, float h) {
    pos      = new Vector2(x, y);
  }
}
