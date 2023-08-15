package de.basti.game_framework.controls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.collision.CollisionHandler;
import de.basti.game_framework.collision.system.CollisionSystem;
import de.basti.game_framework.collision.system.NaiveCollisionSystem;
import de.basti.game_framework.collision.system.SpatialHashGridCollisionSystem;
import de.basti.game_framework.collision.system.ThreadedSpatialHashGridCollisionSystem;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.drawing.GameDrawing;
import de.basti.game_framework.input.InputListenerData;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.entities.CustomEntity;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;

public class Engine<E extends Entity<?, ?, ?>> {

	public static final int BEGIN = 100;
	public static final int USER_UPDATE = 200;
	public static final int INPUT_UPDATE = 300;
	public static final int COLLISION_UPDATE = 400;
	public static final int CAMERA_UPDATE = 500;
	public static final int DRAWING_UPDATE = 600;
	public static final int END = 700;
	private HashMap<Integer, Updater> updatePhases = new HashMap<>();

	private double width;
	private double height;

	private Scene scene;
	private GraphicsContext gc;

	private Set<E> entities = new HashSet<>();
	private CollisionSystem<E> collisionSystem = new ThreadedSpatialHashGridCollisionSystem<E>();
	private GameDrawing drawing;
	private Loop loop = new Loop();
	private InputListenerData inputData;
	private List<Runnable> endOfUpdateTasks = new ArrayList<>();
	private E camera = null;

	public static interface SizeChangeListener {
		public void onSizeChange(double width, double height);
	}

	private List<SizeChangeListener> sizeChangeListeners = new ArrayList<>();

	public Engine(Scene scene, GraphicsContext gc) {
		this.scene = scene;
		this.gc = gc;
		this.drawing = new GameDrawing(gc);
		this.inputData = new InputListenerData(scene);

		this.addUpdatable(inputData, INPUT_UPDATE);
		this.addUpdatable(collisionSystem, COLLISION_UPDATE);
		this.addUpdatable(new Updatable() {

			@Override
			public void update(long deltaMillis) {
				// transform the camera so the player stays in the middle
				Vector2D transform;

				if (camera == null) {
					transform = new Vector2D(0, 0);
				} else {
					transform = camera.getPosition().clone();
				}

				transform.scale(-1);
				transform.translate(width / 2, height / 2);
				getDrawing().setTransform(transform);
				getInputData().getMouseData().setTransform(transform);

			}
		}, CAMERA_UPDATE);
		this.addUpdatable(drawing, DRAWING_UPDATE);
		this.addUpdatable(new Updatable() {

			@Override
			public void update(long deltaMillis) {
				for (Runnable runnable : endOfUpdateTasks) {
					runnable.run();
				}
				endOfUpdateTasks = new ArrayList<>();

			}
		}, END);

		Updatable topLevelUpdate = new Updatable() {

			@Override
			public void update(long deltaMillis) {
				for (Updater upd : updatePhases.values()) {
					upd.update(deltaMillis);
				}
			}
		};

		this.loop.getUpdater().add(topLevelUpdate);

		this.width = gc.getCanvas().getWidth();
		this.height = gc.getCanvas().getHeight();

		this.scene.widthProperty().addListener((observable, oldValue, newValue) -> {
			this.width = (double) newValue;
			fireSizeChange();
		});

		this.scene.heightProperty().addListener((observable, oldValue, newValue) -> {
			this.height = (double) newValue;
			fireSizeChange();
		});
	}

	private void fireSizeChange() {
		this.sizeChangeListeners.forEach(scl -> scl.onSizeChange(width, height));
	}

	public void addSizeChangeListener(SizeChangeListener scl) {
		this.sizeChangeListeners.add(scl);
	}

	public boolean removeSizeChangeListener(SizeChangeListener scl) {
		return this.sizeChangeListeners.remove(scl);
	}

	public void addEntity(int layer, E e) {
		this.addDrawableRelative(layer, e);
		this.addCollider(e);
		this.addUserUpdatable(e);
		this.entities.add(e);

	}

	public boolean removeEntity(E e) {
		this.removeDrawable(e);
		this.removeCollider(e);
		this.removeUserUpdatable(e);
		return this.entities.remove(e);
	}

	public void removeAllEntities() {
		for (E e : this.entities) {
			this.removeDrawable(e);
			this.removeCollider(e);
			this.removeUserUpdatable(e);
		}
		this.entities.clear();
	}

	public void removeAllColliders() {
		this.collisionSystem.clear();
	}

	public void removeAllDrawables() {
		this.drawing.removeAll();

	}

	public void removeAllUpdatables(int updatePhase) {

		Updater updater = this.updatePhases.get(updatePhase);
		if (updater == null) {
			return;
		}
		updater.removeAll();
	}

	public void removeAllUserUpdatables() {

		this.removeAllUpdatables(USER_UPDATE);
	}

	public void removeAllCollisionHandlers() {
		collisionSystem.clear();
	}

	public void removeAll() {
		this.removeAllEntities();
		this.removeAllColliders();
		this.removeAllDrawables();
		this.removeAllUserUpdatables();
		this.removeAllCollisionHandlers();
	}

	public boolean removeUpdatable(Updatable u, int updatePhase) {
		Updater updater = this.updatePhases.get(updatePhase);
		if (updater == null) {
			return false;
		}
		return updater.remove(u);
	}

	public boolean removeUserUpdatable(Updatable u) {
		return this.removeUpdatable(u, USER_UPDATE);
	}

	public void addUpdatable(Updatable u, int updatePhase) {
		Updater updater = this.updatePhases.get(updatePhase);
		if (updater == null) {
			updater = new Updater();
			this.updatePhases.put(updatePhase, updater);
		}
		updater.add(u);
	}

	public void addUserUpdatable(Updatable u) {
		this.addUpdatable(u, USER_UPDATE);
	}

	public boolean removeDrawable(Drawable d) {
		return this.drawing.remove(d);
	}

	public void addDrawableRelative(int layer, Drawable d) {
		this.drawing.addRelative(layer, d);
	}

	public void addDrawableAbsolute(Drawable d) {
		this.drawing.addAbsolute(d);
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

	public boolean removeCollisionHandler(CollisionHandler<E> collHandler) {
		return this.collisionSystem.removeHandler(collHandler);
	}

	public CollisionSystem<E> getCollisionSystem() {
		return collisionSystem;
	}

	public GameDrawing getDrawing() {
		return drawing;
	}

	public Loop getLoop() {
		return loop;
	}

	public InputListenerData getInputData() {
		return inputData;
	}

	public double getWidth() {
		return gc.getCanvas().getWidth();
	}

	public double getHeight() {
		return gc.getCanvas().getHeight();
	}

}
