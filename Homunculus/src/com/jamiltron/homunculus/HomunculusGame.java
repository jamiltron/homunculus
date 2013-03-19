package com.jamiltron.homunculus;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;

import com.badlogic.gdx.Game;
import com.jamiltron.homunculus.screen.CreditsScreen;
import com.jamiltron.homunculus.screen.GameScreen;
import com.jamiltron.homunculus.screen.HighScoreScreen;
import com.jamiltron.homunculus.screen.InstructionScreen;
import com.jamiltron.homunculus.screen.MainMenu;
import com.jamiltron.homunculus.screen.SettingsScreen;


public class HomunculusGame extends Game {
  public Settings settings;
  public boolean desktopGame;
  public List<SimpleEntry<String, Integer>> scores;
  private MainMenu mainMenu;
  private CreditsScreen creditsScreen;
  private GameScreen gameScreen;
  private HighScoreScreen highscoreScreen;
  private InstructionScreen instructionScreen;
  private SettingsScreen settingsScreen;
  
  public HomunculusGame(boolean desktopGame) {
    super();
    this.desktopGame = desktopGame;
  }
  
  public void goToMainMenu() {
    setScreen(mainMenu);
  }
  
  public void goToSettings() {
    settingsScreen.reset();
    setScreen(settingsScreen);
  }
  
  public void goToInstructions() {
    setScreen(instructionScreen);
  }
  
  public void goToHighScores() {
    setScreen(highscoreScreen);
  }
  
  public void goToGame() {
    setScreen(gameScreen);
  }
  
  public void goToCredits() {
    setScreen(creditsScreen);
  }
  
  public HomunculusGame() {
    this(false);
  }
  
  @Override
  public void create() {
    Assets.loadSettingsString(this.desktopGame);
    settings = Assets.getSettings();
    scores = Assets.getHighScores(this.desktopGame);
    Assets.loadSounds();
    Assets.loadMusic();
    Assets.loadImages(false);
    Assets.loadFonts();
    
    if (settings.getMusicOn()) {
      Assets.titleMusic.setLooping(true);
      Assets.titleMusic.play();
    }
    mainMenu = new MainMenu(this);
    creditsScreen = new CreditsScreen(this);
    gameScreen = new GameScreen(this);
    highscoreScreen = new HighScoreScreen(this);
    instructionScreen = new InstructionScreen(this);
    settingsScreen = new SettingsScreen(this);

    setScreen(mainMenu);
  }
  
}