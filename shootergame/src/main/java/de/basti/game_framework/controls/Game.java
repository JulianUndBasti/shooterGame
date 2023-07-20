package de.basti.game_framework.controls;

import java.util.ArrayList;
import java.util.List;

import de.basti.game_framework.collision.CollisionHandler;
import de.basti.game_framework.collision.GameCollisionSystem;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.drawing.DrawingLayer;
import de.basti.game_framework.drawing.GameDrawing;
import de.basti.game_framework.input.InputListenerData;
import de.basti.game_framework.math.Vector2D;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;

public class Game<E extends Entity<?, ?, ?>> {

	private double width;
	private double height;

	private GameCollisionSystem<E> collisionSystem = new GameCollisionSystem<E>();
	private GameDrawing drawing;
	private Updater updater = new Updater();
	private GameLoop loop = new GameLoop();
	private InputListenerData inputData;

	private List<Runnable> endOfUpdateTasks = new ArrayList<>();

	private Updatable endOfUpdate = new Updatable() {

		@Override
		public void update(long deltaMillis) {
			for (Runnable runnable : endOfUpdateTasks) {
				runnable.run();
			}
			endOfUpdateTasks = new ArrayList<>();

		}
	};

	private E camera = null;

	private Updatable cameraUpdate = new Updatable() {

		@Override
		public void update(long deltaMillis) {
			// transform the camera so the player stays in the middle
			Vector2D transform = camera.getPosition().clone();
			transform.scale(-1);
			transform.translate(width/2,height/2);
			drawing.setTransform(transform);
			inputData.getMouseData().setTransform(transform);
			System.out.println(deltaMillis);

		}
	};

	public Game(Scene scene, GraphicsContext gc) {
		this.drawing = new GameDrawing(gc);
		this.inputData = new InputListenerData(scene);

		loop.getUpdater().add(this.updater);
		loop.getUpdater().add(this.inputData);
		loop.getUpdater().add(this.collisionSystem);
		loop.getUpdater().add(cameraUpdate);
		loop.getUpdater().add(drawing);
		loop.getUpdater().add(endOfUpdate);

		this.width = gc.getCanvas().getWidth();
		this.height = gc.getCanvas().getHeight();

	}

	public void addEntity(DrawingLayer l, E e) {
		this.addDrawable(l, e);
		this.addCollider(e);
		this.addUpdatable(e);

	}

	public void removeEntity(E e) {
		this.removeDrawable(e);
		this.removeCollider(e);
		this.removeUpdatable(e);

	}

	public boolean removeUpdatable(Updatable u) {
		return this.updater.remove(u);
	}

	public void addUpdatable(Updatable u) {
		this.updater.add(u);
	}

	public boolean removeDrawable(Drawable d) {
		return this.drawing.remove(d);
	}

	public void addDrawable(DrawingLayer l, Drawable d) {
		this.drawing.add(l, d);
	}

	public boolean removeCollider(E e) {
		return this.collisionSystem.remove(e);
	}

	public void addCollider(E e) {
		this.collisionSystem.add(e);
	}

	public void stickCameraTo(E e) {
		camera = e;
	}

	public void addTaskForEndOfUpdate(Runnable runnable) {
		endOfUpdateTasks.add(runnable);
	}

	public void start() {
		this.loop.start();
	}

	public void stop() {
		this.loop.stop();
	}

	public void addCollisionHandler(CollisionHandler<E> collHandler) {
		this.collisionSystem.addHandler(collHandler);
	}

	public GameCollisionSystem<E> getCollisionSystem() {
		return collisionSystem;
	}

	public GameDrawing getDrawing() {
		return drawing;
	}

	public Updater getUpdater() {
		return updater;
	}

	public GameLoop getLoop() {
		return loop;
	}

	public InputListenerData getInputData() {
		return inputData;
	}

}
