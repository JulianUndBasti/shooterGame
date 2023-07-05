package de.basti.game_framework.controls;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@code Updatable} which updates all given {@code Updatable} when {@code update(double)}  is called.
 * 
 * @see Updatable
 * @see GameLoop
 */
public class Updater implements Updatable{
	private ArrayList<Updatable> list = new ArrayList<>();

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
