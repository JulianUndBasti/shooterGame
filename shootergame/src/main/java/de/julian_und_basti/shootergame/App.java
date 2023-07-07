package de.julian_und_basti.shootergame;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.collision.Collider;
import de.basti.game_framework.collision.CollisionHandler;
import de.basti.game_framework.collision.CollisionPair;
import de.basti.game_framework.controls.TypeEntity;
import de.basti.game_framework.drawing.Circle;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.drawing.DrawingLayer;
import de.basti.game_framework.math.Vector2D;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

	// game
	private Player player = new Player(new Vector2D(Game.width / 2, Game.height / 2));
	private Enemy enemy = new Enemy(new Vector2D(200, 300), player);
	private Circle circle = new Circle(new Vector2D(30, 30), 20);

	CollisionHandler<TypeEntity<? extends Drawable, ? extends BoxCollider, EntityType>> handler = new CollisionHandler<TypeEntity<? extends Drawable, ? extends BoxCollider, EntityType>>() {

		@Override
		public void handle(
				CollisionPair<TypeEntity<? extends Drawable, ? extends BoxCollider, EntityType>> pair) {
			var c1 = pair.getCollider1();
			var c2 = pair.getCollider2();
			BoxCollider box1 = c1.getCollider();
			BoxCollider box2 = c2.getCollider();

			Vector2D displacement = this.getDisplacement(box1, box2);
			
			int cp1 =c1.getType().collisionPriority;
			int cp2 =c2.getType().collisionPriority;
			
			
			if(cp1<cp2) {
				c1.translate(displacement);
			}else if(cp2<cp1) {
				displacement.scale(-1);
				c2.translate(displacement);
			}else {
				displacement.scale(0.5);
				c1.translate(displacement);
				displacement.scale(-1);
				c2.translate(displacement);
				
			}

		}

		private Vector2D getDisplacement(BoxCollider box1, BoxCollider box2) {
			double rightLeftDiff = box1.getPosition().getX() + box1.getWidth() - box2.getPosition().getX();
			double leftRightDiff = box1.getPosition().getX()
					- (box2.getPosition().getX() + box2.getWidth());

			double bottomTopDiff = box1.getPosition().getY() + box1.getHeight() - box2.getPosition().getY();
			double topBottomDiff = box1.getPosition().getY()
					- (box2.getPosition().getY() + box2.getHeight());

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
				throw new RuntimeException("Error in collision!");
			}
			
			displacement.scale(-1);

			return displacement;
		}
	};

	@Override
	public void start(Stage stage) {

		Game.collisionSystem.setOnCollisionBegin(handler);
		Game.collisionSystem.setOnCollisionOngoing(handler);

		Game.drawing.add(DrawingLayer.FOREGROUND, player.getDrawable());
		Game.collisionSystem.add(player);

		Game.drawing.add(DrawingLayer.FOREGROUND, enemy.getDrawable());
		Game.collisionSystem.add(enemy);

		circle.setFillColor(Color.GRAY);
		Game.drawing.add(DrawingLayer.BACKGROUND, circle);

		Game.loop.addUpdatableAfter(enemy);
		Game.loop.addUpdatableAfter(player);
		Game.loop.addUpdatableAfter(Game.collisionSystem);
		Game.loop.addUpdatableAfter(Game.drawing);

		Game.loop.start();

		stage.setScene(Game.scene);
		stage.show();

	}

	public static void main(String[] args) {
		launch();
	}

}