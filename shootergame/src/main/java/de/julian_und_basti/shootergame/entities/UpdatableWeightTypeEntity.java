package de.julian_und_basti.shootergame.entities;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.controls.TypeEntity;
import de.basti.game_framework.controls.Updatable;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.math.Vector2D;

public abstract class UpdatableWeightTypeEntity<D extends Drawable,C extends BoxCollider> extends TypeEntity<D, C, EntityType> implements Updatable{
	//TODO add physics weight
	public UpdatableWeightTypeEntity(Vector2D position, C collider, D drawable, EntityType type) {
		super(position, collider, drawable, type);
	}

	

}
