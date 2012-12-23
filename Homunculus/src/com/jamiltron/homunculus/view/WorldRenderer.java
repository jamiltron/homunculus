package com.jamiltron.homunculus.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jamiltron.homunculus.Assets;
import com.jamiltron.homunculus.model.Color;
import com.jamiltron.homunculus.model.Homunculus;
import com.jamiltron.homunculus.model.Component;
import com.jamiltron.homunculus.model.Spell;
import com.jamiltron.homunculus.model.World;


public class WorldRenderer {
  
  private static final float CAMERA_W = 18.75f;
  private static final float CAMERA_H = 25f;
  
  private World world;
  private OrthographicCamera cam;
  private SpriteBatch spriteBatch;
  
  private float ppuX;
  private float ppuY;
  private int width;
  private int height;
  
  public void setSize(int w, int h) {
    width = w;
    height = h;
    ppuX = (float)width / CAMERA_W;
    ppuY = (float)height / CAMERA_H;
  }
  
  public WorldRenderer(World w) {
    world = w;
    this.cam = new OrthographicCamera(CAMERA_W, CAMERA_H);
    this.cam.position.set(CAMERA_W / 2f, CAMERA_H / 2f, 0f);
    this.cam.update();
    spriteBatch = new SpriteBatch();
    Assets.loadTextures();
    Assets.loadSounds();
  }
  
  public void render() {
    spriteBatch.begin();
      renderBackground();
      renderHomunculi();
      renderSpells();
      renderMessages();
    spriteBatch.end();
  }
  
  private void renderMessages() {
    TextureRegion tempRegion = null;
    if (world.won) {
      tempRegion = Assets.wonRegion;
    } else if (world.paused){
      tempRegion = Assets.pausedRegion;
    } else if (world.lost) {
      tempRegion = Assets.overRegion;
    }
    
    if (tempRegion != null) spriteBatch.draw(tempRegion, 2 * ppuX, 8 * ppuY, 256, 256);
  }
  private void renderBackground() {
    spriteBatch.draw(Assets.backgroundRegion, 0, 0, width, height);
  }

  private void renderHomunculi() {
    TextureRegion drawingTexture;
    
    for (Homunculus homunculi : world.homunculi) {
      if (homunculi != null) {
        if (homunculi.color.equals(Color.BLUE)) {
          drawingTexture = Assets.blueHomunculusRegion;
        } else if (homunculi.color.equals(Color.RED)) {
          drawingTexture = Assets.redHomunculusRegion;
        } else {
          drawingTexture = Assets.yellowHomunculusRegion;
        }
      
        spriteBatch.draw(drawingTexture, homunculi.pos.x * ppuX,
            homunculi.pos.y * ppuY, Homunculus.WIDTH * ppuX, 
            Homunculus.HEIGHT * ppuY);
      }
    }
  }
  
  private void renderSpells() {
    renderSpell(world.getActiveSpell());
    renderSpell(world.getNextSpell());
    for (Spell setSpell : world.setSpells) {
      renderSpell(setSpell);
    }
  }
  
  private void renderSpell(Spell spell) {
    if (spell != null) {
      renderComponent(spell.component1);
      renderComponent(spell.component2);
    }
  }
  
  private void renderComponent(Component component) {
    TextureRegion drawingTexture;
    
    if (component != null) {
      if (component.color.equals(Color.BLUE)){ 
        drawingTexture = Assets.blueSpellRegion;
      } else if (component.color.equals(Color.RED)) {
        drawingTexture = Assets.redSpellRegion;
      } else {
        drawingTexture = Assets.yellowSpellRegion;
      }
      
      spriteBatch.draw(drawingTexture, component.pos.x * ppuX,
          component.pos.y * ppuY, Homunculus.WIDTH * ppuX, 
         Homunculus.HEIGHT * ppuY);
    }
  }
}
