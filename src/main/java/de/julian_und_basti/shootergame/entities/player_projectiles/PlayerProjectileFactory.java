package de.julian_und_basti.shootergame.entities.player_projectiles;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.controls.Engine;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.entities.CustomEntity;

public interface PlayerProjectileFactory{
	public PlayerProjectile getNew(Vector2D shootPosition, Vector2D movement, PlayerProjectileStats stats,Engine<CustomEntity<? extends Drawable, ? extends BoxCollider>> game);
}
