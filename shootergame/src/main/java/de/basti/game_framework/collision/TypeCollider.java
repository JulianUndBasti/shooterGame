package de.basti.game_framework.collision;

import de.basti.game_framework.collision.Collider;
import de.basti.game_framework.math.Vector2D;

//diese KLasse implementiert das interface Collider, ist also selbst ein Collider, aber sie kann durch 
//int type auch von anderen Unterschieden werden
public class TypeCollider<C extends Collider,T extends Enum> implements Collider {
	
	private C collider;
	private T type;
	
	//der Collider der hier der hgier übergeben wird ist der eigentliche Collider
	public TypeCollider(C coll, T type) {
		this.collider = coll;
		this.type = type;
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
	public void translate(Vector2D vector) {
		this.collider.translate(vector);
		
	}

	public C getCollider() {
		return collider;
	}

	public void setCollider(C collider) {
		this.collider = collider;
	}

	public T getType() {
		return type;
	}

	public void setType(T type) {
		this.type = type;
	}
	
	

}
