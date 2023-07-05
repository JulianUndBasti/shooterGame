package de.julian_und_basti.shootergame;

import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.drawing.DrawingLayer;

import de.basti.game_framework.math.Vector2D;
import javafx.application.Application;

import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

	// game
	private Player player = new Player(new Vector2D(Game.width / 2, Game.height / 2));
	private Player player2 = new Player(new Vector2D(200,300)) {
		public void update(long deltaMillis) {};//doesnt update
	};
	
	@Override
	public void start(Stage stage) {

		Game.collisionSystem.setOnCollisionBegin((pair) -> {
			System.out.println(pair.getCollider1().getType().name() + " collided with " + pair.getCollider2().getType().name() + "!");
		});

		Game.drawing.add(DrawingLayer.FOREGROUND, player.getDrawable());
		Game.collisionSystem.add(player.getCollider());
		
		Game.drawing.add(DrawingLayer.FOREGROUND, player2.getDrawable());
		Game.collisionSystem.add(player2.getCollider());
		

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