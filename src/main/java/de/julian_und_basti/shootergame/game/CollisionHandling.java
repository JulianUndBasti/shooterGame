package de.julian_und_basti.shootergame.game;

import java.util.ArrayList;
import java.util.List;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.collision.CollisionHandler;
import de.basti.game_framework.collision.CollisionPair;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.entities.enemies.Enemy;
import de.julian_und_basti.shootergame.entities.player.Player;
import de.julian_und_basti.shootergame.entities.player_projectiles.PlayerProjectile;
import javafx.util.Pair;
import de.julian_und_basti.shootergame.entities.EntityType;
import de.julian_und_basti.shootergame.Images;
import de.julian_und_basti.shootergame.entities.CustomEntity;

public class CollisionHandling {
	
	private static CollisionHandling instance;

	private List<Pair<EntityType, EntityType>> noCollisions = new ArrayList<>();

	private CollisionHandling() {
		noCollisions.add(new Pair<>(EntityType.WALL, EntityType.WALL));
		noCollisions.add(new Pair<>(EntityType.PLAYER_PROJECTILE, EntityType.PLAYER_PROJECTILE));
		noCollisions.add(new Pair<>(EntityType.PLAYER_PROJECTILE, EntityType.PLAYER));

	}
	
	
	//only works with valid input
	private boolean swapToTemplate(CollisionPair<CustomEntity<? extends Drawable, ? extends BoxCollider>> pair, EntityType type1, EntityType type2){
		if(pair.getCollider1().getType()==type1&&pair.getCollider2().getType()==type2) {
			return true;
		} 
		
		if(pair.getCollider2().getType()==type1&&pair.getCollider1().getType()==type2) {
			CustomEntity<? extends Drawable, ? extends BoxCollider> temp = pair.getCollider1();
			pair.setCollider1(pair.getCollider2());
			pair.setCollider2(temp);
			return true;
		}
		
		return false;
		
	}
	

	public final CollisionHandler<CustomEntity<? extends Drawable, ? extends BoxCollider>> handler = new CollisionHandler<>() {

		@Override
		public void onBegin(CollisionPair<CustomEntity<? extends Drawable, ? extends BoxCollider>> pair) {

			for (Pair<EntityType, EntityType> noCollision : noCollisions) {
				if (pair.getCollider1().getType() == noCollision.getKey() && pair.getCollider2().getType() == noCollision.getValue()) {
					return;
				}
				if (pair.getCollider2().getType() == noCollision.getKey() && pair.getCollider1().getType() == noCollision.getValue()) {
					return;
				}
			}

			
			if (swapToTemplate(pair, EntityType.ENEMY, EntityType.PLAYER_PROJECTILE)) {

				Enemy<?> enemy = (Enemy<?>) pair.getCollider1();
				PlayerProjectile projectile = (PlayerProjectile) pair.getCollider2();

				enemyProjectileCollision(enemy, projectile);

				return;
			}


			if (swapToTemplate(pair, EntityType.PLAYER_PROJECTILE, EntityType.WALL)) {
				PlayerProjectile projectile = (PlayerProjectile) pair.getCollider1();

				wallProjectileCollision(projectile);
				return;

			}
			
			if (swapToTemplate(pair, EntityType.ENEMY, EntityType.PLAYER)) {
				Enemy<?> enemy = (Enemy<?>) pair.getCollider1();
				Player player = (Player) pair.getCollider2();

				player.hitByEnemy(enemy);
				//dont return here

			}
			
			BoxCollider box1 = pair.getCollider1().getCollider();
			BoxCollider box2 = pair.getCollider2().getCollider();


			Vector2D displacement = this.getDisplacement(box1, box2);

			int cw1 = pair.getCollider1().getWeight();
			int cw2 = pair.getCollider2().getWeight();

			if (pair.getCollider1().getType() == EntityType.WALL) {
				cw1 = 1;
				cw2 = 0;
			}
			if (pair.getCollider2().getType() == EntityType.WALL) {
				cw1 = 0;
				cw2 = 1;
			}

			double sum = cw1 + cw2;
			Vector2D c1Displacement = displacement;
			Vector2D c2Displacement = displacement.clone();
			c1Displacement.scale(cw2 / sum);
			c2Displacement.scale(-(cw1 / sum));

			pair.getCollider1().translate(c1Displacement);
			pair.getCollider2().translate(c2Displacement);

		}

		private void enemyProjectileCollision(Enemy<?> enemy, PlayerProjectile projectile) {
			projectile.hit(enemy);
			enemy.hit(projectile);

		}

		private void wallProjectileCollision(PlayerProjectile projectile) {
			projectile.hitWall();

		}

		private Vector2D getDisplacement(BoxCollider box1, BoxCollider box2) {
			double rightLeftDiff = box1.getPosition().getX() + box1.getWidth()/2 - (box2.getPosition().getX()-box2.getWidth()/2);
			double leftRightDiff = (box1.getPosition().getX()-box1.getWidth()/2) - (box2.getPosition().getX() + box2.getWidth()/2);

			double bottomTopDiff = box1.getPosition().getY() + box1.getHeight()/2 - (box2.getPosition().getY()-box2.getHeight()/2);
			double topBottomDiff = (box1.getPosition().getY()-box1.getHeight()/2) - (box2.getPosition().getY() + box2.getHeight()/2);

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
		public void onOngoing(CollisionPair<CustomEntity<? extends Drawable, ? extends BoxCollider>> pair) {
			this.onBegin(pair);

		}

		@Override
		public void onEnd(CollisionPair<CustomEntity<? extends Drawable, ? extends BoxCollider>> pair) {

		}
	};
	
	
	public static CollisionHandling instance() {
		if(instance==null) {
			instance = new CollisionHandling();
		}
		return instance;
	}
}
