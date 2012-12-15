package com.jamiltron.homunculus.util;

import com.badlogic.gdx.utils.Array;

public class JArray<T> extends Array<T> {
  private int cols;
  private int rows;
  
  public JArray(int x, int y) {
    super(true, x * y);
    size = x * y;
    cols = x;
    rows = y;
  }
  
  public T get(float x, float y) {
    int ix = (int)x - 2;
    int iy = (rows - 1) - ((int)y - 2);
    return super.get(cols * iy + ix);
  }
  
  public void set(float x, float y, T value) {
    int ix = (int)x - 2;
    int iy = (rows - 1) - ((int)y - 2);
    super.set(cols * iy + ix, value);
  }

}
