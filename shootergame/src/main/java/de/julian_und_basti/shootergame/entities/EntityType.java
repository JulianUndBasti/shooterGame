package de.julian_und_basti.shootergame.entities;

public enum EntityType {
	PLAYER(100),
	ENEMY(50),
	PLAYER_PROJECTILE(1);

	public final int collisionPriority;
	
	private EntityType(int collPrio) {
		this.collisionPriority = collPrio;
	}
}
