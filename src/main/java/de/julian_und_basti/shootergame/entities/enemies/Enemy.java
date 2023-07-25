package de.julian_und_basti.shootergame.entities.enemies;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.controls.Engine;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.entities.CustomEntity;
import de.julian_und_basti.shootergame.entities.EntityType;
import de.julian_und_basti.shootergame.entities.player_projectiles.PlayerProjectile;

public abstract class Enemy<D extends Drawable> extends CustomEntity<D, BoxCollider>{
	
	private int health = 100;
	private double speed = 0.2;
	private int damage = 15; 

	public Enemy(Vector2D position, BoxCollider collider, D drawable,Engine<CustomEntity<? extends Drawable, ? extends BoxCollider>> game) {
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
	
	private Vector2D movement = new Vector2D();
	
	protected Vector2D getMovementDirection(Vector2D goal) {
		movement.set(this.getPosition().getX(), this.getPosition().getY());
		movement.scale(-1);
		movement.translate(goal);
		return movement;

	}

	public abstract void hit(PlayerProjectile p);

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	

}
