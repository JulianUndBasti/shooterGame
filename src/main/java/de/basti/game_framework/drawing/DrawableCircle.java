package de.basti.game_framework.drawing;

import java.util.logging.Level;
import java.util.logging.Logger;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.math.Circle;
import de.basti.game_framework.math.Vector2D;
import javafx.scene.canvas.GraphicsContext;

public class DrawableCircle extends Shape{
	private static final Logger LOGGER = Logger.getLogger(DrawableCircle.class.getName());
	static {
		LOGGER.setLevel(Level.INFO);
	}
	private Circle circle;
	public DrawableCircle(Vector2D position,double radius) {
		this(new Circle(position,radius));
	}
	
	public DrawableCircle(Circle circle) {
		LOGGER.finer("Construction " + this);
		this.circle = circle;
	}

	public double getRadius() {
		return this.circle.getRadius();
	}

	public void setRadius(double radius) {
		LOGGER.finer("setRadius() of " + this + " to " + radius);
		this.circle.setRadius(radius);
	}

	@Override
	public void draw(GraphicsContext gc) {
		LOGGER.finest("draw() " + this + " to " + gc);
		gc.beginPath();
		gc.setStroke(strokeColor);
		gc.setFill(fillColor);
		gc.setLineWidth(lineWidth);
		
		Vector2D position = this.circle.getPosition();
		double radius = this.circle.getRadius();

		if (this.isShouldFill()) {
			gc.fillOval(position.getX()-radius, position.getY()-radius, radius*2, radius*2);
		} else {
			gc.strokeOval(position.getX()-radius, position.getY()-radius, radius*2, radius*2);
		}

		gc.closePath();
		
	}

	@Override
	public Vector2D getPosition() {
		return this.circle.getPosition();
	}

	@Override
	public void translate(Vector2D vector) {
		this.circle.translate(vector);
	}
	
	
	
}
