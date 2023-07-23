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
private static Queue<MediaPlayer> playersNotReady = new ConcurrentLinkedQueue<MediaPlayer>();

	public static MediaPlayer explosion;
	public static MediaPlayer hurt;
	

	public static void initPlayers() {
		URL url = Sounds.class.getClassLoader().getResource("explosion.wav");
		explosion = new MediaPlayer(new Media(url.toString()));
		
		url = Sounds.class.getClassLoader().getResource("hurt.wav");
		hurt = new MediaPlayer(new Media(url.toString()));
		

	}
	
	
}
