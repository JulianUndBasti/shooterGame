package de.julian_und_basti.shootergame;

import java.util.Random;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.controls.Game;
import de.basti.game_framework.controls.Updatable;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.drawing.DrawingLayer;
import de.basti.game_framework.drawing.Sprite;
import de.basti.game_framework.drawing.Text;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.entities.CustomEntity;
import de.julian_und_basti.shootergame.entities.enemies.Enemy;
import de.julian_und_basti.shootergame.entities.enemies.WalkerEnemy;
import de.julian_und_basti.shootergame.entities.player.Player;
import de.julian_und_basti.shootergame.entities.player_projectiles.RocketPlayerProjectile;
import de.julian_und_basti.shootergame.levels.Level;
import de.julian_und_basti.shootergame.levels.SimpleLevel;
import de.julian_und_basti.shootergame.weapons.RocketLauncher;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

	private final int width = 800;
	private final int height = 600;

	private Canvas canvas = new Canvas(width, height);
	private Group root = new Group(canvas);
	private Scene scene = new Scene(root);
	private GraphicsContext gc = canvas.getGraphicsContext2D();

	private Game<CustomEntity<? extends Drawable, ? extends BoxCollider>> game = new Game<>(scene, gc);

	private Player player = new Player(new Vector2D(width / 2, height / 2),
			new RocketLauncher(RocketPlayerProjectile::new, game), game);
	
	private Text healthText = new Text(new Vector2D(10,10),""+player.getHealth());

	private Sprite backgroundSprite = new Sprite(new Vector2D(), Images.background);

	private Background background = new Background(backgroundSprite, player);

	private int enemyDelay = 1000;
	private int minEnemyDelay = 300;

	private Updatable enemySpawner = new Updatable() {

		Random random = new Random();
		
		private int timeSinceLastSpawn = enemyDelay;
		
		
		@Override
		public void update(long deltaMillis) {
			timeSinceLastSpawn+=deltaMillis;
			if(timeSinceLastSpawn<enemyDelay)return;
			
			timeSinceLastSpawn = 0;
			if(enemyDelay>minEnemyDelay) {
				enemyDelay = enemyDelay-10;
			}else {
				enemyDelay = minEnemyDelay;
			}
			
			
			
			Vector2D enemyPos = new Vector2D(random.nextDouble() * 600 + 200, random.nextDouble() * 600 + 200);

			if (random.nextBoolean()) {
				enemyPos.setX(enemyPos.getX() * -1);
			}
			if (random.nextBoolean()) {
				enemyPos.setY(enemyPos.getY() * -1);
			}

			enemyPos.translate(player.getPosition());

			Enemy<?> enemy = new WalkerEnemy(enemyPos, player, game);
			game.addEntity(DrawingLayer.MIDDLE, enemy);

		}
	};
	
	private Updatable textUpdate = new Updatable() {
		
		@Override
		public void update(long deltaMillis) {
			healthText.setText(player.getHealth()+"");
			
		}
	}; 

	@Override
	public void start(Stage stage) {
		Sounds.initMediaPlayers();

		game.getCollisionSystem().setUpdateIterations(4);
		game.addCollisionHandler(CollisionHandling.handler);

		game.addEntity(DrawingLayer.FORE_MIDDLE, player);

		game.addDrawable(DrawingLayer.BACKGROUND, background);
		
		healthText.setFont(Font.font("Calibri", FontWeight.BOLD, FontPosture.REGULAR, 36));
		game.addDrawable(DrawingLayer.ABSOLUTE, healthText);
		
		game.addUpdatable(enemySpawner);
		game.addUpdatable(textUpdate);
		

		game.stickCameraTo(player);

		game.start();

		stage.setScene(this.scene);
		stage.show();

	}

	public static void main(String[] args) {
		launch();
	}

}