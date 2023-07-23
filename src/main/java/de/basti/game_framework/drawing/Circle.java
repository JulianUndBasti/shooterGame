package de.basti.game_framework.drawing;

import de.basti.game_framework.math.Vector2D;
import javafx.scene.canvas.GraphicsContext;

public class Circle extends Shape{
	
	private double radius;
	
	public Circle(Vector2D position,double radius) {
		super(position);
		this.setRadius(radius);
		// TODO Auto-generated constructor stub
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.beginPath();
		gc.setStroke(strokeColor);
		gc.setFill(fillColor);
		gc.setLineWidth(lineWidth);

		if (this.isShouldFill()) {
			gc.fillOval(this.position.getX(), this.position.getY(), radius*2, radius*2);
		} else {
			gc.strokeOval(this.position.getX(), this.position.getY(), radius*2, radius*2);
		}

		gc.closePath();
		
	}
	
	
	
}
