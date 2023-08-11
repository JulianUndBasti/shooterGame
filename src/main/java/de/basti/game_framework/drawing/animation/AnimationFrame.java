package de.basti.game_framework.drawing.animation;

import de.basti.game_framework.drawing.Sprite;

public class AnimationFrame {
	private int frames;
	private Sprite sprite;

	public AnimationFrame(int frames, Sprite sprite) {
		super();
		this.frames = frames;
		this.sprite = sprite;
	}

	public int getFrameTime() {
		return frames;
	}

	public void setFrames(int frames) {
		this.frames = frames;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

}
