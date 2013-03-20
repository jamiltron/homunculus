package com.jamiltron.homunculus.screen;

import com.jamiltron.homunculus.HomunculusGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jamiltron.homunculus.Assets;
import java.util.AbstractMap.SimpleEntry;

public class HighScoreScreen implements Screen, InputProcessor {

  private final HomunculusGame game;
  private final SpriteBatch spriteBatch;
  private final OrthographicCamera cam;
  private int width;
  private int height;
  private float ppuX;
  private float ppuY;
  private float yDiff;
  private float xDiff;
  private static final float CAMERA_W = 18.75f;
  private static final float CAMERA_H = 25f;
  private static final float SCALE = 3.5f;
  private static final float INSTRUCTIONS_W = Assets.highScoresW.getRegionWidth()
      / 32f * SCALE;
  private static final float INSTRUCTIONS_H = Assets.highScoresW.getRegionHeight()
      / 32f * SCALE;
  private static float INSTRUCTIONS_Y; 
  private static final float INSTRUCTIONS_X = CAMERA_W / 2f - INSTRUCTIONS_W / 2f;
  private static float TEXT_Y;
  private static final float TEXT_X = 1.4f;

  public HighScoreScreen(HomunculusGame g) {
    this.cam = new OrthographicCamera(CAMERA_W, CAMERA_H);
    this.cam.position.set(CAMERA_W / 2f, CAMERA_H / 2f, 0f);
    this.cam.update();
    spriteBatch = new SpriteBatch();
    game = g;
  }

  @Override
  public boolean keyDown(int keycode) {
    game.goToMainMenu();
    return true;
  }

  @Override
  public boolean keyUp(int keycode) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean keyTyped(char character) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    if (!game.desktopGame) {
      game.goToMainMenu();
      return true;
    } else {
      return false;
    }
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
  public void render(float delta) {
    Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    spriteBatch.begin();
    renderBackground();
    renderText();
    spriteBatch.end();
  }
  
  private void setHeight() {
    INSTRUCTIONS_Y = height / ppuY - 3f;
    TEXT_Y = INSTRUCTIONS_Y - 1.5f;
  }
  
  public void renderBackground() {
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
  
  public void renderText() {
    Assets.font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    spriteBatch.draw(Assets.highScoresW, INSTRUCTIONS_X * ppuX, INSTRUCTIONS_Y * ppuY, INSTRUCTIONS_W * ppuX, INSTRUCTIONS_H * ppuY);
    
    int i =0;
    for (SimpleEntry<String, Integer> score : game.scores) {
      if (i < 10) {
        Assets.font.draw(spriteBatch, score.getKey(), (TEXT_X + 2f) * ppuX, (TEXT_Y - ((float) i)) * ppuY);
        Assets.font.draw(spriteBatch, Integer.toString(score.getValue()), (TEXT_X + 9f) * ppuX, (TEXT_Y - ((float) i)) * ppuY);
        i++;
      }
    }
  }

  @Override
  public void resize(int w, int h) {
    width = w;
    height = h;
    ppuX = width / CAMERA_W;
    ppuY = height / CAMERA_H;
    ppuX = Math.min(ppuX, ppuY);
    ppuY = ppuX;
    yDiff = height - CAMERA_H * ppuY;
    xDiff = width - CAMERA_W * ppuX;
    Assets.scaleFont(ppuX / (32f / ppuX), ppuY / (32f / ppuY));
    setHeight();
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
    // do nothing
  }

  @Override
  public void resume() {
    // do nothing
  }

  @Override
  public void dispose() {
    Gdx.input.setInputProcessor(null);
  }

}
