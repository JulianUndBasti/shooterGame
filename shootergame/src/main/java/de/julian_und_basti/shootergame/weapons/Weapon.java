package de.julian_und_basti.shootergame.weapons;

import java.util.function.Supplier;

import de.basti.game_framework.controls.Updatable;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.entities.player_projectiles.PlayerProjectile;
import de.julian_und_basti.shootergame.entities.player_projectiles.PlayerProjectileFactory;

public abstract class Weapon<P extends PlayerProjectile> implements Updatable {

	private int shotDelay;
	private int timeSinceLastShot;
	
	private PlayerProjectileFactory<P> factory;

	public Weapon(int shotDelay, PlayerProjectileFactory<P> factory) {
		this.shotDelay = shotDelay;
		this.timeSinceLastShot = shotDelay;
		
		this.factory = factory;
	}

	@Override
	public void update(long deltaMillis) {
		this.timeSinceLastShot += deltaMillis;

	}

	public void shootIfPossible(Vector2D shootPosition, Vector2D mousePosition) {
		if (timeSinceLastShot < shotDelay) {
			return;
		}
		this.shoot(shootPosition,mousePosition);
		this.timeSinceLastShot = 0;
	}

	protected abstract void shoot(Vector2D playerPosition, Vector2D mousePosition);

	public int getShotDelay() {
		return shotDelay;
	}

	public void setShotDelay(int shotDelay) {
		this.shotDelay = shotDelay;
	}
	
	protected P getNewProjectile(Vector2D shootPosition, Vector2D mousePosition) {
		return factory.getNew(shootPosition, mousePosition);
	}

}
