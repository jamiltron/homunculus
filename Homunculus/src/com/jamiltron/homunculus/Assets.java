package com.jamiltron.homunculus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.Random;

public class Assets {
  public static final int TOUCH_BOX = 2;
  
  public static Sound drop;
  public static Sound match;
  public static Sound selectMove;
  public static Sound select;
  public static Sound rotate;
  
  public static Texture startScreenBackgroundT;
  public static Texture gameOverBackgroundT;
  public static Texture pauseBackgroundT;
  public static Texture playGameBackgroundT;
  public static Texture debug;

  public static Texture cursor;
  public static Texture spriteSheet;
  public static Texture selector;

  
  public static TextureRegion gameOverBackground;
  public static TextureRegion startScreenBackground;
  public static TextureRegion pauseBackground;
  public static TextureRegion playGameBackground;
  
  public static TextureRegion leftArrow;
  public static TextureRegion rightArrow;
  public static TextureRegion downArrow;
  public static TextureRegion rotateArrow;
  
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
  
  public static Random random = new Random();
  
  public static BitmapFont font;

  public static void loadSounds() {
    drop = Gdx.audio.newSound(Gdx.files.internal("data/sfx/hit.wav"));
    match = Gdx.audio.newSound(Gdx.files.internal("data/sfx/match.wav"));
    selectMove = Gdx.audio.newSound(Gdx.files.internal("data/sfx/select1.wav"));
    select = Gdx.audio.newSound(Gdx.files.internal("data/sfx/select2.wav"));
    rotate = Gdx.audio.newSound(Gdx.files.internal("data/sfx/rotate.wav"));
  }
  
  public static void loadFonts() {
    font = new BitmapFont(Gdx.files.internal("data/fnt/font2.fnt"),
        Gdx.files.internal("data/fnt/fontw.png"), false);
  }
  
  public static void loadImages() {
    cursor = loadTexture("data/gfx/cursor.png");
    gameOverBackgroundT = loadTexture("data/gfx/game-over-bg.png");
    pauseBackgroundT = loadTexture("data/gfx/pause-bg.png");
    playGameBackgroundT = loadTexture("data/gfx/play-game-bg.png");
    spriteSheet = loadTexture("data/gfx/sprite-sheet.png");
    startScreenBackgroundT = loadTexture("data/gfx/start-screen-bg.png");

    debug = loadTexture("data/gfx/debug.png");
    selector = loadTexture("data/gfx/selector.png");
    
    startScreenBackground = new TextureRegion(startScreenBackgroundT, 0, 0, 600, 800);
    playGameBackground = new TextureRegion(playGameBackgroundT, 0, 0, 600, 800);
    pauseBackground = new TextureRegion(pauseBackgroundT, 0, 0, 300, 400);
    gameOverBackground = new TextureRegion(gameOverBackgroundT, 0, 0, 300, 400);
    
    wizardAnim = new Animation(0.5f, new TextureRegion(spriteSheet, 0, 0, 128, 128),
        new TextureRegion(spriteSheet, 128, 0, 128, 128),
        new TextureRegion(spriteSheet, 256, 0, 128, 128));
    
    redHomLiveAnim = new Animation(0.18f, new TextureRegion(spriteSheet, 0, 128, 32, 32),
        new TextureRegion(spriteSheet, 32, 128, 32, 32),
        new TextureRegion(spriteSheet, 64, 128, 32, 32),
        new TextureRegion(spriteSheet, 32, 128, 32, 32));
    
    yellowHomLiveAnim = new Animation(0.15f, new TextureRegion(spriteSheet, 0, 160, 32, 32),
        new TextureRegion(spriteSheet, 32, 160, 32, 32),
        new TextureRegion(spriteSheet, 64, 160, 32, 32),
        new TextureRegion(spriteSheet, 32, 160, 32, 32));
    
    blueHomLiveAnim = new Animation(0.2f, new TextureRegion(spriteSheet, 0, 192, 32, 32),
        new TextureRegion(spriteSheet, 32, 192, 32, 32),
        new TextureRegion(spriteSheet, 64, 192, 32, 32),
        new TextureRegion(spriteSheet, 32, 192, 32, 32));
    
    redHomDeadAnim = new Animation(0.1f, new TextureRegion(spriteSheet, 96, 128, 32, 32),
        new TextureRegion(spriteSheet, 128, 128, 32, 32),
        new TextureRegion(spriteSheet, 160, 128, 32, 32));
    
    yellowHomDeadAnim = new Animation(0.1f, new TextureRegion(spriteSheet, 96, 160, 32, 32),
        new TextureRegion(spriteSheet, 128, 160, 32, 32),
        new TextureRegion(spriteSheet, 160, 160, 32, 32));

    blueHomDeadAnim = new Animation(0.1f, new TextureRegion(spriteSheet, 96, 192, 32, 32),
        new TextureRegion(spriteSheet, 128, 192, 32, 32),
        new TextureRegion(spriteSheet, 160, 192, 32, 32));
    
    redSpellLiveAnim = new Animation(0.2f, new TextureRegion(spriteSheet, 192, 128, 32, 32),
        new TextureRegion(spriteSheet, 224, 128, 32, 32),
        new TextureRegion(spriteSheet, 256, 128, 32, 32),
        new TextureRegion(spriteSheet, 224, 128, 32, 32));
    
    yellowSpellLiveAnim = new Animation(0.2f, new TextureRegion(spriteSheet, 192, 160, 32, 32),
        new TextureRegion(spriteSheet, 224, 160, 32, 32),
        new TextureRegion(spriteSheet, 256, 160, 32, 32),
        new TextureRegion(spriteSheet, 224, 160, 32, 32));
    
    blueSpellLiveAnim = new Animation(0.2f, new TextureRegion(spriteSheet, 192, 192, 32, 32),
        new TextureRegion(spriteSheet, 224, 192, 32, 32),
        new TextureRegion(spriteSheet, 256, 192, 32, 32),
        new TextureRegion(spriteSheet, 224, 192, 32, 32));
    
    redSpellDeadAnim = new Animation(0.1f, new TextureRegion(spriteSheet, 288, 128, 32, 32),
        new TextureRegion(spriteSheet, 320, 128, 32, 32),
        new TextureRegion(spriteSheet, 352, 128, 32, 32));
    
    yellowSpellDeadAnim = new Animation(0.1f, new TextureRegion(spriteSheet, 288, 160, 32, 32),
        new TextureRegion(spriteSheet, 320, 160, 32, 32),
        new TextureRegion(spriteSheet, 352, 160, 32, 32));
    
    blueSpellDeadAnim = new Animation(0.1f, new TextureRegion(spriteSheet, 288, 192, 32, 32),
        new TextureRegion(spriteSheet, 320, 192, 32, 32),
        new TextureRegion(spriteSheet, 352, 192, 32, 32));
    
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
