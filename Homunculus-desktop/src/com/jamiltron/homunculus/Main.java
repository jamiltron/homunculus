package com.jamiltron.homunculus;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {

  public static void main(String[] args) {
    LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
    cfg.title = "Homunculus";
    cfg.useGL20 = true;
    cfg.width   = 450;
    cfg.height  = 600;
    new LwjglApplication(new HomunculusGame(), cfg);
  }
}