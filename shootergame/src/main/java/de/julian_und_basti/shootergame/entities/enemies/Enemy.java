package de.julian_und_basti.shootergame.entities.enemies;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.controls.TypeEntity;
import de.basti.game_framework.controls.Updatable;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.entities.EntityType;
import de.julian_und_basti.shootergame.entities.UpdatableWeightTypeEntity;
import de.julian_und_basti.shootergame.entities.player_projectiles.PlayerProjectile;
import de.julian_und_basti.shootergame.entities.player_projectiles.SimplePlayerProjectile;

public abstract class Enemy<D extends Drawable> extends UpdatableWeightTypeEntity<D, BoxCollider>{

	private int health = 100;
	private double speed = 0.2;

	public Enemy(Vector2D position, BoxCollider collider, D drawable, EntityType type) {
		super(position, collider, drawable, type);
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public void moveIntoDirection(Vector2D direction, double deltaMillis) {
		
	}

	public abstract void hit(PlayerProjectile p);

}
