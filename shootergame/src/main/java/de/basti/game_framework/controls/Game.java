package de.basti.game_framework.controls;

import java.util.ArrayList;
import java.util.List;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.collision.GameCollisionSystem;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.drawing.DrawingLayer;
import de.basti.game_framework.drawing.GameDrawing;
import de.julian_und_basti.shootergame.entities.WeightEntity;

public class Game<E extends Entity<?, ?, ?>> {
	private GameCollisionSystem<E> collisionSystem = new GameCollisionSystem<E>();
	private GameDrawing drawing;
	private Updater entityUpdater = new Updater();
	private GameLoop loop = new GameLoop();
	
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
	
	
	public void addEntity(DrawingLayer l, E e) {
		drawing.add(l, e);
		collisionSystem.add(e);
		entityUpdater.add(e);

	}

	public void removeEntity(E e) {
		drawing.remove(e);
		collisionSystem.remove(e);
		entityUpdater.remove(e);

	}
}
