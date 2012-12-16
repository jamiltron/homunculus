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

  public static TextureRegion backgroundRegion;
  public static TextureRegion blueSpellRegion;
  public static TextureRegion redSpellRegion;
  public static TextureRegion yellowSpellRegion;
  public static TextureRegion blueHomunculusRegion;
  public static TextureRegion redHomunculusRegion;
  public static TextureRegion yellowHomunculusRegion;
  
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
    font = new BitmapFont(Gdx.files.internal("data/font.fnt"),
        Gdx.files.internal("data/font.png"), false);
  }

}
