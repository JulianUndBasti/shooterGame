package de.julian_und_basti.shootergame.weapons;

import de.basti.game_framework.drawing.DrawingLayer;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.Game;
import de.julian_und_basti.shootergame.entities.player_projectiles.PlayerProjectile;
import de.julian_und_basti.shootergame.entities.player_projectiles.PlayerProjectileFactory;
import de.julian_und_basti.shootergame.entities.player_projectiles.SimplePlayerProjectile;

public class RocketLauncher extends Weapon {

	public RocketLauncher(PlayerProjectileFactory factory) {
		super(1000, factory);

	}

	@Override
	protected void shoot(Vector2D shootPosition, Vector2D mousePosition) {
		PlayerProjectile projectile = this.getNewProjectile(shootPosition, mousePosition);
		projectile.setSpeed(1);

		Game.addEntity(DrawingLayer.MIDDLE, projectile);

	}
}
