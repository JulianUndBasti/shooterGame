package de.julian_und_basti.shootergame.weapons;

import de.basti.game_framework.drawing.DrawingLayer;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.Game;
import de.julian_und_basti.shootergame.entities.player_projectiles.PlayerProjectile;
import de.julian_und_basti.shootergame.entities.player_projectiles.PlayerProjectileFactory;
import de.julian_und_basti.shootergame.entities.player_projectiles.PlayerProjectileStats;

public class MachineGun extends Weapon{
	
	public static final PlayerProjectileStats DEFAULT_STATS = new PlayerProjectileStats(5, 0.6);
	
	
	public MachineGun(PlayerProjectileFactory factory) {
		super(60,factory,DEFAULT_STATS);
		this.setRadiansSpread(Math.toRadians(8));
		
	}
	
	public MachineGun(PlayerProjectileFactory factory, PlayerProjectileStats stats) {
		super(1, factory, stats);

	}

	@Override
	protected void shoot(Vector2D shootPosition, Vector2D direction) {
		PlayerProjectile projectile = this.getNewProjectile(shootPosition, direction);
		
		Game.addEntity(DrawingLayer.MIDDLE, projectile);
	}
	

}
