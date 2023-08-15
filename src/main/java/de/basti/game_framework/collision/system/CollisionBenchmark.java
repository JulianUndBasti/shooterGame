package de.basti.game_framework.collision.system;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.collision.Collider;
import de.basti.game_framework.collision.CollisionHandler;
import de.basti.game_framework.collision.CollisionPair;
import de.basti.game_framework.math.Vector2D;

public class CollisionBenchmark {

	private static long avgMillisPerUpdate;
	private static long countedBeginCollisions = 0;
	private static long countedOngoingCollisions = 0;
	private static long countedEndCollisions = 0;

	private static CollisionHandler<Collider> collisionHandler = new CollisionHandler<Collider>() {

		@Override
		public void onOngoing(CollisionPair<Collider> pair) {
			countedOngoingCollisions++;

		}

		@Override
		public void onEnd(CollisionPair<Collider> pair) {
			countedEndCollisions++;

		}

		@Override
		public void onBegin(CollisionPair<Collider> pair) {
			countedBeginCollisions++;

		}
	};

	public static void main(String[] args) {
		CollisionSystem<Collider> naive = new NaiveCollisionSystem<Collider>();
		CollisionSystem<Collider> hash = new SpatialHashGridCollisionSystem<Collider>(150,20.0);
		CollisionSystem<Collider> threadedHash = new SpatialHashGridCollisionSystem<Collider>(150,20.0);
		
		benchMarkSystem(naive);
		printStats();
		benchMarkSystem(hash);
		printStats();
		benchMarkSystem(threadedHash);
		printStats();
		
		
		
		
		
		
		
	}
	
	private static void printStats() {
		System.out.println("Average Milliseconds per Update: "+ avgMillisPerUpdate);
		System.out.println("counted begin collisions: "+ countedBeginCollisions);
		System.out.println("counted ongoing collisions: "+ countedOngoingCollisions);
		System.out.println("counted end collisions: "+ countedEndCollisions);
		
		
	}

	private static int colliderCount = 600;
	private static int updateCount = 20;

	private static void benchMarkSystem(CollisionSystem<Collider> collSystem) {
		avgMillisPerUpdate = 0;
		countedBeginCollisions = 0;
		countedOngoingCollisions = 0;
		countedEndCollisions = 0;
		collSystem.addHandler(collisionHandler);
		for (int x = 0; x < Math.sqrt(colliderCount); x++) {
			for (int y = 0; y < Math.sqrt(colliderCount); y++) {
				BoxCollider collider = new BoxCollider(new Vector2D(x*9, y*9), 10, 10);
				collSystem.add(collider);

			}
		}

		long time = System.currentTimeMillis();
		
		for(int i = 1;i<=updateCount;i++){
			collSystem.update(0);
			if(i%100==0) {
				System.out.println(i);
			}
			
		}
		
		time = System.currentTimeMillis() - time;
		avgMillisPerUpdate = time/updateCount;

	}

}
