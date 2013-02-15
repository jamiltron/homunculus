package com.jamiltron.homunculus.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jamiltron.homunculus.Assets;
import com.jamiltron.homunculus.model.Color;
import com.jamiltron.homunculus.model.Component;
import com.jamiltron.homunculus.model.Homunculus;
import com.jamiltron.homunculus.model.Spell;
import com.jamiltron.homunculus.model.World;

public class WorldRenderer {

  private static final float CAMERA_W = 18.75f;
  private static final float CAMERA_H = 25f;

  private World world;
  private final OrthographicCamera cam;
  private final SpriteBatch spriteBatch;

  private float ppuX;
  private float ppuY;
  private int width;
  private int height;

  public void setSize(int w, int h) {
    width = w;
    height = h;
    ppuX = width / CAMERA_W;
    ppuY = height / CAMERA_H;
  }

  public WorldRenderer(World w) {
    world = w;
    this.cam = new OrthographicCamera(CAMERA_W, CAMERA_H);
    this.cam.position.set(CAMERA_W / 2f, CAMERA_H / 2f, 0f);
    this.cam.update();
    spriteBatch = new SpriteBatch();

  }

  public void resetRenderer(World w) {
    world = w;
  }

  public void render() {
    spriteBatch.begin();
    renderBackground();
    renderHomunculi();
    renderSpells();
    renderMessages();
    renderText();
    spriteBatch.end();
  }

  private void renderText() {
    Assets.font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    Assets.font.draw(spriteBatch, Integer.toString(world.score), 12.75f * ppuX,
        22.5f * ppuY);
    
    Assets.font.setColor(0.0f, 0.0f, 0.0f, 1.0f);
    Assets.font.draw(spriteBatch, "1000000", 13f * ppuX,
        14.5f * ppuY);
    Assets.font.draw(spriteBatch, "5", 13f * ppuX,
        13.15f * ppuY);
    Assets.font.draw(spriteBatch, "Classic", 13f * ppuX,
        11.75f * ppuY);
  }

  private void renderMessages() {
    if (world.won) {
      spriteBatch.draw(Assets.pauseBackground, 2 * ppuX, 8 * ppuY, 256, 256);
    } else if (world.paused) {
      spriteBatch.draw(Assets.pauseBackground, 2 * ppuX, 8 * ppuY, 256, 256);
    } else if (world.lost) {
      spriteBatch.draw(Assets.gameOverBackground, 2 * ppuX, 8 * ppuY, 256, 256);
    }
  }
  
  private void renderBackground() {
    TextureRegion keyFrame;
    
    spriteBatch.draw(Assets.playGameBackground, 0, 0, width, height);
    keyFrame = Assets.wizardAnim.getFrame(0, false);
    spriteBatch.draw(keyFrame, 12.7f * ppuX, 16.45f * ppuY, 4 * ppuX, 4 * ppuY);
    spriteBatch.draw(Assets.leftArrow, 1.25f * ppuX, 0.25f * ppuY, 3 * ppuX, 3 * ppuY);
    spriteBatch.draw(Assets.downArrow, 5.5f * ppuX, 0.25f * ppuY, 3 * ppuX, 3 * ppuY);
    spriteBatch.draw(Assets.rightArrow, 9.75f * ppuX, 0.25f * ppuY, 3 * ppuX, 3 * ppuY);
    spriteBatch.draw(Assets.rotateArrow, 14f * ppuX, 0.25f * ppuY, 3 * ppuX, 3 * ppuY);
    
    
  }

  private void renderHomunculi() {
    TextureRegion keyFrame;
    
    for (Homunculus homunculi : world.homunculi) {
      if (homunculi != null) {
        if (homunculi.isDying){
          if (homunculi.color.equals(Color.BLUE)) {
            keyFrame = Assets.blueHomDeadAnim.getFrame(homunculi.stateTime, false);
            spriteBatch.draw(keyFrame, homunculi.pos.x * ppuX, homunculi.pos.y * 
                ppuY, Homunculus.WIDTH * ppuX, Homunculus.HEIGHT * ppuY);
          } else if (homunculi.color.equals(Color.RED)) {
            keyFrame = Assets.redHomDeadAnim.getFrame(homunculi.stateTime, false);
            spriteBatch.draw(keyFrame, homunculi.pos.x * ppuX, homunculi.pos.y * 
                ppuY, Homunculus.WIDTH * ppuX, Homunculus.HEIGHT * ppuY);
          } else {
            keyFrame = Assets.yellowHomDeadAnim.getFrame(homunculi.stateTime, false);
            spriteBatch.draw(keyFrame, homunculi.pos.x * ppuX, homunculi.pos.y * 
                ppuY, Homunculus.WIDTH * ppuX, Homunculus.HEIGHT * ppuY);
          }
        } else {
          if (homunculi.color.equals(Color.BLUE)) {
            keyFrame = Assets.blueHomLiveAnim.getFrame(homunculi.stateTime, true);
            spriteBatch.draw(keyFrame, homunculi.pos.x * ppuX, homunculi.pos.y * 
                ppuY, Homunculus.WIDTH * ppuX, Homunculus.HEIGHT * ppuY);
          } else if (homunculi.color.equals(Color.RED)) {
            keyFrame = Assets.redHomLiveAnim.getFrame(homunculi.stateTime, true);
            spriteBatch.draw(keyFrame, homunculi.pos.x * ppuX, homunculi.pos.y * 
                ppuY, Homunculus.WIDTH * ppuX, Homunculus.HEIGHT * ppuY);
          } else {
            keyFrame = Assets.yellowHomLiveAnim.getFrame(homunculi.stateTime, true);
            spriteBatch.draw(keyFrame, homunculi.pos.x * ppuX, homunculi.pos.y * 
                ppuY, Homunculus.WIDTH * ppuX, Homunculus.HEIGHT * ppuY);
          }
        }
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
    TextureRegion keyFrame;

    if (component != null) {
      if (component.isDying) {
        if (component.color.equals(Color.BLUE)) {
          keyFrame = Assets.blueSpellDeadAnim.getFrame(component.stateTime, false);
          spriteBatch.draw(keyFrame, component.pos.x * ppuX, component.pos.y * 
              ppuY, Component.WIDTH * ppuX, Component.HEIGHT * ppuY);
        } else if (component.color.equals(Color.RED)) {
          keyFrame = Assets.redSpellDeadAnim.getFrame(component.stateTime, false);
          spriteBatch.draw(keyFrame, component.pos.x * ppuX, component.pos.y * 
              ppuY, Component.WIDTH * ppuX, Component.HEIGHT * ppuY);
        } else {
          keyFrame = Assets.yellowSpellDeadAnim.getFrame(component.stateTime, false);
          spriteBatch.draw(keyFrame, component.pos.x * ppuX, component.pos.y * 
              ppuY, Component.WIDTH * ppuX, Component.HEIGHT * ppuY);
        }
      } else {
        if (component.color.equals(Color.BLUE)) {
          keyFrame = Assets.blueSpellLiveAnim.getFrame(component.stateTime, true);
          spriteBatch.draw(keyFrame, component.pos.x * ppuX, component.pos.y * 
              ppuY, Component.WIDTH * ppuX, Component.HEIGHT * ppuY);
        } else if (component.color.equals(Color.RED)) {
          keyFrame = Assets.redSpellLiveAnim.getFrame(component.stateTime, true);
          spriteBatch.draw(keyFrame, component.pos.x * ppuX, component.pos.y * 
              ppuY, Component.WIDTH * ppuX, Component.HEIGHT * ppuY);
        } else {
          keyFrame = Assets.yellowSpellLiveAnim.getFrame(component.stateTime, true);
          spriteBatch.draw(keyFrame, component.pos.x * ppuX, component.pos.y * 
              ppuY, Component.WIDTH * ppuX, Component.HEIGHT * ppuY);
        }
      }
    }
  }
}
