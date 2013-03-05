package com.jamiltron.homunculus.screen;

import com.jamiltron.homunculus.HomunculusGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jamiltron.homunculus.Assets;
import com.jamiltron.homunculus.Settings;

public class SettingsScreen implements Screen, InputProcessor {

  private final HomunculusGame game;
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
  private boolean playSelectMove;
  
  private static final float CAMERA_W = 18.75f;
  private static final float CAMERA_H = 25f;
  private static final float SETTINGS_SCALE = 2.5f;
  private static final float SETTINGS_W = Assets.settingsText.getRegionWidth()
      / 32f * SETTINGS_SCALE;
  private static final float SETTINGS_H = Assets.settingsText.getRegionHeight()
      / 32f * SETTINGS_SCALE;
  private static final float SETTINGS_Y = 22.5f;
  private static final float SETTINGS_X = CAMERA_W / 2f - SETTINGS_W / 2f;

  private static final float HEADINGS_SCALE = 2f;
  private static final float OPTIONS_SCALE = 1.875f;
  private static final float LEVEL_X = 4f;
  private static final float LEVEL_Y = SETTINGS_Y - 2f;
  private static final float LEVEL_W = Assets.level.getRegionWidth() / 32f
      * HEADINGS_SCALE;
  private static final float LEVEL_H = Assets.level.getRegionHeight() / 32f
      * HEADINGS_SCALE;

  private static final float SPEED_X = LEVEL_X;
  private static final float SPEED_Y = LEVEL_Y - 2.09375f;
  private static final float SPEED_W = Assets.speed.getRegionWidth() / 32f
      * HEADINGS_SCALE;
  private static final float SPEED_H = Assets.speed.getRegionHeight() / 32f
      * HEADINGS_SCALE;
  private static final float SLOW_X = 9f;
  private static final float SLOW_Y = SPEED_Y + .09375f * 2f;
  private static final float SLOW_W = Assets.slowW.getRegionWidth() / 32f
      * OPTIONS_SCALE;
  private static final float SLOW_H = Assets.slowW.getRegionHeight() / 32f
      * OPTIONS_SCALE;
  private static final float MED_X = SLOW_X + 3f;
  private static final float MED_Y = SLOW_Y;
  private static final float MED_W = Assets.medW.getRegionWidth() / 32f
      * OPTIONS_SCALE;
  private static final float MED_H = Assets.medW.getRegionHeight() / 32f
      * OPTIONS_SCALE;
  private static final float FAST_X = MED_X + 2.5f;
  private static final float FAST_Y = SLOW_Y - 0.0625f;
  private static final float FAST_W = Assets.fastW.getRegionWidth() / 32f
      * OPTIONS_SCALE;
  private static final float FAST_H = Assets.fastW.getRegionHeight() / 32f
      * OPTIONS_SCALE;
  private static final float SOUND_X = SPEED_X;
  private static final float SOUND_Y = SPEED_Y - 2f;
  private static final float SOUND_W = Assets.sounds.getRegionWidth() / 32f
      * HEADINGS_SCALE;
  private static final float SOUND_H = Assets.sounds.getRegionHeight() / 32f
      * HEADINGS_SCALE;
  private static final float ON1_X = 9f;
  private static final float ON1_Y = SOUND_Y;
  private static final float ON_W = Assets.onW.getRegionWidth() / 32f
      * OPTIONS_SCALE;
  private static final float ON_H = Assets.onW.getRegionHeight() / 32f
      * OPTIONS_SCALE;
  private static final float OFF1_X = ON1_X + 3f;
  private static final float OFF1_Y = ON1_Y - 0.0625f;
  private static final float OFF_W = Assets.offW.getRegionWidth() / 32f
      * OPTIONS_SCALE;
  private static final float OFF_H = Assets.offW.getRegionHeight() / 32f
      * OPTIONS_SCALE;
  private static final float MUSIC_X = SOUND_X;
  private static final float MUSIC_Y = SOUND_Y - 2f;
  private static final float MUSIC_W = Assets.music.getRegionWidth() / 32f
      * HEADINGS_SCALE;
  private static final float MUSIC_H = Assets.music.getRegionHeight() / 32f
      * HEADINGS_SCALE;
  private static final float ON2_X = 9f;
  private static final float ON2_Y = MUSIC_Y;
  private static final float OFF2_X = ON2_X + 3f;
  private static final float OFF2_Y = ON2_Y - 0.0625f;
  private static final float START_Y = MUSIC_Y - 2f;
  private static final float START_W = Assets.startW.getRegionWidth() / 32f
      * HEADINGS_SCALE;
  private static final float START_H = Assets.startW.getRegionHeight() / 32f
      * HEADINGS_SCALE;
  private static final float START_X = CAMERA_W / 2f - START_W / 2f;
  private static final float NUM_X = LEVEL_X + 5f;
  private static final float NUM_Y = LEVEL_Y + 0.35f;

  public SettingsScreen(final HomunculusGame game) {
    this.game = game;
    showSelector = true;
    cursorLevel = 0;
    music = game.settings.getMusicOn();
    sound = game.settings.getSoundOn();
    level = 0;
    speed = 0;
    cam = new OrthographicCamera(CAMERA_W, CAMERA_H);
    cam.position.set(CAMERA_W / 2f, CAMERA_H / 2f, 0f);
    cam.update();
    spriteBatch = new SpriteBatch();
  }

  @Override
  public void dispose() {
    Gdx.input.setInputProcessor(null);

  }

  @Override
  public void hide() {
    Gdx.input.setInputProcessor(null);
  }

  @Override
  public boolean keyDown(final int keycode) {
    showSelector = true;
    playSelectMove = false;
    
    if (cursorLevel == 0) {
      if (keycode == Keys.LEFT && level > 0) {
        level -= 1;
        playSelectMove = true;
      } else if (keycode == Keys.RIGHT && level < 20) {
        level += 1;
        playSelectMove = true;
      }
    } else if (cursorLevel == 1) {
      if (keycode == Keys.LEFT && speed > 0) {
        speed -= 1;
        playSelectMove = true;
      } else if (keycode == Keys.RIGHT && speed < 2) {
        speed += 1;
        playSelectMove = true;
      }
    } else if (cursorLevel == 2) {
      if (keycode == Keys.LEFT && sound == false) {
        sound = true;
        playSelectMove = true;
      } else if (keycode == Keys.RIGHT && sound == true) {
        sound = false;
      }
    } else if (cursorLevel == 3) {
      if (keycode == Keys.LEFT && music == false) {
        playSelectMove = true;
        music = true;
        Assets.titleMusic.play();
      } else if (keycode == Keys.RIGHT && music == true) {
        playSelectMove = true;
        music = false;
        Assets.titleMusic.stop();
      }
    }

    if (cursorLevel <= 4 &&
        (keycode == Keys.DOWN || keycode == Keys.ENTER || keycode == Keys.SPACE)) {
      playSelectMove = true;
      cursorLevel += 1;
    } else if ((keycode == Keys.UP || keycode == Keys.BACKSPACE)
        && cursorLevel > 0) {
      playSelectMove = true;
      cursorLevel -= 1;
    }

    if (cursorLevel == 5 && (keycode == Keys.ENTER || keycode == Keys.DOWN || keycode == Keys.SPACE)) {
      if (sound) Assets.playSound(Assets.selectEnter);
      final Settings settings = new Settings();
      settings.setSpeed(speed);
      settings.setSoundOn(sound);
      settings.setMusicOn(music);
      settings.setHomunculiNum(level);
      game.settings = settings;
      game.setScreen(new GameScreen(game));
    }
    
    if (sound && playSelectMove) Assets.playSound(Assets.selectMove);
    return true;
  }

  @Override
  public boolean keyTyped(final char character) {
    // TODO Auto-generated method stub
    return true;
  }

  @Override
  public boolean keyUp(final int keycode) {
    return true;
  }

  @Override
  public boolean mouseMoved(final int screenX, final int screenY) {
    return false;
  }

  @Override
  public void pause() {
    // TODO Auto-generated method stub

  }

  @Override
  public void render(final float dt) {
    Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    spriteBatch.begin();
    renderBackground();
    renderCursors();
    renderText();
    spriteBatch.end();
  }

  private void renderBackground() {
    spriteBatch.draw(Assets.startScreenBackground, 0, 0, CAMERA_W * ppuX,
        CAMERA_H * ppuY);
  }

  private void renderCursors() {
    spriteBatch.draw(Assets.leftArrow, 7.25f * ppuX, (LEVEL_Y - 0.25f) * ppuY);
    spriteBatch
        .draw(Assets.rightArrow, 10.25f * ppuX, (LEVEL_Y - 0.25f) * ppuY);

    if (showSelector) {
      if (cursorLevel == 0) {
        spriteBatch.draw(Assets.selector, (LEVEL_X - 0.25f) * ppuX,
            (LEVEL_Y - 0.2f) * ppuY, 2.9f * ppuX, 0.9f * ppuY);
      } else if (cursorLevel == 1 && speed == 0) {
        spriteBatch.draw(Assets.selector, (SLOW_X - .2f) * ppuX, (SLOW_Y - .2f)
            * ppuY, (SLOW_W + .4f) * ppuX, (SLOW_H + .35f) * ppuY);
      } else if (cursorLevel == 1 && speed == 1) {
        spriteBatch.draw(Assets.selector, (MED_X - .2f) * ppuX, (MED_Y - .2f)
            * ppuY, (MED_W + .4f) * ppuX, (MED_H + .35f) * ppuY);
      } else if (cursorLevel == 1 && speed == 2) {
        spriteBatch.draw(Assets.selector, (FAST_X - .2f) * ppuX, (FAST_Y - .2f)
            * ppuY, (FAST_W + .4f) * ppuX, (FAST_H + .35f) * ppuY);
      } else if (cursorLevel == 2 && sound == true) {
        spriteBatch.draw(Assets.selector, (ON1_X - .2f) * ppuX, (ON1_Y - .2f)
            * ppuY, (ON_W + .4f) * ppuX, (ON_H + .4f) * ppuY);
      } else if (cursorLevel == 2 && sound == false) {
        spriteBatch.draw(Assets.selector, (OFF1_X - .2f) * ppuX, (OFF1_Y - .2f)
            * ppuY, (OFF_W + .4f) * ppuX, (OFF_H + .35f) * ppuY);
      } else if (cursorLevel == 3 && music == true) {
        spriteBatch.draw(Assets.selector, (ON2_X - .2f) * ppuX, (ON2_Y - .2f)
            * ppuY, (ON_W + .4f) * ppuX, (ON_H + .4f) * ppuY);
      } else if (cursorLevel == 3 && music == false) {
        spriteBatch.draw(Assets.selector, (OFF2_X - .2f) * ppuX, (OFF2_Y - .2f)
            * ppuY, (OFF_W + .4f) * ppuX, (OFF_H + .35f) * ppuY);
      } else if (cursorLevel == 4) {
        spriteBatch.draw(Assets.selector, (START_X - 0.2f) * ppuX,
            (START_Y - 0.2f) * ppuY, (START_W + .4f) * ppuX, (START_H + .35f)
                * ppuY);
      }
    }
  }

  private void renderText() {
    TextureRegion tmp;

    spriteBatch.draw(Assets.settingsText, SETTINGS_X * ppuX, SETTINGS_Y * ppuY,
        SETTINGS_W * ppuX, SETTINGS_H * ppuY);
    spriteBatch.draw(Assets.level, LEVEL_X * ppuX, LEVEL_Y * ppuY, LEVEL_W
        * ppuX, LEVEL_H * ppuY);
    spriteBatch.draw(Assets.speed, SPEED_X * ppuX, SPEED_Y * ppuY, SPEED_W
        * ppuX, SPEED_H * ppuY);

    if (speed == 0) {
      tmp = Assets.slowW;
    } else {
      tmp = Assets.slowB;
    }
    spriteBatch.draw(tmp, SLOW_X * ppuX, SLOW_Y * ppuY, SLOW_W * ppuX, SLOW_H
        * ppuY);

    if (speed == 1) {
      tmp = Assets.medW;
    } else {
      tmp = Assets.medB;
    }
    spriteBatch.draw(tmp, MED_X * ppuX, MED_Y * ppuY, MED_W * ppuX, MED_H
        * ppuY);

    if (speed == 2) {
      tmp = Assets.fastW;
    } else {
      tmp = Assets.fastB;
    }
    spriteBatch.draw(tmp, FAST_X * ppuX, FAST_Y * ppuY, FAST_W * ppuX, FAST_H
        * ppuY);

    spriteBatch.draw(Assets.sounds, SOUND_X * ppuX, SOUND_Y * ppuY, SOUND_W
        * ppuX, SOUND_H * ppuY);

    if (sound) {
      tmp = Assets.onW;
    } else {
      tmp = Assets.onB;
    }
    spriteBatch.draw(tmp, ON1_X * ppuX, ON1_Y * ppuY, ON_W * ppuX, ON_H * ppuY);

    if (!sound) {
      tmp = Assets.offW;
    } else {
      tmp = Assets.offB;
    }
    spriteBatch.draw(tmp, OFF1_X * ppuX, OFF1_Y * ppuY, OFF_W * ppuX, OFF_H
        * ppuY);

    spriteBatch.draw(Assets.music, MUSIC_X * ppuX, MUSIC_Y * ppuY, MUSIC_W
        * ppuX, MUSIC_H * ppuY);

    if (music) {
      tmp = Assets.onW;
    } else {
      tmp = Assets.onB;
    }
    spriteBatch.draw(tmp, ON2_X * ppuX, ON2_Y * ppuY, ON_W * ppuX, ON_H * ppuY);

    if (!music) {
      tmp = Assets.offW;
    } else {
      tmp = Assets.offB;
    }
    spriteBatch.draw(tmp, OFF2_X * ppuX, OFF2_Y * ppuY, OFF_W * ppuX, OFF_H
        * ppuY);

    spriteBatch.draw(Assets.startW, START_X * ppuX, START_Y * ppuY, START_W
        * ppuX, START_H * ppuY);
    
    Assets.font.draw(spriteBatch, Integer.toString(level), NUM_X * ppuX, NUM_Y * ppuY);
  }

  @Override
  public void resize(final int w, final int h) {
    width = w;
    height = h;
    ppuX = width / CAMERA_W;
    ppuY = height / CAMERA_H;
    Assets.font.setScale(1.5f + (ppuX - 32f) / 32f, 1.5f + (ppuY - 32f) / 32f);
  }

  @Override
  public void resume() {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean scrolled(final int amount) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void show() {
    Gdx.input.setInputProcessor(this);
  }

  @Override
  public boolean touchDown(final int screenX, final int screenY,
      final int pointer, final int button) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean touchDragged(final int screenX, final int screenY,
      final int pointer) {
    return false;
  }

  @Override
  public boolean touchUp(final int screenX, final int screenY,
      final int pointer, final int button) {
/*    final Settings settings = new Settings();
    settings.setSpeed(speed);
    settings.setSoundOn(sound);
    settings.setMusicOn(music);
    settings.setHomunculiNum(level);
    game.settings = settings;
    game.setScreen(new GameScreen(game));
    return true;*/
    return false;
  }

}
