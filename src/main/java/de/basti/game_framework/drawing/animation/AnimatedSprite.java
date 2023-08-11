package de.basti.game_framework.drawing.animation;

import java.io.UTFDataFormatException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.basti.game_framework.controls.Updatable;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.drawing.Sprite;
import de.basti.game_framework.math.Vector2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class AnimatedSprite implements Updatable, Drawable {
	private static final Logger LOGGER = Logger.getLogger(AnimatedSprite.class.getName());
	static {
		LOGGER.setLevel(Level.INFO);
	}

	private AnimationFrame[] frames;
	private Vector2D position;

	private double width;
	private double height;
	private double rotation;

	private int timer = 0;
	private int frameIndex = 0;
	private boolean spriteChanged;

	public AnimatedSprite(Vector2D position, AnimationFrame... frames) {
		LOGGER.finer("Constructing " + this + " from " + frames);
		this.position = position;
		this.frames = frames;
		this.width = frames[0].getSprite().getWidth();
		this.height = frames[0].getSprite().getHeight();
		this.rotation = frames[0].getSprite().getRotation();

		Arrays.stream(frames).forEach(frame -> {
			frame.getSprite().setWidth(width);
			frame.getSprite().setHeight(height);
			frame.getSprite().setRotation(rotation);
			frame.getSprite().setPosition(position.clone());
		});
	}

	@Override
	public void update(long deltaMillis) {
		LOGGER.finest("update() on " + this);
		this.timer += deltaMillis;
		if (timer > this.frames[frameIndex].getFrameTime()) {
			nextFrame();
		}

	}

	private void nextFrame() {
		this.timer -= this.frames[frameIndex].getFrameTime();
		this.frameIndex++;
		
		if(frameIndex==this.frames.length)this.frameIndex=0;
		
		updateSpriteValues();

	}

	private void updateSpriteValues() {
		AnimationFrame frame = this.frames[frameIndex];
		frame.getSprite().setRotation(rotation);
		frame.getSprite().setWidth(width);
		frame.getSprite().setHeight(height);
		this.spriteChanged = false;
	}

	@Override
	public void draw(GraphicsContext gc) {

		if (this.spriteChanged) {
			updateSpriteValues();
		}
		this.frames[frameIndex].getSprite().draw(gc);
	}

	@Override
	public Vector2D getPosition() {
		return this.position;
	}

	@Override
	public void translate(Vector2D vector) {
		this.position.translate(vector);
		Arrays.stream(frames).forEach(frame -> {
			frame.getSprite().translate(vector);
		});

	}

	public void setPosition(Vector2D position) {
		LOGGER.finer("setPosition() of " + this + " to " + position);
		Vector2D translation = this.position.clone();
		translation.scale(-1);
		translation.translate(position);
		this.translate(translation);
	}

	public double getWidth() {
		return width;

	}

	public void setWidth(double width) {
		LOGGER.finer("setWidth() of " + this + " to " + width);
		this.width = width;
		Arrays.stream(frames).forEach(frame -> {
			frame.getSprite().setWidth(width);
		});
		this.spriteChanged = true;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		LOGGER.finer("setHeight() of " + this + " to " + height);
		this.height = height;
		Arrays.stream(frames).forEach(frame -> {
			frame.getSprite().setHeight(height);
		});
		this.spriteChanged = true;
	}

	public double getRotation() {
		return rotation;
	}

	public void setRotation(double rotation) {
		LOGGER.finer("setRotation() of " + this + " to " + rotation);
		this.rotation = rotation;
		Arrays.stream(frames).forEach(frame -> {
			frame.getSprite().setRotation(rotation);
		});
		this.spriteChanged = true;
	}

}
