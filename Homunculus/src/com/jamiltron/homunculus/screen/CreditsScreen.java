package com.jamiltron.homunculus.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jamiltron.homunculus.Assets;

public class CreditsScreen implements Screen, InputProcessor {

  private final Game game;
  private final SpriteBatch spriteBatch;
  private final OrthographicCamera cam;
  private final MainMenu mainMenu;
  private int width;
  private int height;
  private float ppuX;
  private float ppuY;
  private static final float CREDITS_Y = 21f;
  private static final float CREDITS_X = 7.05f;
  private static final float TEXT_Y = 18f;
  private static final float TEXT_X = 1.4f;

  private static final float CAMERA_W = 18.75f;
  private static final float CAMERA_H = 25f;

  public CreditsScreen(Game g, MainMenu mm) {
    this.cam = new OrthographicCamera(CAMERA_W, CAMERA_H);
    this.cam.position.set(CAMERA_W / 2f, CAMERA_H / 2f, 0f);
    this.cam.update();
    spriteBatch = new SpriteBatch();
    game = g;
    mainMenu = mm;
  }

  @Override
  public boolean keyDown(int keycode) {
    Assets.font.scale(1f);
    game.setScreen(mainMenu);
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
    Assets.font.scale(1f);
    game.setScreen(mainMenu);
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
    Assets.font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    Assets.font.scale(1f);
    Assets.font.draw(spriteBatch, "credits", CREDITS_X * ppuX,
        CREDITS_Y * ppuY);
    Assets.font.scale(-1f);
    Assets.font.draw(spriteBatch, "game development & programming", TEXT_X * ppuX,
        TEXT_Y * ppuY);
    Assets.font.draw(spriteBatch, "justin hamilton", TEXT_X * ppuX,
        (TEXT_Y - 1f) * ppuY);
    Assets.font.draw(spriteBatch, "graphic design & art", TEXT_X * ppuX,
        (TEXT_Y - 3f) * ppuY);
    Assets.font.draw(spriteBatch, "donna almendrala", TEXT_X * ppuX,
        (TEXT_Y - 4f) * ppuY);
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
