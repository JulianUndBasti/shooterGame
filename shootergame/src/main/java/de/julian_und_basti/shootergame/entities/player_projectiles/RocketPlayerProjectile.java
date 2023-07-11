package de.julian_und_basti.shootergame.entities.player_projectiles;

import java.nio.channels.NonWritableChannelException;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.drawing.DrawingLayer;
import de.basti.game_framework.drawing.Rectangle;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.Game;
import de.julian_und_basti.shootergame.entities.EntityType;
import de.julian_und_basti.shootergame.entities.enemies.Enemy;
import de.julian_und_basti.shootergame.weapons.Weapon;
import javafx.scene.paint.Color;

public class RocketPlayerProjectile extends PlayerProjectile{
	
	private double height = 10;
	private double width = 10;
	
	private double actualSpeed = 0.01;
	
	private Vector2D shootPosition;
	private Vector2D mousePosition;

	Weapon<RocketExplosion> weapon;
	
	public RocketPlayerProjectile(Vector2D shootPosition, Vector2D mousePosition) {
		super(shootPosition, null, null, EntityType.PLAYER_PROJECTILE);
		
		shootPosition.translate(-width/2, -height/2);
		
		this.shootPosition = shootPosition.clone();
		this.mousePosition = mousePosition.clone();

		Rectangle rect = new Rectangle(shootPosition.clone(), width, height);
		
		this.setDrawable(rect);
		this.getDrawable().setFillColor(Color.BLACK);
		this.getDrawable().setShouldFill(true);
		
		this.setCollider(new BoxCollider(shootPosition.clone(), width, height));
		
		this.calculateMovementDirection(mousePosition);
		
	}
	
	private Vector2D movement = new Vector2D();

	public void calculateMovementDirection(Vector2D mousePosition) {
		
		movement.set(this.getPosition().getX() + width/2 - mousePosition.getX() , this.getPosition().getY() - mousePosition.getY()+height/2);
		movement.normalize();
		movement.scale(-1);
		
	}

	@Override
	public void update(long deltaMillis) {
		Vector2D thisFrameMovement = movement.clone();
		thisFrameMovement.scale(deltaMillis * actualSpeed);
		this.translate(thisFrameMovement);
		
		if(actualSpeed < this.getSpeed()) {
			
			actualSpeed *= 1.07;//speeding up the rocket
			
		}
	}

	@Override
	public void hit(Enemy<?> enemy) {
		
		PlayerProjectile projectile = new RocketExplosion(getPosition().clone(), null, null, getType(), width, height);		
		projectile.setDamage(80);
		projectile.setSpeed(0);
		Game.addTaskForEndOfUpdate(() -> {
			Game.addEntity(DrawingLayer.FOREGROUND, projectile);
		});
		
		Game.removeEntity(this);
	}
}
