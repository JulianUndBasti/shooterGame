package de.julian_und_basti.shootergame.entities.player_projectiles;

import de.basti.game_framework.math.Vector2D;

public interface PlayerProjectileFactory<P extends PlayerProjectile> {
	public P getNew(Vector2D shootPosition, Vector2D mousePosition);
}
