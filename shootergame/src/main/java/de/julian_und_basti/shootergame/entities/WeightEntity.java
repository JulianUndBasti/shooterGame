package de.julian_und_basti.shootergame.entities;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.controls.Entity;
import de.basti.game_framework.controls.Updatable;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.math.Vector2D;

public abstract class WeightEntity<D extends Drawable,C extends BoxCollider> extends Entity<D, C, EntityType>{
	public WeightEntity(Vector2D position, C collider, D drawable, EntityType type) {
		super(position, collider, drawable, type);
	}
	private int weight = 1;

	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}

}
