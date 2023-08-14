package de.basti.game_framework.collision.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import de.basti.game_framework.collision.Collider;
import de.basti.game_framework.controls.Entity;

public class SpatialHashGrid<T extends Collider> {
	private Map<Integer, Map<Integer, Set<T>>> grid;

	public SpatialHashGrid() {
		this(50);
	}

	public SpatialHashGrid(int intitialCapacity) {
		grid = new HashMap<Integer, Map<Integer, Set<T>>>(intitialCapacity);
	}

	public Set<T> get(int x, int y) {
		Map<Integer, Set<T>> column = this.grid.get(x);
		if (column == null) {
			column = new HashMap<Integer, Set<T>>();
			this.grid.put(x, column);
		}
		Set<T> colliders = column.get(y);
		if (colliders == null) {
			colliders = new HashSet<T>();
			column.put(y, colliders);
		}

		return colliders;

	}

	public void clear() {
		grid.clear();
	}

	public Set<Set<T>> getAll() {
		Set<Set<T>> all = new HashSet<Set<T>>();
		for (Map<Integer, Set<T>> column : this.grid.values()) {
			for (Set<T> cell : column.values()) {
				all.add(cell);
			}
		}
		return all;
	}

	public Map<Integer, Map<Integer, Set<T>>> getGrid() {
		return grid;
	}

	
	
	

}
