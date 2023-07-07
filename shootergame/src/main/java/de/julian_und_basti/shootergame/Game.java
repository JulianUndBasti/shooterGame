package de.julian_und_basti.shootergame;


import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.collision.Collider;
import de.basti.game_framework.collision.GameCollisionSystem;

import de.basti.game_framework.controls.GameLoop;
import de.basti.game_framework.controls.TypeEntity;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.drawing.GameDrawing;
import de.basti.game_framework.input.InputListenerData;
import de.julian_und_basti.shootergame.entities.EntityType;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class Game {
	// niemand kann diese Klasse instanzieren (wegen private)
	private Game() {
	}
	
	public static int width = 800;
	public static int height = 600;
	
	
	// javafx
	public static Canvas canvas = new Canvas(width, height);
	public static Group root = new Group(canvas);
	public static Scene scene = new Scene(root);

	public static GraphicsContext gc = canvas.getGraphicsContext2D();

	// game-framework
	public static GameDrawing drawing = new GameDrawing(gc);
	public static InputListenerData inputData = new InputListenerData(scene);
	public static GameCollisionSystem<TypeEntity<? extends Drawable, ? extends BoxCollider, EntityType>> collisionSystem = new GameCollisionSystem<>();
	public static GameLoop loop = new GameLoop();
	
	
	

}
