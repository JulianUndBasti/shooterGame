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
		return new MediaPlayer(new Media(url.toString()));
	}
	
	public static Sounds sounds() {
		if(instance == null) {
			instance = new Sounds();
		}
		return instance;
	}
	
	
	
	

	
	
}
