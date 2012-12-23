package com.jamiltron.homunculus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
  public static Texture background;
  public static Texture spells;
  public static Texture homunculi;
  public static Texture cursor;
  public static Texture cursorDown;
  public static Texture title;
  public static Texture startGame;
  public static Texture quit;
  public static Texture instructions;
  public static Texture instructionsPage;
  public static Texture settingsPage;

  public static TextureRegion backgroundRegion;
  public static TextureRegion blueSpellRegion;
  public static TextureRegion cursorRegion;
  public static TextureRegion cursorDownRegion;
  public static TextureRegion redSpellRegion;
  public static TextureRegion yellowSpellRegion;
  public static TextureRegion blueHomunculusRegion;
  public static TextureRegion redHomunculusRegion;
  public static TextureRegion yellowHomunculusRegion;
  public static TextureRegion titleRegion;
  public static TextureRegion startGameRegion;
  public static TextureRegion quitRegion;
  public static TextureRegion instructionsRegion;
  public static TextureRegion instructionsPageRegion;
  public static TextureRegion settingsPageRegion;
  
  public static BitmapFont font;
  
  public static Sound drop;
  public static Sound match;
  public static Sound selectMove;
  public static Sound select;
  public static Sound rotate;
  
  public static void loadSounds() {
    drop  = Gdx.audio.newSound(Gdx.files.internal("data/sfx/hit.wav"));
    match = Gdx.audio.newSound(Gdx.files.internal("data/sfx/match.wav"));
    selectMove = Gdx.audio.newSound(Gdx.files.internal("data/sfx/select1.wav"));
    select = Gdx.audio.newSound(Gdx.files.internal("data/sfx/select2.wav"));
    rotate = Gdx.audio.newSound(Gdx.files.internal("data/sfx/rotate.wav"));
  }
  
  public static void playSound(Sound sound) {
      sound.play(1);
  }
  
  public static Texture loadTexture(String file) {
    return new Texture(Gdx.files.internal(file));
  }
  
  public static void loadMenuTextures() {
    cursor = loadTexture("data/gfx/cursor.png");
    cursorRegion = new TextureRegion(cursor, 0, 0, 64, 64);
    
    cursorDown = loadTexture("data/gfx/cursor_down.png");
    cursorDownRegion = new TextureRegion(cursorDown, 0, 0, 16, 16);
    
    title = loadTexture("data/gfx/Title.png");
    titleRegion = new TextureRegion(title, 0, 0, 1028, 256);
    
    instructions = loadTexture("data/gfx/Instructions.png");
    instructionsRegion = new TextureRegion(instructions, 0, 0, 512, 128);
    
    startGame = loadTexture("data/gfx/StartGame.png");
    startGameRegion = new TextureRegion(startGame, 0, 0, 512, 128);
    
    quit = loadTexture("data/gfx/Quit.png");
    quitRegion = new TextureRegion(quit, 0, 0, 512, 128);
    
    instructionsPage = loadTexture("data/gfx/InstructionScreen.png");
    instructionsPageRegion = new TextureRegion(instructionsPage, 
        0, 0, 600, 800);
    
    settingsPage = loadTexture("data/gfx/SettingsScreen.png");
    settingsPageRegion = new TextureRegion(settingsPage,
        0, 0, 600, 800);
  }
  
  public static void loadTextures() {
    background = loadTexture("data/gfx/background.png");
    backgroundRegion = new TextureRegion(background, 0, 1024 - 800, 600, 800);
    
    
    
    spells = loadTexture("data/gfx/pills.png");
    blueSpellRegion   = new TextureRegion(spells, 0, 0, 32, 32);
    redSpellRegion    = new TextureRegion(spells, 32, 0, 32, 32);
    yellowSpellRegion = new TextureRegion(spells, 64, 0, 32, 32);
    
    homunculi = loadTexture("data/gfx/homunculi.png");
    blueHomunculusRegion   = new TextureRegion(homunculi, 0, 0, 32, 32);
    redHomunculusRegion    = new TextureRegion(homunculi, 32, 0, 32, 32);
    yellowHomunculusRegion = new TextureRegion(homunculi, 64, 0, 32, 32);
  }
  
  public static void loadFonts() {
    font = new BitmapFont(Gdx.files.internal("data/fnt/font.fnt"),
        Gdx.files.internal("data/fnt/font.png"), false);
  }

}
