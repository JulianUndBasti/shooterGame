package de.julian_und_basti.shootergame.entities;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.controls.TypeEntity;
import de.basti.game_framework.controls.Updatable;
import de.basti.game_framework.drawing.DrawingLayer;
import de.basti.game_framework.drawing.Rectangle;
import de.basti.game_framework.input.KeyInputListenerData;
import de.basti.game_framework.input.MouseInputListenerData;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.App;
import de.julian_und_basti.shootergame.Game;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;

public class Player extends TypeEntity<Rectangle, BoxCollider, EntityType> implements Updatable{
	
	private double width = 30;
	private double height = 30;
	
	
	private double speed = 0.4;
	
	private MouseInputListenerData mouseData;
	private KeyInputListenerData keyData;
	
	
	public Player(Vector2D position) { 
		super(position, null, null,EntityType.PLAYER);
		
		Rectangle rect = new Rectangle(position.clone(), width,height);
		this.setDrawable(rect);
		this.getDrawable().setFillColor(Color.BLUE);
		this.getDrawable().setShouldFill(true);
		
		
		
		this.setCollider(new BoxCollider(position.clone(),width,height));
		this.mouseData = Game.inputData.getMouseData();
		this.keyData = Game.inputData.getKeyData();
		
	}
	
	private Vector2D movement = new Vector2D();
	
	@Override
	public void update(long deltaMillis) {
		movement.set(0, 0);
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
		
		//transform the camera so the player stays in the middle
		Vector2D transform = this.getPosition().clone();
		transform.scale(-1);
		transform.translate((Game.width - this.getDrawable().getWidth())/2,(Game.height - this.getDrawable().getHeight())/2);
		//Game.drawing.setCameraTransform(transform);
		
		
		//shoot logic
		if(mouseData.isPressed(MouseButton.PRIMARY)) {

			Projectile projectile = new Projectile(getPosition().clone(), mouseData.getMousePosition());
			Game.collisionSystem.add(projectile);
			Game.drawing.add(DrawingLayer.MIDDLE, projectile);
			Game.loop.addUpdatableBefore(projectile);
			
			
			
		}
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

}
