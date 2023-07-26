package de.julian_und_basti.shootergame;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import javafx.scene.image.Image;

public class Images {
	
	private static Images instance;
	
	
	private final String prefix = "images/";
	
	public final Image background;
	public final Image player;
	
	private Images(){
		background = loadImage("background.png");
		player = loadImage("tank.png");
	}
	
	private Image loadImage(String fileName) {
		InputStream stream = Images.class.getClassLoader().getResourceAsStream(prefix+fileName);
		
		return new Image(stream);
	}

	public static Images instance() {
		if(instance==null) {
			instance = new Images();
		}
		return instance;
	}


	

	
}
