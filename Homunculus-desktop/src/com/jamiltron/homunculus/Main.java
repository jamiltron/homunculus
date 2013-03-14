package com.jamiltron.homunculus;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {

  public static void main(String[] args) {
    LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
    cfg.title = "Homunculus";
    cfg.useGL20 = true;
    cfg.width = 800;
    cfg.height = 714;
    //cfg.width   = 450;
    //cfg.height  = 600;
    cfg.resizable = false;
    new LwjglApplication(new HomunculusGame(true), cfg);
  }
}