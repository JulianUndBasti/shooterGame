package de.basti.game_framework.collision;

import de.basti.game_framework.math.Vector2D;

public class CircleCollider implements Collider {

	private Vector2D position;
	private double radius;

	int precision = 10;
	
	boolean radiusChanged = false;
	private Vector2D[] vectors = new Vector2D[precision];

	public CircleCollider(Vector2D position, double radius) {
		this.setPosition(position);
		this.setRadius(radius);

	}

	private void calculateVectors() {
		for (int i = 0; i < precision; i++) {
			double a = (double) i;
			a = a / precision;
			a = a * 2 * Math.PI;
			Vector2D v = new Vector2D();
			v.set(Math.sin(a)*radius, Math.cos(a)*radius);
			v.translate(this.position.getX()+radius,this.position.getY()+radius);
			this.vectors[i] = v;
		}
	}

	@Override
	public boolean collidesWith(Vector2D vector) {
		Vector2D center = this.position.clone();
		center.translate(radius, radius);
		center.translate(vector.scaled(-1));
		if(center.length()<this.radius) {
			return true;
		}
		return false;
	
		
	
	
		
		
	}

	@Override
	public Vector2D[] getVectors() {
		if(radiusChanged) {
			this.calculateVectors();
			radiusChanged = false;
		}
		return this.vectors;
	}

	@Override
	public void translate(Vector2D vector) {
		//why does this not work
		//Arrays.stream(this.vectors).forEach(v->v.translate(vector));
		this.radiusChanged = true;//workaround, much slower
		this.position.translate(vector);

	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
		this.radiusChanged = true;
		
	}

	public Vector2D getPosition() {
		return position;
	}

	public void setPosition(Vector2D position) {
		this.position = position;
	}

}
