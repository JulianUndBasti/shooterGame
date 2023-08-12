package de.basti.game_framework.math;

public class Circle {
	private Vector2D position;
	private double radius;

	public Circle(Vector2D position, double radius) {
		super();
		this.position = position;
		this.radius = radius;
	}

	public Vector2D getPosition() {
		return position;
	}

	public void setPosition(Vector2D position) {
		this.position = position;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public void translate(Vector2D other) {
		position.translate(other);
	}

	public void translate(double x, double y) {
		position.translate(x, y);
	}
	
	

}
