package de.julian_und_basti.shootergame;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

public class Sounds {
	
	private static Sounds instance;
	
	
	private final String prefix = "sounds/";
	
	private final List<MediaPlayer> registeredPlayers = new ArrayList<>();
	
	public final MediaPlayer explosion;
	public final MediaPlayer hurt;
	
	private Sounds() {
		explosion = loadSound("explosion.wav");
		hurt = loadSound("player_hurt.wav");
		
		startLoadingPlayers();
	}
	
	private MediaPlayer loadSound(String fileName) {
		URL url = Sounds.class.getClassLoader().getResource(prefix+fileName);
		MediaPlayer p = new MediaPlayer(new Media(url.toString()));
		registeredPlayers.add(p);
		
		return p;
		
	}
	
	
	private void startLoadingPlayers() {
		for(MediaPlayer p:this.registeredPlayers) {
			p.setVolume(0);
			p.play();
			
			
		}
		
	}
	
	public boolean haveAllLoaded() {
		for(int i = 0;i<registeredPlayers.size();i++) {
			MediaPlayer p = registeredPlayers.get(i);
			if(p.getStatus()==Status.UNKNOWN) {
				return false;
			}else {
				p.pause();
				p.setVolume(100);
				registeredPlayers.remove(i);
				i--;
			}
		}
		return true;
	}
	
	public static Sounds instance() {
		if(instance == null) {
			instance = new Sounds();
		}
		return instance;
	}
	
	
	
	

	
	
}
