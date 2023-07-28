package de.julian_und_basti.shootergame;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class Sounds {
	
	private static Sounds instance;
	
	
	private final String prefix = "sounds/";
	
	public final MediaPlayer explosion;
	public final MediaPlayer hurt;
	
	private Sounds() {
		explosion = loadSound("explosion.wav");
		hurt = loadSound("hurt.wav");
	}
	
	private MediaPlayer loadSound(String fileName) {
		URL url = Sounds.class.getClassLoader().getResource(prefix+fileName);
		MediaPlayer p = new MediaPlayer(new Media(url.toString()));
		p.play();
		p.pause();
		p.seek(Duration.ZERO);
		
		return p;
		
	}
	
	public static Sounds instance() {
		if(instance == null) {
			instance = new Sounds();
		}
		return instance;
	}
	
	
	
	

	
	
}
