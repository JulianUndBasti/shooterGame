package de.julian_und_basti.shootergame.entities.player_projectiles;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.controls.Engine;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.drawing.DrawableRectangle;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.entities.EntityType;
import de.julian_und_basti.shootergame.entities.CustomEntity;
import de.julian_und_basti.shootergame.entities.enemies.Enemy;

public abstract class PlayerProjectile extends CustomEntity<DrawableRectangle, BoxCollider>{

	private double speed;
	private int damage;
	
	private Vector2D movement = new Vector2D(0,0);
	
	private int maxLifeTimeMillis = 2500;
	private int lifeTimeMillis = 0;

	public PlayerProjectile(Vector2D position, BoxCollider collider, DrawableRectangle drawable, PlayerProjectileStats stats,Engine<CustomEntity<? extends Drawable, ? extends BoxCollider>> engine) {
		super(position, collider, drawable, EntityType.PLAYER_PROJECTILE,engine);
		this.setWeight(0);
		this.setSpeed(stats.speed);
		this.setDamage(stats.damage); 
		

	}
	
	@Override
	public void update(long deltaMillis) {
		this.lifeTimeMillis+=deltaMillis;
		if(lifeTimeMillis >maxLifeTimeMillis) {
			this.getEngine().removeEntity(this);
			return;
		}
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
	
	

	public int getLifeTimeMillis() {
		return lifeTimeMillis;
	}

	public void setLifeTimeMillis(int lifeTimeMillis) {
		this.lifeTimeMillis = lifeTimeMillis;
	}

	public abstract void hit(Enemy<?> enemy);
	public abstract void hitWall();
	

}
