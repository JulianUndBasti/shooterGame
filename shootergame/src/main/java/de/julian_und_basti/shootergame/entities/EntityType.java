package de.julian_und_basti.shootergame.entities;

public enum EntityType {
	PLAYER(70),
	ENEMY(30),
	PLAYER_PROJECTILE(1);

	public final int collisionWeight;
	
	private EntityType(int collWeight) {
		this.collisionWeight = collWeight;
	}
}
