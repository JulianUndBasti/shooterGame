package de.julian_und_basti.shootergame.entities.player_projectiles;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.controls.TypeEntity;
import de.basti.game_framework.controls.Updatable;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.drawing.Rectangle;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.entities.EntityType;
import de.julian_und_basti.shootergame.entities.UpdatableWeightTypeEntity;
import de.julian_und_basti.shootergame.entities.enemies.Enemy;

public abstract class PlayerProjectile extends UpdatableWeightTypeEntity<Rectangle, BoxCollider>{

	private double speed = 0.3;

	private double damage = 20;

	public PlayerProjectile(Vector2D position, BoxCollider collider, Rectangle drawable, EntityType type) {
		super(position, collider, drawable, type);

	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}
	
	public abstract void hit(Enemy<?> enemy);

}
