package de.julian_und_basti.shootergame.weapons;

import de.basti.game_framework.drawing.DrawingLayer;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.Game;
import de.julian_und_basti.shootergame.entities.player_projectiles.PlayerProjectile;
import de.julian_und_basti.shootergame.entities.player_projectiles.PlayerProjectileFactory;
import de.julian_und_basti.shootergame.entities.player_projectiles.SimplePlayerProjectile;

public class MachineGun extends Weapon{

	public MachineGun(PlayerProjectileFactory factory) {
		super(60,factory);
		
	}

	@Override
	protected void shoot(Vector2D shootPosition, Vector2D mousePosition) {
		PlayerProjectile projectile = this.getNewProjectile(shootPosition, mousePosition);
		projectile.setSpeed(0.8);
		
		Game.addEntity(DrawingLayer.MIDDLE, projectile);
	}
	

}
