package com.jamiltron.homunculus;

import com.badlogic.gdx.Game;
import com.jamiltron.homunculus.screen.MainMenu;

public class HomunculusGame extends Game {
  
  @Override
  public void create() {
    Assets.loadSounds();
    Assets.loadImages();
    Assets.loadFonts();
    setScreen(new MainMenu(this));
  }
  
}