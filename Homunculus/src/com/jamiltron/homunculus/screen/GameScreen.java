package com.jamiltron.homunculus.screen;

import java.util.AbstractMap.SimpleEntry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.utils.Pool;
import com.jamiltron.homunculus.Assets;
import com.jamiltron.homunculus.HomunculusGame;
import com.jamiltron.homunculus.Settings;
import com.jamiltron.homunculus.controller.WorldController;
import com.jamiltron.homunculus.model.World;
import com.jamiltron.homunculus.view.WorldRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class GameScreen implements Screen, InputProcessor {
  private World                world;
  private Pool<World>          worldPool;
  private WorldRenderer        renderer;
  private WorldController      controller;
  private final HomunculusGame game;
  private final Settings       settings;
  private boolean              touching;
  private boolean              leftPressed;
  private boolean              rightPressed;
  private boolean              dropPressed;
  private boolean              rotrPressed;
  private boolean              rotlPressed;
  private int                  highScore;
  private float                ppuX;
  private float                ppuY;
  private float                lastX;
  private float                lastDragX;
  private static final float   CAMERA_W = 18.75f;
  private static final float   CAMERA_H = 25f;
  private Stage stage;
  private TextField textfield;

  public GameScreen(final HomunculusGame g) {
    super();
    game         = g;
    settings     = g.settings;
    touching     = false;
    leftPressed  = false;
    rightPressed = false;
    dropPressed  = false;
    rotrPressed  = false;
    rotlPressed  = false;
    highScore    = game.scores.get(0).getValue();
    worldPool = new Pool<World>() {
      @Override
      protected World newObject() {
        return new World();
      }
    };
    stage = new Stage(Gdx.graphics.getWidth(),
        Gdx.graphics.getHeight(), false);
    TextFieldStyle tStyle = new TextFieldStyle();
    tStyle.font = Assets.font;
    tStyle.fontColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);
    tStyle.cursor =  new TextureRegionDrawable(Assets.textCursor);
    textfield = new TextField("", tStyle);
    textfield.setMaxLength(10);
    stage.addActor(textfield);
    final GameScreen tmpGameScreen = this;
    textfield.setTextFieldListener(new TextFieldListener() {
      public void keyTyped (TextField textfield, char key) {
        if (key == '\n' || key == '\r') {
          textfield.getOnscreenKeyboard().show(false);
          Gdx.input.setInputProcessor(tmpGameScreen);
          world.scoreBroken = false;
          game.setScreen(new MainMenu(game));
          game.scores.remove(game.scores.size() - 1);
          for (int i=0; i < 9; i++) {
            if (world.score > game.scores.get(i).getValue()) {
              game.scores.add(i, new SimpleEntry<String, Integer>(textfield.getText(), world.score));
              break;
            }
          }
          Assets.writeHighScores(game.scores);
        }
      }
    });
  }

  @Override
  public void dispose() {
    stage.dispose();
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
    
    if (keycode == Keys.ESCAPE){
      controller.quitPress();
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
    if (keycode == Keys.LEFT || keycode == Keys.A) {
      controller.leftRelease();
      leftPressed = false;
    }

    if (keycode == Keys.RIGHT || keycode == Keys.D) {
      controller.rightRelease();
      rightPressed = false;
    }

    if (keycode == Keys.DOWN || keycode == Keys.S) {
      controller.dropRelease();
      dropPressed = false;
    }

    if (keycode == Keys.X || keycode == Keys.UP || keycode == Keys.W || keycode == Keys.SHIFT_RIGHT) {
      controller.rotrRelease();
      rotrPressed = false;
    }

    if (keycode == Keys.Z || keycode == Keys.SHIFT_LEFT) {
      rotlPressed = false;
      controller.rotlRelease();
    }

    if (keycode == Keys.P) {
      controller.pauseRelease();
    }
    
    if (keycode == Keys.ESCAPE){
      controller.quitRelease();
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
    //logger.log();
    Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

    if (!world.scoreBroken || !world.lost) {
    controller.update(delta);
    renderer.render();

    if (!game.desktopGame) {
      if (!leftPressed)  controller.leftRelease();
      if (!rightPressed) controller.rightRelease();
      if (!touching) {
        if (!dropPressed) controller.dropRelease();
        if (!rotrPressed) controller.rotrRelease();
        if (!rotlPressed) controller.rotlRelease();
      }
    }

    if (controller.nextLevel) {
      int numHomunculi = 20 * 4;
      int score = world.score;
      highScore = world.highScore;
      if (world.numHomunculi + 4 <= 20 * 4) {
        numHomunculi = world.numHomunculi + 4;
      }

      //world = new World(numHomunculi, world.score);
      worldPool.free(world);
      world = worldPool.obtain();
      world.setProps(numHomunculi, score);
      Assets.titleMusic.stop();
      if (game.settings.getMusicOn() && Assets.titleMusic.isPlaying()) {
        Assets.titleMusic.stop();
        Assets.levelMusic.setLooping(true);
        Assets.levelMusic.play();
      }
      controller.resetController(world);
      renderer.resetRenderer(world);
    }
    } else {
      Gdx.input.setInputProcessor(stage);
      renderer.render();
      stage.act(delta);
      stage.setKeyboardFocus(textfield);
      stage.draw();
      
    }
  }

  @Override
  public void resize(final int w, final int h) {
    ppuX = w / CAMERA_W;
    ppuY = h / CAMERA_H;
    renderer.setSize(w, h);
    textfield.setPosition(4.5f * ppuX, 14.5f * ppuY);
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
    world = worldPool.obtain();
    world.highScore = highScore;
    world.setProps((settings.getHomunculiNum() + 1) * 4, 0);
    renderer = new WorldRenderer(world, game);
    controller = new WorldController(world, game);
    Gdx.input.setInputProcessor(this);
    if (game.settings.getMusicOn()) {
      Assets.titleMusic.stop();
      Assets.levelMusic.setLooping(true);
      Assets.levelMusic.play();
    }
  }

  @Override
  public boolean touchDown(final int screenX, final int screenY,
      final int pointer, final int button) {
      
    if (!game.desktopGame) {
     if (button == 0) { 
       final float x = screenX / ppuX; final float y = CAMERA_H - screenY / ppuY;
     
       if (x >= 1.25f && x <= 4.25f && y >= 0.25f && y <= 3.25f) {
         controller.leftPress(); 
       } else if (x >= 5.5f && x <= 8.5f && y >= 0.25f && y <= 3.25f) { 
         controller.dropPress(); 
       } else if (x >= 9.75f && x <= 12.75f && y >= 0.25f && y <= 3.25f) { 
         controller.rightPress(); 
       } else if (x >= 14f && x <= 17f && y >= 0.25f && y <= 3.25f) {
         controller.rotrPress(); 
       }
       
       touching = true;
     }
     return true;
     } else {
       return false;
    }
  }

  @Override
  public boolean touchDragged(final int screenX, final int screenY,
      final int pointer) {
    if (!game.desktopGame) {
      if (screenX - lastDragX > 2) { controller.rightPress();
      controller.leftRelease(); } else if (lastDragX - screenX > 2) {
        controller.leftPress(); controller.rightRelease();
      }
      lastDragX = screenX;
      
      return true;
     } else {
    return false;
     }
  }

  @Override
  public boolean touchUp(final int screenX, final int screenY,
      final int pointer, final int button) {
    
    if (!game.desktopGame) {
      if (button == 0) {
        float x = screenX / ppuX; float y = CAMERA_H - screenY  / ppuY;
        
        if (Math.abs(screenX - lastX) <= Assets.TOUCH_BOX &&
           !(x >= 1.25f && x <= 4.25f && y >= 0.25f && y <= 3.25f) && 
           !(x >= 5.5f && x <= 8.5f &&
           y >= 0.25f && y <= 3.25f) && 
           !(x >= 9.75f && x <= 12.75f && y >= 0.25f &&
           y <= 3.25f) && !(x >= 14f && x <= 17f && y >= 0.25f && y <= 3.25f)) {
          controller.rotrPress(); 
        }
        touching = false;
      }
      return true;
    } else {
      return false;
    }
  }
}
