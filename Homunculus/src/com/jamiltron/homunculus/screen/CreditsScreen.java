package com.jamiltron.homunculus.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jamiltron.homunculus.Assets;
import com.jamiltron.homunculus.HomunculusGame;

public class CreditsScreen implements Screen, InputProcessor {

  private final HomunculusGame game;
  private final SpriteBatch spriteBatch;
  private final OrthographicCamera cam;
  private final MainMenu mainMenu;
  private int width;
  private int height;
  private float ppuX;
  private float ppuY;
  private static final float CAMERA_W = 18.75f;
  private static final float CAMERA_H = 25f;
  private static final float SCALE = 2.5f;
  private static final float CREDITS_W = Assets.creditsW.getRegionWidth() / 32f * SCALE;
  private static final float CREDITS_H = Assets.creditsW.getRegionHeight() / 32f * SCALE;
  private static final float CREDITS_Y = 22.5f;
  private static final float CREDITS_X = CAMERA_W / 2f - CREDITS_W / 2f;
  private static final float TEXT_Y = 20.5f;
  private static final float TEXT_X = 1.4f;

  public CreditsScreen(HomunculusGame g, MainMenu mm) {
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
    return false;
  }

  @Override
  public boolean scrolled(int amount) {
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
    spriteBatch.draw(Assets.startScreenBackground, 0, 0, width, height);
  }
  
  public void renderText() {
    spriteBatch.draw(Assets.creditsW, CREDITS_X * ppuX, CREDITS_Y * ppuY, CREDITS_W * ppuX, CREDITS_H * ppuY);
    Assets.font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    Assets.font.draw(spriteBatch, "game development & programming", (TEXT_X + 1f) * ppuX,
        TEXT_Y * ppuY);
    Assets.font.draw(spriteBatch, "justin hamilton", (TEXT_X + 5f) * ppuX,
        (TEXT_Y - 1f) * ppuY);
    Assets.font.draw(spriteBatch, "graphic design & art", (TEXT_X + 4f) * ppuX,
        (TEXT_Y - 4f) * ppuY);
    Assets.font.draw(spriteBatch, "donna almendrala", (TEXT_X + 5f) * ppuX,
        (TEXT_Y - 5f) * ppuY);
    Assets.font.draw(spriteBatch, "music", (TEXT_X + 7f) * ppuX,
        (TEXT_Y - 8f) * ppuY);
    Assets.font.draw(spriteBatch, "lucky lion studios", (TEXT_X + 4.25f) * ppuX,
        (TEXT_Y - 9f) * ppuY);
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
