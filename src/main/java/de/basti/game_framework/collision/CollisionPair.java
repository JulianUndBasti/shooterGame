package de.basti.game_framework.collision;

import de.basti.game_framework.controls.Entity;
import de.basti.game_framework.drawing.Drawable;

/**
 * Wrapper for two Collider Objects, which have collided. 
 * The equals method returns true even if the {@code Collider}s are swapped.
 *
 * @param <T> type of {@code Collider}  to use
 * 
 * @see GameCollisionSystem
 * @see Collider
 * @see CollisionHandler
 *  
 */
public class CollisionPair<T extends Entity<? extends Drawable,? extends Collider,? extends Enum<?>>> {
	private T collider1;
	private T collider2;
	private CollisionType type = null;
	
	
	public CollisionPair(T collider1, T collider2) {
		super();
		this.collider1 = collider1;
		this.collider2 = collider2;
		
	}
	
	public T getCollider1() {
		return collider1;
	}
	public void setCollider1(T collider1) {
		this.collider1 = collider1;
	}
	public T getCollider2() {
		return collider2;
	}
	public void setCollider2(T collider2) {
		this.collider2 = collider2;
	}
	
	public boolean contains(T other) {
		if(collider1.equals(other)||collider2.equals(other)) {
			return true;
		}
		return false;
	}

	public boolean equals(CollisionPair<T> other) {
		if (other == null)
			return false;
		if(contains(other.collider1)&&contains(other.collider2)) {
			return true;
		}
		return false;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((collider1 == null) ? 0 : collider1.hashCode());
		result = prime * result + ((collider2 == null) ? 0 : collider2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CollisionPair<T> other = (CollisionPair<T>) obj;
		return this.equals(other);
	}
	
	

	@Override
	public String toString() {
		return "CollisionPair [collider1=" + collider1 + ", collider2=" + collider2 + "]";
	}

	public CollisionType getType() {
		return type;
	}

	public void setType(CollisionType type) {
		this.type = type;
	}
	
	
	
	
	
	
	
	
	
}
