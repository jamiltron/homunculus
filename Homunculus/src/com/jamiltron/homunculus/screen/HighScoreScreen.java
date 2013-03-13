package com.jamiltron.homunculus.screen;

import com.jamiltron.homunculus.HomunculusGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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
  private final MainMenu mainMenu;
  private int width;
  private int height;
  private float ppuX;
  private float ppuY;
  private float yDiff;
  private static final float CAMERA_W = 18.75f;
  private static final float CAMERA_H = 25f;
  private static final float SCALE = 2.5f;
  private static final float INSTRUCTIONS_W = Assets.highScoresW.getRegionWidth()
      / 32f * SCALE;
  private static final float INSTRUCTIONS_H = Assets.highScoresW.getRegionHeight()
      / 32f * SCALE;
  private static final float INSTRUCTIONS_Y = 22.5f; 
  private static final float INSTRUCTIONS_X = CAMERA_W / 2f - INSTRUCTIONS_W / 2f;
  private static final float TEXT_Y = 21.5f;
  private static final float TEXT_X = 1.4f;

  public HighScoreScreen(HomunculusGame g, MainMenu mm) {
    this.cam = new OrthographicCamera(CAMERA_W, CAMERA_H);
    this.cam.position.set(CAMERA_W / 2f, CAMERA_H / 2f, 0f);
    this.cam.update();
    spriteBatch = new SpriteBatch();
    game = g;
    mainMenu = mm;
  }

  @Override
  public boolean keyDown(int keycode) {
    if (keycode == Keys.ESCAPE) {
      Gdx.app.exit();
    } else {
      game.setScreen(mainMenu);
    }
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
      game.setScreen(mainMenu);
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
  
  public void renderBackground() {
    spriteBatch.draw(Assets.startScreenBackground, 0, 0, CAMERA_W * ppuX, CAMERA_H * ppuY);
    if (yDiff > 0) {
      for (float i = -1; i < yDiff / 32f; i++) {
        spriteBatch.draw(Assets.startScreenStretch, 0, (CAMERA_H + i) * ppuY, CAMERA_W * ppuX, ppuY);
      }
      spriteBatch.draw(Assets.startScreenTop, 0, height - ppuY, CAMERA_W * ppuX, ppuY);
    }

  }
  
  public void renderText() {
    Assets.font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    spriteBatch.draw(Assets.highScoresW, INSTRUCTIONS_X * ppuX, INSTRUCTIONS_Y * ppuY, INSTRUCTIONS_W * ppuX, INSTRUCTIONS_H * ppuY);
    
    //for (int i=0; i < 10; i++) {
    int i =0;
    for (SimpleEntry<String, Integer> score : game.scores) {
      if (i < 10) {
        Assets.font.draw(spriteBatch, score.getKey(), (TEXT_X + 2f) * ppuX, (TEXT_Y - ((float) i)) * ppuY);
        Assets.font.draw(spriteBatch, Integer.toString(score.getValue()), (TEXT_X + 12f) * ppuX, (TEXT_Y - ((float) i)) * ppuY);
        i++;
      }
    }
    Assets.font.draw(spriteBatch, "       press any key to continue", TEXT_X * ppuX,
        (TEXT_Y - 12.5f) * ppuY);

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
    Assets.scaleFont(ppuX / 1.5f, ppuY / 1.5f);
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
