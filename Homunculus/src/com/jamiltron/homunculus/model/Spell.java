package com.jamiltron.homunculus.model;

public class Spell {
  public Component component1;
  public Component component2;
  private float pauseTime;
  private float rotateTime;
  public boolean isFalling;
  
  public float getRotateTime() {
    return rotateTime;
  }
  
  public void setRotateTime(float r) {
    rotateTime = r;
  }
  
  public Spell(Component p1, Component p2) {
    component1 = p1;
    component2 = p2;
    pauseTime = 0;
    isFalling = false;
  }
  
  public boolean isFlat() {
    return bottomComponent().pos.y == topComponent().pos.y;
  }
  
  public boolean isStanding() {
    return !isFlat();
  }
  
  public float top() {
    return topComponent().pos.y + 1f;
  }
  
  public float bottom() {
    return bottomComponent().pos.y;
  }
  
  public float left() {
    return leftComponent().pos.x;
  }
  
  public float right() {
    return rightComponent().pos.x + 1f;
  }
  
  public Component bottomComponent() {
    if (component1.pos.y <= component2.pos.y) {
      return component1;
    } else {
      return component2;
    }
  }
  
  public Component topComponent() {
    if (component1.pos.y >= component2.pos.y) {
      return component1;
    } else {
      return component2;
    }
  }
  
  public Component leftComponent() {
    if (component1.pos.x <= component2.pos.x) {
      return component1;
    } else {
      return component2;
    }
  }
  
  public Component rightComponent() {
    if (component1.pos.x >= component2.pos.x) {
      return component1;
    } else {
      return component2;
    }
  }
  
  public float getPauseTime() {
    return pauseTime;
  }
  
  public void update(Float dt) {
    pauseTime -= dt;
    rotateTime -= dt;
    if (pauseTime < 0) {
      pauseTime = 0;
    }
    
    if (rotateTime < 0) {
      rotateTime = 0;
    }
    
    if (component1 != null) component1.update(1f);
    if (component2 != null) component2.update(1f);
  }
  
  public void setPauseTime(float pt) {
    pauseTime = pt;
  }
  
  public void setVel(Float x, Float y) {
    if (x != null) {
      if (component1 != null) component1.vel.x = x;
      if (component2 != null) component2.vel.x = x;
    }
    
    if (y != null) {
      if (component1 != null) component1.vel.y = y;
      if (component2 != null) component2.vel.y = y;
    }
  }

}
