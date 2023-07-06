package de.julian_und_basti.shootergame;

import de.basti.game_framework.drawing.Circle;
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
	private Enemy enemy = new Enemy(new Vector2D(200,300), player);
	private Circle circle = new Circle(new Vector2D(30, 30), 20);
	
	@Override
	public void start(Stage stage) {

		Game.collisionSystem.setOnCollisionBegin((pair) -> {
			System.out.println(pair.getCollider1().getType().name() + " collided with " + pair.getCollider2().getType().name() + "!");
			if(pair.getCollider1().getType()==EntityType.ENEMY) {
				((Enemy) pair.getCollider1()).resetPosition();
			}
			if(pair.getCollider2().getType()==EntityType.ENEMY) {
				((Enemy) pair.getCollider2()).resetPosition();
			}
		});

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