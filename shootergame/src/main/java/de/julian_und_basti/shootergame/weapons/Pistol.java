package de.julian_und_basti.shootergame.weapons;

import de.basti.game_framework.drawing.DrawingLayer;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.Game;
import de.julian_und_basti.shootergame.entities.player_projectiles.PlayerProjectile;
import de.julian_und_basti.shootergame.entities.player_projectiles.PlayerProjectileFactory;
import de.julian_und_basti.shootergame.entities.player_projectiles.SimplePlayerProjectile;

public class Pistol<P extends PlayerProjectile> extends Weapon<P>{

	public Pistol(PlayerProjectileFactory<P> factory) {
		super(600,factory);
		
	}

	@Override
	protected void shoot(Vector2D shootPosition, Vector2D mousePosition) {
		PlayerProjectile projectile = this.getNewProjectile(shootPosition, mousePosition);
		projectile.setDamage(20);
		projectile.setSpeed(1);
		
		Game.collisionSystem.add(projectile);
		Game.drawing.add(DrawingLayer.MIDDLE, projectile);
		Game.loop.addUpdatableBefore(projectile);
		
	}
	

}
