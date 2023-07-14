package de.basti.game_framework.collision;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

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
		long time = System.nanoTime();
		for(int i = 0;i<updateIterations;i++) {
			this.updateOnce();
		}
		time = System.nanoTime()-time;
		System.out.println(time);
	}
	
	private void updateOnce() {
		
		Set<CollisionPair<T>> allCollisions = ConcurrentHashMap.newKeySet(colliders.size()*colliders.size());
		for(T c1:this.colliders) {
			if(c1==null) {
				System.out.println(c1==null);
			}
			
			this.colliders.parallelStream().filter(c2 -> ((c1.collidesWith(c2)||c2.collidesWith(c1))&&c1!=c2)).forEach(c2 -> allCollisions.add(new CollisionPair<T>(c1, c2)));
		}
		
		
		this.handleAllCollisions(allCollisions);
	}
	
	private void handleAllCollisions(Set<CollisionPair<T>> allCollisions) {
		for(CollisionHandler<T> handler:this.handlers) {
			for(CollisionPair<T> pair:allCollisions) {
				if(lastCollisions.contains(pair)) {
					handler.onOngoing(pair);
					lastCollisions.remove(pair);
				}else {
					handler.onBegin(pair);
				}
				
			}
			for(CollisionPair<T> pair:lastCollisions) {
				handler.onEnd(pair);
			}
			
			
		}
		lastCollisions = allCollisions;
		
		
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
	
	
}
