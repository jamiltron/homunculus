package com.jamiltron.homunculus;

import com.badlogic.gdx.Game;
import com.jamiltron.homunculus.screen.MainMenu;
import com.jamiltron.homunculus.Settings;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;


public class HomunculusGame extends Game {
  public Settings settings;
  public boolean desktopGame;
  public List<SimpleEntry<String, Integer>> scores;
  
  public HomunculusGame(boolean desktopGame) {
    super();
    this.desktopGame = desktopGame;
  }
  
  public HomunculusGame() {
    this(false);
  }
  
  @Override
  public void create() {
    Assets.loadSettingsString();
    settings = Assets.getSettings();
    scores = Assets.getHighScores();
    
    for (int i = 0; i < 10; i++) {
      //System.out.println(Integer.toString(i) + " " + scores.get(i).getKey() + " " + scores.get(i).getValue());
    }
    
    Assets.loadSounds();
    Assets.loadMusic();
    Assets.loadImages();
    Assets.loadFonts();
    
    if (settings.getMusicOn()) {
      Assets.titleMusic.setLooping(true);
      Assets.titleMusic.play();
    }
    setScreen(new MainMenu(this));
  }
  
}