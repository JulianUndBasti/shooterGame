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
	private Set<T> colliders = new HashSet<>();

	private Set<CollisionPair<T>> lastCollisions = new HashSet<>();
	
	private CollisionHandler<T> beginHandler = (pair)->{};//empty CollisionHandler
	private CollisionHandler<T> ongoingHandler = (pair)->{};//empty CollisionHandler
	private CollisionHandler<T> endHandler = (pair)->{};//empty CollisionHandler
	
	
	

	public GameCollisionSystem() {

	}
	
	public void update(long deltaMillis) {
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
	
	
	public void setOnCollisionBegin(CollisionHandler<T> handler) {
		
		this.beginHandler  = handler;
	}
	
	public void setOnCollisionEnd(CollisionHandler<T> handler) {
		
		this.endHandler  = handler;
	}
	
	public void setOnCollisionOngoing(CollisionHandler<T> handler) {
		
		this.ongoingHandler  = handler;
	}
}
