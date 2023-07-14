package de.julian_und_basti.shootergame;

import de.basti.game_framework.controls.Updatable;
import de.basti.game_framework.drawing.DrawingLayer;
import de.basti.game_framework.drawing.Sprite;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.entities.enemies.Enemy;
import de.julian_und_basti.shootergame.entities.enemies.HeavyEnemy;
import de.julian_und_basti.shootergame.entities.enemies.SplitterEnemy;
import de.julian_und_basti.shootergame.entities.enemies.WalkerEnemy;
import de.julian_und_basti.shootergame.entities.player.Player;
import de.julian_und_basti.shootergame.entities.player_projectiles.RocketPlayerProjectile;
import de.julian_und_basti.shootergame.entities.player_projectiles.SimplePlayerProjectile;
import de.julian_und_basti.shootergame.weapons.MachineGun;
import de.julian_und_basti.shootergame.weapons.Pistol;
import de.julian_und_basti.shootergame.weapons.RocketLauncher;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

	

	private Player player = new Player(new Vector2D(Game.width/2, Game.height/2), new Pistol(SimplePlayerProjectile::new));
	
	private Sprite backgroundSprite = new Sprite(new Vector2D(), Images.background);
	
	private Background background = new Background(backgroundSprite, player);
	
	@Override
	public void start(Stage stage) {
		
		Game.collisionSystem.setUpdateIterations(4);
		Game.collisionSystem.addHandler(CollisionHandling.handler);
		
		Game.addEntity(DrawingLayer.FORE_MIDDLE, player);
		
		
		for(int i = 0;i<1;i++) {
			Enemy<?> enemy = new SplitterEnemy(new Vector2D(Math.random()*Game.width,Math.random()*Game.height), player);
			Game.addEntity(DrawingLayer.MIDDLE, enemy);
		}
		
		Game.drawing.add(DrawingLayer.BACKGROUND, background);

		Game.loop.start();

		stage.setScene(Game.scene);
		stage.show();
		
	}
	
	

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