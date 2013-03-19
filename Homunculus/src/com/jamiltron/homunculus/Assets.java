package com.jamiltron.homunculus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Iterator;

public class Assets {
  private static final String path           = ".homunculus/data/";
  private static final String highScoresPath = path + "highscores.dat";
  private static final String settingsPath   = path + "settings.dat";
  public static final int TOUCH_BOX = 2;
  
  public static int unit;
  public static int wizardFrames = 5;
  public static float wizardTime = 0.1f;
  public static Music titleMusic;
  public static Music levelMusic;
  public static Sound drop;
  public static Sound match;
  public static Sound selectMove;
  public static Sound selectEnter;
  public static Sound rotate;
  
  public static TextureRegion instructionsBase;
  public static Texture startScreenBackgroundT;
  public static Texture gameOverBackgroundT;
  public static Texture pauseBackgroundT;
  public static Texture playGameBackgroundT;
  public static Texture debug;
  public static Texture overlay;

  public static Texture spriteSheet;
  public static Texture selector;
  public static Texture textT;
    
  public static TextureRegion gameOverBackground;
  public static TextureRegion startScreenBackground;
  public static TextureRegion pauseBackground;
  public static TextureRegion playGameBackground;
  
  public static TextureRegion startScreenStretch;
  public static TextureRegion startScreenTop;
  public static TextureRegion gameScreenStretch;
  public static TextureRegion wallScreenStretch;
  
  public static TextureRegion leftArrow;
  public static TextureRegion rightArrow;
  public static TextureRegion downArrow;
  public static TextureRegion rotateArrow;
  
  public static TextureRegion logo;
  
  public static TextureRegion pressAnyKey;
  public static TextureRegion tapScreen;
  public static TextureRegion gameOver;
  public static TextureRegion enterName;
  public static TextureRegion highScore;
  public static TextureRegion complete;
  
  public static TextureRegion paused;
  public static TextureRegion startW;
  public static TextureRegion startB;
  public static TextureRegion highScoresW;
  public static TextureRegion highScoresB;
  public static TextureRegion creditsW;
  public static TextureRegion creditsB;
  public static TextureRegion quitW;
  public static TextureRegion quitB;
  public static TextureRegion settingsText;
  public static TextureRegion instructionsW;
  public static TextureRegion instructionsB;
  public static TextureRegion slowW;
  public static TextureRegion slowB;
  public static TextureRegion medW;
  public static TextureRegion medB;
  public static TextureRegion fastW;
  public static TextureRegion fastB;
  public static TextureRegion onW;
  public static TextureRegion onB;
  public static TextureRegion offW;
  public static TextureRegion offB;
  public static TextureRegion music;
  public static TextureRegion speed;
  public static TextureRegion level;
  public static TextureRegion sounds;
  public static Texture textCursorT;
  public static TextureRegion textCursor;
  
  public static Animation wizardAnim;
  public static Animation blueHomLiveAnim;
  public static Animation redHomLiveAnim;
  public static Animation yellowHomLiveAnim;
  public static Animation blueHomDeadAnim;
  public static Animation redHomDeadAnim;
  public static Animation yellowHomDeadAnim;
  public static Animation blueSpellLiveAnim;
  public static Animation redSpellLiveAnim;
  public static Animation yellowSpellLiveAnim;
  public static Animation blueSpellDeadAnim;
  public static Animation redSpellDeadAnim;
  public static Animation yellowSpellDeadAnim;
  private static float lastFontScaleX = 0;
  private static float lastFontScaleY = 0;
  public static Random random = new Random();
  public static BitmapFont font;
  private static String settingsString;
  
  private static void loadScores(FileHandle file, List<SimpleEntry<String, Integer>> scores) {
    String highScoreString = file.readString("UTF-8");
    String[] highScoreList = highScoreString.split("\t");
    for (int i = 0; i < 10; i++) {
      if (i < highScoreList.length) {
        String[] lineParts = highScoreList[i].split("\\|");
        String name;
        if (lineParts[0].length() > 10) {
          name = lineParts[0].substring(0, 10);
          } else {
            name = lineParts[0];
          }
        SimpleEntry<String, Integer> entry = new SimpleEntry<String, Integer>(name, Integer.parseInt(lineParts[1]));
        scores.add(entry);
        } else {
          SimpleEntry<String, Integer> entry = new SimpleEntry<String, Integer>("homunculus", 0);
          scores.add(entry);
        }
      }
  }
  
  public static List<SimpleEntry<String, Integer>> getHighScores(boolean desktopGame) {
    List<SimpleEntry<String, Integer>> scores = new ArrayList<SimpleEntry<String, Integer>>();
    try {
      FileHandle file;
      if (desktopGame && Gdx.files.isExternalStorageAvailable() &&
          Gdx.files.external(highScoresPath).exists()) {
        file = Gdx.files.external(highScoresPath);
        loadScores(file, scores);
      } else if (!desktopGame && Gdx.files.isLocalStorageAvailable() &&
                 Gdx.files.local(highScoresPath).exists()) {
            file = Gdx.files.local(highScoresPath);
            loadScores(file, scores);
      } else {
        for (int i = 0; i < 10; i++) {
          SimpleEntry<String, Integer> entry = new SimpleEntry<String, Integer>("homunculus", 0);
          scores.add(entry);
        }
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
      while(scores.size() < 10) {
        SimpleEntry<String, Integer> entry = new SimpleEntry<String, Integer>("homunculus", 0);
        scores.add(entry);
      }
    }
    return scores;
  }
  
  public static void scaleFont(float ppuX, float ppuY) {
    lastFontScaleX = 1.5f + (ppuX - 32f) / 32f;
    lastFontScaleY = 1.5f + (ppuY - 32f) / 32f;
    font.setScale(lastFontScaleX, lastFontScaleY);
  }

  public static void writeHighScores(List<SimpleEntry<String, Integer>> scores, boolean desktopGame) {
    try {
      if (desktopGame && Gdx.files.isExternalStorageAvailable()) {
        FileHandle file = Gdx.files.external(highScoresPath);
        file.writeString("", false);
        Iterator<SimpleEntry<String, Integer>> iterator = scores.iterator();
        int i = 0;
        while (iterator.hasNext() && i <= 9) {
          SimpleEntry<String, Integer> entry = iterator.next();
          String name = entry.getKey();
          name.replaceAll("\\|", "\\|");
          name.replaceAll("\t", "");
          name.replaceAll("\n", "");
          name.replaceAll("\r", "");
          file.writeString(name + "|" + Integer.toString(entry.getValue()) + "\t", true);
          i++;
        }
      } else if (!desktopGame && Gdx.files.isLocalStorageAvailable()){
        FileHandle file = Gdx.files.local(highScoresPath);
        file.writeString("", false);
        Iterator<SimpleEntry<String, Integer>> iterator = scores.iterator();
        int i = 0;
        while (iterator.hasNext() && i <= 9) {
          SimpleEntry<String, Integer> entry = iterator.next();
          String name = entry.getKey();
          name.replaceAll("\\|", "\\|");
          name.replaceAll("\t", "");
          name.replaceAll("\n", "");
          name.replaceAll("\r", "");
          file.writeString(name + "|" + Integer.toString(entry.getValue()) + "\t", true);
          i++;
        }
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
  
  public static Settings getSettings() {
    Settings settings = new Settings();
    try {
    char music = settingsString.charAt(0);
    char sound = settingsString.charAt(2);
    int speed =  Integer.parseInt(Character.toString(settingsString.charAt(4)));
    String numHomunculi = settingsString.substring(6);
    
    if (music == '1') {
      settings.setMusicOn(true);
    } else {
      settings.setMusicOn(false);
    }
    
    if (sound == '1') {
      settings.setSoundOn(true);
    } else {
      settings.setSoundOn(false);
    }
    
    if (speed >= 0 && speed < 3) {
      settings.setSpeed(speed);
    } else {
      settings.setSpeed(1);
    }
    
    int iHomunculi = Integer.parseInt(numHomunculi);
    if (iHomunculi >= 0 && iHomunculi <= 20) {
      settings.setHomunculiNum(iHomunculi);
    } else {
      settings.setHomunculiNum(0);
    }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return settings;
  }
  
  public static void loadSettingsString(boolean desktopGame) {
    try {
      if (desktopGame && Gdx.files.isExternalStorageAvailable() && 
          Gdx.files.external(settingsPath).exists()) {
          FileHandle file = Gdx.files.external(settingsPath);
          settingsString = file.readString("UTF-8");
      } else if (!desktopGame && Gdx.files.isLocalStorageAvailable() &&
          Gdx.files.local(settingsPath).exists()) {
          FileHandle file = Gdx.files.local(settingsPath);
          settingsString = file.readString("UTF-8");
      } else {
        settingsString = "1 1 1 0";
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
      settingsString = "1 1 1 0";
    }
  }
  
  public static void writeSettings(Settings settings, boolean desktopGame) {
    try {
      if (desktopGame && Gdx.files.isExternalStorageAvailable()) {
        FileHandle file = Gdx.files.external(settingsPath);
        String music = "0";
        String sound = "0";
        if (settings.getMusicOn()) music = "1";
        if (settings.getSoundOn()) sound = "1";
        
        file.writeString(music + " " + sound + " " +
            Integer.toString(settings.getSpeed().ordinal()) + " " +
            Integer.toString(settings.getHomunculiNum()), false, "UTF-8");
      } else if (!desktopGame && Gdx.files.isLocalStorageAvailable()) {
        FileHandle file = Gdx.files.local(settingsPath);
        String music = "0";
        String sound = "0";
        if (settings.getMusicOn()) music = "1";
        if (settings.getSoundOn()) sound = "1";
        
        file.writeString(music + " " + sound + " " +
            Integer.toString(settings.getSpeed().ordinal()) + " " +
            Integer.toString(settings.getHomunculiNum()), false, "UTF-8");
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
  
  public static void loadMusic() {
    titleMusic = Gdx.audio.newMusic(Gdx.files.internal("data/sfx/LLS-ParaBellum.mp3"));
    levelMusic = Gdx.audio.newMusic(Gdx.files.internal("data/sfx/LLS-Surge.mp3"));
  }

  public static void loadSounds() {
    drop = Gdx.audio.newSound(Gdx.files.internal("data/sfx/hit.wav"));
    match = Gdx.audio.newSound(Gdx.files.internal("data/sfx/match.wav"));
    selectMove = Gdx.audio.newSound(Gdx.files.internal("data/sfx/select1.wav"));
    selectEnter = Gdx.audio.newSound(Gdx.files.internal("data/sfx/select2.wav"));
    rotate = Gdx.audio.newSound(Gdx.files.internal("data/sfx/rotate.wav"));
  }
  
  public static void loadFonts() {
    font = new BitmapFont(Gdx.files.internal("data/fnt/font2.fnt"),
        Gdx.files.internal("data/fnt/fontw.png"), false);
  }
  
  public static void loadImages(boolean small) {
    int yb;
    if (small) {
      unit = 16;
      yb = 400;
    } else {
      unit = 32;
      yb = 0;
    }
    textCursorT = loadTexture("data/gfx/textCursor.png");
    textCursor = new TextureRegion(textCursorT, 0, 0, 8, 12);
    overlay = loadTexture("data/gfx/overlay.png");
    textT = loadTexture("data/gfx/text.png");
    pressAnyKey = new TextureRegion(textT, 322, 28, 96, 22);
    tapScreen = new TextureRegion(textT, 419, 28, 83, 22);
    gameOver = new TextureRegion(textT, 0, 42, 71, 10);
    enterName = new TextureRegion(textT, 73, 40, 77, 22);
    highScore = new TextureRegion(textT, 262, 0, 74, 12);
    complete  = new TextureRegion(textT, 152,41,66,12);
    instructionsBase = new TextureRegion(textT, 0, 66, 282, 94);

    paused = new TextureRegion(textT, 328, 52, 53, 12);
    startW = new TextureRegion(textT, 0, 0, 41, 10);
    startB = new TextureRegion(textT, 42, 0, 41, 10);
    quitW = new TextureRegion(textT, 83, 0, 33, 14);
    quitB = new TextureRegion(textT, 118, 0, 33, 14);
    creditsW = new TextureRegion(textT, 152, 0, 54, 12);
    creditsB = new TextureRegion(textT, 207, 0, 54, 12);
    highScoresW = new TextureRegion(textT, 261, 0, 82, 10);
    highScoresB = new TextureRegion(textT, 345, 0, 82, 10);
    instructionsW = new TextureRegion(textT, 128, 28, 96, 10);
    instructionsB = new TextureRegion(textT, 225, 28, 96, 10);
    settingsText = new TextureRegion(textT, 64, 28, 63, 12);
    level = new TextureRegion(textT, 0, 14, 40, 9);
    speed = new TextureRegion(textT, 82, 14, 41, 12);
    sounds = new TextureRegion(textT, 166, 14, 49, 9);
    music = new TextureRegion(textT, 266, 15, 44, 8);
    slowW = new TextureRegion(textT, 355, 14, 34, 9);
    slowB = new TextureRegion(textT, 390, 14, 34, 9);
    medW = new TextureRegion(textT, 425, 14, 26, 9);
    medB = new TextureRegion(textT, 452, 14, 26, 9);
    fastW = new TextureRegion(textT, 0, 27, 31, 12);
    fastB = new TextureRegion(textT, 32, 27, 31, 12);
    onW = new TextureRegion(textT, 428, 2, 15, 8);
    onB = new TextureRegion(textT, 444, 2, 15, 8);
    offW = new TextureRegion(textT, 460, 0, 21, 12);
    offB = new TextureRegion(textT, 482, 0, 21, 12);
    
    gameOverBackgroundT = loadTexture("data/gfx/game-over-bg.png");
    pauseBackgroundT = loadTexture("data/gfx/pause-bg.png");
    playGameBackgroundT = loadTexture("data/gfx/play-game-bg.png");
    
    spriteSheet = loadTexture("data/gfx/sprite-sheet.png");
    startScreenBackgroundT = loadTexture("data/gfx/start-screen-bg.png");

    debug = loadTexture("data/gfx/debug.png");
    selector = loadTexture("data/gfx/selector.png");
    
    startScreenBackground = new TextureRegion(startScreenBackgroundT, 0, 0, 600, 800);
    startScreenTop        = new TextureRegion(startScreenBackgroundT, 0, 0, 600, 32);
    startScreenStretch    = new TextureRegion(startScreenBackgroundT, 0, 32, 600, 32);
    playGameBackground    = new TextureRegion(playGameBackgroundT, 0, 0, 600, 800);
    wallScreenStretch     = new TextureRegion(playGameBackgroundT, 0, 0, 32, 800);
    gameScreenStretch     = new TextureRegion(playGameBackgroundT, 0, 32, 600, 32);
    pauseBackground       = new TextureRegion(pauseBackgroundT, 0, 0, 300, 400);
    gameOverBackground    = new TextureRegion(gameOverBackgroundT, 0, 0, 300, 400);
    
    
    wizardAnim = new Animation(wizardTime, new TextureRegion(spriteSheet, unit * 4 * 0, 0 + yb, unit * 4, unit * 4),
        new TextureRegion(spriteSheet, unit * 4 * 1, 0 + yb, unit * 4, unit * 4),
        new TextureRegion(spriteSheet, unit * 4 * 2, 0 + yb, unit * 4, unit * 4),
        new TextureRegion(spriteSheet, unit * 4 * 2, 0 + yb, unit * 4, unit * 4),
        new TextureRegion(spriteSheet, unit * 4 * 1, 0 + yb, unit * 4, unit * 4));
    
    redHomLiveAnim = new Animation(0.18f, new TextureRegion(spriteSheet, unit * 0, unit * 4 + yb, unit, unit),
        new TextureRegion(spriteSheet, unit * 1, unit * 4 + yb, unit, unit),
        new TextureRegion(spriteSheet, unit * 2, unit * 4 + yb, unit, unit),
        new TextureRegion(spriteSheet, unit * 1, unit * 4 + yb, unit, unit));
    
    yellowHomLiveAnim = new Animation(0.15f, new TextureRegion(spriteSheet, 0, unit * 5 + yb, unit, unit),
        new TextureRegion(spriteSheet, unit * 1, unit * 5 + yb, unit, unit),
        new TextureRegion(spriteSheet, unit * 2, unit * 5 + yb, unit, unit),
        new TextureRegion(spriteSheet, unit * 1, unit * 5 + yb, unit, unit));
    
    blueHomLiveAnim = new Animation(0.2f, new TextureRegion(spriteSheet, 0, unit * 6 + yb, unit, unit),
        new TextureRegion(spriteSheet, unit * 1, unit * 6 + yb, unit, unit),
        new TextureRegion(spriteSheet, unit * 2, unit * 6 + yb, unit, unit),
        new TextureRegion(spriteSheet, unit * 1, unit * 6 + yb, unit, unit));
    
    redHomDeadAnim = new Animation(0.1f, new TextureRegion(spriteSheet, unit * 3, unit * 4 + yb, unit, unit),
        new TextureRegion(spriteSheet, unit * 4, unit * 4 + yb, unit, unit),
        new TextureRegion(spriteSheet, unit * 5, unit * 4 + yb, unit, unit));
    
    yellowHomDeadAnim = new Animation(0.1f, new TextureRegion(spriteSheet, unit * 3, unit * 5 + yb, unit, unit),
        new TextureRegion(spriteSheet, unit * 4, unit * 5 + yb, unit, unit),
        new TextureRegion(spriteSheet, unit * 5, unit * 5 + yb, unit, unit));

    blueHomDeadAnim = new Animation(0.1f, new TextureRegion(spriteSheet, unit * 3, unit * 6 + yb, unit, unit),
        new TextureRegion(spriteSheet, unit * 5, unit * 6 + yb, unit, unit),
        new TextureRegion(spriteSheet, unit * 5, unit * 6 + yb, unit, unit));
    
    redSpellLiveAnim = new Animation(0.1f, new TextureRegion(spriteSheet, unit * 6, unit * 4 + yb, unit, unit),
        new TextureRegion(spriteSheet, unit * 7, unit * 4 + yb, unit, unit),
        new TextureRegion(spriteSheet, unit * 8, unit * 4 + yb, unit, unit),
        new TextureRegion(spriteSheet, unit * 7, unit * 4 + yb, unit, unit));
    
    yellowSpellLiveAnim = new Animation(0.1f, new TextureRegion(spriteSheet, unit * 6, unit * 5 + yb, unit, unit),
        new TextureRegion(spriteSheet, unit * 7, unit * 5 + yb, unit, unit),
        new TextureRegion(spriteSheet, unit * 8, unit * 5 + yb, unit, unit),
        new TextureRegion(spriteSheet, unit * 7, unit * 5 + yb, unit, unit));
    
    blueSpellLiveAnim = new Animation(0.1f, new TextureRegion(spriteSheet, unit * 6, unit * 6 + yb, unit, unit),
        new TextureRegion(spriteSheet, unit * 7, unit * 6 + yb, unit, unit),
        new TextureRegion(spriteSheet, unit * 8, unit * 6 + yb, unit, unit),
        new TextureRegion(spriteSheet, unit * 7, unit * 6 + yb, unit, unit));
    
    redSpellDeadAnim = new Animation(0.1f, new TextureRegion(spriteSheet, unit * 9, unit * 4 + yb, unit, unit),
        new TextureRegion(spriteSheet, unit * 10, unit * 4 + yb, unit, unit),
        new TextureRegion(spriteSheet, unit * 11, unit * 4 + yb, unit, unit));
    
    yellowSpellDeadAnim = new Animation(0.1f, new TextureRegion(spriteSheet, unit * 9, unit * 5 + yb, unit, unit),
        new TextureRegion(spriteSheet, unit * 10, unit * 5 + yb, unit, unit),
        new TextureRegion(spriteSheet, unit * 11, unit * 5 + yb, unit, unit));
    
    blueSpellDeadAnim = new Animation(0.1f, new TextureRegion(spriteSheet, unit * 9, unit * 6 + yb, unit, unit),
        new TextureRegion(spriteSheet, unit * 10, unit * 6 + yb, unit, unit),
        new TextureRegion(spriteSheet, unit * 11, unit * 6 + yb, unit, unit));
    
    leftArrow = new TextureRegion(spriteSheet, 0, 224, 32, 32);
    rightArrow = new TextureRegion(spriteSheet, 64, 224, 32, 32);
    rotateArrow = new TextureRegion(spriteSheet, 32, 224, 32, 32);
    downArrow = new TextureRegion(spriteSheet, 96, 224, 32, 32);
    
    logo = new TextureRegion(spriteSheet, 0, 256, 385, 88);

  }

  public static void playSound(Sound sound) {
    sound.play(1);
  }

  private static Texture loadTexture(String file) {
    return new Texture(Gdx.files.internal(file));
  }

}
