package de.julian_und_basti.shootergame.entities;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.controls.Entity;
import de.basti.game_framework.controls.Game;
import de.basti.game_framework.controls.Updatable;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.math.Vector2D;

public abstract class CustomEntity<D extends Drawable, C extends BoxCollider> extends Entity<D, C, EntityType> {

	private Game<CustomEntity<? extends Drawable, ? extends BoxCollider>> game;
	private int weight = 1;

	public CustomEntity(Vector2D position, C collider, D drawable, EntityType type,
			Game<CustomEntity<? extends Drawable, ? extends BoxCollider>> game) {
		super(position, collider, drawable, type);
		this.game = game;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public Game<CustomEntity<? extends Drawable, ? extends BoxCollider>> getGame() {
		return game;
	}

	public void setGame(Game<CustomEntity<? extends Drawable, ? extends BoxCollider>> game) {
		this.game = game;
	}

}
