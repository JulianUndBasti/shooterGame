package de.julian_und_basti.shootergame.entities.player_projectiles;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.drawing.Rectangle;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.Game;
import de.julian_und_basti.shootergame.entities.EntityType;
import de.julian_und_basti.shootergame.entities.enemies.Enemy;
import javafx.scene.paint.Color;

public class SimplePlayerProjectile extends PlayerProjectile{
	
	private double height = 10;
	private double width = 10;
	
	public SimplePlayerProjectile(Vector2D shootPosition, Vector2D mousePosition,PlayerProjectileStats stats) {
		super(shootPosition, null, null, stats);
		
		shootPosition.translate(0-width/2, 0-height/2);

		Rectangle rect = new Rectangle(shootPosition.clone(), width, height);
		
		this.setDrawable(rect);
		this.getDrawable().setFillColor(Color.BLUE);
		this.getDrawable().setShouldFill(true);
		
		this.setCollider(new BoxCollider(shootPosition.clone(), width, height));
		
		this.calculateMovementDirection(mousePosition);
		
		this.setDamage(20);
		
	}
	
	private Vector2D movement = new Vector2D();

	public void calculateMovementDirection(Vector2D mousePosition) {
		
		movement.set(this.getPosition().getX() - mousePosition.getX()+width/2, this.getPosition().getY() - mousePosition.getY()+height/2);
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
		Game.loop.getUpdater().getList().remove(this);
	}

	
	
}
