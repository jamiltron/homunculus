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
  
  public JArray(float x, float y) {
    super(true, (int)x * (int)y);
    int ix = (int) x;
    int iy = (int) y;
    size = ix * iy;
    cols = ix;
    rows = iy;
  }
  
  public T get(float x, float y) {
    int ix = (int)x - 4;
    int iy = (rows - 1) - ((int)(y - 4.75));
    return super.get(cols * iy + ix);
  }
  
  public void set(float x, float y, T value) {
    int ix = (int)x - 4;
    int iy = (rows - 1) - ((int)(y - 4.75));
    super.set(cols * iy + ix, value);
  }
  
  @Override
  public void clear() {
    for (int y = 0; y < rows; y++) {
      for (int x = 0; x < cols; x++) {
        super.set(cols * y + x, null);
      }
    }
  }
}
