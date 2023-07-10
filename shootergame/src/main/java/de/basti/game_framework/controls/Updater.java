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

	public List<Updatable> getList() {
		return list;
	}	
	
}
