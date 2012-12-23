package com.jamiltron.homunculus.screen;

import com.badlogic.gdx.Game;
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

  private World           world;
  private WorldRenderer   renderer;
  private WorldController controller;
  private Game            game;
  private Settings        settings;
  
  public GameScreen(Game g, Settings s) {
    super();
    game = g;
    settings = s;
  }
  
  @Override
  public boolean keyDown(int keycode) {
    if (keycode == Keys.LEFT){
      controller.leftPress();
    }
    
    if (keycode == Keys.RIGHT){
      controller.rightPress();
    }
    
    if (keycode == Keys.DOWN) {
      controller.dropPress();
    }
    
    if (keycode == Keys.X){
      controller.rotrPress();
    }
    
    if (keycode == Keys.Z){
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
    if (keycode == Keys.LEFT){
      controller.leftRelease();
    }
    
    if (keycode == Keys.RIGHT){
      controller.rightRelease();
    }
    
    if (keycode == Keys.DOWN){
      controller.dropRelease();
    }
    
    if (keycode == Keys.X){
      controller.rotrRelease();
    }
    
    if (keycode == Keys.Z){
      controller.rotlRelease();
    }
    
    if (keycode == Keys.P){
      controller.pauseRelease();
    }
    
    controller.anyRelease();
    
    return true;
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
    
    controller.update(delta);
    renderer.render();
    
  }

  @Override
  public void resize(int w, int h) {
    renderer.setSize(w, h);
  }

  @Override
  public void show() {
    world = new World(settings.getHomunculiNum() + 4);
    renderer = new WorldRenderer(world);
    controller = new WorldController(world, settings);
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
