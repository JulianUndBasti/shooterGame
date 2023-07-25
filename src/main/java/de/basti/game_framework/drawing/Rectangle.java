package de.basti.game_framework.drawing;

import de.basti.game_framework.math.Vector2D;
import javafx.scene.canvas.GraphicsContext;

/**
 * Implementation of {@code Shape} which represents a single-colored Rectangle.
 * 
 * @see Shape
 * @see Drawable
 */
public class Rectangle extends Shape {
	private double width = 0;
	private double height = 0;

	public Rectangle(Vector2D position, double width, double height) {
		super(position);
		this.width = width;
		this.height = height;
	}

	@Override
	public void draw(GraphicsContext gc) {
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
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	@Override
	public String toString() {
		return "Rectangle [width=" + width + ", height=" + height + ", position=" + position + ", strokeColor="
				+ strokeColor + ", fillColor=" + fillColor + "]";
	}

}
