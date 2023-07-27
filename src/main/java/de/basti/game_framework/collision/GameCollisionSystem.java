package de.basti.game_framework.collision;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.basti.game_framework.controls.Entity;
import de.basti.game_framework.controls.Updatable;
import de.basti.game_framework.drawing.Drawable;
import de.julian_und_basti.shootergame.game.Game;

/**
 * Class which checks collisions between multiple {@code Collider}s, which can
 * be added to it. On collision begin, ongoing collision or collision end it
 * sends a {@code CollisionPair} to the respective {@code CollisionHandler}. The
 * collisions are only checked when the update(double deltaMillis) is called.
 * 
 * @param <T> type of {@code Collider} to use
 */
public class GameCollisionSystem<T extends Entity<? extends Drawable, ? extends Collider, ? extends Enum<?>>>
		implements Updatable {
	private static final Logger LOGGER = Logger.getLogger(GameCollisionSystem.class.getName());
	static {
		LOGGER.setLevel(Level.INFO);
	}

	private int updateIterations = 1;
	private Set<T> colliders = new HashSet<>();

	private Set<CollisionPair<T>> lastCollisions = new HashSet<>();

	private Set<CollisionHandler<T>> handlers = new HashSet<>();

	public GameCollisionSystem() {

	}

	public void update(long deltaMillis) {
		LOGGER.fine("update()");
		for (int i = 0; i < updateIterations; i++) {
			this.updateSubstep();
		}
	}

	private void updateSubstep() {
		LOGGER.finer("updateSubstep()");
		Set<CollisionPair<T>> currentCollisions = ConcurrentHashMap.newKeySet(colliders.size() * colliders.size());
		for (T c1 : this.colliders) {
			if (c1 == null) {
				System.out.println(c1 == null);
			}

			this.colliders.parallelStream().filter(c2->c1!=c2).filter(c2 -> c1.collidesWith(c2) || c2.collidesWith(c1))
					.forEach(c2 -> currentCollisions.add(new CollisionPair<T>(c1, c2)));
		}

		Set<CollisionPair<T>> toHandle = this.getCollisionsToHandle(currentCollisions);
		
		
		for(var handler:this.handlers) {
			for(var pair:toHandle) {
				handler.onCollision(pair);
			}
		}
	}
	
	
	private Set<CollisionPair<T>> getCollisionsToHandle(Set<CollisionPair<T>> allCollisions) {
		
		Set<CollisionPair<T>> toHandle = new HashSet<>();
		
		for (CollisionPair<T> pair : allCollisions) {
			if (lastCollisions.contains(pair)) {
				pair.setType(CollisionType.ONGOING);
				lastCollisions.remove(pair);
			} else {
				pair.setType(CollisionType.BEGIN);
			}
			toHandle.add(pair);

		}
		for (CollisionPair<T> pair : lastCollisions) {
			pair.setType(CollisionType.END);
			toHandle.add(pair);
		}

		lastCollisions = allCollisions;
		return toHandle;

	}

	public boolean add(T collider) {
		return this.colliders.add(collider);
	}

	public boolean remove(T collider) {
		return this.colliders.remove(collider);
	}

	public void addHandler(CollisionHandler<T> handler) {
		this.handlers.add(handler);
	}

	public boolean removeHandler(CollisionHandler<T> handler) {
		return this.handlers.remove(handler);
	}

	public int getUpdateIterations() {
		return updateIterations;
	}

	public void setUpdateIterations(int updateIterations) {
		this.updateIterations = updateIterations;
	}
	
	public void removeAll() {
		this.colliders.clear();
	}

}
