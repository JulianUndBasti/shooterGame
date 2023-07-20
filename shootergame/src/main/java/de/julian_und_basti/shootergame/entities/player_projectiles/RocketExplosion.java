package de.julian_und_basti.shootergame.entities.player_projectiles;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.controls.Game;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.drawing.Rectangle;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.entities.CustomEntity;
import de.julian_und_basti.shootergame.entities.enemies.Enemy;
import javafx.scene.paint.Color;

public class RocketExplosion extends PlayerProjectile{
	private double height = 50;
	private double width = 50;
	
	private int lifespan = 500;
	
	private int timeAlive;
	
	

	public RocketExplosion(Vector2D position, int damage, Game<CustomEntity<? extends Drawable, ? extends BoxCollider>> game) {
		super(position, null, null, new PlayerProjectileStats(damage, 0),game);
		position.translate(-width/2, -height/2);
		
		Rectangle rect = new Rectangle(position.clone(), width, height);
		
		this.setDrawable(rect);
		this.getDrawable().setFillColor(Color.BLACK);
		this.getDrawable().setShouldFill(true);
		this.setCollider(new BoxCollider(position.clone(), width, height));
		
	}

	@Override
	public void update(long deltaMillis) {
		
		if(timeAlive >= lifespan) {
			
			this.getGame().removeEntity(this);
			return;
		}
		
		timeAlive += deltaMillis;
	}

	@Override
	public void hit(Enemy<?> enemy) {
		
		this.getGame().getCollisionSystem().remove(this);

	}

	@Override
	public void hitWall() {
		
	}
}