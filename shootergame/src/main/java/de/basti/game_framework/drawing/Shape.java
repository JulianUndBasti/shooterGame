package de.basti.game_framework.drawing;

import de.basti.game_framework.math.Vector2D;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Should be extended by all {@code Drawable} implementations which represent a shape.
 * This has variables for stroking or filling Color, the width of outer lines and the position.
 *	
 * @see Drawable
 * @see Rectagle
 */
public abstract class Shape implements Drawable {
	protected Vector2D position;
	protected Paint strokeColor = Color.BLACK;
	protected Paint fillColor = Color.BLACK;
	protected double lineWidth = 2;
	private boolean shouldFill = true;
	
	
	public Shape(Vector2D position) {
		super();
		this.position = position;
	}

	public Vector2D getPosition() {
		return this.position;
	}
	
	public void translate(Vector2D vector) {
		this.position.translate(vector);
	}



	public Paint getStrokeColor() {
		return strokeColor;
	}



	public void setStrokeColor(Paint strokeColor) {
		this.strokeColor = strokeColor;
	}



	public Paint getFillColor() {
		return fillColor;
	}



	public void setFillColor(Paint fillColor) {
		this.fillColor = fillColor;
	}



	public double getLineWidth() {
		return lineWidth;
	}



	public void setLineWidth(double lineWidth) {
		this.lineWidth = lineWidth;
	}



	public boolean isShouldFill() {
		return shouldFill;
	}



	public void setShouldFill(boolean shouldFill) {
		this.shouldFill = shouldFill;
	}
	
	
	
	
	
	

}
