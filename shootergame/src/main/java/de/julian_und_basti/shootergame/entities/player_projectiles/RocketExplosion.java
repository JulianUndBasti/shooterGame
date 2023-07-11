package de.julian_und_basti.shootergame.entities.player_projectiles;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.drawing.Rectangle;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.Game;
import de.julian_und_basti.shootergame.entities.EntityType;
import de.julian_und_basti.shootergame.entities.enemies.Enemy;
import javafx.scene.paint.Color;

public class RocketExplosion extends PlayerProjectile{
	private double height = 50;
	private double width = 50;
	
	private int lifespan = 500;
	
	private int timeAlive;

	public RocketExplosion(Vector2D position, BoxCollider collider, Rectangle drawable, EntityType type, double rocketWidth, double rocketHeight) {
		super(position, null, null, EntityType.PLAYER_PROJECTILE);

		position.translate(0-width/2 + rocketWidth/2, 0-height/2 + rocketHeight/2);
		
		Rectangle rect = new Rectangle(position.clone(), width, height);
		
		this.setDrawable(rect);
		this.getDrawable().setFillColor(Color.BLACK);
		this.getDrawable().setShouldFill(true);
		
		this.setCollider(new BoxCollider(position.clone(), width, height));
		
		
	}

	@Override
	public void update(long deltaMillis) {
		
		if(timeAlive >= lifespan) {
			
			Game.removeEntity(this);
			return;
		}
		
		timeAlive += deltaMillis;
	}

	@Override
	public void hit(Enemy<?> enemy) {
		
		Game.collisionSystem.remove(this);

	}
}