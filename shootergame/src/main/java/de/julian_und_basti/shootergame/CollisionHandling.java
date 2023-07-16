package de.julian_und_basti.shootergame;

import java.util.ArrayList;
import java.util.List;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.collision.CollisionHandler;
import de.basti.game_framework.collision.CollisionPair;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.entities.enemies.Enemy;
import de.julian_und_basti.shootergame.entities.player_projectiles.PlayerProjectile;
import de.julian_und_basti.shootergame.entities.walls.Wall;
import javafx.util.Pair;
import de.julian_und_basti.shootergame.entities.EntityType;
import de.julian_und_basti.shootergame.entities.UpdatableWeightTypeEntity;

public class CollisionHandling {
	
	private static List<Pair<EntityType,EntityType>> noCollisions = new ArrayList<>();
	
	static {
		noCollisions.add(new Pair<>(EntityType.WALL, EntityType.WALL));
		noCollisions.add(new Pair<>(EntityType.PLAYER_PROJECTILE, EntityType.PLAYER_PROJECTILE));
		noCollisions.add(new Pair<>(EntityType.PLAYER_PROJECTILE, EntityType.PLAYER));
		
		
	}
	
	public static CollisionHandler<UpdatableWeightTypeEntity<? extends Drawable, ? extends BoxCollider>> handler = new CollisionHandler<>() {

		@Override
		public void onBegin(CollisionPair<UpdatableWeightTypeEntity<? extends Drawable, ? extends BoxCollider>> pair) {

			var c1 = pair.getCollider1();
			var c2 = pair.getCollider2();

			for(Pair<EntityType, EntityType> noCollision : noCollisions) {
				if(c1.getType()==noCollision.getKey()&&c2.getType()==noCollision.getValue()) {
					return;
				}
				if(c2.getType()==noCollision.getKey()&&c1.getType()==noCollision.getValue()) {
					return;
				}
			}

			BoxCollider box1 = c1.getCollider();
			BoxCollider box2 = c2.getCollider();

			if (c1.getType() == EntityType.PLAYER_PROJECTILE && c2.getType() == EntityType.ENEMY) {

				Enemy<?> enemy = (Enemy<?>) c2;
				PlayerProjectile projectile = (PlayerProjectile) c1;

				enemyProjectileCollision(enemy, projectile);

				return;
			}

			if (c1.getType() == EntityType.ENEMY && c2.getType() == EntityType.PLAYER_PROJECTILE) {

				Enemy<?> enemy = (Enemy<?>) c1;
				PlayerProjectile projectile = (PlayerProjectile) c2;

				enemyProjectileCollision(enemy, projectile);

				return;
			}
			
			if (c1.getType() == EntityType.PLAYER_PROJECTILE && c2.getType() == EntityType.WALL) {
				PlayerProjectile projectile = (PlayerProjectile) c1;

				wallProjectileCollision(projectile);

				return;
			
			}
			
			if (c2.getType() == EntityType.PLAYER_PROJECTILE && c1.getType() == EntityType.WALL) {
				PlayerProjectile projectile = (PlayerProjectile) c2;

				wallProjectileCollision(projectile);

				
			}
			
			
			
			

			Vector2D displacement = this.getDisplacement(box1, box2);

			int cw1 = c1.getWeight();
			int cw2 = c2.getWeight();

			if (c1.getType() == EntityType.WALL) {
				cw1 = 1;
				cw2 = 0;
			}
			if (c2.getType() == EntityType.WALL) {
				cw1 = 0;
				cw2 = 1;
			}

			double sum = cw1 + cw2;
			Vector2D c1Displacement = displacement;
			Vector2D c2Displacement = displacement.clone();
			c1Displacement.scale(cw2 / sum);
			c2Displacement.scale(-(cw1 / sum));

			c1.translate(c1Displacement);
			c2.translate(c2Displacement);

		}

		private void enemyProjectileCollision(Enemy<?> enemy, PlayerProjectile projectile) {
			projectile.hit(enemy);
			enemy.hit(projectile);

		}
		
		
		private void wallProjectileCollision(PlayerProjectile projectile) {
			projectile.hitWall();

		}
		
		

		private Vector2D getDisplacement(BoxCollider box1, BoxCollider box2) {
			double rightLeftDiff = box1.getPosition().getX() + box1.getWidth() - box2.getPosition().getX();
			double leftRightDiff = box1.getPosition().getX() - (box2.getPosition().getX() + box2.getWidth());

			double bottomTopDiff = box1.getPosition().getY() + box1.getHeight() - box2.getPosition().getY();
			double topBottomDiff = box1.getPosition().getY() - (box2.getPosition().getY() + box2.getHeight());

			double absRightLeftDiff = Math.abs(rightLeftDiff);
			double absLeftRightDiff = Math.abs(leftRightDiff);

			double absBottomTopDiff = Math.abs(bottomTopDiff);
			double absTopBottomDiff = Math.abs(topBottomDiff);

			double lowest = Math.min(absBottomTopDiff, absTopBottomDiff);
			lowest = Math.min(lowest, absLeftRightDiff);
			lowest = Math.min(lowest, absRightLeftDiff);

			Vector2D displacement = new Vector2D();

			if (lowest == absRightLeftDiff) {
				displacement.setX(rightLeftDiff);
			} else if (lowest == absLeftRightDiff) {
				displacement.setX(leftRightDiff);
			} else if (lowest == absTopBottomDiff) {
				displacement.setY(topBottomDiff);
			} else if (lowest == absBottomTopDiff) {
				displacement.setY(bottomTopDiff);
			} else {
				displacement.setX(rightLeftDiff);
			}

			displacement.scale(-1);

			return displacement;
		}

		@Override
		public void onOngoing(
				CollisionPair<UpdatableWeightTypeEntity<? extends Drawable, ? extends BoxCollider>> pair) {
			this.onBegin(pair);

		}

		@Override
		public void onEnd(CollisionPair<UpdatableWeightTypeEntity<? extends Drawable, ? extends BoxCollider>> pair) {

		}
	};
}
