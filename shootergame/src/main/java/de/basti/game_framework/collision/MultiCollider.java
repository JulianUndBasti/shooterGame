package de.basti.game_framework.collision;

import de.basti.game_framework.math.Vector2D;

/**
 * A {@code Collider} which can have multiple other {@code Collider}s added to it. These are all considered in the collisions with this collider.
 * 
 * @see Collider
 * @see GameCollisionSystem
 */
public class MultiCollider implements Collider{
	
	private Collider[] colliders; 
	
	private MultiCollider(Collider... colliders) {
		super();
		this.colliders = colliders;
	}
	
	
	@Override
	public boolean collidesWith(Vector2D vector) {
		for(Collider collider:this.colliders) {
			if(collider.collidesWith(vector)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Vector2D[] getVectors() {
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
		for(Collider collider:this.colliders) {
			collider.translate(vector);
		
		}
	}
	
}
