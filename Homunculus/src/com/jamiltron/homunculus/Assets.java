package com.jamiltron.homunculus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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

  private static TextureAtlas menuAtlas;
  private static TextureAtlas gameAtlas;
  
  public static final int wizardFrames = 5;
  public static final float wizardTime = 0.1f;
  public static Music titleMusic;
  public static Music levelMusic;
  public static Sound drop;
  public static Sound match;
  public static Sound selectMove;
  public static Sound selectEnter;
  public static Sound rotate;
  
  public static TextureRegion startScreenBackground;
  public static TextureRegion startScreenStretch;
  public static TextureRegion startScreenTop;
  public static TextureRegion pressAnyKeyGame; 
  public static TextureRegion tapScreenGame; 
  public static TextureRegion toContinueGame;
  public static TextureRegion leftArrowMenu;
  public static TextureRegion leftArrowGame;
  public static TextureRegion pauseButtonGame;
  public static TextureRegion rightArrowMenu;
  public static TextureRegion rightArrowGame;
  public static TextureRegion rotrArrowGame;
  public static TextureRegion downArrowGame;
  public static TextureRegion playGameBackground;
  public static TextureRegion wallScreenStretch;
  public static TextureRegion gameScreenStretch;
  public static TextureRegion pauseBackground;
  public static TextureRegion gameOverBackground;
  
  public static TextureRegion gameOver;
  public static TextureRegion enterYour;
  public static TextureRegion name;
  public static TextureRegion highScore;
  public static TextureRegion complete;
 
  public static TextureRegion justinHamilton;
  public static TextureRegion donnaAlmendrala;
  public static TextureRegion luckyLion;
  public static TextureRegion programming;
  public static TextureRegion art;
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
  public static TextureRegion textCursor;
  public static TextureRegion overlay;
  public static TextureRegion selector;
  
  public static TextureRegion wizard0;
  public static TextureRegion wizard1;
  public static TextureRegion wizard2;
  public static TextureRegion blueHomAlive0;
  public static TextureRegion blueHomAlive1;
  public static TextureRegion blueHomAlive2;
  public static TextureRegion redHomAlive0;
  public static TextureRegion redHomAlive1;
  public static TextureRegion redHomAlive2;
  public static TextureRegion yellowHomAlive0;
  public static TextureRegion yellowHomAlive1;
  public static TextureRegion yellowHomAlive2;
  public static TextureRegion blueHomDead0;
  public static TextureRegion blueHomDead1;
  public static TextureRegion blueHomDead2;
  public static TextureRegion redHomDead0;
  public static TextureRegion redHomDead1;
  public static TextureRegion redHomDead2;
  public static TextureRegion yellowHomDead0;
  public static TextureRegion yellowHomDead1;
  public static TextureRegion yellowHomDead2;
  public static TextureRegion blueSpellAlive0;
  public static TextureRegion blueSpellAlive1;
  public static TextureRegion blueSpellAlive2;
  public static TextureRegion redSpellAlive0;
  public static TextureRegion redSpellAlive1;
  public static TextureRegion redSpellAlive2;
  public static TextureRegion yellowSpellAlive0;
  public static TextureRegion yellowSpellAlive1;
  public static TextureRegion yellowSpellAlive2;
  public static TextureRegion blueSpellDead0;
  public static TextureRegion blueSpellDead1;
  public static TextureRegion blueSpellDead2;
  public static TextureRegion redSpellDead0;
  public static TextureRegion redSpellDead1;
  public static TextureRegion redSpellDead2;
  public static TextureRegion yellowSpellDead0;
  public static TextureRegion yellowSpellDead1;
  public static TextureRegion yellowSpellDead2;
  public static TextureRegion logo;
  
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
  
  public static void loadImages() {
    menuAtlas = new TextureAtlas(Gdx.files.internal("data/gfx/menuPack.pack"));
    gameAtlas = new TextureAtlas(Gdx.files.internal("data/gfx/mainPack.pack"));

    textCursor = gameAtlas.createSprite("textCursor");
    overlay = gameAtlas.createSprite("overlay");
    
    pressAnyKeyGame = gameAtlas.createSprite("pressAnyKeyW");
    tapScreenGame   = gameAtlas.createSprite("tapScreenW");
    toContinueGame  = gameAtlas.createSprite("toContinueW");
    
    gameOver  = gameAtlas.createSprite("gameoverW");
    enterYour = gameAtlas.createSprite("enteryourW");
    name      = gameAtlas.createSprite("nameW");
    highScore = gameAtlas.createSprite("highscoreW");
    complete  = gameAtlas.createSprite("completeW");

    paused = gameAtlas.createSprite("pausedW");
    startW = menuAtlas.createSprite("startW");
    startB = menuAtlas.createSprite("startB");
    quitW = menuAtlas.createSprite("quitW");
    quitB = menuAtlas.createSprite("quitB");
    creditsW = menuAtlas.createSprite("creditsW");
    creditsB = menuAtlas.createSprite("creditsB");
    highScoresW = menuAtlas.createSprite("highScoresW");
    highScoresB = menuAtlas.createSprite("highScoresB");
    instructionsW = menuAtlas.createSprite("instructionsW");
    instructionsB = menuAtlas.createSprite("instructionsB");
    settingsText = menuAtlas.createSprite("settingsW");
    level = menuAtlas.createSprite("levelW");
    speed = menuAtlas.createSprite("speedW");
    sounds = menuAtlas.createSprite("soundsW");
    music = menuAtlas.createSprite("musicW");
    slowW = menuAtlas.createSprite("slowW");
    slowB = menuAtlas.createSprite("slowB");
    medW = menuAtlas.createSprite("medW");
    medB = menuAtlas.createSprite("medB");
    fastW = menuAtlas.createSprite("fastW");
    fastB = menuAtlas.createSprite("fastB");
    onW = menuAtlas.createSprite("onW");
    onB = menuAtlas.createSprite("onB");
    offW = menuAtlas.createSprite("offW");
    offB = menuAtlas.createSprite("offB");    
    selector = menuAtlas.createSprite("selector");
    startScreenBackground = menuAtlas.createSprite("startBackground");
    highScoresW = menuAtlas.createSprite("highscoresW");
    highScoresB = menuAtlas.createSprite("highscoresB");
    
    startScreenTop        = new TextureRegion(startScreenBackground, 0, 0, 600, 32);
    startScreenStretch    = new TextureRegion(startScreenBackground, 0, 32, 600, 32);
    playGameBackground    = gameAtlas.createSprite("playgameBackground");
    wallScreenStretch     = new TextureRegion(playGameBackground, 0, 0, 32, 800);
    gameScreenStretch     = new TextureRegion(playGameBackground, 0, 32, 600, 32);
    pauseBackground       = gameAtlas.createSprite("pauseBackground");
    gameOverBackground    = gameAtlas.createSprite("gameoverBackground");
    
    wizard0 = gameAtlas.createSprite("wizard0");
    wizard1 = gameAtlas.createSprite("wizard1");
    wizard2 = gameAtlas.createSprite("wizard2");
    wizardAnim = new Animation(wizardTime, wizard0, wizard1, wizard2, wizard2, wizard1);

    redHomAlive0 = gameAtlas.createSprite("redAlive0");
    redHomAlive1 = gameAtlas.createSprite("redAlive1");
    redHomAlive2 = gameAtlas.createSprite("redAlive2");
    redHomLiveAnim = new Animation(0.18f, redHomAlive0, redHomAlive1, redHomAlive2, redHomAlive1);

    yellowHomAlive0 = gameAtlas.createSprite("yellowAlive0");
    yellowHomAlive1 = gameAtlas.createSprite("yellowAlive1");
    yellowHomAlive2 = gameAtlas.createSprite("yellowAlive2");
    yellowHomLiveAnim = new Animation(0.15f, yellowHomAlive0, yellowHomAlive1, yellowHomAlive2, yellowHomAlive1);

    blueHomAlive0 = gameAtlas.createSprite("blueAlive0");
    blueHomAlive1 = gameAtlas.createSprite("blueAlive1");
    blueHomAlive2 = gameAtlas.createSprite("blueAlive2");
    blueHomLiveAnim = new Animation(0.2f, blueHomAlive0, blueHomAlive1, blueHomAlive2, blueHomAlive1);

    redHomDead0 = gameAtlas.createSprite("redDead0");
    redHomDead1 = gameAtlas.createSprite("redDead1");
    redHomDead2 = gameAtlas.createSprite("redDead2");
    redHomDeadAnim = new Animation(0.1f, redHomDead0, redHomDead1, redHomDead2);
    
    yellowHomDead0 = gameAtlas.createSprite("yellowDead0");
    yellowHomDead1 = gameAtlas.createSprite("yellowDead1");
    yellowHomDead2 = gameAtlas.createSprite("yellowDead2");
    yellowHomDeadAnim = new Animation(0.1f, yellowHomDead0, yellowHomDead1, yellowHomDead2);
    
    blueHomDead0 = gameAtlas.createSprite("blueDead0");
    blueHomDead1 = gameAtlas.createSprite("blueDead1");
    blueHomDead2 = gameAtlas.createSprite("blueDead2");
    blueHomDeadAnim = new Animation(0.1f, blueHomDead0, blueHomDead1, blueHomDead2);
    
    redSpellAlive0 = gameAtlas.createSprite("ra0");
    redSpellAlive1 = gameAtlas.createSprite("ra1");
    redSpellAlive2 = gameAtlas.createSprite("ra2");
    redSpellLiveAnim = new Animation(0.1f, redSpellAlive0, redSpellAlive1, redSpellAlive2, redSpellAlive1);

    yellowSpellAlive0 = gameAtlas.createSprite("ya0");
    yellowSpellAlive1 = gameAtlas.createSprite("ya1");
    yellowSpellAlive2 = gameAtlas.createSprite("ya2");
    yellowSpellLiveAnim = new Animation(0.1f, yellowSpellAlive0, yellowSpellAlive1, yellowSpellAlive2, yellowSpellAlive1);
    
    blueSpellAlive0 = gameAtlas.createSprite("ba0");
    blueSpellAlive1 = gameAtlas.createSprite("ba1");
    blueSpellAlive2 = gameAtlas.createSprite("ba2");
    blueSpellLiveAnim = new Animation(0.1f, blueSpellAlive0, blueSpellAlive1, blueSpellAlive2, blueSpellAlive1);
    
    redSpellDead0 = gameAtlas.createSprite("rd0");
    redSpellDead1 = gameAtlas.createSprite("rd1");
    redSpellDead2 = gameAtlas.createSprite("rd2");
    redSpellDeadAnim = new Animation(0.1f, redSpellDead0, redSpellDead1, redSpellDead2);

    yellowSpellDead0 = gameAtlas.createSprite("yd0");
    yellowSpellDead1 = gameAtlas.createSprite("yd1");
    yellowSpellDead2 = gameAtlas.createSprite("yd2");
    yellowSpellDeadAnim = new Animation(0.1f, yellowSpellDead0, yellowSpellDead1, yellowSpellDead2);
    
    blueSpellDead0 = gameAtlas.createSprite("bd0");
    blueSpellDead1 = gameAtlas.createSprite("bd1");
    blueSpellDead2 = gameAtlas.createSprite("bd2");
    blueSpellDeadAnim = new Animation(0.1f, blueSpellDead0, blueSpellDead1, blueSpellDead2);
    
    
    pauseButtonGame = gameAtlas.createSprite("pauseButton");
    leftArrowGame = gameAtlas.createSprite("leftArrow");
    rightArrowGame = gameAtlas.createSprite("rightArrow");
    rotrArrowGame = gameAtlas.createSprite("rotrArrow");
    downArrowGame = gameAtlas.createSprite("downArrow");
    leftArrowMenu = menuAtlas.createSprite("leftArrow");
    rightArrowMenu = menuAtlas.createSprite("rightArrow");
    logo = menuAtlas.createSprite("logo");
    justinHamilton = menuAtlas.createSprite("justinHamiltonW");
    donnaAlmendrala = menuAtlas.createSprite("donnaW");
    luckyLion = menuAtlas.createSprite("lionW");
    programming = menuAtlas.createSprite("programmingW");
    art = menuAtlas.createSprite("artW");
  }

  public static void playSound(Sound sound) {
    sound.play(1);
  }

/*  private static Texture loadTexture(String file) {
    return new Texture(Gdx.files.internal(file));
  }*/

}
