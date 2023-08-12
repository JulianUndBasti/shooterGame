package de.basti.game_framework.collision;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.basti.game_framework.collision.system.NaiveCollisionSystem;
import de.basti.game_framework.math.Vector2D;

/**
 * A {@code Collider} representing a Rectangle collider.
 * 
 * @see Collider
 * @see NaiveCollisionSystem
 * 
 */
public class BoxCollider implements Collider {
	private static final Logger LOGGER = Logger.getLogger(BoxCollider.class.getName());
	static {
		LOGGER.setLevel(Level.INFO);
	}
	
	private Vector2D position;
	private double width;
	private double height;
	
	public static int vectorsPerSide = 5;
	
	private Vector2D[] vectors = {new Vector2D(),new Vector2D(),new Vector2D(),new Vector2D(),};
	

	public BoxCollider(Vector2D position, double width, double height) {
		super();
		LOGGER.finer("Constructing " + this.toString());
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
		
		boolean collidesWith = xCollision&&yCollision;
		LOGGER.finest("collidesWith() of " + this + " with " + vector + " is " + collidesWith);
		return collidesWith;
		
	}

	@Override
	public Vector2D[] getVectors() {
		return vectors;

	}
	
	private void recalculateVectors() {
		LOGGER.finest("recalculateVectors() of " + super.toString());
		double x = this.position.getX();
		double y = this.position.getY();
		double w = this.width;
		double h= this.height;
		
		
		vectors[0].set(x-w/2, y-h/2);
		vectors[1].set(x-w/2, y+h/2);
		vectors[2].set(x+w/2, y-h/2);
		vectors[3].set(x+w/2, y+h/2);
		

	}
	

	@Override
	public void translate(Vector2D vector) {
		LOGGER.finest("translate() of " + super.toString() + " by " + vector);
		this.position.translate(vector);
		this.recalculateVectors();
	}

	public Vector2D getPosition() {
		
		return position;
	}

	public void setPosition(Vector2D position) {
		LOGGER.finest("setPosition() of " + super.toString() + " to " + position);
		this.position = position;
		this.recalculateVectors();
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		LOGGER.finest("setWidth() of " + super.toString() + " to " + width);
		this.width = width;
		this.recalculateVectors();
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		LOGGER.finest("setHeight() of " + super.toString() + " to " + height);
		this.height = height;
		this.recalculateVectors();
	}
	
	

}
