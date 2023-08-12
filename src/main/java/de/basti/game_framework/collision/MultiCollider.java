package de.basti.game_framework.collision;

import java.util.logging.Level;
import java.util.logging.Logger;

import de.basti.game_framework.collision.system.NaiveCollisionSystem;
import de.basti.game_framework.math.Vector2D;

/**
 * A {@code Collider} which can have multiple other {@code Collider}s added to it. These are all considered in the collisions with this collider.
 * 
 * @see Collider
 * @see NaiveCollisionSystem
 */
public class MultiCollider implements Collider{
	private static final Logger LOGGER = Logger.getLogger(MultiCollider.class.getName());
	static {
		LOGGER.setLevel(Level.INFO);
	}
	
	private Collider[] colliders; 
	
	private MultiCollider(Collider... colliders) {
		super();
		LOGGER.finer("Constructing " + this);
		this.colliders = colliders;
	}
	
	
	@Override
	public boolean collidesWith(Vector2D vector) {
		for(Collider collider:this.colliders) {
			if(collider.collidesWith(vector)) {
				LOGGER.finest("collidesWith() of " + this + " with " + vector + " is true");
				return true;
			}
		}
		LOGGER.finest("collidesWith() of " + this + " with " + vector + " is false");
		return false;
	}

	@Override
	public Vector2D[] getVectors() {
		LOGGER.finest("getVectors() of " + this);
		Vector2D[] allVectors = this.colliders[0].getVectors();
		for(int i = 1;i<colliders.length;i++) {
			Vector2D[] newVectors = this.colliders[i].getVectors();
			
			Vector2D[] allVectors2 = new Vector2D[allVectors.length + newVectors.length];
			System.arraycopy(allVectors, 0, allVectors2, 0, allVectors.length);
			System.arraycopy(newVectors, 0, allVectors2, allVectors.length, newVectors.length);
			allVectors = allVectors2;
		}
		return allVectors;
	}


	@Override
	public void translate(Vector2D vector) {
		LOGGER.finest("translate() of " + this + " by " + vector);
		for(Collider collider:this.colliders) {
			collider.translate(vector);
		
		}
	}
	
}
