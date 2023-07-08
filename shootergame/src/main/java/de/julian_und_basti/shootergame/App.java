package de.julian_und_basti.shootergame;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.collision.Collider;
import de.basti.game_framework.collision.CollisionHandler;
import de.basti.game_framework.collision.CollisionPair;
import de.basti.game_framework.controls.TypeEntity;
import de.basti.game_framework.controls.Updatable;
import de.basti.game_framework.controls.Updater;
import de.basti.game_framework.drawing.Circle;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.drawing.DrawingLayer;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.entities.Enemy;
import de.julian_und_basti.shootergame.entities.EntityType;
import de.julian_und_basti.shootergame.entities.Player;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

	

	private Player player = new Player(new Vector2D(Game.width / 2, Game.height / 2));
	
	private Circle circle = new Circle(new Vector2D(30, 30), 20);
	
	private Updater enemyUpdater = new Updater();
	
	
	
	@Override
	public void start(Stage stage) {
		
		Game.collisionSystem.setUpdateIterations(4);
		Game.collisionSystem.setOnCollisionBegin(CollisionHandling.handler);
		Game.collisionSystem.setOnCollisionOngoing(CollisionHandling.handler);

		Game.drawing.add(DrawingLayer.FOREGROUND, player);
		Game.collisionSystem.add(player);
		
		for(int i = 0;i<100;i++) {
			double x = Math.random()*800;
			double y = Math.random()*600;
			
			Enemy enemy = new Enemy(new Vector2D(x,y), player);
			Game.drawing.add(DrawingLayer.FOREGROUND, enemy);
			Game.collisionSystem.add(enemy);
			enemyUpdater.getList().add(enemy);
		}
		

		

		circle.setFillColor(Color.GRAY);
		Game.drawing.add(DrawingLayer.BACKGROUND, circle);


		Game.loop.addUpdatableAfter(player);
		Game.loop.addUpdatableAfter(enemyUpdater);
		Game.loop.addUpdatableAfter(Game.collisionSystem);
		Game.loop.addUpdatableAfter(Game.drawing);
		Game.loop.addUpdatableAfter(inputConsumer);
		
		Game.loop.start();

		stage.setScene(Game.scene);
		stage.show();
		
	}
	
	Updatable inputConsumer = new Updatable() {
		
		@Override
		public void update(long deltaMillis) {
			
			Game.inputData.pressesAndReleasesConsumed();
			
		}
	};

	public static void main(String[] args) {
		launch();
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	
	
}