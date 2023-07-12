package de.julian_und_basti.shootergame.weapons;

import de.basti.game_framework.drawing.DrawingLayer;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.Game;
import de.julian_und_basti.shootergame.entities.player_projectiles.PlayerProjectile;
import de.julian_und_basti.shootergame.entities.player_projectiles.PlayerProjectileFactory;
import de.julian_und_basti.shootergame.entities.player_projectiles.PlayerProjectileStats;

public class Pistol extends Weapon{
	
	public static final PlayerProjectileStats DEFAULT_STATS = new PlayerProjectileStats(20, 0.3);
	
	public Pistol(PlayerProjectileFactory factory) {
		super(600,factory,DEFAULT_STATS);
		this.setRadiansSpread(Math.toRadians(15));
		
	}
	
	public Pistol(PlayerProjectileFactory factory, PlayerProjectileStats stats) {
		super(600, factory,stats);

	}

	@Override
	protected void shoot(Vector2D shootPosition, Vector2D direction) {
		PlayerProjectile projectile = this.getNewProjectile(shootPosition, direction);
		projectile.setSpeed(0.3);
		
		Game.addEntity(DrawingLayer.MIDDLE, projectile);
		
	}
	

}
