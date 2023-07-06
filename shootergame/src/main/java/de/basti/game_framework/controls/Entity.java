package de.basti.game_framework.controls;

import de.basti.game_framework.collision.Collider;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.math.Vector2D;
import javafx.scene.canvas.GraphicsContext;

public class Entity<D extends Drawable, C extends Collider> implements Drawable,Collider{
	private Vector2D position;
	private C collider;
	private D drawable;
	
	public Entity(Vector2D position, C collider, D drawable) {
		super();
		this.position = position;
		this.collider = collider;
		this.drawable = drawable;
	}
	
	public C getCollider() {
		return collider;
	}
	public void setCollider(C collider) {
		this.collider = collider;
	}
	public D getDrawable() {
		return drawable;
	}
	public void setDrawable(D drawable) {
		this.drawable = drawable;
	}

	/**
	 * sets the position of the whole Entity, including the collider and the drawable, to that position
	 * the relative positions of the Entity, the Drawable and the Collider stay the same
	 */
	public void setPosition(Vector2D newPosition) {
		//translate instead of setting the position, to keep relative positions
		Vector2D translation = this.position.clone();
		translation.scale(-1);
		translation.translate(newPosition);
		this.translate(translation);
	}
	

	
	public Vector2D getPosition() {
		return position;
	}

	public void translate(Vector2D vector) {
		this.position.translate(vector);
		this.collider.translate(vector);
		this.drawable.translate(vector);
		
	}

	@Override
	public Vector2D[] getVectors() {
		return this.collider.getVectors();
	}

	@Override
	public boolean collidesWith(Vector2D vector) {
		return this.collider.collidesWith(vector);
	}

	@Override
	public void draw(GraphicsContext gc) {
		this.drawable.draw(gc);
	}
	
}
