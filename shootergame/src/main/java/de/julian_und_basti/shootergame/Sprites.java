package de.julian_und_basti.shootergame;

import java.io.InputStream;
import java.net.URL;

import javafx.scene.image.Image;

public class Sprites {
	public static Image background;
	
	static {
		InputStream stream= Sprites.class.getClassLoader().getResourceAsStream("background.png");
		background = new Image(stream);
	}
}