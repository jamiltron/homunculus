package com.jamiltron.homunculus.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.jamiltron.homunculus.Assets;

public class MainMenu implements Screen, InputProcessor {
  private final Vector2 arrowPos;
  private boolean over;
  private final Game game;
  private final SpriteBatch spriteBatch;
  private final OrthographicCamera cam;
  private float ppuX;
  private float ppuY;
  private int width;
  private int height;

  private static final float CAMERA_W = 18.75f;
  private static final float CAMERA_H = 25f;

  public MainMenu(Game g) {
    this.cam = new OrthographicCamera(CAMERA_W, CAMERA_H);
    this.cam.position.set(CAMERA_W / 2f, CAMERA_H / 2f, 0f);
    this.cam.update();
    spriteBatch = new SpriteBatch();
    arrowPos = new Vector2(4, 18);
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
    if (keycode == Keys.DOWN && arrowPos.y > 12) {
      arrowPos.y -= 3;
    }

    if (keycode == Keys.UP && arrowPos.y < 18) {
      arrowPos.y += 3;
    }

    if (keycode == Keys.SPACE || keycode == Keys.ENTER) {
      if (arrowPos.y == 18) {
        game.setScreen(new SettingsScreen(game));
      } else if (arrowPos.y == 15) {
        game.setScreen(new InstructionScreen(game, this));
      } else {
        over = true;
      }
    }

    return true;
  }

  private void draw(float dt) {
    spriteBatch.begin();
    renderBackground();
    renderText();
    renderCursor();
    spriteBatch.end();
  }

  private void renderCursor() {
    spriteBatch.draw(Assets.cursor, arrowPos.x * ppuX, arrowPos.y * ppuY,
        1 * ppuX, 1 * ppuY);
  }
  
  private void renderBackground() {
    spriteBatch.draw(Assets.startScreenBackground, 0, 0, width, height);
  }

  private void renderText() {
    spriteBatch.draw(Assets.titleScreenText, 0, 0, width, height);

//    spriteBatch.draw(Assets.startGameRegion, 5 * ppuX, 16 * ppuY, 8 * ppuX,
//        4 * ppuY);
//    spriteBatch.draw(Assets.instructionsRegion, 5 * ppuX, 13 * ppuY, 8 * ppuX,
//        4 * ppuY);
//    spriteBatch
//        .draw(Assets.quitRegion, 5 * ppuX, 10 * ppuY, 8 * ppuX, 4 * ppuY);
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
