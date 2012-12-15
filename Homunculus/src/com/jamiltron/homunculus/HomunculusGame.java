package com.jamiltron.homunculus;

import com.badlogic.gdx.Game;
import com.jamiltron.homunculus.screen.GameScreen;

public class HomunculusGame extends Game {
  
  @Override
  public void create() {
    setScreen(new GameScreen());
  }
}