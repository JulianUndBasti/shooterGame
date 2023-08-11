package de.basti.game_framework.controls;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.collision.CollisionHandler;
import de.basti.game_framework.collision.GameCollisionSystem;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.drawing.GameDrawing;
import de.basti.game_framework.input.InputListenerData;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.entities.CustomEntity;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;

public class Engine<E extends Entity<?, ?, ?>> {

	public enum UpdatePhase implements Updatable {
		BEGIN, USER_UPDATE, INPUT_UPDATE, COLLISION_UPDATE, CAMERA_UPDATE, DRAWING_UPDATE, END;

		private Updater updater;

		private UpdatePhase() {
			this.updater = new Updater();
		}

		@Override
		public void update(long deltaMillis) {
			this.updater.update(deltaMillis);

		}
		
		public void add(Updatable u) {
			this.updater.add(u);
		}
		
		public boolean remove(Updatable u) {
			return this.updater.remove(u);
		}
		

	}

	private double width;
	private double height;
	
	private Scene scene;
	private GraphicsContext gc;
	
	private Set<E> entities = new HashSet<>();
	private GameCollisionSystem<E> collisionSystem = new GameCollisionSystem<E>();
	private GameDrawing drawing;
	private Loop loop = new Loop();
	private InputListenerData inputData;
	private List<Runnable> endOfUpdateTasks = new ArrayList<>();
	private E camera = null;
	
	public Engine(Scene scene, GraphicsContext gc) {
		this.scene = scene;
		this.gc = gc;
		this.drawing = new GameDrawing(gc);
		this.inputData = new InputListenerData(scene);

		
		
		UpdatePhase.INPUT_UPDATE.add(inputData);
		
		UpdatePhase.COLLISION_UPDATE.add(collisionSystem);
		
		UpdatePhase.CAMERA_UPDATE.add(new Updatable() {

			@Override
			public void update(long deltaMillis) {
				// transform the camera so the player stays in the middle
				Vector2D transform;
				
				if(camera == null) {
					transform = new Vector2D(0,0);
				}else {
					transform = camera.getPosition().clone();
				}
				
				transform.scale(-1);
				transform.translate(width / 2, height / 2);
				getDrawing().setTransform(transform);
				getInputData().getMouseData().setTransform(transform);

			}
		});
		
		UpdatePhase.DRAWING_UPDATE.add(drawing);
		
		UpdatePhase.END.add( new Updatable() {

			@Override
			public void update(long deltaMillis) {
				for (Runnable runnable : endOfUpdateTasks) {
					runnable.run();
				}
				endOfUpdateTasks = new ArrayList<>();

			}
		});
		
		loop.getUpdater().add(UpdatePhase.BEGIN);
		loop.getUpdater().add(UpdatePhase.USER_UPDATE);
		loop.getUpdater().add(UpdatePhase.INPUT_UPDATE);
		loop.getUpdater().add(UpdatePhase.COLLISION_UPDATE);
		loop.getUpdater().add(UpdatePhase.CAMERA_UPDATE);
		loop.getUpdater().add(UpdatePhase.DRAWING_UPDATE);
		loop.getUpdater().add(UpdatePhase.END);

		this.width = gc.getCanvas().getWidth();
		this.height = gc.getCanvas().getHeight();

	}

	public void addEntity(int layer, E e) {
		this.addDrawableRelative(layer, e);
		this.addCollider(e);
		this.addUpdatable(e);
		this.entities.add(e);

	}

	public boolean removeEntity(E e) {
		this.removeDrawable(e);
		this.removeCollider(e);
		this.removeUpdatable(e);
		return this.entities.remove(e);
	}
	
	public void removeAllEntities() {
		for(E e:this.entities) {
			this.removeDrawable(e);
			this.removeCollider(e);
			this.removeUpdatable(e);
		}
		this.entities.clear();
	}
	
	public void removeAllColliders() {
		this.collisionSystem.removeAll();
	}
	
	public void removeAllDrawables() {
		this.drawing.removeAll();
		
	}
	
	public void removeAllUpdatables() {

		UpdatePhase.USER_UPDATE.updater.removeAll();
		
	}
	
	public void removeAllCollisionHandlers() {
		collisionSystem.removeAll();
	}
	
	public void removeAll() {
		this.removeAllEntities();
		this.removeAllColliders();
		this.removeAllDrawables();
		this.removeAllUpdatables();
		this.removeAllCollisionHandlers();
	}

	public boolean removeUpdatable(Updatable u) {
		return UpdatePhase.USER_UPDATE.remove(u);
	}

	public void addUpdatable(Updatable u) {
		UpdatePhase.USER_UPDATE.add(u);
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
	

	public GameCollisionSystem<E> getCollisionSystem() {
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
