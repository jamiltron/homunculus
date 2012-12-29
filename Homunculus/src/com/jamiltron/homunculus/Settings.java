package com.jamiltron.homunculus;

public class Settings {
  public enum Speed {
    SLOW, MED, FAST;
  }

  private int numHomunculi;
  private Speed speed;
  private boolean musicOn;
  private boolean soundOn;

  public void setHomunculiNum(int num) {
    if (num < 0 || num > 20) {
      throw new IllegalArgumentException("Num must be between 0 and 20");
    } else {
      numHomunculi = num;
    }
  }

  public void setSpeed(int n) {
    if (n == 0) {
      speed = Speed.SLOW;
    } else if (n == 1) {
      speed = Speed.MED;
    } else if (n == 2) {
      speed = Speed.FAST;
    } else {
      throw new IllegalArgumentException("Speed must be between 0 and 2");
    }
  }

  public void setMusicOn(boolean on) {
    musicOn = on;
  }

  public void setSoundOn(boolean on) {
    soundOn = on;
  }

  public int getHomunculiNum() {
    return numHomunculi;
  }

  public int getScoreModifier() {
    if (speed == Speed.SLOW) {
      return 100;
    } else if (speed == Speed.MED) {
      return 200;
    } else
      return 300;
  }

  public Speed getSpeed() {
    return speed;
  }

  public boolean getMusicOn() {
    return musicOn;
  }

  public boolean getSondOn() {
    return soundOn;
  }
}
