package com.jamiltron.homunculus.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
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
  private static final float MENU_SPACE_Y = 2f;
  private static final float CURSOR_SPACE_X = 0.95f;
  private static final float START_Y = 16f;
  private static final float INSTRUCTIONS_Y = 14f;
  private static final float CREDITS_Y = 12f;
  private static final float QUIT_Y = 10f;
  
  private static final float AREA_SPACE_X = 2f;
  private static final float AREA_SPACE_Y = 2f;
  
  private static final float START_X = 7.7f;
  private static final float INSTRUCTIONS_X = 5.4f;
  private static final float CREDITS_X = 7.05f;
  private static final float QUIT_X = 7.9f;
  
  
  
  private Rectangle startArea;
  private Rectangle instructionsArea;
  private Rectangle creditsArea;
  private Rectangle quitArea;

  public MainMenu(Game g) {
    this.cam = new OrthographicCamera(CAMERA_W, CAMERA_H);
    this.cam.position.set(CAMERA_W / 2f, CAMERA_H / 2f, 0f);
    this.cam.update();
    spriteBatch = new SpriteBatch();
    arrowPos = new Vector2(START_X - CURSOR_SPACE_X, 16);
    over = false;
    game = g;
    Assets.font.scale(1f);
  }

  public void setSize(int w, int h) {
    width = w;
    height = h;
    ppuX = width / CAMERA_W;
    ppuY = height / CAMERA_H;
  }

  @Override
  public boolean keyDown(int keycode) {
    if (keycode == Keys.DOWN && arrowPos.y == START_Y) {
      arrowPos.y -= MENU_SPACE_Y;
      arrowPos.x = INSTRUCTIONS_X - CURSOR_SPACE_X;
    } else if (keycode == Keys.UP && arrowPos.y == INSTRUCTIONS_Y) {
      arrowPos.y += MENU_SPACE_Y;
      arrowPos.x = START_X - CURSOR_SPACE_X;
    } else if (keycode == Keys.DOWN && arrowPos.y == INSTRUCTIONS_Y) {
      arrowPos.y -= MENU_SPACE_Y;
      arrowPos.x = CREDITS_X - CURSOR_SPACE_X;
    } else if (keycode == Keys.DOWN && arrowPos.y == CREDITS_Y) {
      arrowPos.y -= MENU_SPACE_Y;
      arrowPos.x = QUIT_X - CURSOR_SPACE_X;
    } else if (keycode == Keys.UP && arrowPos.y == CREDITS_Y) {
      arrowPos.y += MENU_SPACE_Y;
      arrowPos.x = INSTRUCTIONS_X - CURSOR_SPACE_X;
    } else if (keycode == Keys.UP && arrowPos.y == QUIT_Y) {
      arrowPos.y += MENU_SPACE_Y;
      arrowPos.x = CREDITS_X - CURSOR_SPACE_X;
    }
    
    if (keycode == Keys.SPACE || keycode == Keys.ENTER) {
      if (arrowPos.y == START_Y) {
        game.setScreen(new SettingsScreen(game));
      } else if (arrowPos.y == INSTRUCTIONS_Y) {
        game.setScreen(new InstructionScreen(game, this));
      } else if (arrowPos.y == CREDITS_Y) {
        // TODO: MAKE CREDIT SCREEN
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
    renderCursor();
    renderText();
    spriteBatch.end();
  }

  private void renderCursor() {
    spriteBatch.draw(Assets.cursor, arrowPos.x * ppuX, arrowPos.y * ppuY,
        0.5f * ppuX, 0.5f * ppuY);
  }
  
  private void renderBackground() {
    spriteBatch.draw(Assets.startScreenBackground, 0, 0, width, height);
    spriteBatch.draw(Assets.logo, 3.5f * ppuX, 19.5f * ppuY, 12.03125f * ppuX, 2.75f * ppuY);
  }

  private void renderText() {
    Assets.font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    Assets.font.draw(spriteBatch, "start", START_X * ppuX,
        16.5f * ppuY);
    Assets.font.draw(spriteBatch, "instructions", INSTRUCTIONS_X * ppuX,
        14.5f * ppuY);
    Assets.font.draw(spriteBatch, "credits", CREDITS_X * ppuX,
        12.5f * ppuY);
    Assets.font.draw(spriteBatch, "quit", QUIT_X * ppuX,
        10.5f * ppuY);
    
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
    if (arrowPos.y == 18) {
      game.setScreen(new SettingsScreen(game));
    } else if (arrowPos.y == 15) {
      game.setScreen(new InstructionScreen(game, this));
    } else {
      over = true;
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
