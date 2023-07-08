package de.julian_und_basti.shootergame.entities;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.controls.TypeEntity;
import de.basti.game_framework.controls.Updatable;
import de.basti.game_framework.drawing.Rectangle;
import de.basti.game_framework.input.MouseInputListenerData;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.Game;
import javafx.scene.paint.Color;

public class Projectile extends TypeEntity<Rectangle, BoxCollider, EntityType> implements Updatable{
	
	private int height = 10;
	private int width = 10;
	
	private double speed = 0.3;
	
	Player player;
	
	MouseInputListenerData mouseInput = Game.inputData.getMouseData();
	
	public Projectile(Vector2D position, Player player) {
		super(position, null, null, EntityType.PROJECTILE);
		
		Rectangle rect = new Rectangle(position.clone(), width, height);
		
		this.setDrawable(rect);
		this.getDrawable().setFillColor(Color.BLUE);
		this.getDrawable().setShouldFill(true);
		
		this.setCollider(new BoxCollider(position.clone(), width, height));
	
		this.player = player;
	}
	
	private Vector2D movement = new Vector2D();

	public void calculateVector2D() {
		
		System.out.println(mouseInput.getMousePosition());
		
		movement.set(player.getPosition().getX() - mouseInput.getMousePosition().getX(), player.getPosition().getY() - mouseInput.getMousePosition().getY());
		movement.normalize();
		movement.scale(-1);
		
	}

	@Override
	public void update(long deltaMillis) {
		System.out.println(deltaMillis);
		Vector2D thisFrameMovement = movement.clone();
		thisFrameMovement.scale(deltaMillis*speed);
		this.translate(thisFrameMovement);
	}
}
