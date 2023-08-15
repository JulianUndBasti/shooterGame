package de.julian_und_basti.shootergame;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class Images {
	
	private static Images instance;
	
	
	private final String prefix = "images/";
	private final String placeholderName = "placeholder.png";
	
	public final Image background;
	public final Image player;
	public final Image heavy;
	public final Image walker;
	public final Image splitter;
	
	
	private Images(){
		background = loadImage("background.png");
		player = loadImage("player.png");
		heavy = loadImage("heavy.png");
		walker = loadImage("walker.png");
		splitter = loadImage("splitter.png");
		
	}
	
	private Image loadImage(String fileName) {
		InputStream stream = Images.class.getClassLoader().getResourceAsStream(prefix+fileName);
		if(stream==null) {
			stream = Images.class.getClassLoader().getResourceAsStream(prefix+placeholderName);
			if(stream == null) {
				return new WritableImage(0, 0);
			}
		}
		return new Image(stream);
	}

	public static Images instance() {
		if(instance==null) {
			instance = new Images();
		}
		return instance;
	}


	

	
}
