package de.julian_und_basti.shootergame.entities;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.controls.TypeEntity;
import de.basti.game_framework.controls.Updatable;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.math.Vector2D;

public abstract class Enemy<D extends Drawable> extends TypeEntity<D, BoxCollider, EntityType> implements Updatable {

	private double health = 100;
	private double speed = 0.2;

	public Enemy(Vector2D position, BoxCollider collider, D drawable, EntityType type) {
		super(position, collider, drawable, type);
	}

	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
		this.health = health;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public void moveIntoDirection(Vector2D direction, double deltaMillis) {
		direction = direction.clone();
		direction.normalize();
		direction.scale(this.speed*deltaMillis);
		this.translate(direction);
	}

	public abstract void hit(Projectile p);

}
