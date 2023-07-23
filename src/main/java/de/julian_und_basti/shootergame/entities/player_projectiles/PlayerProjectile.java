package de.julian_und_basti.shootergame.entities.player_projectiles;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.controls.Game;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.drawing.Rectangle;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.entities.EntityType;
import de.julian_und_basti.shootergame.entities.CustomEntity;
import de.julian_und_basti.shootergame.entities.enemies.Enemy;

public abstract class PlayerProjectile extends CustomEntity<Rectangle, BoxCollider>{

	private double speed;
	private int damage;
	
	private Vector2D movement = new Vector2D(0,0);

	public PlayerProjectile(Vector2D position, BoxCollider collider, Rectangle drawable, PlayerProjectileStats stats,Game<CustomEntity<? extends Drawable, ? extends BoxCollider>> game) {
		super(position, collider, drawable, EntityType.PLAYER_PROJECTILE,game);
		this.setWeight(0);
		this.setSpeed(stats.speed);
		this.setDamage(stats.damage);
		

	}
	
	@Override
	public void update(long deltaMillis) {
		this.translate(movement.scaled(deltaMillis*speed));
		
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	
	
	public Vector2D getMovement() {
		return movement;
	}

	public void setMovement(Vector2D movement) {
		this.movement = movement;
	}

	public abstract void hit(Enemy<?> enemy);
	public abstract void hitWall();
	

}
