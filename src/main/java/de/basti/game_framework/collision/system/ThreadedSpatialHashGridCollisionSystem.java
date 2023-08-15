package de.basti.game_framework.collision.system;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

import de.basti.game_framework.collision.Collider;
import de.basti.game_framework.collision.CollisionPair;
import de.basti.game_framework.controls.Entity;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.math.Rectangle;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.entities.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ThreadedSpatialHashGridCollisionSystem<T extends Collider> extends CollisionSystem<T> {

	private Set<T> allColliders = new HashSet<>();
	private SpatialHashGrid<T> grid;
	private double cellWidth;

	public ThreadedSpatialHashGridCollisionSystem() {
		this(50, 50d);

	}

	public ThreadedSpatialHashGridCollisionSystem(int capicity) {
		this(capicity, 50d);

	}

	public ThreadedSpatialHashGridCollisionSystem(double cellWidth) {
		this(50, cellWidth);

	}

	public ThreadedSpatialHashGridCollisionSystem(int capacity, double cellWidth) {
		this.grid = new SpatialHashGrid<>(capacity);
		this.cellWidth = cellWidth;
	}

	@Override
	protected void updateSubstep() {

		this.grid.clear();
		for (T collider : this.allColliders) {
			this.insert(collider);
		}

		Set<CollisionPair<T>> currentCollisions = ConcurrentHashMap
				.newKeySet(allColliders.size() * allColliders.size());

		List<Thread> threads = new ArrayList<Thread>();

		for (Set<T> colliders : this.grid.getAll()) {

			Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					Set<CollisionPair<T>> threadColl = ConcurrentHashMap.newKeySet();

					for (T c1 : colliders) {
						if (c1 == null) {
							throw new NullPointerException("Collider cant be null!");
						}
						colliders.parallelStream().filter(c2 -> c1 != c2)
								.filter(c2 -> c1.collidesWith(c2) || c2.collidesWith(c1))
								.forEach(c2 -> threadColl.add(new CollisionPair<T>(c1, c2)));
					}
					currentCollisions.addAll(threadColl);
				}
			});
			threads.add(thread);
			thread.start();

		}

		while (threads.size() > 0) {
			if (!threads.get(0).isAlive()) {
				threads.remove(0);
			}
		}

		this.handleCollisions(currentCollisions);

	}

	@Override
	public void clear() {
		this.allColliders.clear();
		this.grid.clear();
	}

	@Override
	public boolean add(T collider) {
		if (this.allColliders.add(collider)) {

			return true;
		}
		return false;
	}

	private void insert(T collider) {
		Rectangle r = collider.getEnclosingBounds();
		Vector2D pos = r.getPosition();
		int lowestX = indexOfPos(pos.getX() - r.getWidth() / 2);
		int lowestY = indexOfPos(pos.getY() - r.getHeight() / 2);
		int highestX = indexOfPos(pos.getX() + r.getWidth() / 2);
		int highestY = indexOfPos(pos.getY() + r.getHeight() / 2);

		if (collider instanceof Player) {
			try {
				Thread.sleep(0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		for (int x = lowestX; x < highestX + 1; x++) {
			for (int y = lowestY; y < highestY + 1; y++) {
				this.insert(collider, x, y);
			}
		}
	}

	private int indexOfPos(double pos) {
		return (int) Math.floor(pos / cellWidth);
	}

	private void insert(T collider, int x, int y) {
		this.grid.get(x, y).add(collider);

	}

	@Override
	public boolean remove(T collider) {
		return this.allColliders.remove(collider);
	}

}
