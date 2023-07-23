package de.julian_und_basti.shootergame;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.controls.Game;
import de.basti.game_framework.controls.Updatable;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.drawing.DrawingLayer;
import de.basti.game_framework.drawing.Sprite;
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

	private Sprite backgroundSprite = new Sprite(new Vector2D(), Images.background);

	private Background background = new Background(backgroundSprite, player);

	private Level level = new SimpleLevel(player, game);

	@Override
	public void start(Stage stage) {
		Sounds.initPlayers();

		game.getCollisionSystem().setUpdateIterations(4);
		game.addCollisionHandler(CollisionHandling.handler);

		game.addEntity(DrawingLayer.FORE_MIDDLE, player);
		
		for(int i = 0;i<20;i++) {
			double x = 2900+Math.random()*100;
			double y = 2900+Math.random()*100;
			Enemy<?> enemy = new WalkerEnemy(new Vector2D(x,y), player, game);
			game.addEntity(DrawingLayer.MIDDLE, enemy);
		}

		level.buildLevel();

		game.addDrawable(DrawingLayer.BACKGROUND, background);

		game.stickCameraTo(player);

		game.start();

		stage.setScene(this.scene);
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