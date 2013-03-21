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

public class SettingsScreen implements Screen, InputProcessor {

  private final HomunculusGame game;
  private final SpriteBatch spriteBatch;
  private final OrthographicCamera cam;
  private int cursorLevel;
  private float ppuX;
  private float ppuY;
  private float xDiff;
  private float yDiff;
  private int width;
  private int height;
  private int level;
  private int speed;
  private boolean music;
  private boolean sound;
  private boolean showSelector;
  private boolean playSelectMove;
  
  private static float YSPACE;
  private static final float CAMERA_W = 18.75f;
  private static final float CAMERA_H = 25f;
  private static final float SETTINGS_SCALE = 3.5f;
  private static final float ARROW_SCALE = 1.5f;
  private static final float STEP = 2.5f;
  private static final float SETTINGS_W = Assets.settingsText.getRegionWidth()
      / 32f * SETTINGS_SCALE;
  private static final float SETTINGS_H = Assets.settingsText.getRegionHeight()
      / 32f * SETTINGS_SCALE;
  private static float SETTINGS_Y;
  private static final float SETTINGS_X = CAMERA_W / 2f - SETTINGS_W / 2f;

  private static final float HEADINGS_SCALE = 2.75f;
  private static final float OPTIONS_SCALE = 2.5f;
  private static final float LEVEL_X = 2f;
  private static float LEVEL_Y;
  private static final float LEVEL_W = Assets.level.getRegionWidth() / 32f
      * HEADINGS_SCALE;
  private static final float LEVEL_H = Assets.level.getRegionHeight() / 32f
      * HEADINGS_SCALE;

  private static final float SPEED_X = LEVEL_X;
  private static float SPEED_Y;
  private static final float SPEED_W = Assets.speed.getRegionWidth() / 32f
      * HEADINGS_SCALE;
  private static final float SPEED_H = Assets.speed.getRegionHeight() / 32f
      * HEADINGS_SCALE;
  private static final float SLOW_X = 7f;
  private static float SLOW_Y;
  private static final float SLOW_W = Assets.slowW.getRegionWidth() / 32f
      * OPTIONS_SCALE;
  private static final float SLOW_H = Assets.slowW.getRegionHeight() / 32f
      * OPTIONS_SCALE;
  private static final float MED_X = SLOW_X + SLOW_W + 1.25f;
  private static float MED_Y;
  private static final float MED_W = Assets.medW.getRegionWidth() / 32f
      * OPTIONS_SCALE;
  private static final float MED_H = Assets.medW.getRegionHeight() / 32f
      * OPTIONS_SCALE;
  private static final float FAST_X = MED_X + MED_W + 1.25f;
  private static float FAST_Y;
  private static final float FAST_W = Assets.fastW.getRegionWidth() / 32f
      * OPTIONS_SCALE;
  private static final float FAST_H = Assets.fastW.getRegionHeight() / 32f
      * OPTIONS_SCALE;
  private static final float SOUND_X = SPEED_X;
  private static float SOUND_Y;
  private static final float SOUND_W = Assets.sounds.getRegionWidth() / 32f
      * HEADINGS_SCALE;
  private static final float SOUND_H = Assets.sounds.getRegionHeight() / 32f
      * HEADINGS_SCALE;
  private static final float ON_W = Assets.onW.getRegionWidth() / 32f
      * OPTIONS_SCALE;
  private static final float ON_H = Assets.onW.getRegionHeight() / 32f
      * OPTIONS_SCALE;
  private static final float ON1_X = SLOW_X + (SLOW_W - ON_W);
  private static float ON1_Y;
  private static final float OFF1_X = FAST_X;
  private static float OFF1_Y;
  private static final float OFF_W = Assets.offW.getRegionWidth() / 32f
      * OPTIONS_SCALE;
  private static final float OFF_H = Assets.offW.getRegionHeight() / 32f
      * OPTIONS_SCALE;
  private static final float MUSIC_X = SOUND_X;
  private static float MUSIC_Y;
  private static final float MUSIC_W = Assets.music.getRegionWidth() / 32f
      * HEADINGS_SCALE;
  private static final float MUSIC_H = Assets.music.getRegionHeight() / 32f
      * HEADINGS_SCALE;
  private static final float ON2_X = ON1_X;
  private static float ON2_Y;
  private static final float OFF2_X = FAST_X;
  private static float OFF2_Y;
  private static final float START_Y = 9f;
  private static final float START_W = Assets.startW.getRegionWidth() / 32f
      * HEADINGS_SCALE;
  private static final float START_H = Assets.startW.getRegionHeight() / 32f
      * HEADINGS_SCALE;
  private static final float START_X = CAMERA_W / 2f - START_W / 2f;
  private static final float LEFT_ARROW_X = 7.25f;
  private static float LEFT_ARROW_Y = LEVEL_Y - 0.25f;
  private static final float LEFT_ARROW_W = Assets.leftArrowMenu.getRegionWidth() / 32f * ARROW_SCALE;
  private static final float LEFT_ARROW_H = Assets.leftArrowMenu.getRegionHeight() / 32f * ARROW_SCALE;
  private static final float RIGHT_ARROW_X = LEFT_ARROW_X + 3.4f;
  private static float RIGHT_ARROW_Y = LEVEL_Y - 0.25f;
  private static final float RIGHT_ARROW_W = Assets.rightArrowMenu.getRegionWidth() / 32f * ARROW_SCALE;
  private static final float RIGHT_ARROW_H = Assets.rightArrowMenu.getRegionHeight() / 32f * ARROW_SCALE;
  private static final float NUM_X = LEFT_ARROW_X + 1.75f;
  private static float NUM_Y;
  private StringBuffer tmpString;

  public SettingsScreen(final HomunculusGame game) {
    this.game = game;
    showSelector = game.desktopGame ? true : false;
    cursorLevel = 0;
    music = game.settings.getMusicOn();
    sound = game.settings.getSoundOn();
    level = game.settings.getHomunculiNum();
    speed = game.settings.getSpeed().ordinal();
    cam = new OrthographicCamera(CAMERA_W, CAMERA_H);
    cam.position.set(CAMERA_W / 2f, CAMERA_H / 2f, 0f);
    cam.update();
    spriteBatch = new SpriteBatch();
    tmpString = new StringBuffer(2);
    Gdx.input.setCatchBackKey(true);
  }
  
  public void reset() {
    showSelector = game.desktopGame ? true : false;
    cursorLevel = 0;
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
    
    if (keycode == Keys.ESCAPE || keycode == Keys.BACK) {
      game.goToMainMenu();
    }
    
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
        Assets.titleMusic.setLooping(true);
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
      game.settings.setSpeed(speed);
      game.settings.setSoundOn(sound);
      game.settings.setMusicOn(music);
      game.settings.setHomunculiNum(level);
      Assets.writeSettings(game.settings, game.desktopGame);
      game.goToGame();
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
    Gdx.input.setInputProcessor(null);
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
    spriteBatch.draw(Assets.startScreenBackground, 0, 0, CAMERA_W * ppuX, CAMERA_H * ppuY);
    if (yDiff > 0) {
      for (float i = (CAMERA_H - 1) * ppuY; i <= height; i += ppuY) {
        spriteBatch.draw(Assets.startScreenStretch, 0, i, CAMERA_W * ppuX, ppuY);
      }
      spriteBatch.draw(Assets.startScreenTop, 0, height - ppuY, CAMERA_W * ppuX, ppuY);
    }
    if (xDiff > 0) {
      for (float i = CAMERA_W * ppuX; i <= width; i += ppuX) {
        spriteBatch.draw(Assets.wallScreenStretch, i, 0, ppuX, CAMERA_H * ppuY);
      }
    }
  }

  private void renderCursors() {
    spriteBatch.draw(Assets.leftArrowMenu, LEFT_ARROW_X * ppuX, (LEVEL_Y - 0.25f) * ppuY, LEFT_ARROW_W * ppuX, LEFT_ARROW_H * ppuY);
    spriteBatch.draw(Assets.rightArrowMenu, RIGHT_ARROW_X * ppuX, (LEVEL_Y - 0.25f) * ppuY, RIGHT_ARROW_W * ppuX, RIGHT_ARROW_H * ppuY);

    if (showSelector) {
      if (cursorLevel == 0) {
        spriteBatch.draw(Assets.selector, (LEVEL_X - 0.25f) * ppuX,
            (LEVEL_Y - 0.2f) * ppuY, (LEVEL_W + .4f) * ppuX, (LEVEL_H + 0.35f) * ppuY);
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
    
    tmpString.delete(0, 2);
    if (level < 10) {
      tmpString.append("0");
      tmpString.append(Integer.toString(level));
    } else {
      tmpString.append(Integer.toString(level));
    }
    Assets.font.draw(spriteBatch, tmpString, NUM_X * ppuX, NUM_Y * ppuY);
  }

  @Override
  public void resize(final int w, final int h) {
    width = w;
    height = h;
    ppuX = width / CAMERA_W;
    ppuY = height / CAMERA_H;
    
    ppuX = Math.min(ppuX, ppuY);
    ppuY = ppuX;
    yDiff = height - CAMERA_H * ppuY;
    xDiff = width - CAMERA_W * ppuX;
    Assets.scaleFont(ppuX + 4f, ppuY + 4f);
    setHeight();
  }
  
  private void setHeight() {
    YSPACE = yDiff / ppuY / 4f;
    SETTINGS_Y = height / ppuY - 3f;
    LEVEL_Y = SETTINGS_Y - STEP;
    SPEED_Y = LEVEL_Y - STEP - YSPACE;
    SLOW_Y = SPEED_Y + .09375f * 2f;
    MED_Y = SLOW_Y;
    FAST_Y = SLOW_Y - 0.0625f;
    SOUND_Y = SPEED_Y - STEP - YSPACE;
    MUSIC_Y = SOUND_Y - STEP - YSPACE;
    ON1_Y = SOUND_Y;
    OFF1_Y =  ON1_Y - 0.0625f;
    ON2_Y = MUSIC_Y;
    OFF2_Y = ON2_Y - 0.0625f;
    NUM_Y = LEVEL_Y + 0.55f;
    LEFT_ARROW_Y = LEVEL_Y - 0.25f;
    RIGHT_ARROW_Y = LEVEL_Y - 0.25f;
    
  }

  @Override
  public void resume() {
    Gdx.input.setInputProcessor(this);
  }

  @Override
  public boolean scrolled(final int amount) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void show() {
    Gdx.input.setInputProcessor(this);
    Assets.font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
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
    if (game.desktopGame) {
      return false;
    } else {
      if (button == 0) {
        playSelectMove = false;
        float x = screenX / ppuX;
        float y = height / ppuY - screenY / ppuY;
      
        // left arrow
        if (overArea(x, y, LEFT_ARROW_X, LEFT_ARROW_Y, LEFT_ARROW_W, LEFT_ARROW_H)) {
          if (level > 0) {
            level -= 1;
            playSelectMove = true;
          }
        // right arrow
        } else if (overArea(x, y, RIGHT_ARROW_X, RIGHT_ARROW_Y, RIGHT_ARROW_W, RIGHT_ARROW_H)) {
           if (level < 20) {
             level += 1;
              playSelectMove = true;
           }
        } else if (overArea(x, y, SLOW_X - 0.25f, SLOW_Y - 0.25f, SLOW_W + 0.25f, SLOW_H + 0.25f)) {
          speed = 0;
          playSelectMove = true;
        } else if (overArea(x, y, MED_X - 0.25f, MED_Y - 0.25f, MED_W + 0.25f, MED_H + 0.25f)) {
          speed = 1;
          playSelectMove = true;
        } else if (overArea(x, y, FAST_X - 0.25f, FAST_Y - 0.25f, FAST_W + 0.25f, FAST_H + 0.25f)) {
          speed = 2;
          playSelectMove = true;
        } else if (overArea(x, y, ON1_X - 1f, ON1_Y - .5f, ON_W + 1f, ON_H + .5f)) {
          sound = true;
          playSelectMove = true;
        } else if (overArea(x, y, OFF1_X - 1f, OFF1_Y - .5f, OFF_W + 1f, OFF_H + .5f)) {
          sound = false;
        }else if (overArea(x, y, ON2_X - 1f, ON2_Y - .5f, ON_W + 1f, ON_H + .5f)) {
          music = true;
          playSelectMove = true;
          Assets.titleMusic.play();
        } else if (overArea(x, y, OFF2_X - 1f, OFF2_Y - .5f, OFF_W + 1f, OFF_H + .5f)) {
          music = false;
          playSelectMove = true;
          Assets.titleMusic.stop();
        } else if (overArea(x, y, START_X, START_Y, START_W, START_H)) {
          if (sound) Assets.playSound(Assets.selectEnter);
            //final Settings settings = new Settings();
            game.settings.setSpeed(speed);
            game.settings.setSoundOn(sound);
            game.settings.setMusicOn(music);
            game.settings.setHomunculiNum(level);
            Assets.writeSettings(game.settings, game.desktopGame);
            game.setScreen(new GameScreen(game));
        }
      }
      if (sound && playSelectMove) Assets.playSound(Assets.selectMove);
      return true;
    }
  }
  
  private boolean overArea(float x, float y, float left, float bottom, float width, float height) {
    return ((x >= left && x <= left + width) &&
        (y >= bottom && y <= bottom + height));
  }

}
