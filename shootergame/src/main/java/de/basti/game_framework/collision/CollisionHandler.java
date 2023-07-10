package de.basti.game_framework.collision;

/**
 * Handler which gets called by {@code GameCollisionSystem}, when a collision happens.
 * The {@code CollisionPair} has the two colliding colliders.
 * 
 * @param <T> type of {@code Collider} to use
 * 
 * @see GameCollisionSystem
 * @see CollisionPair
 * @see Collider
 * 
 */
public interface CollisionHandler<T extends Collider>  {
	public void onBegin(CollisionPair<T> pair);
	public void onOngoing(CollisionPair<T> pair);
	public void onEnd(CollisionPair<T> pair);
	
}
