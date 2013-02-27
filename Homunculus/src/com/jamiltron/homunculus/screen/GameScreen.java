package com.jamiltron.homunculus.screen;

import com.jamiltron.homunculus.HomunculusGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.jamiltron.homunculus.Settings;
import com.jamiltron.homunculus.controller.WorldController;
import com.jamiltron.homunculus.model.World;
import com.jamiltron.homunculus.view.WorldRenderer;

public class GameScreen implements Screen, InputProcessor {

  private World                world;
  private WorldRenderer        renderer;
  private WorldController      controller;
  private final HomunculusGame game;
  private final Settings       settings;
  private final boolean        touching;
  private boolean              leftPressed;
  private boolean              rightPressed;
  private boolean              dropPressed;
  private boolean              rotrPressed;
  private boolean              rotlPressed;
  private float                ppuX;
  private float                ppuY;
  private static final float   CAMERA_W = 18.75f;
  private static final float   CAMERA_H = 25f;

  public GameScreen(final HomunculusGame g) {
    super();
    game = g;
    settings = g.settings;
    touching = false;
    leftPressed = false;
    rightPressed = false;
    dropPressed = false;
    rotrPressed = false;
    rotlPressed = false;
  }

  @Override
  public void dispose() {
    Gdx.input.setInputProcessor(null);
  }

  @Override
  public void hide() {
    Gdx.input.setInputProcessor(null);
  }

  @Override
  public boolean keyDown(final int keycode) {
    if (keycode == Keys.LEFT) {
      controller.leftPress();
      leftPressed = true;
    }

    if (keycode == Keys.RIGHT) {
      controller.rightPress();
      rightPressed = true;
    }

    if (keycode == Keys.DOWN) {
      controller.dropPress();
      dropPressed = true;
    }

    if (keycode == Keys.X || keycode == Keys.UP) {
      rotrPressed = true;
      controller.rotrPress();
    }

    if (keycode == Keys.Z) {
      rotlPressed = true;
      controller.rotlPress();
    }

    if (keycode == Keys.P) {
      controller.pausePress();
    }

    controller.anyPress();

    return true;
  }

  @Override
  public boolean keyTyped(final char character) {
    return false;
  }

  @Override
  public boolean keyUp(final int keycode) {
    if (keycode == Keys.LEFT) {
      controller.leftRelease();
      leftPressed = false;
    }

    if (keycode == Keys.RIGHT) {
      controller.rightRelease();
      rightPressed = false;
    }

    if (keycode == Keys.DOWN) {
      controller.dropRelease();
      dropPressed = false;
    }

    if (keycode == Keys.X || keycode == Keys.UP) {
      controller.rotrRelease();
      rotrPressed = false;
    }

    if (keycode == Keys.Z) {
      rotlPressed = false;
      controller.rotlRelease();
    }

    if (keycode == Keys.P) {
      controller.pauseRelease();
    }

    controller.anyRelease();

    return true;
  }

  @Override
  public boolean mouseMoved(final int screenX, final int screenY) {
    return false;
  }

  @Override
  public void pause() {
    // TODO Auto-generated method stub

  }

  @Override
  public void render(final float delta) {
    Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

    controller.update(delta);
    renderer.render();

    // TODO: ADD A SETTING FOR TOUCHSCREEN YES/NO
    if (!leftPressed) {
      controller.leftRelease();
    }
    if (!rightPressed) {
      controller.rightRelease();
    }
    if (!touching) {
      if (!dropPressed) {
        controller.dropRelease();
      }
      if (!rotrPressed) {
        controller.rotrRelease();
      }
      if (!rotlPressed) {
        controller.rotlRelease();
      }
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
  public void resize(final int w, final int h) {
    ppuX = w / CAMERA_W;
    ppuY = h / CAMERA_H;
    System.out.println(ppuX);
    System.out.println(ppuY);
    renderer.setSize(w, h);
  }

  @Override
  public void resume() {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean scrolled(final int amount) {
    return false;
  }

  @Override
  public void show() {
    world = new World(settings.getHomunculiNum() + 4);
    renderer = new WorldRenderer(world);
    controller = new WorldController(world, game);
    Gdx.input.setInputProcessor(this);
  }

  @Override
  public boolean touchDown(final int screenX, final int screenY,
      final int pointer, final int button) {
    /*
     * if (button == 0) { final float x = screenX / ppuX; final float y =
     * CAMERA_H - screenY / ppuY;
     * 
     * if (x >= 1.25f && x <= 4.25f && y >= 0.25f && y <= 3.25f) {
     * controller.leftPress(); } else if (x >= 5.5f && x <= 8.5f && y >= 0.25f
     * && y <= 3.25f) { controller.dropPress(); } else if (x >= 9.75f && x <=
     * 12.75f && y >= 0.25f && y <= 3.25f) { controller.rightPress(); } else if
     * (x >= 14f && x <= 17f && y >= 0.25f && y <= 3.25f) {
     * controller.rotrPress(); }
     * 
     * touching = true; } return true;
     */
    return false;
  }

  @Override
  public boolean touchDragged(final int screenX, final int screenY,
      final int pointer) {
    /*
     * if (screenX - lastDragX > 2) { controller.rightPress();
     * controller.leftRelease(); } else if (lastDragX - screenX > 2) {
     * controller.leftPress(); controller.rightRelease(); }
     * 
     * lastDragX = screenX;
     * 
     * return true;
     */
    return false;
  }

  @Override
  public boolean touchUp(final int screenX, final int screenY,
      final int pointer, final int button) {
    /*
     * if (button == 0) { float x = screenX / ppuX; float y = CAMERA_H - screenY
     * / ppuY; if (Math.abs(screenX - lastX) <= Assets.TOUCH_BOX && !(x >= 1.25f
     * && x <= 4.25f && y >= 0.25f && y <= 3.25f) && !(x >= 5.5f && x <= 8.5f &&
     * y >= 0.25f && y <= 3.25f) && !(x >= 9.75f && x <= 12.75f && y >= 0.25f &&
     * y <= 3.25f) && !(x >= 14f && x <= 17f && y >= 0.25f && y <= 3.25f)) {
     * controller.rotrPress(); } touching = false; } return true;
     */
    return false;
  }
}
