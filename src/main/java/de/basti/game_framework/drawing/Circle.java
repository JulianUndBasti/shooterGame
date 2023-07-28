package de.basti.game_framework.drawing;

import java.util.logging.Level;
import java.util.logging.Logger;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.math.Vector2D;
import javafx.scene.canvas.GraphicsContext;

public class Circle extends Shape{
	private static final Logger LOGGER = Logger.getLogger(Circle.class.getName());
	static {
		LOGGER.setLevel(Level.INFO);
	}
	private double radius;
	
	public Circle(Vector2D position,double radius) {
		super(position);
		LOGGER.finer("Construction " + this);
		this.setRadius(radius);
		// TODO Auto-generated constructor stub
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		LOGGER.finer("setRadius() of " + this + " to " + radius);
		this.radius = radius;
	}

	@Override
	public void draw(GraphicsContext gc) {
		LOGGER.finest("draw() " + this + " to " + gc);
		gc.beginPath();
		gc.setStroke(strokeColor);
		gc.setFill(fillColor);
		gc.setLineWidth(lineWidth);

		if (this.isShouldFill()) {
			gc.fillOval(this.position.getX()-radius, this.position.getY()-radius, radius*2, radius*2);
		} else {
			gc.strokeOval(this.position.getX()-radius, this.position.getY()-radius, radius*2, radius*2);
		}

		gc.closePath();
		
	}
	
	
	
}
