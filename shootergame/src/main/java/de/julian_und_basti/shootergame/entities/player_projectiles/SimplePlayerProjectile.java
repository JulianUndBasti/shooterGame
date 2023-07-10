package de.julian_und_basti.shootergame.entities.player_projectiles;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.controls.TypeEntity;
import de.basti.game_framework.controls.Updatable;
import de.basti.game_framework.drawing.Rectangle;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.Game;
import de.julian_und_basti.shootergame.entities.EntityType;
import de.julian_und_basti.shootergame.entities.enemies.Enemy;
import javafx.scene.paint.Color;

public class SimplePlayerProjectile extends PlayerProjectile{
	
	private double height = 10;
	private double width = 10;
	
	

	public SimplePlayerProjectile(Vector2D position, Vector2D mousePosition, double playerWidth, double playerHeight) {
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
		thisFrameMovement.scale(deltaMillis*this.getSpeed());
		this.translate(thisFrameMovement);
	}

	@Override
	public void hit(Enemy<?> enemy) {
		Game.drawing.remove(this);
		Game.collisionSystem.remove(this);
		Game.loop.removeUpdatable(this);
	}

	
	
}
