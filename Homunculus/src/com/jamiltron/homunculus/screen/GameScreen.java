package com.jamiltron.homunculus.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.jamiltron.homunculus.Assets;
import com.jamiltron.homunculus.Settings;
import com.jamiltron.homunculus.controller.WorldController;
import com.jamiltron.homunculus.model.World;
import com.jamiltron.homunculus.view.WorldRenderer;

public class GameScreen implements Screen, InputProcessor {

  private World world;
  private WorldRenderer renderer;
  private WorldController controller;
  private final Game game;
  private final Settings settings;
  private boolean touching;
  private float lastX;
  private float lastY;
  private float lastDragX;

  public GameScreen(Game g, Settings s) {
    super();
    game = g;
    settings = s;
  }

  @Override
  public boolean keyDown(int keycode) {
    if (keycode == Keys.LEFT) {
      controller.leftPress();
    }

    if (keycode == Keys.RIGHT) {
      controller.rightPress();
    }

    if (keycode == Keys.DOWN) {
      controller.dropPress();
    }

    if (keycode == Keys.X || keycode == Keys.UP) {
      controller.rotrPress();
    }

    if (keycode == Keys.Z) {
      controller.rotlPress();
    }

    if (keycode == Keys.P) {
      controller.pausePress();
    }

    controller.anyPress();

    return true;
  }

  @Override
  public boolean keyUp(int keycode) {
    if (keycode == Keys.LEFT) {
      controller.leftRelease();
    }

    if (keycode == Keys.RIGHT) {
      controller.rightRelease();
    }

    if (keycode == Keys.DOWN) {
      controller.dropRelease();
    }
    

    if (keycode == Keys.X || keycode == Keys.UP) {
      controller.rotrRelease();
    }

    if (keycode == Keys.Z) {
      controller.rotlRelease();
    }

    if (keycode == Keys.P) {
      controller.pauseRelease();
    }

    controller.anyRelease();

    return true;
  }

  @Override
  public boolean keyTyped(char character) {
    return false;
  }

  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    if (button == 0) {
      touching = true;
      lastX = screenX;
      lastY = screenY;
      lastDragX = screenX;
    }
    return true;
  }

  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    if (button == 0) {
      if (Math.abs(screenX - lastX) <= Assets.TOUCH_BOX) {
        touching = false;
        controller.rotrPress();
      }
    }
    return true;
  }

  @Override
  public boolean touchDragged(int screenX, int screenY, int pointer) {
    if (screenX - lastDragX > Assets.TOUCH_BOX) {
      controller.rightPress();
    } else if (lastDragX - screenX > Assets.TOUCH_BOX) {
      controller.leftPress();
    }
    
    lastDragX = screenX;
    return true;
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

    controller.update(delta);
    renderer.render();
    
    // TODO: ADD A SETTING FOR TOUCHSCREEN YES/NO
    controller.leftRelease();
    controller.rightRelease();
    
    if (!touching) {
      controller.rotrRelease();
    }
    // TODO: END ALL TOUCHSCREEN STUFF HERE
    
    if (controller.nextLevel) {
      int numHomunculi = 20;
      if (world.numHomunculi + 1 <= 20) {
        numHomunculi = world.numHomunculi + 1;
      }
      
      

      world = new World(numHomunculi, world.score);
      controller.resetController(world);
      renderer.resetRenderer(world);
    }

  }
  @Override
  public void resize(int w, int h) {
    renderer.setSize(w, h);
  }

  @Override
  public void show() {
    world = new World(settings.getHomunculiNum() + 4);
    renderer = new WorldRenderer(world);
    controller = new WorldController(world, settings, game);
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
