package com.jamiltron.homunculus;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animation {
  final TextureRegion[] frames;
  final float duration;
  
  public Animation(float duration, TextureRegion... frames) {
    this.duration = duration;
    this.frames = frames;
  }
  
  public TextureRegion getFrame(float stateTime, boolean looping) {
    int frameNum = (int)(stateTime / duration);
    
    if (looping) {
      frameNum = frameNum % frames.length;
    } else {
      frameNum = Math.min(frames.length - 1, frameNum);
    }
    return frames[frameNum];
  }

}
