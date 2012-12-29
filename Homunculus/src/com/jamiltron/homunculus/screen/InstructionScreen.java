package com.jamiltron.homunculus.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jamiltron.homunculus.Assets;

public class InstructionScreen implements Screen, InputProcessor {

  private final Game game;
  private final SpriteBatch spriteBatch;
  private final OrthographicCamera cam;
  private final MainMenu mainMenu;
  private int width;
  private int height;

  private static final float CAMERA_W = 18.75f;
  private static final float CAMERA_H = 25f;

  public InstructionScreen(Game g, MainMenu mm) {
    this.cam = new OrthographicCamera(CAMERA_W, CAMERA_H);
    this.cam.position.set(CAMERA_W / 2f, CAMERA_H / 2f, 0f);
    this.cam.update();
    spriteBatch = new SpriteBatch();
    game = g;
    mainMenu = mm;
  }

  @Override
  public boolean keyDown(int keycode) {
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
    // TODO Auto-generated method stub
    return false;
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
    spriteBatch.draw(Assets.instructionsPageRegion, 0, 0, width, height);
    spriteBatch.end();

  }

  @Override
  public void resize(int w, int h) {
    width = w;
    height = h;
    // TODO Auto-generated method stub

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
