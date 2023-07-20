package de.basti.game_framework.collision;

import de.basti.game_framework.controls.Entity;
import de.basti.game_framework.drawing.Drawable;

/**
 * Handler which gets called by {@code GameCollisionSystem}, when a collision
 * happens. The {@code CollisionPair} has the two colliding colliders.
 * 
 * @param <T> type of {@code Collider} to use
 * 
 * @see GameCollisionSystem
 * @see CollisionPair
 * @see Collider
 * 
 */
public abstract class CollisionHandler<T extends Entity<? extends Drawable, ? extends Collider, ? extends Enum<?>>> {
	
	public void onCollision(CollisionPair<T> pair) {
		switch (pair.getType()) {
		case BEGIN:
			this.onBegin(pair);
			break;
		case ONGOING:
			this.onOngoing(pair);
			break;
		case END:
			this.onEnd(pair);
			break;

		}
	}

	public abstract void onBegin(CollisionPair<T> pair);

	public abstract void onOngoing(CollisionPair<T> pair);

	public abstract void onEnd(CollisionPair<T> pair);

}
