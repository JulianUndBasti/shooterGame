package de.julian_und_basti.shootergame;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.collision.CollisionHandler;
import de.basti.game_framework.collision.CollisionPair;
import de.basti.game_framework.controls.TypeEntity;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.entities.enemies.Enemy;
import de.julian_und_basti.shootergame.entities.enemies.WalkerEnemy;
import de.julian_und_basti.shootergame.entities.player_projectiles.PlayerProjectile;
import de.julian_und_basti.shootergame.entities.player_projectiles.SimplePlayerProjectile;
import de.julian_und_basti.shootergame.entities.EntityType;
import de.julian_und_basti.shootergame.entities.UpdatableWeightTypeEntity;

public class CollisionHandling {
	public static CollisionHandler<UpdatableWeightTypeEntity<? extends Drawable, ? extends BoxCollider>> handler = new CollisionHandler<>() {

		@Override
		public void onBegin(CollisionPair<UpdatableWeightTypeEntity<? extends Drawable, ? extends BoxCollider>> pair) {

			var c1 = pair.getCollider1();
			var c2 = pair.getCollider2();
			BoxCollider box1 = c1.getCollider();
			BoxCollider box2 = c2.getCollider();

			if (c1.getType() == EntityType.PLAYER_PROJECTILE && c2.getType() == EntityType.ENEMY) {

				WalkerEnemy enemy = (WalkerEnemy) c2;
				PlayerProjectile projectile = (PlayerProjectile) c1;

				enemyProjectileCollision(enemy, projectile);

				return;
			}

			if (c1.getType() == EntityType.ENEMY && c2.getType() == EntityType.PLAYER_PROJECTILE) {

				WalkerEnemy enemy = (WalkerEnemy) c1;
				PlayerProjectile projectile = (PlayerProjectile) c2;

				enemyProjectileCollision(enemy, projectile);

				return;
			}

			if (c1.getType() == EntityType.PLAYER_PROJECTILE || c2.getType() == EntityType.PLAYER_PROJECTILE) {

				return;

			}

			Vector2D displacement = this.getDisplacement(box1, box2);

			int cw1 = c1.getWeight();
			int cw2 = c2.getWeight();
			
			double sum = cw1+cw2;
			Vector2D c1Displacement = displacement;
			Vector2D c2Displacement = displacement.clone();
			c1Displacement.scale(cw2/sum);
			c2Displacement.scale(-(cw1/sum));
			
			c1.translate(c1Displacement);
			c2.translate(c2Displacement);
			
			/*
			if (cw1 < cw2) {
				c1.translate(displacement);
			} else if (cw2 < cw1) {
				displacement.scale(-1);
				c2.translate(displacement);
			} else {
				displacement.scale(0.5);
				c1.translate(displacement);
				displacement.scale(-1);
				c2.translate(displacement);

			}
			*/

		}

		private void enemyProjectileCollision(Enemy<?> enemy, PlayerProjectile projectile) {
			projectile.hit(enemy);
			enemy.hit(projectile);

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
		public void onOngoing(CollisionPair<UpdatableWeightTypeEntity<? extends Drawable, ? extends BoxCollider>> pair) {
			this.onBegin(pair);
			
		}

		@Override
		public void onEnd(CollisionPair<UpdatableWeightTypeEntity<? extends Drawable, ? extends BoxCollider>> pair) {

		}
	};
}
