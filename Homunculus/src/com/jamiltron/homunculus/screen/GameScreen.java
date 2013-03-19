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
  private float                height;
  private float                lastX;
  private float                lastDragX;
  private static final float   CAMERA_W = 18.75f;
  private static final float   CAMERA_H = 25f;
  private Stage stage;
  private TextField textfield;
  
  private static final float LEFT_X = 1.25f;
  private static final float LEFT_W = 3;
  private static final float LEFT_Y = 0.25f;
  private static final float LEFT_H = LEFT_W;
  
  private static final float DOWN_X = 5.5f;
  private static final float DOWN_W = 3;
  private static final float DOWN_Y = LEFT_Y;
  private static final float DOWN_H = LEFT_W;
  
  private static final float RIGHT_X = 9.75f;
  private static final float RIGHT_W = 3;
  private static final float RIGHT_Y = LEFT_Y;
  private static final float RIGHT_H = LEFT_W;

  private static final float ROTR_X = 14f;
  private static final float ROTR_W = 3;
  private static final float ROTR_Y = LEFT_Y;
  private static final float ROTR_H = LEFT_W;


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
          for (int i=0; i < 10; i++) {
            if (world.score > game.scores.get(i).getValue()) {
              game.scores.add(i, new SimpleEntry<String, Integer>(textfield.getText(), world.score));
              break;
            }
          }
          Assets.writeHighScores(game.scores, game.desktopGame);
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
    world.paused = true;
    Gdx.input.setInputProcessor(null);
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
        controller.anyRelease();
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
      textfield.getOnscreenKeyboard().show(true);
      renderer.render();
      stage.act(delta);
      stage.setKeyboardFocus(textfield);
      stage.draw();
      
    }
  }

  @Override
  public void resize(final int w, final int h) {
    height = h;
    ppuX = w / CAMERA_W;
    ppuY = h / CAMERA_H;
    ppuX = Math.min(ppuX, ppuY);
    ppuY = ppuX;
    renderer.setSize(w, h);
    textfield.setPosition(4.5f * ppuX, 14.5f * ppuY);
  }

  @Override
  public void resume() {
    Gdx.input.setInputProcessor(this);
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
       if (!world.paused) { 
       final float x = screenX / ppuX; 
       final float y = height / ppuY - screenY / ppuY;

       if (x >= LEFT_X && x <= LEFT_X + LEFT_W && y >= LEFT_Y && y <= LEFT_Y + LEFT_H) {
         controller.leftPress(); 
         leftPressed = true;
       } else if (x >= DOWN_X && x <= DOWN_X + DOWN_W && y >= DOWN_Y && y <= DOWN_Y + DOWN_H) { 
         controller.dropPress(); 
       } else if (x >= RIGHT_X && x <= RIGHT_X + RIGHT_W && y >= RIGHT_Y && y <= RIGHT_Y + RIGHT_H) { 
         controller.rightPress();
         rightPressed = true;
       } else if (x >= ROTR_X && x <= ROTR_X + ROTR_W && y >= ROTR_Y && y <= ROTR_Y + ROTR_H) {
         controller.rotrPress();
       }
       lastX = screenX;
       lastDragX = screenX;
       touching = true;
       }
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
      final float y = height / ppuY - screenY / ppuY;
      if (y > 3.25f) {
        if (screenX - lastDragX >= 51) {
          controller.rightPress();
          controller.leftRelease(); 
        } else if (lastDragX - screenX >= 51) {
          controller.leftPress(); 
          controller.rightRelease();
        } else {
          controller.leftRelease();
          controller.rightRelease();
        }
      }
      return true;
    } else {
      return false;
    }
  }

  @Override
  public boolean touchUp(final int screenX, final int screenY,
      final int pointer, final int button) {
    if (!game.desktopGame) {
      if (!world.paused) {
        controller.anyPress();
      if (Math.abs(lastX - screenX) <= 1f && screenY < height - 3.25 * ppuY) {
        controller.rotlPress();
      }

      leftPressed = false;
      rightPressed = false;
      lastDragX = screenX;
      touching = false;
      return true;
      } else {
        world.paused = false;
        controller.unpausable = false;
        if (game.settings.getMusicOn()) Assets.levelMusic.play();
        return true;
      }
    } else {
      return false;
    }
  }
}

