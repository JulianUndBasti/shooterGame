package de.julian_und_basti.shootergame.entities.enemies;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.controls.Game;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.entities.CustomEntity;
import de.julian_und_basti.shootergame.entities.EntityType;
import de.julian_und_basti.shootergame.entities.player_projectiles.PlayerProjectile;

public abstract class Enemy<D extends Drawable> extends CustomEntity<D, BoxCollider>{
	
	private int health = 100;
	private double speed = 0.2;

	public Enemy(Vector2D position, BoxCollider collider, D drawable,Game<CustomEntity<? extends Drawable, ? extends BoxCollider>> game) {
		super(position, collider, drawable, EntityType.ENEMY, game);
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
