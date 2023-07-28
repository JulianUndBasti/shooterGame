package de.basti.game_framework.drawing;

import java.util.logging.Level;
import java.util.logging.Logger;

import de.basti.game_framework.math.Vector2D;
import javafx.scene.canvas.GraphicsContext;

/**
 * Implementation of {@code Shape} which represents a single-colored Rectangle.
 * 
 * @see Shape
 * @see Drawable
 */
public class Rectangle extends Shape {
	private static final Logger LOGGER = Logger.getLogger(Rectangle.class.getName());
	static {
		LOGGER.setLevel(Level.INFO);
	}
	
	private double width = 0;
	private double height = 0;

	public Rectangle(Vector2D position, double width, double height) {
		super(position);
		LOGGER.finer("Construction " + this);
		this.width = width;
		this.height = height;
	}

	@Override
	public void draw(GraphicsContext gc) {
		LOGGER.finest("draw() " + this + " to " + gc);
		gc.beginPath();
		gc.setStroke(strokeColor);
		gc.setFill(fillColor);
		gc.setLineWidth(lineWidth);

		if (this.isShouldFill()) {
			gc.fillRect(this.position.getX()-width/2, this.position.getY()-width/2, width, height);
		} else {
			gc.strokeRect(this.position.getX()-width/2, this.position.getY()-width/2, width, height);
		}

		gc.closePath();
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		LOGGER.finer("setWidth() of " + this + " to " + width);
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		LOGGER.finer("setHeight() of " + this + " to " + height);
		this.height = height;
	}

	@Override
	public String toString() {
		return "Rectangle [width=" + width + ", height=" + height + ", position=" + position + ", strokeColor="
				+ strokeColor + ", fillColor=" + fillColor + "]";
	}

}
