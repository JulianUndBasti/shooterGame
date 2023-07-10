package de.julian_und_basti.shootergame;


import java.util.ArrayList;
import java.util.List;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.collision.GameCollisionSystem;

import de.basti.game_framework.controls.GameLoop;
import de.basti.game_framework.controls.TypeEntity;
import de.basti.game_framework.controls.Updatable;
import de.basti.game_framework.controls.Updater;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.drawing.DrawingLayer;
import de.basti.game_framework.drawing.GameDrawing;
import de.basti.game_framework.input.InputListenerData;
import de.julian_und_basti.shootergame.entities.EntityType;
import de.julian_und_basti.shootergame.entities.UpdatableWeightTypeEntity;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class Game {
	// niemand kann diese Klasse instanzieren (wegen private)
	private Game() {
	}
	
	private static Updater entityUpdater = new Updater();
	
	private static List<Runnable> endOfUpdateTasks = new ArrayList<Runnable>();
	
	private static Updatable endOfUpdate = new Updatable() {
		
		@Override
		public void update(long deltaMillis) {
			for(Runnable runnable:endOfUpdateTasks) {
				runnable.run();
			}
			
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
	public static GameCollisionSystem<UpdatableWeightTypeEntity<? extends Drawable, ? extends BoxCollider>> collisionSystem = new GameCollisionSystem<>();
	public static GameLoop loop = new GameLoop();
	
	
	static {
		loop.getUpdater().getList().add(entityUpdater);
		loop.getUpdater().getList().add(drawing);
		loop.getUpdater().getList().add(collisionSystem);
		loop.getUpdater().getList().add(endOfUpdate);
		
		
		
	}
	
	public static void addEntity(DrawingLayer l,UpdatableWeightTypeEntity<? extends Drawable, ? extends BoxCollider> e) {
		drawing.add(l, e);
		collisionSystem.add(e);
		entityUpdater.getList().add(e);
		
	}
	
	public static void removeEntity(UpdatableWeightTypeEntity<? extends Drawable, ? extends BoxCollider> e) {
		drawing.remove(e);
		collisionSystem.remove(e);
		entityUpdater.getList().remove(e);
		
	}
	
	public static void addTaskForEndOfUpdate(Runnable runnable) {
		endOfUpdateTasks.add(runnable);
	}
	
	
	
	
	
}
