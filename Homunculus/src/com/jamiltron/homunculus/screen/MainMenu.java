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
import com.badlogic.gdx.math.Rectangle;
import com.jamiltron.homunculus.Assets;

public class MainMenu implements Screen, InputProcessor {
  private float arrowY;
  private boolean over;
  private final HomunculusGame game;
  private final SpriteBatch spriteBatch;
  private final OrthographicCamera cam;
  private float ppuX;
  private float ppuY;
  private int width;
  private int height;
  
  private static final float SCALE = 2f;
  private static final float CAMERA_W = 18.75f;
  private static final float CAMERA_H = 25f;
  private static final float START_W = Assets.startW.getRegionWidth() / 32f * SCALE;
  private static final float START_H = Assets.startW.getRegionHeight() / 32f * SCALE;
  private static final float START_X = CAMERA_W / 2f - START_W / 2f;
  private static final float START_Y = 16f;
  private static final float HIGHSCORES_W = Assets.highScoresW.getRegionWidth() / 32f * SCALE;
  private static final float HIGHSCORES_H = Assets.highScoresW.getRegionHeight() / 32f * SCALE;
  private static final float HIGHSCORES_X = CAMERA_W / 2f - HIGHSCORES_W / 2f;
  private static final float HIGHSCORES_Y = 14f;
  private static final float CREDITS_W = Assets.creditsW.getRegionWidth() / 32f * SCALE;
  private static final float CREDITS_H = Assets.creditsW.getRegionHeight() / 32f * SCALE;
  private static final float CREDITS_X = CAMERA_W / 2f - CREDITS_W / 2f;
  private static final float CREDITS_Y = 12f;
  private static final float QUIT_W = Assets.quitW.getRegionWidth() / 32f * SCALE;
  private static final float QUIT_H = Assets.quitW.getRegionHeight() / 32f * SCALE;
  private static final float QUIT_X = CAMERA_W / 2f - QUIT_W / 2f;
  private static final float QUIT_Y = 10f;
  private static final float AREA_H = 0.75f;
  
  private Rectangle startArea = new Rectangle(2f, 
      START_Y - AREA_H, CAMERA_W - 4f, AREA_H * 3f);
  private Rectangle instructionsArea = new Rectangle(2f, 
      HIGHSCORES_Y - AREA_H, CAMERA_W - 4f, AREA_H * 3f);
  private Rectangle creditsArea = new Rectangle(2f, 
      CREDITS_Y - AREA_H, CAMERA_W - 4f, AREA_H * 3f);
  private Rectangle quitArea = new Rectangle(2f, 
      QUIT_Y - AREA_H, CAMERA_W - 4f, AREA_H * 3f);

  public MainMenu(HomunculusGame g) {
    this.cam = new OrthographicCamera(CAMERA_W, CAMERA_H);
    this.cam.position.set(CAMERA_W / 2f, CAMERA_H / 2f, 0f);
    this.cam.update();
    spriteBatch = new SpriteBatch();
    arrowY = START_Y;
    over = false;
    game = g;
  }

  public void setSize(int w, int h) {
    width = w;
    height = h;
    ppuX = width / CAMERA_W;
    ppuY = height / CAMERA_H;
  }

  @Override
  public boolean keyDown(int keycode) {
    if (keycode == Keys.Q) {
      Gdx.graphics.setDisplayMode(300, 400, false);
    }
    
    if (keycode == Keys.DOWN && arrowY == START_Y) {
      arrowY = HIGHSCORES_Y;
    } else if (keycode == Keys.UP && arrowY == HIGHSCORES_Y) {
      arrowY = START_Y;
    } else if (keycode == Keys.DOWN && arrowY == HIGHSCORES_Y) {
      arrowY = CREDITS_Y;
    } else if (keycode == Keys.DOWN && arrowY == CREDITS_Y) {
      arrowY = QUIT_Y;
    } else if (keycode == Keys.UP && arrowY == CREDITS_Y) {
      arrowY = HIGHSCORES_Y;
    } else if (keycode == Keys.UP && arrowY == QUIT_Y) {
      arrowY = CREDITS_Y;

    }
    
    if (keycode == Keys.SPACE || keycode == Keys.ENTER) {
      if (arrowY == START_Y) {
        game.setScreen(new SettingsScreen(game));
      } else if (arrowY == HIGHSCORES_Y) {
        game.setScreen(new InstructionScreen(game, this));
      } else if (arrowY == CREDITS_Y) {
        game.setScreen(new CreditsScreen(game, this));
      } else {
        over = true;
      }
    }

    return true;
  }

  private void draw(float dt) {
    spriteBatch.begin();
    renderBackground();
    renderCursor();
    renderText();
    spriteBatch.end();
  }

  private void renderCursor() {
  }
  
  private void renderBackground() {
    spriteBatch.draw(Assets.startScreenBackground, 0, 0, CAMERA_W * ppuX, CAMERA_H * ppuY);
    spriteBatch.draw(Assets.logo, 3.5f * ppuX, 19.5f * ppuY, 12.03125f * ppuX, 2.75f * ppuY);
  }

  private void renderText() {
    TextureRegion tmp = null;
    if (arrowY == START_Y) {
      tmp = Assets.startW;
    } else {
      tmp = Assets.startB;
    }
    spriteBatch.draw(tmp, START_X * ppuX, START_Y * ppuY, START_W * ppuX, START_H * ppuY);
    
    if (arrowY == HIGHSCORES_Y) {
      tmp = Assets.highScoresW;
    } else {
      tmp = Assets.highScoresB;
    }
    spriteBatch.draw(tmp, HIGHSCORES_X * ppuX, HIGHSCORES_Y * ppuY, HIGHSCORES_W * ppuX, HIGHSCORES_H * ppuY);
    
    if (arrowY == CREDITS_Y) {
      tmp = Assets.creditsW;
    } else {
      tmp = Assets.creditsB;
    }
    spriteBatch.draw(tmp, CREDITS_X * ppuX, CREDITS_Y * ppuY, CREDITS_W * ppuX, CREDITS_H * ppuY);
    
    if (arrowY == QUIT_Y) {
      tmp = Assets.quitW;
    } else {
      tmp = Assets.quitB;
    }
    spriteBatch.draw(tmp, QUIT_X * ppuX, QUIT_Y * ppuY, QUIT_W * ppuX, QUIT_H * ppuY);
  }

  private void update(float dt) {
    if (over) {
      Gdx.app.exit();
    }
    
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
    float x = screenX / ppuX;
    float y = CAMERA_H - screenY / ppuY;
    if (button == 0) {
      if ((x >= startArea.x && x <= startArea.x + startArea.width) &&
          (y >= startArea.y && y <= startArea.y + startArea.height)) {
        game.setScreen(new SettingsScreen(game));
        
      } else if ((x >= instructionsArea.x && x <= instructionsArea.x + instructionsArea.width) &&
          (y >= instructionsArea.y && y <= instructionsArea.y + instructionsArea.height)) {
        game.setScreen(new InstructionScreen(game, this));
      } else if ((x >= creditsArea.x && x <= creditsArea.x + creditsArea.width) &&
          (y >= creditsArea.y && y <= creditsArea.y + creditsArea.height)) {
        game.setScreen(new CreditsScreen(game, this));
      } else if ((x >= quitArea.x && x <= quitArea.x + quitArea.width) &&
          (y >= quitArea.y && y <= quitArea.y + quitArea.height)) {
        over = true;
      }
    }

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
    update(dt);
    draw(dt);
  }

  @Override
  public void resize(int width, int height) {
    setSize(width, height);

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
