package com.jamiltron.homunculus.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jamiltron.homunculus.Assets;
import com.jamiltron.homunculus.Settings;

public class SettingsScreen implements Screen, InputProcessor {
  
  private final Game game;
  private final SpriteBatch spriteBatch;
  private final OrthographicCamera cam;
  private int cursorLevel;
  private float ppuX;
  private float ppuY;
  private int width;
  private int height;
  private int level;
  private int speed;
  private boolean music;
  private boolean sound;
  private boolean showSelector;
  private static final float SETTINGS_Y = 22.5f;
  private static final float SETTINGS_X = 6.4f;
  private static final float LEVEL_X = 4f;
  private static final float LEVEL_Y = SETTINGS_Y - 2f;
  private static final float NUM_X = 9f;
  private static final float NUM_Y = LEVEL_Y;
  private static final float SPEED_X = LEVEL_X;
  private static final float SPEED_Y = LEVEL_Y - 2f;
  private static final float SPEEDS_X = 8f;
  private static final float SPEEDS_Y = SPEED_Y;
  private static final float SOUND_X = SPEED_X;
  private static final float SOUND_Y = SPEED_Y - 2f;
  private static final float SOUNDS_X = 8f;
  private static final float SOUNDS_Y = SOUND_Y;
  private static final float MUSIC_X = SOUND_X;
  private static final float MUSIC_Y = SOUND_Y - 2f;
  private static final float MUSICS_X = 8f;
  private static final float MUSICS_Y = MUSIC_Y;
  private static final float START_X = MUSIC_X;
  private static final float START_Y = MUSIC_Y - 2f;

  private static final float CAMERA_W = 18.75f;
  private static final float CAMERA_H = 25f;

  public SettingsScreen(Game g) {
    showSelector = true;
    cursorLevel = 0;
    music = true;
    sound = true;
    level = 0;
    speed = 0;
    this.cam = new OrthographicCamera(CAMERA_W, CAMERA_H);
    this.cam.position.set(CAMERA_W / 2f, CAMERA_H / 2f, 0f);
    this.cam.update();
    spriteBatch = new SpriteBatch();
    game = g;
  }

  @Override
  public boolean keyDown(int keycode) {
    showSelector = true;
    if (cursorLevel == 0) {
      if (keycode == Keys.LEFT && level > 0) {
        level -= 1;
      } else if (keycode == Keys.RIGHT && level < 20) {
        level += 1;
      }
    } else if (cursorLevel == 1) {
      if (keycode == Keys.LEFT && speed > 0) {
        speed -= 1;
      } else if (keycode == Keys.RIGHT && speed < 2) {
        speed += 1;
      }
    } else if (cursorLevel == 2) {
      if (keycode == Keys.LEFT && sound == false) {
        sound = true;
      } else if (keycode == Keys.RIGHT && sound == true) {
        sound = false;
      }
    } else if (cursorLevel == 3) {
      if (keycode == Keys.LEFT && music == false) {
        music = true;
      } else if (keycode == Keys.RIGHT && music == true) {
        music = false;
      }
    }
    
          
    if ((keycode == Keys.DOWN || keycode == Keys.ENTER || keycode == Keys.SPACE) && cursorLevel < 4) {
      cursorLevel += 1;
    } else if ((keycode == Keys.UP || keycode == Keys.BACKSPACE)&& cursorLevel > 0) {
      cursorLevel -= 1;
    }
    
    if (cursorLevel == 4 && (keycode == Keys.ENTER)) {
      Settings settings = new Settings();
      settings.setSpeed(speed);
      settings.setSoundOn(sound);
      settings.setMusicOn(music);
      settings.setHomunculiNum(level);
      game.setScreen(new GameScreen(game, settings));
    }

    return true;
  }

  @Override
  public boolean keyUp(int keycode) {
    return true;
  }

  @Override
  public boolean keyTyped(char character) {
    // TODO Auto-generated method stub
    return true;
  }

  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    // TODO Auto-generated method stub
    return true;
  }

  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    Settings settings = new Settings();
    settings.setSpeed(speed);
    settings.setSoundOn(sound);
    settings.setMusicOn(music);
    settings.setHomunculiNum(level);
    game.setScreen(new GameScreen(game, settings));
    return true;
  }

  @Override
  public boolean touchDragged(int screenX, int screenY, int pointer) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean mouseMoved(int screenX, int screenY) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean scrolled(int amount) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void render(float dt) {
    Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    spriteBatch.begin();
    renderBackground();
    renderCursors();
    renderText();
    spriteBatch.end();
  }

  private void renderText() {
    Assets.font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    Assets.font.scale(1f);
    Assets.font.draw(spriteBatch, "settings", SETTINGS_X * ppuX,
        SETTINGS_Y * ppuY);
    Assets.font.scale(-0.5f);
    Assets.font.draw(spriteBatch, "level", LEVEL_X * ppuX,
        LEVEL_Y * ppuY);
    Assets.font.scale(-0.125f);
    Assets.font.draw(spriteBatch, Integer.toString(level), NUM_X * ppuX, 
        NUM_Y * ppuY);
    Assets.font.scale(0.125f);
    Assets.font.draw(spriteBatch, "speed", SPEED_X * ppuX, SPEED_Y * ppuY);
    
    Assets.font.scale(-0.125f);
    if (speed == 0) {
      Assets.font.setColor(1f, 1f, 1f, 1.0f);
    } else {
      Assets.font.setColor(0.6f, 0.6f, 0.6f, 1.0f);
    }
    Assets.font.draw(spriteBatch, "slow", SPEEDS_X * ppuX, SPEEDS_Y * ppuY);
    
    if (speed == 1) {
      Assets.font.setColor(1f, 1f, 1f, 1.0f);
    } else {
      Assets.font.setColor(0.6f, 0.6f, 0.6f, 1.0f);
    }
    Assets.font.draw(spriteBatch, "med", (SPEEDS_X + 3f) * ppuX, SPEEDS_Y * ppuY);
    
    if (speed == 2) {
      Assets.font.setColor(1f, 1f, 1f, 1.0f);
    } else {
      Assets.font.setColor(0.6f, 0.6f, 0.6f, 1.0f);
    }
    Assets.font.draw(spriteBatch, "fast", (SPEEDS_X + 6f) * ppuX, SPEEDS_Y * ppuY);
    Assets.font.scale(0.125f);
    
    Assets.font.setColor(1f, 1f, 1f, 1.0f);
    Assets.font.draw(spriteBatch, "sounds", SOUND_X * ppuX, SOUND_Y * ppuY);
    Assets.font.scale(-0.125f);
    if (sound == false) {
      Assets.font.setColor(0.6f, 0.6f, 0.6f, 1.0f);
    }
    Assets.font.draw(spriteBatch, "on", SOUNDS_X * ppuX, SOUNDS_Y * ppuY);
    
    if (sound == true) {
      Assets.font.setColor(0.6f, 0.6f, 0.6f, 1.0f);
    } else {
      Assets.font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }
    Assets.font.draw(spriteBatch, "off", (SOUNDS_X + 3f) * ppuX, SOUNDS_Y * ppuY);
    Assets.font.scale(0.125f);
    
    Assets.font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    Assets.font.draw(spriteBatch, "music", MUSIC_X * ppuX, MUSIC_Y * ppuY);
    Assets.font.scale(-0.125f);
    if (music == false) {
      Assets.font.setColor(0.6f, 0.6f, 0.6f, 1.0f);
    }
    Assets.font.draw(spriteBatch, "on", MUSICS_X * ppuX, MUSICS_Y * ppuY);
    
    if (music == true) {
      Assets.font.setColor(0.6f, 0.6f, 0.6f, 1.0f);
    } else {
      Assets.font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }
    Assets.font.draw(spriteBatch, "off", (MUSICS_X + 3f) * ppuX, MUSICS_Y * ppuY);
    Assets.font.scale(0.125f);
    
    Assets.font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    Assets.font.draw(spriteBatch, "start", START_X * ppuX, START_Y * ppuY);
    Assets.font.scale(-0.5f);
  }

  private void renderBackground() {
    spriteBatch.draw(Assets.startScreenBackground, 0, 0, width, height);
  }

  private void renderCursors() {
    spriteBatch.draw(Assets.leftArrow, 7.25f * ppuX, (LEVEL_Y - .75f) * ppuY);
    spriteBatch.draw(Assets.rightArrow, 10.25f * ppuX, (LEVEL_Y - .75f) * ppuY);
    
    if (showSelector) {
      if (cursorLevel == 0) {
        spriteBatch.draw(Assets.selector, (LEVEL_X - 0.25f) * ppuX, (LEVEL_Y -0.6f) * ppuY, 2.9f * ppuX, 0.9f * ppuY);
      } else if (cursorLevel == 1 && speed == 0) {
        spriteBatch.draw(Assets.selector, (SPEEDS_X - 0.25f) * ppuX, (SPEEDS_Y -0.6f) * ppuY, 2.3f * ppuX, 0.9f * ppuY);
      } else if (cursorLevel == 1 && speed == 1) {
        spriteBatch.draw(Assets.selector, (SPEEDS_X + 2.75f) * ppuX, (SPEEDS_Y -0.6f) * ppuY, 1.9f * ppuX, 0.9f * ppuY);
      } else if (cursorLevel == 1 && speed == 2) {
        spriteBatch.draw(Assets.selector, (SPEEDS_X + 5.75f) * ppuX, (SPEEDS_Y -0.6f) * ppuY, 2.2f * ppuX, 0.9f * ppuY);
      } else if (cursorLevel == 2 && sound == true) {
        spriteBatch.draw(Assets.selector, (SOUNDS_X -0.25f) * ppuX, (SOUNDS_Y -0.6f) * ppuY, 1.3f * ppuX, 0.9f * ppuY);
      } else if (cursorLevel == 2 && sound == false) {
        spriteBatch.draw(Assets.selector, (SOUNDS_X + 2.75f) * ppuX, (SOUNDS_Y -0.6f) * ppuY, 1.65f * ppuX, 0.9f * ppuY);
      } else if (cursorLevel == 3 && music == true) {
        spriteBatch.draw(Assets.selector, (MUSICS_X -0.25f) * ppuX, (MUSICS_Y -0.6f) * ppuY, 1.3f * ppuX, 0.9f * ppuY);
      } else if (cursorLevel == 3 && music == false) {
        spriteBatch.draw(Assets.selector, (MUSICS_X + 2.75f) * ppuX, (MUSICS_Y -0.6f) * ppuY, 1.65f * ppuX, 0.9f * ppuY);
      } else if (cursorLevel == 4) {
        spriteBatch.draw(Assets.selector, (START_X - 0.25f) * ppuX, (START_Y - 0.6f) * ppuY, 2.75f * ppuX, 0.9f * ppuY);
      }
    }
  }

  @Override
  public void resize(int w, int h) {
    width = w;
    height = h;
    ppuX = width / CAMERA_W;
    ppuY = height / CAMERA_H;
  }

  @Override
  public void show() {
    Gdx.input.setInputProcessor(this);

  }

  @Override
  public void hide() {
    Gdx.input.setInputProcessor(null);

  }

  @Override
  public void pause() {
    // TODO Auto-generated method stub

  }

  @Override
  public void resume() {
    // TODO Auto-generated method stub

  }

  @Override
  public void dispose() {
    Gdx.input.setInputProcessor(null);

  }

}
