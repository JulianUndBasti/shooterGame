package de.julian_und_basti.shootergame.entities;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.controls.TypeEntity;
import de.basti.game_framework.controls.Updatable;
import de.basti.game_framework.drawing.Rectangle;
import de.basti.game_framework.math.Vector2D;
import javafx.scene.paint.Color;

public class Projectile extends TypeEntity<Rectangle, BoxCollider, EntityType> implements Updatable{
	
	private double height = 10;
	private double width = 10;
	
	private double speed = 0.3;
	
	private double damage = 20;

	public Projectile(Vector2D position, Vector2D mousePosition, double playerWidth, double playerHeight) {
		super(position, null, null, EntityType.PLAYER_PROJECTILE);
		
		position.translate(playerWidth/2 - width/2, playerHeight/2 - height/2);
		
		Rectangle rect = new Rectangle(position.clone(), width, height);
		
		this.setDrawable(rect);
		this.getDrawable().setFillColor(Color.BLUE);
		this.getDrawable().setShouldFill(true);
		
		this.setCollider(new BoxCollider(position.clone(), width, height));
		
		this.calculateMovementDirection(mousePosition);
		
	}
	
	private Vector2D movement = new Vector2D();

	public void calculateMovementDirection(Vector2D mousePosition) {
		
		movement.set(this.getPosition().getX() - mousePosition.getX(), this.getPosition().getY() - mousePosition.getY());
		movement.normalize();
		movement.scale(-1);
		
	}

	@Override
	public void update(long deltaMillis) {
		Vector2D thisFrameMovement = movement.clone();
		thisFrameMovement.scale(deltaMillis*speed);
		this.translate(thisFrameMovement);
	}

	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}
	
	
}
