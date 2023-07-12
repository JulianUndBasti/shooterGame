package de.julian_und_basti.shootergame.entities.player_projectiles;

import de.basti.game_framework.math.Vector2D;

public interface PlayerProjectileFactory{
	public PlayerProjectile getNew(Vector2D shootPosition, Vector2D mousePosition);
}
