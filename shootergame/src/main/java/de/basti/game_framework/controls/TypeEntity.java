package de.basti.game_framework.controls;

import de.basti.game_framework.collision.Collider;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.math.Vector2D;

//diese KLasse implementiert das interface Collider, ist also selbst ein Collider, aber sie kann durch 
//int type auch von anderen Unterschieden werden
public class TypeEntity<D extends Drawable,C extends Collider,T extends Enum> extends Entity<D,C> {
	
	private T type;
	
	public TypeEntity(Vector2D position, C collider, D drawable,T type) {
		super(position, collider, drawable);
		this.type = type;
	}

	

	public T getType() {
		return type;
	}

	public void setType(T type) {
		this.type = type;
	}
	
	

}
