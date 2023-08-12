package de.basti.game_framework.math;

public class Rectangle {
	private Vector2D position;
	private double width;
	private double height;
	
	
	public Vector2D getPosition() {
		return position;
	}
	public void setPosition(Vector2D position) {
		this.position = position;
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
	
	public double area() {
		return width/height;
	}
	
	
}
