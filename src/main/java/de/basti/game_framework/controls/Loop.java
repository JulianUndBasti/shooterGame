package de.basti.game_framework.controls;

import javafx.animation.AnimationTimer;


/**
 * Class which updates all given {@code Updatable}.
 * The {@code Updatable} are updated in the order they where given.
 * 
 * @see Updatable
 * @see Updater
 */
public class Loop{
	
	private Updater updater = new Updater();
	
	
	
	public Loop() {	
	}
	
	private AnimationTimer animationTimer = new AnimationTimer() {
		
		@Override
		public void handle(long currentTimeNanos) {
			long currentNanos = currentTimeNanos;
			
			loopPulse(currentNanos);
		}
		
	};
	
	
	
	private long previousNanos = 0l;
	private void loopPulse(long currentNanos) {
		if(this.previousNanos == 0l) {
			previousNanos = currentNanos;
			return;
		}
		
		long elapsedNanos = currentNanos-previousNanos;
		if(elapsedNanos==0) {
			System.out.println();
		}
		
		long deltaMillis = elapsedNanos/1000000;
		
		this.update(deltaMillis);
		
		previousNanos = currentNanos;
	}
	
	private void update(long deltaMillis) {
		this.updater.update(deltaMillis);
	}
	
	public void start() {
		animationTimer.start();
	}
	
	public void stop() {
		animationTimer.stop();
	}

	public Updater getUpdater() {
		return updater;
	}

	public void setUpdater(Updater updater) {
		this.updater = updater;
	}
	
	
	
	
}
