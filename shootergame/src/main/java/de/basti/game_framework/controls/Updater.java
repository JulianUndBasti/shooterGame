package de.basti.game_framework.controls;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Implementation of {@code Updatable} which updates all given {@code Updatable} when {@code update(double)}  is called.
 * 
 * @see Updatable
 * @see GameLoop
 */
public class Updater implements Updatable{
	private CopyOnWriteArrayList<Updatable> list = new CopyOnWriteArrayList<>();

	@Override
	public void update(long deltaMillis) {
		for(Updatable upd: list) {
			upd.update(deltaMillis);
		}
	}

	public Updatable get(int i) {
		return list.get(i);
	}
	
	public void add(int i,Updatable u) {
		this.list.add(i,u);
	}
	
	public void add(Updatable u) {
		this.list.add(u);
	}
	
	public boolean remove(Updatable u) {
		return this.list.remove(u);
	}
	
	public void remove(int i) {
		this.list.remove(i);
	}
	
	
	
	
	
	
}
