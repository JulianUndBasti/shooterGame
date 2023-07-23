package de.julian_und_basti.shootergame;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import javafx.scene.image.Image;

public class Images {
	
	private Images() {
	}
	public static Image background;
	public static Image player;
	
	
	static {
		InputStream stream = Images.class.getClassLoader().getResourceAsStream("background.png");
		background = new Image(stream);
		stream = Images.class.getClassLoader().getResourceAsStream("tank.png");
		player = new Image(stream);
	}
}
