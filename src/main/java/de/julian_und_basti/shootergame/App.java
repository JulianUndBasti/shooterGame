package de.julian_und_basti.shootergame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.controls.Engine;
import de.basti.game_framework.controls.Logging;
import de.basti.game_framework.controls.Updatable;
import de.basti.game_framework.controls.Updater;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.drawing.Sprite;
import de.basti.game_framework.drawing.Text;
import de.basti.game_framework.drawing.gui.ButtonComponent;
import de.basti.game_framework.drawing.gui.GUI;
import de.basti.game_framework.drawing.gui.MouseListener;
import de.basti.game_framework.drawing.gui.TextComponent;
import de.basti.game_framework.input.KeyInputListenerData;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.entities.CustomEntity;
import de.julian_und_basti.shootergame.entities.enemies.Enemy;
import de.julian_und_basti.shootergame.entities.enemies.WalkerEnemy;
import de.julian_und_basti.shootergame.entities.player.Player;
import de.julian_und_basti.shootergame.entities.player_projectiles.RocketPlayerProjectile;
import de.julian_und_basti.shootergame.game.CollisionHandling;
import de.julian_und_basti.shootergame.game.Game;
import de.julian_und_basti.shootergame.levels.Level;
import de.julian_und_basti.shootergame.levels.SimpleLevel;
import de.julian_und_basti.shootergame.weapons.RocketLauncher;
import javafx.application.Application;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {
	
	{
		Logging.init();
	}

	private final int width = (int) Screen.getPrimary().getBounds().getMaxX();
	private final int height = (int) Screen.getPrimary().getBounds().getMaxY();

	private Canvas canvas = new Canvas(width, height);
	private Group root = new Group(canvas);
	private Scene scene = new Scene(root);
	private GraphicsContext gc = canvas.getGraphicsContext2D();

	private Engine<CustomEntity<? extends Drawable, ? extends BoxCollider>> engine = new Engine<>(scene, gc);

	private GUI gui = new GUI(new Vector2D(0, 0), engine.getInputData());

	private Game game = new Game(engine);
	private ButtonComponent startButton = new ButtonComponent(new Vector2D(width / 2 - 35, height / 2 - 20), 70, 40);
	
	
	
	public App() {
		
		game.setOnGameEnd(() -> {
			this.showGui();
		});

		startButton.setText("Start");
		startButton.setFont(Font.font("Calibri", FontWeight.BOLD, FontPosture.REGULAR, 36));
		startButton.setBackgroundColor(Color.BLUE);
		startButton.translate(-startButton.getWidth() / 2, -startButton.getHeight() / 2);
		startButton.addActionListener(() -> {
			showGame();
		});
		gui.add(startButton);
		
		engine.addSizeChangeListener((width, height) -> {
			startButton.setPosition(new Vector2D(width / 2 - startButton.getWidth()/2, height / 2 - startButton.getHeight()/2));
		});

	}

	private void showGame() {
		engine.removeDrawable(gui);
		engine.removeUpdatable(gui);

		game.addToEngine();

	}

	private void showGui() {
		engine.removeCollisionHandler(CollisionHandling.instance().handler);
		engine.removeAll();

		engine.addDrawableAbsolute(gui);
		engine.addUpdatable(gui);

	}
	
	private Runnable waitForLoadingThenStart = () -> {
		System.out.println("Loading sounds!");
		Sounds sounds = Sounds.instance();
		boolean soundsHaveLoaded = false;
		while(!soundsHaveLoaded) {
			soundsHaveLoaded = sounds.haveAllLoaded();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {

			}
		}
		System.out.println("Sounds loaded!");
		engine.start();
		
	};

	@Override
	public void start(Stage stage) {
		stage.resizableProperty().set(true);
		
		stage.setFullScreenExitHint("");
		stage.setFullScreen(true);
		Sounds.instance();	
		Logging.LOGGER.getClass();

		engine.getCollisionSystem().setUpdateIterations(4);

		this.showGui();

		Thread waiterThread = new Thread(waitForLoadingThenStart);
		waiterThread.start();
		
		System.out.println("Showing stage!");
		stage.setScene(this.scene);
		stage.show();

	}

	public static void main(String[] args) {
		launch();
	}

}