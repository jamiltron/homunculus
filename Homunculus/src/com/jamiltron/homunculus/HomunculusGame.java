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
    settings = new Settings();
    settings.setSoundOn(true);
    settings.setMusicOn(true);
    Assets.loadSounds();
    Assets.loadMusic();
    Assets.loadImages();
    Assets.loadFonts();
    Assets.titleMusic.setLooping(true);
    Assets.titleMusic.play();
    setScreen(new MainMenu(this));
  }
  
}