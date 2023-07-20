package de.julian_und_basti.shootergame;

import java.util.ArrayList;
import java.util.List;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.collision.Collider;
import de.basti.game_framework.collision.GameCollisionSystem;
import de.basti.game_framework.controls.Entity;
import de.basti.game_framework.controls.GameLoop;
import de.basti.game_framework.controls.Updatable;
import de.basti.game_framework.controls.Updater;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.drawing.DrawingLayer;
import de.basti.game_framework.drawing.GameDrawing;
import de.basti.game_framework.input.InputListenerData;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.entities.WeightEntity;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class Game {
	// niemand kann diese Klasse instanzieren (wegen private)
	private Game() {
	}

	private static Updater entityUpdater = new Updater();

	private static List<Runnable> endOfUpdateTasks = new ArrayList<>();

	private static Updatable endOfUpdate = new Updatable() {

		@Override
		public void update(long deltaMillis) {
			for (Runnable runnable : endOfUpdateTasks) {
				runnable.run();
			}
			endOfUpdateTasks = new ArrayList<>();

			inputData.pressesAndReleasesConsumed();

		}
	};

	public static int width = 800;
	public static int height = 600;

	// javafx
	public static Canvas canvas = new Canvas(width, height);
	public static Group root = new Group(canvas);
	public static Scene scene = new Scene(root);

	public static GraphicsContext gc = canvas.getGraphicsContext2D();

	// game-framework
	public static GameDrawing drawing = new GameDrawing(gc);
	public static InputListenerData inputData = new InputListenerData(scene);
	public static GameCollisionSystem<WeightEntity<? extends Drawable, ? extends BoxCollider>> collisionSystem = new GameCollisionSystem<>();
	public static GameLoop loop = new GameLoop();

	private static WeightEntity<? extends Drawable, ? extends BoxCollider> camera = new WeightEntity<Drawable, BoxCollider>(
			new Vector2D(0, 0), new BoxCollider(new Vector2D(0, 0), 0, 0), null, null) {

		@Override
		public void update(long deltaMillis) {
			

		}

	};

	private static Updatable cameraUpdate = new Updatable() {

		@Override
		public void update(long deltaMillis) {
			// transform the camera so the player stays in the middle
			Vector2D transform = camera.getPosition().clone();
			transform.scale(-1);
			transform.translate((Game.width - camera.getCollider().getWidth()) / 2,
					(Game.height - camera.getCollider().getHeight()) / 2);
			Game.drawing.setTransform(transform);
			Game.inputData.getMouseData().setTransform(transform);
			System.out.println(deltaMillis);

		}
	};

	static {
		loop.getUpdater().getList().add(entityUpdater);
		loop.getUpdater().getList().add(collisionSystem);
		loop.getUpdater().getList().add(cameraUpdate);
		loop.getUpdater().getList().add(drawing);
		loop.getUpdater().getList().add(endOfUpdate);

	}

	public static void addEntity(DrawingLayer l,
			WeightEntity<? extends Drawable, ? extends BoxCollider> e) {
		drawing.add(l, e);
		collisionSystem.add(e);
		entityUpdater.getList().add(e);

	}

	public static void removeEntity(WeightEntity<? extends Drawable, ? extends BoxCollider> e) {
		drawing.remove(e);
		collisionSystem.remove(e);
		entityUpdater.getList().remove(e);

	}

	public static void stickCameraTo(WeightEntity<? extends Drawable, ? extends BoxCollider> e) {
		camera = e;
	}

	public static void addTaskForEndOfUpdate(Runnable runnable) {
		endOfUpdateTasks.add(runnable);
	}

}
