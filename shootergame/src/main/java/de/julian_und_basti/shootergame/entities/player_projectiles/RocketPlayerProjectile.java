package de.julian_und_basti.shootergame.entities.player_projectiles;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.drawing.DrawingLayer;
import de.basti.game_framework.drawing.Rectangle;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.Game;
import de.julian_und_basti.shootergame.entities.enemies.Enemy;
import javafx.scene.paint.Color;

public class RocketPlayerProjectile extends PlayerProjectile {

	private double height = 10;
	private double width = 10;

	private double maxSpeed;


	public RocketPlayerProjectile(Vector2D shootPosition, Vector2D direction, PlayerProjectileStats stats) {
		super(shootPosition, null, null, new PlayerProjectileStats(stats.damage, stats.speed/10));
		
		
		Rectangle rect = new Rectangle(shootPosition.clone(), width, height);
		this.setDrawable(rect);
		this.getDrawable().setFillColor(Color.BLACK);
		this.getDrawable().setShouldFill(true);
		this.setCollider(new BoxCollider(shootPosition.clone(), width, height));

		this.translate(new Vector2D(-width / 2, -height / 2));

		this.setMovement(direction);
		this.maxSpeed = stats.speed;

	}

	@Override
	public void update(long deltaMillis) {
		if (this.getSpeed() < this.maxSpeed) {

			this.setSpeed(this.getSpeed()*1.07);// speeding up the rocket

		}
		super.update(deltaMillis);
	}

	@Override
	public void hit(Enemy<?> enemy) {

		explode();
	}

	@Override
	public void hitWall() {
		explode();
		
	}
	
	private void explode() {
		PlayerProjectile projectile = new RocketExplosion(getPosition().clone(), 80);
		Game.addTaskForEndOfUpdate(() -> {
			Game.addEntity(DrawingLayer.FOREGROUND, projectile);
		});

		Game.removeEntity(this);
	}
}
