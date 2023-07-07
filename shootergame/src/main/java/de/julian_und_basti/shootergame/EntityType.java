package de.julian_und_basti.shootergame;

public enum EntityType {
	PLAYER(100),
	ENEMY(50);

	public final int collisionPriority;
	
	private EntityType(int collPrio) {
		this.collisionPriority = collPrio;
	}
}
