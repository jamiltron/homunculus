package com.jamiltron.homunculus.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jamiltron.homunculus.Assets;
import com.jamiltron.homunculus.HomunculusGame;
import com.jamiltron.homunculus.model.Color;
import com.jamiltron.homunculus.model.Component;
import com.jamiltron.homunculus.model.Homunculus;
import com.jamiltron.homunculus.model.Spell;
import com.jamiltron.homunculus.model.World;

public class WorldRenderer {

  private static final float CAMERA_W = 18.75f;
  private static final float CAMERA_H = 25f;

  private World world;
  private HomunculusGame game;
  private final OrthographicCamera cam;
  private final SpriteBatch spriteBatch;

  private float ppuX;
  private float ppuY;
  private int width;
  private int height;
  private String tmpString;
  
  private TextureRegion keyFrame;
  private Component component;

  public void setSize(int w, int h) {
    width = w;
    height = h;
    ppuX = width / CAMERA_W;
    ppuY = height / CAMERA_H;
    Assets.scaleFont(ppuX, ppuY);
  }

  public WorldRenderer(World w, HomunculusGame g) {
    game = g;
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
    
    if (world.paused) {
      if (game.desktopGame) {
        Assets.font.draw(spriteBatch, "paused", 5.5f * ppuX, 19.5f * ppuY);
        Assets.font.draw(spriteBatch, "press any key", 3.4f * ppuX, 12 * ppuY);
        Assets.font.draw(spriteBatch, "to continue", 4f * ppuX, 11 * ppuY);
      } else {
        Assets.font.draw(spriteBatch, "touch screen to continue", 4 * ppuX, 7 * ppuY);
      }
    } else if (world.won) {
      Assets.font.draw(spriteBatch, "     complete", 2f * ppuX, 19.5f * ppuY);
      Assets.font.draw(spriteBatch, "press any key", 3.4f * ppuX, 12 * ppuY);
      Assets.font.draw(spriteBatch, "to continue", 4f * ppuX, 11 * ppuY);
    }
    
    Assets.font.setColor(0.0f, 0.0f, 0.0f, 1.0f);
    Assets.font.draw(spriteBatch, Integer.toString(world.highScore), 13f * ppuX,
        14.5f * ppuY);
    Assets.font.draw(spriteBatch, Integer.toString((world.numHomunculi / 4) - 1), 13f * ppuX,
        13.15f * ppuY);
    if (game.settings.getSpeed().ordinal() == 0) tmpString = "slow"; 
    if (game.settings.getSpeed().ordinal() == 1) tmpString = "med";
    if (game.settings.getSpeed().ordinal() == 2) tmpString = "fast";
    Assets.font.draw(spriteBatch, tmpString, 13f * ppuX,
        11.75f * ppuY);
  }

  private void renderMessages() {
    if (world.won) {
      spriteBatch.draw(Assets.pauseBackground, 2 * ppuX, 3.65f * ppuY, 10 * ppuX, 18 * ppuY);
    } else if (world.paused) {
      spriteBatch.draw(Assets.overlay, 0, 0, CAMERA_W * ppuX, CAMERA_H * ppuY);
      spriteBatch.draw(Assets.pauseBackground, 2 * ppuX, 3.65f * ppuY, 10 * ppuX, 18 * ppuY);
    } else if (world.lost) {
      spriteBatch.draw(Assets.overlay, 0, 0, CAMERA_W * ppuX, CAMERA_H * ppuY);
      spriteBatch.draw(Assets.gameOverBackground, 2 * ppuX, 3.65f * ppuY, 10 * ppuX, 18 * ppuY);
    }
  }
  
  private void renderBackground() {
    spriteBatch.draw(Assets.playGameBackground, 0, 0, CAMERA_W * ppuX, CAMERA_H * ppuY);
    if (world.won) { 
      keyFrame = Assets.wizardAnim.getFrame(Assets.wizardTime * 3, true);
    } else if (world.lost) {
      keyFrame = Assets.wizardAnim.getFrame(0, true);
    } else {
      keyFrame = Assets.wizardAnim.getFrame(world.switchingTime, true);
    }
    spriteBatch.draw(keyFrame, 12.7f * ppuX, 16.45f * ppuY, 4 * ppuX, 4 * ppuY);
    
    if (!game.desktopGame) {
      spriteBatch.draw(Assets.leftArrow, 1.25f * ppuX, 0.25f * ppuY, 3 * ppuX, 3 * ppuY);
      spriteBatch.draw(Assets.downArrow, 5.5f * ppuX, 0.25f * ppuY, 3 * ppuX, 3 * ppuY);
      spriteBatch.draw(Assets.rightArrow, 9.75f * ppuX, 0.25f * ppuY, 3 * ppuX, 3 * ppuY);
      spriteBatch.draw(Assets.rotateArrow, 14f * ppuX, 0.25f * ppuY, 3 * ppuX, 3 * ppuY);
    }
    
    
  }

  private void renderHomunculi() {
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
    if (!world.switchingSpells) {
      if (!world.won && !world.paused && !world.lost) {
        renderSpell(world.getActiveSpell());
        if (!world.activeEntering) {
          renderSpell(world.getNextSpell());
        } else {
          renderEnteringActiveSpell(world.getNextSpell());
        }
      }
    } else {
      if (!world.won && !world.paused && !world.lost) {
        renderEnteringSpell(world.getActiveSpell());
        renderExitingSpell(world.getNextSpell());
      }
    }
    for (Spell setSpell : world.setSpells) {
      renderSpell(setSpell);
    }
  }
  
  private void renderEnteringActiveSpell(Spell spell) {
    TextureRegion keyFrame;
    Component component = spell.component1;
    
        
    if (component.color.equals(Color.BLUE)) {
      keyFrame = Assets.blueSpellDeadAnim.getFrame(world.activeEnteringTime, false);
    } else if (component.color.equals(Color.RED)) {
      keyFrame = Assets.redSpellDeadAnim.getFrame(world.activeEnteringTime, false);
    } else {
      keyFrame = Assets.yellowSpellDeadAnim.getFrame(world.activeEnteringTime, false);
    }
    spriteBatch.draw(keyFrame, component.pos.x * ppuX, component.pos.y * 
        ppuY, Component.WIDTH * ppuX, Component.HEIGHT * ppuY);

    
    component = spell.component2;
    if (component.color.equals(Color.BLUE)) {
      keyFrame = Assets.blueSpellDeadAnim.getFrame(world.activeEnteringTime, false);
    } else if (component.color.equals(Color.RED)) {
      keyFrame = Assets.redSpellDeadAnim.getFrame(world.activeEnteringTime, false);
    } else {
      keyFrame = Assets.yellowSpellDeadAnim.getFrame(world.activeEnteringTime, false);
    }
    spriteBatch.draw(keyFrame, component.pos.x * ppuX, component.pos.y *
        ppuY, Component.WIDTH * ppuX, Component.HEIGHT * ppuY);


  }
  
  private void renderExitingSpell(Spell spell) {
    component = spell.component1;
    
    if (component.color.equals(Color.BLUE)) {
      keyFrame = Assets.blueSpellDeadAnim.getFrame(world.switchingTime / 2, false);
    } else if (component.color.equals(Color.RED)) {
      keyFrame = Assets.redSpellDeadAnim.getFrame(world.switchingTime / 2, false);
    } else {
      keyFrame = Assets.yellowSpellDeadAnim.getFrame(world.switchingTime / 2, false);
    }
    spriteBatch.draw(keyFrame, component.pos.x * ppuX, component.pos.y * 
          ppuY, Component.WIDTH * ppuX, Component.HEIGHT * ppuY);
    
    component = spell.component2;
    if (component.color.equals(Color.BLUE)) {
      keyFrame = Assets.blueSpellDeadAnim.getFrame(world.switchingTime / 2, false);
    } else if (component.color.equals(Color.RED)) {
      keyFrame = Assets.redSpellDeadAnim.getFrame(world.switchingTime / 2, false);
    } else {
      keyFrame = Assets.yellowSpellDeadAnim.getFrame(world.switchingTime / 2, false);
    }
     spriteBatch.draw(keyFrame, component.pos.x * ppuX, component.pos.y *
         ppuY, Component.WIDTH * ppuX, Component.HEIGHT * ppuY);
  }
  
  private void renderEnteringSpell(Spell spell) {
    component = spell.component1;
    
    if (component.color.equals(Color.BLUE)) {
      keyFrame = Assets.blueSpellDeadAnim.getFrame((Assets.wizardTime * Assets.wizardFrames - world.switchingTime) / 2, false);
    } else if (component.color.equals(Color.RED)) {
      keyFrame = Assets.redSpellDeadAnim.getFrame((Assets.wizardTime * Assets.wizardFrames - world.switchingTime) / 2, false);
    } else {
      keyFrame = Assets.yellowSpellDeadAnim.getFrame((Assets.wizardTime * Assets.wizardFrames - world.switchingTime) / 2, false);
    }
    spriteBatch.draw(keyFrame, component.pos.x * ppuX, component.pos.y *
        ppuY, Component.WIDTH * ppuX, Component.HEIGHT * ppuY);
    
    component = spell.component2;
    if (component.color.equals(Color.BLUE)) {
      keyFrame = Assets.blueSpellDeadAnim.getFrame((Assets.wizardTime * Assets.wizardFrames - world.switchingTime) / 2, false);
    } else if (component.color.equals(Color.RED)) {
      keyFrame = Assets.redSpellDeadAnim.getFrame((Assets.wizardTime * Assets.wizardFrames - world.switchingTime) / 2, false);
    } else {
      keyFrame = Assets.yellowSpellDeadAnim.getFrame((Assets.wizardTime * Assets.wizardFrames - world.switchingTime) / 2, false);
    }
    spriteBatch.draw(keyFrame, component.pos.x * ppuX, component.pos.y *
        ppuY, Component.WIDTH * ppuX, Component.HEIGHT * ppuY);
  }

  private void renderSpell(Spell spell) {
    if (spell != null) {
      renderComponent(spell.component1);
      renderComponent(spell.component2);
    }
  }

  private void renderComponent(Component component) {
    if (!component.isDead) {
      if (component.isDying) {
        if (component.color.equals(Color.BLUE)) {
          keyFrame = Assets.blueSpellDeadAnim.getFrame(component.stateTime, false);
        } else if (component.color.equals(Color.RED)) {
          keyFrame = Assets.redSpellDeadAnim.getFrame(component.stateTime, false);
        } else {
          keyFrame = Assets.yellowSpellDeadAnim.getFrame(component.stateTime, false);
        }
        spriteBatch.draw(keyFrame, component.pos.x * ppuX, component.pos.y * 
              ppuY, Component.WIDTH * ppuX, Component.HEIGHT * ppuY);
      } else {
        if (component.color.equals(Color.BLUE)) {
          keyFrame = Assets.blueSpellLiveAnim.getFrame(component.stateTime, true);
        } else if (component.color.equals(Color.RED)) {
          keyFrame = Assets.redSpellLiveAnim.getFrame(component.stateTime, true);
        } else {
          keyFrame = Assets.yellowSpellLiveAnim.getFrame(component.stateTime, true);
        }
        spriteBatch.draw(keyFrame, component.pos.x * ppuX, component.pos.y *
            ppuY, Component.WIDTH * ppuX, Component.HEIGHT * ppuY);
      }
    }
  }
}
