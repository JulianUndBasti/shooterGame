package de.julian_und_basti.shootergame;


import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.collision.CircleCollider;
import de.basti.game_framework.collision.Collider;
import de.basti.game_framework.collision.TypeCollider;
import de.basti.game_framework.controls.Entity;
import de.basti.game_framework.controls.Updatable;
import de.basti.game_framework.drawing.Circle;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.drawing.Rectangle;
import de.basti.game_framework.input.InputListenerData;
import de.basti.game_framework.input.KeyInputListenerData;
import de.basti.game_framework.input.MouseInputListenerData;
import de.basti.game_framework.math.Vector2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class Player extends Entity<Circle, TypeCollider<CircleCollider,ColliderType>> implements Updatable{
	
	private double radius = 50;
	
	private double speed = 0.02;
	
	private MouseInputListenerData mouseData;
	private KeyInputListenerData keyData;
	
	
	public Player(Vector2D position) { 
		super(position, null, null);
		
		Circle circle = new Circle(position, radius);
		this.setDrawable(circle);
		this.getDrawable().setFillColor(Color.BLUE);
		this.getDrawable().setShouldFill(true);
		
		
		
		this.setCollider(new TypeCollider<>(new CircleCollider(position,radius),ColliderType.PLAYER));
		this.mouseData = Game.inputData.getMouseData();
		this.keyData = Game.inputData.getKeyData();
		
	}

	@Override
	public void update(long deltaMillis) {
		Vector2D movement = new Vector2D();
		if(keyData.isDown(KeyCode.W)) {
			movement.translate(0,-1);
		}
		if(keyData.isDown(KeyCode.S)) {
			movement.translate(0,1);
		}
		if(keyData.isDown(KeyCode.A)) {
			movement.translate(-1,0);
		}
		if(keyData.isDown(KeyCode.D)) {
			movement.translate(1,0);
		}
		movement.normalize();
		movement.scale(deltaMillis*speed);
		
		if(movement.getX()!=0 || movement.getY()!=0 ) {
			this.translate(movement);
		}
		
		
	}

}
