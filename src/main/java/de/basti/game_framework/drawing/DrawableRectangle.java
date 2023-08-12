package de.basti.game_framework.drawing;

import java.util.logging.Level;
import java.util.logging.Logger;

import de.basti.game_framework.math.Rectangle;
import de.basti.game_framework.math.Vector2D;
import javafx.scene.canvas.GraphicsContext;

/**
 * Implementation of {@code Shape} which represents a single-colored Rectangle.
 * 
 * @see Shape
 * @see Drawable
 */
public class DrawableRectangle extends Shape {
	private static final Logger LOGGER = Logger.getLogger(DrawableRectangle.class.getName());
	static {
		LOGGER.setLevel(Level.INFO);
	}

	private Rectangle rectangle;

	public DrawableRectangle(Vector2D position, double width, double height) {
		this(new Rectangle(position, width, height));

	}

	public DrawableRectangle(Rectangle rect) {
		LOGGER.finer("Construction " + this);
		this.rectangle = rect;
	}

	@Override
	public void draw(GraphicsContext gc) {
		LOGGER.finest("draw() " + this + " to " + gc);
		gc.beginPath();
		gc.setStroke(strokeColor);
		gc.setFill(fillColor);
		gc.setLineWidth(lineWidth);
		
		Vector2D position = this.rectangle.getPosition();
		double w = this.rectangle.getWidth();
		double h = this.rectangle.getHeight();
		

		if (this.isShouldFill()) {
			gc.fillRect(position.getX() - w / 2, position.getY() - h / 2, w, h);
		} else {
			gc.strokeRect(position.getX() - w / 2, position.getY() - h / 2, w, h);
		}

		gc.closePath();
	}

	public double getWidth() {
		return this.rectangle.getWidth();
	}

	public void setWidth(double width) {
		LOGGER.finer("setWidth() of " + this + " to " + width);
		this.rectangle.setWidth(width);
	}

	public double getHeight() {
		return this.rectangle.getHeight();
	}

	public void setHeight(double height) {
		LOGGER.finer("setHeight() of " + this + " to " + height);
		this.rectangle.setHeight(height);
	}

	

	public Vector2D getPosition() {
		return this.rectangle.getPosition();
	}

	public void translate(Vector2D vector) {
		LOGGER.finer("translate() of" + this + " by " + vector);
		this.rectangle.translate(vector);
	}
	
	@Override
	public String toString() {
		return "DrawableRectangle [rectangle=" + rectangle + "]";
	}

}

	
