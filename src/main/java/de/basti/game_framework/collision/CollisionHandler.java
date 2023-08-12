package de.basti.game_framework.collision;

import java.util.logging.Level;
import java.util.logging.Logger;

import de.basti.game_framework.collision.system.NaiveCollisionSystem;
import de.basti.game_framework.controls.Entity;
import de.basti.game_framework.drawing.Drawable;

/**
 * Handler which gets called by {@code GameCollisionSystem}, when a collision
 * happens. The {@code CollisionPair} has the two colliding colliders.
 * 
 * @param <T> type of {@code Collider} to use
 * 
 * @see NaiveCollisionSystem
 * @see CollisionPair
 * @see Collider
 * 
 */
public abstract class CollisionHandler<T extends Collider> {
	private static final Logger LOGGER = Logger.getLogger(CollisionHandler.class.getName());
	static {
		LOGGER.setLevel(Level.INFO);
	}
	
	
	public void onCollision(CollisionPair<T> pair) {
		switch (pair.getType()) {
		case BEGIN:
			LOGGER.finer("Collision Begin:"+pair);
			this.onBegin(pair);
			break;
		case ONGOING:
			LOGGER.finer("Collision Ongoing:"+pair);
			this.onOngoing(pair);
			break;
		case END:
			LOGGER.finer("Collision End:"+pair);
			this.onEnd(pair);
			break;

		}
	}

	public abstract void onBegin(CollisionPair<T> pair);

	public abstract void onOngoing(CollisionPair<T> pair);

	public abstract void onEnd(CollisionPair<T> pair);

}
