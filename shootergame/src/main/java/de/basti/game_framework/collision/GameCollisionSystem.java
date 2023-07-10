package de.basti.game_framework.collision;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.basti.game_framework.controls.Updatable;

/**
 * Class which checks collisions between multiple {@code Collider}s, which can be added to it.
 * On collision begin, ongoing collision or collision end it sends a {@code CollisionPair} to the respective {@code CollisionHandler}.
 * The collisions are only checked when the update(double deltaMillis) is called. 
 * @param <T> type of {@code Collider} to use
 */
public class GameCollisionSystem<T extends Collider> implements Updatable {
	
	private int updateIterations = 1; 
	private Set<T> colliders = new HashSet<>();

	private Set<CollisionPair<T>> lastCollisions = new HashSet<>();
	
	private Set<CollisionHandler<T>> handlers = new HashSet<>();
	

	public GameCollisionSystem() {

	}
	
	public void update(long deltaMillis) {
		for(int i = 0;i<updateIterations;i++) {
			this.updateOnce();
		}
	}
	
	private void updateOnce() {
		List<T> colliderList = new ArrayList<>(colliders);
		Set<CollisionPair<T>> allCollisions = new HashSet<>();
		for(int i = 0;i<colliderList.size();i++) {
			T c1 = colliderList.get(i);
			for(int j = i+1;j<colliderList.size();j++) {
				T c2 = colliderList.get(j);
				if(c1.collidesWith(c2)||c2.collidesWith(c1)) {
					allCollisions.add(new CollisionPair<T>(c1, c2));
				}	
			}
		}
		this.handleAllCollisions(allCollisions);
	}
	
	private void handleAllCollisions(Set<CollisionPair<T>> allCollisions) {
		for(CollisionPair<T> pair:allCollisions) {
			if(lastCollisions.contains(pair)) {
				ongoingHandler.handle(pair);
				lastCollisions.remove(pair);
			}else {
				beginHandler.handle(pair);
			}
			
		}
		for(CollisionPair<T> pair:lastCollisions) {
			endHandler.handle(pair);
		}
		
		lastCollisions = allCollisions;
	}
	
	
	public boolean add(T collider) {
		return this.colliders.add(collider);
	}
	
	public boolean remove(T collider) {
		return this.colliders.remove(collider);
	}
	

	private void addHandler(CollisionHandler<T> handler) {
		this.handlers.add(handler);
	}
	
	private boolean removeHandler(CollisionHandler<T> handler) {
		return this.handlers.remove(handler);
	}

	public int getUpdateIterations() {
		return updateIterations;
	}

	public void setUpdateIterations(int updateIterations) {
		this.updateIterations = updateIterations;
	}
	
	
}
