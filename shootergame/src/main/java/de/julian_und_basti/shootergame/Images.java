package de.julian_und_basti.shootergame;

import java.io.InputStream;
import javafx.scene.image.Image;

public class Images {
	public static Image background;
	
	static {
		InputStream stream= Images.class.getClassLoader().getResourceAsStream("background.png");
		background = new Image(stream);
	}
}
