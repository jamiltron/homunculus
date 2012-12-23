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
import com.jamiltron.homunculus.Settings;

public class SettingsScreen implements Screen, InputProcessor {
  
  private Game game;
  private SpriteBatch spriteBatch;
  private OrthographicCamera cam;
  private MainMenu mainMenu;
  private Vector2 arrowPos1;
  private Vector2 arrowPos2;
  private Vector2 arrowPos3;
  private int currentArrow;
  private boolean canMove;
  private int currKeyDown;
  private float ppuX;
  private float ppuY;
  private int width;
  private int height;
  private int numHomunculi;
  private int speed;
  private boolean music;
  private boolean sound;
  
  private static final float CAMERA_W = 18.75f;
  private static final float CAMERA_H = 25f;
  
  public SettingsScreen(Game g, MainMenu mm) {
    music = true;
    sound = true;
    numHomunculi = 0;
    speed = 0;
    canMove = true;
    currentArrow = 1;
    arrowPos1 = new Vector2(2f, 15f);
    this.cam = new OrthographicCamera(CAMERA_W, CAMERA_H);
    this.cam.position.set(CAMERA_W / 2f, CAMERA_H / 2f, 0f);
    this.cam.update();
    spriteBatch = new SpriteBatch();
    game = g;
    mainMenu = mm;
  }
  
  @Override
  public boolean keyDown(int keycode) {
    if (currentArrow == 1) {
      if (canMove && keycode == Keys.LEFT) {
        currKeyDown = keycode;
        canMove = false;
        if (arrowPos1.x > 2) {
          arrowPos1.x -= .75f;
          numHomunculi -= 1;
        }
      } else if (canMove && keycode == Keys.RIGHT){
        currKeyDown = keycode;
        canMove = false;
        if (arrowPos1.x < 16.5f) {
          arrowPos1.x += .75f;
          numHomunculi += 1;
        }
      } else if (keycode == Keys.DOWN || keycode == Keys.ENTER) {
          if (arrowPos2 == null) {
            arrowPos2 = new Vector2(0.5f, 10f);
            canMove = true;
          }
          currentArrow = 2;
        }
      
    } else if (currentArrow == 2) {
      if (canMove && keycode == Keys.LEFT) {
        currKeyDown = keycode;
        canMove = false;
        if (arrowPos2.x > 0.5) {
          arrowPos2.x -= 5f;
          speed -= 1;
        }
      } else if (canMove && keycode == Keys.RIGHT){
        currKeyDown = keycode;
        canMove = false;
        if (arrowPos2.x < 10.5f) {
          arrowPos2.x += 5f;
          speed += 1;
        }
      } else if (keycode == Keys.ENTER || keycode == Keys.DOWN) {
          if (arrowPos3 == null) {
            arrowPos3 = new Vector2(0.5f, 2f);
            canMove = true;
          }
          currentArrow = 3;
      }

    } else if (currentArrow == 3) {
      if (canMove && keycode == Keys.LEFT) {
        currKeyDown = keycode;
        canMove = false;
        if (arrowPos3.x > 0.5f) {
          arrowPos3.x -= 5f;
          music = true;
        }
      } else if (canMove && keycode == Keys.RIGHT){
        currKeyDown = keycode;
        canMove = false;
        if (arrowPos3.x < 5.5f) {
          arrowPos3.x += 5f;
          music = false;
        }
      } else if (keycode == Keys.ENTER || keycode == Keys.DOWN) {
        Settings settings = new Settings();
        settings.setSpeed(speed);
        settings.setSoundOn(sound);
        settings.setMusicOn(music);
        settings.setHomunculiNum(numHomunculi);
        game.setScreen(new GameScreen(game, settings));
      }
    }
  
    return true;
  }
  
  @Override
  public boolean keyUp(int keycode) {
    if (keycode == currKeyDown) canMove = true;
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
    spriteBatch.begin();
      renderBackground();
      renderCursors();
    spriteBatch.end();
  }
  
  private void renderBackground() {
    spriteBatch.draw(Assets.settingsPageRegion, 0, 0, 600, 800);
  }
  
  private void renderCursors() {
    if (arrowPos1 != null) {
      spriteBatch.draw(Assets.cursorDownRegion, arrowPos1.x * ppuX, 
          arrowPos1.y * ppuY, 0.5f * ppuX, 0.5f * ppuY);
          
    }
    
    if (arrowPos2 != null) {
      spriteBatch.draw(Assets.cursorRegion, arrowPos2.x * ppuX, 
          arrowPos2.y * ppuY, 1f * ppuX, 1f * ppuY);
    }
    
    if (arrowPos3 != null) {
      spriteBatch.draw(Assets.cursorRegion, arrowPos3.x * ppuX, 
          arrowPos3.y * ppuY, 1f * ppuX, 1f * ppuY);
    }
  }
  
  @Override
  public void resize(int w, int h) {
    width = w;
    height = h;
    ppuX = (float)width / CAMERA_W;
    ppuY = (float)height / CAMERA_H;
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
