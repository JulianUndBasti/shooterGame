package de.julian_und_basti.shootergame.weapons;

import de.basti.game_framework.controls.Updatable;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.entities.player_projectiles.PlayerProjectile;
import de.julian_und_basti.shootergame.entities.player_projectiles.PlayerProjectileFactory;
import de.julian_und_basti.shootergame.entities.player_projectiles.PlayerProjectileStats;

public abstract class Weapon implements Updatable {

	private int shotDelay;
	private int timeSinceLastShot;
	
	private PlayerProjectileFactory factory;
	
	private PlayerProjectileStats stats = new PlayerProjectileStats(0, 0);
	private double radiansSpread = 0;
	
	
	public Weapon(int shotDelay, PlayerProjectileFactory factory, PlayerProjectileStats stats) {
		this.shotDelay = shotDelay;
		this.timeSinceLastShot = shotDelay;
		this.factory = factory;
		this.setStats(stats);
	}

	@Override
	public void update(long deltaMillis) {
		this.timeSinceLastShot += deltaMillis;

	}

	public void shootIfPossible(Vector2D shootPosition, Vector2D direction) {
		if (timeSinceLastShot < shotDelay) {
			return;
		}
		
		double currAngle = Math.atan2(direction.getY(), direction.getX());
		currAngle+=(Math.random()*this.radiansSpread)-(this.radiansSpread/2);
		direction.set(Math.cos(currAngle),Math.sin(currAngle));
		
		this.shoot(shootPosition, direction);
		this.timeSinceLastShot = 0;
	}

	protected abstract void shoot(Vector2D playerPosition, Vector2D direction);

	public int getShotDelay() {
		return shotDelay;
	}

	public void setShotDelay(int shotDelay) {
		this.shotDelay = shotDelay;
	}
	
	protected PlayerProjectile getNewProjectile(Vector2D shootPosition, Vector2D direction) {
		return factory.getNew(shootPosition, direction, this.stats);
	}

	public PlayerProjectileStats getStats() {
		return stats;
	}

	public void setStats(PlayerProjectileStats stats) {
		this.stats = stats;
	}

	public int getTimeSinceLastShot() {
		return timeSinceLastShot;
	}

	public void setTimeSinceLastShot(int timeSinceLastShot) {
		this.timeSinceLastShot = timeSinceLastShot;
	}

	public PlayerProjectileFactory getFactory() {
		return factory;
	}

	public void setFactory(PlayerProjectileFactory factory) {
		this.factory = factory;
	}

	public double getRadiansSpread() {
		return radiansSpread;
	}

	public void setRadiansSpread(double radiansSpread) {
		this.radiansSpread = radiansSpread;
	}
	
	
	
	
	
	

}
