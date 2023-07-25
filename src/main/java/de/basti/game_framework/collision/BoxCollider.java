package de.basti.game_framework.collision;

import java.util.ArrayList;
import java.util.List;

import de.basti.game_framework.math.Vector2D;

/**
 * A {@code Collider} representing a Rectangle collider.
 * 
 * @see Collider
 * @see GameCollisionSystem
 * 
 */
public class BoxCollider implements Collider {
	private Vector2D position;
	private double width;
	private double height;
	
	public static int vectorsPerSide = 5;
	
	private Vector2D[] vectors;
	

	public BoxCollider(Vector2D position, double width, double height) {
		super();
		this.position = position;
		this.width = width;
		this.height = height;
		this.recalculateVectors();
	}

	@Override
	public boolean collidesWith(Vector2D vector) {
		double x = position.getX()-width/2;
		double y = position.getY()-height/2;

		double x1 = vector.getX();
		double y1 = vector.getY();

		boolean xCollision = ((x1 >= x) && (x1 <= x + width));
		boolean yCollision = ((y1 >= y) && (y1 <= y + height));
		
		
		return xCollision&&yCollision;
		
	}

	@Override
	public Vector2D[] getVectors() {
		return vectors;

	}
	
	private void recalculateVectors() {
		int vectorXDistance = (int) (this.width/(3+1));
		int vectorYDistance = (int) (this.height/(3+1));
		List<Vector2D> vectorList = new ArrayList<>();
		int length = 0;
		while(length<this.width) {
			vectorList.add(makeVectorAbsolute(new Vector2D(length,0)));
			vectorList.add(makeVectorAbsolute(new Vector2D(length,height)));
			length+=vectorXDistance;
		}
		
		length = 0;
		while(length<this.height) {
			vectorList.add(makeVectorAbsolute(new Vector2D(0,length)));
			vectorList.add(makeVectorAbsolute(new Vector2D(width,length)));
			length+=vectorYDistance;
		}
		vectorList.add(position.translated(width/2,height/2));
		
		this.vectors = new Vector2D[vectorList.size()];
		vectorList.toArray(this.vectors);

	}
	
	private Vector2D makeVectorAbsolute(Vector2D vector) {
		Vector2D newVector = vector.clone();
		newVector.translate(this.position);
		newVector.translate(-this.width/2,-this.height/2);
		return newVector;
	}

	@Override
	public void translate(Vector2D vector) {
		this.position.translate(vector);
		this.recalculateVectors();
	}

	public Vector2D getPosition() {
		
		return position;
	}

	public void setPosition(Vector2D position) {
		this.position = position;
		this.recalculateVectors();
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
		this.recalculateVectors();
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
		this.recalculateVectors();
	}
	
	

}
