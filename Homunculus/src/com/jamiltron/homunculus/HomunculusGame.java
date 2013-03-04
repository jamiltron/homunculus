package com.jamiltron.homunculus;

import com.badlogic.gdx.Game;
import com.jamiltron.homunculus.screen.MainMenu;
import com.jamiltron.homunculus.Settings;

public class HomunculusGame extends Game {
  public Settings settings;
  public boolean desktopGame;
  
  public HomunculusGame(boolean desktopGame) {
    super();
    this.desktopGame = desktopGame;
  }
  
  public HomunculusGame() {
    this(false);
  }
  
  @Override
  public void create() {
    settings = null;
    Assets.loadSounds();
    Assets.loadImages();
    Assets.loadFonts();
    setScreen(new MainMenu(this));
  }
  
}