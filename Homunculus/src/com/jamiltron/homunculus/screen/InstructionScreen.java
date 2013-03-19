package com.jamiltron.homunculus.screen;

import com.jamiltron.homunculus.HomunculusGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jamiltron.homunculus.Assets;

public class InstructionScreen implements Screen, InputProcessor {

  private final HomunculusGame game;
  private final SpriteBatch spriteBatch;
  private final OrthographicCamera cam;
  private float ppuX;
  private float ppuY;
  private float width;
  private float height;
  private float yDiff;
  private float xDiff;
  private static final float CAMERA_W = 18.75f;
  private static final float CAMERA_H = 25f;
  private static final float SCALE = 3.5f;
  private static final float INSTRUCTIONS_W = Assets.instructionsW.getRegionWidth()
      / 32f * SCALE;
  private static final float INSTRUCTIONS_H = Assets.instructionsB.getRegionHeight()
      / 32f * SCALE;
  private static float INSTRUCTIONS_Y = 22.5f; 
  private static final float INSTRUCTIONS_X = CAMERA_W / 2f - INSTRUCTIONS_W / 2f;
  private static float TEXT_Y = 21.5f;
  private static final float TEXT_X = 1.4f;



  public InstructionScreen(HomunculusGame g) {
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
    spriteBatch.draw(Assets.instructionsW, INSTRUCTIONS_X * ppuX, INSTRUCTIONS_Y * ppuY, INSTRUCTIONS_W * ppuX, INSTRUCTIONS_H * ppuY);
    Assets.font.draw(spriteBatch, "a spell went awry and loosed", TEXT_X * ppuX,
        TEXT_Y * ppuY);
    Assets.font.draw(spriteBatch, "homunculi within your tower", TEXT_X * ppuX,
        (TEXT_Y - 1f) * ppuY);
    Assets.font.draw(spriteBatch, "match four of the same colors", TEXT_X * ppuX,
        (TEXT_Y - 2.5f) * ppuY);
    Assets.font.draw(spriteBatch, "to return them to the void", TEXT_X * ppuX,
        (TEXT_Y - 3.5f) * ppuY);
    
    if (game.desktopGame) {
      Assets.font.draw(spriteBatch, "use arrow or wasd keys to move", TEXT_X * ppuX,
          (TEXT_Y - 5f) * ppuY);
      Assets.font.draw(spriteBatch, "use 'z' key to rotate left", TEXT_X * ppuX,
          (TEXT_Y - 6f) * ppuY);
      Assets.font.draw(spriteBatch, "use 'x' or 'up' keys to rotate right", TEXT_X * ppuX,
          (TEXT_Y - 7f) * ppuY);
      Assets.font.draw(spriteBatch, "use 'p' key to pause", TEXT_X * ppuX,
          (TEXT_Y - 8f) * ppuY);
      Assets.font.draw(spriteBatch, "use 'esc' key to go back or quit", TEXT_X * ppuX,
          (TEXT_Y - 9f) * ppuY);
    } else {
      Assets.font.draw(spriteBatch, "use the screen buttons to move", TEXT_X * ppuX,
          (TEXT_Y - 5f) * ppuY);
      Assets.font.draw(spriteBatch, "you may also drag to move", TEXT_X * ppuX, (TEXT_Y - 6f) * ppuY);
      Assets.font.draw(spriteBatch, "tapping the screen will rotate", TEXT_X * ppuX, (TEXT_Y - 7f) * ppuY);
    }

  }

  @Override
  public void resize(int width, int height) {
    this.height = height;
    this.width = width;
    ppuX = width / CAMERA_W;
    ppuY = height / CAMERA_H;
    ppuX = Math.min(ppuX, ppuY);
    ppuY = ppuX;
    xDiff = width - CAMERA_W * ppuX;
    yDiff = height - CAMERA_H * ppuY;
    Assets.scaleFont(ppuX / (32f / ppuX), ppuY / (32f/ ppuY));
    setHeight();
  }
  
  public void setHeight() {
    INSTRUCTIONS_Y = height / ppuY - 3f;
    TEXT_Y = INSTRUCTIONS_Y - 1.5f;
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
    Gdx.input.setInputProcessor(null);
  }

  @Override
  public void resume() {
    Gdx.input.setInputProcessor(this);
  }

  @Override
  public void dispose() {
    Gdx.input.setInputProcessor(null);
  }

}
