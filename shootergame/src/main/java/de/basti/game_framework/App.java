package de.basti.game_framework;

import de.basti.game_framework.collision.Collider;
import de.basti.game_framework.collision.CollisionHandler;
import de.basti.game_framework.collision.CollisionPair;
import de.basti.game_framework.collision.GameCollisionSystem;
import de.basti.game_framework.controls.GameLoop;
import de.basti.game_framework.drawing.DrawingLayer;
import de.basti.game_framework.drawing.GameDrawing;
import de.basti.game_framework.input.InputListenerData;
import de.basti.game_framework.math.Vector2D;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {
	
	private final int width = 800;
	private final int height = 600;
	
	//javafx
	private Canvas canvas = new Canvas(width,height);
	private Group root = new Group(canvas);
	private Scene scene = new Scene(root);
	
	private GraphicsContext gc = canvas.getGraphicsContext2D(); 
	
	//game-framework
	private GameDrawing graphics = new GameDrawing(gc);
	private InputListenerData inputData = new InputListenerData(scene);
	private GameCollisionSystem<Collider> collisionSystem = new GameCollisionSystem<Collider>();
	private GameLoop loop = new GameLoop();
	
	
	//game
	private Player player = new Player(new Vector2D(width/2,height/2),inputData);
	private Player player2 = new Player(new Vector2D(width/2+100,height/2+100),inputData) {
		public void update(long deltaMillis) {};
	};
	
	
	
	
    @Override
    public void start(Stage stage) {
    	
    	collisionSystem.setOnCollisionBegin((pair)->{
    		System.out.println(pair.getCollider1()+" collided with "+pair.getCollider2()+"!");
    	});
    	
    	
    	this.graphics.add(DrawingLayer.FOREGROUND, player);
    	this.graphics.add(DrawingLayer.FOREGROUND, player2);
    	this.collisionSystem.add(player);
    	this.collisionSystem.add(player2);
    	
    	
    	
    	loop.addUpdatableAfter(player);
    	loop.addUpdatableAfter(collisionSystem);
    	loop.addUpdatableAfter(graphics);
    	
    	
    	loop.start();
    	
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}