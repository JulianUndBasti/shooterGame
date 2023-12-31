package de.julian_und_basti.shootergame.entities.player_projectiles;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.controls.Engine;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.drawing.GameDrawing;
import de.basti.game_framework.drawing.DrawableRectangle;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.Sounds;
import de.julian_und_basti.shootergame.entities.CustomEntity;
import de.julian_und_basti.shootergame.entities.EntityType;
import de.julian_und_basti.shootergame.entities.enemies.Enemy;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class RocketPlayerProjectile extends PlayerProjectile {

	private double height = 10;
	private double width = 10;

	private double maxSpeed;

	public RocketPlayerProjectile(Vector2D shootPosition, Vector2D movement, PlayerProjectileStats stats,
			Engine<CustomEntity<? extends Drawable, ? extends BoxCollider>> game) {
		super(shootPosition, null, null, new PlayerProjectileStats(stats.damage, stats.speed / 10), game);

		DrawableRectangle rect = new DrawableRectangle(shootPosition.clone(), width, height);
		this.setDrawable(rect);
		this.getDrawable().setFillColor(Color.BLACK);
		this.getDrawable().setShouldFill(true);
		this.setCollider(new BoxCollider(shootPosition.clone(), width, height));

		this.setMovement(movement);
		this.maxSpeed = stats.speed;

	}

	@Override
	public void update(long deltaMillis) {
		if (this.getSpeed() < this.maxSpeed) {

			this.setSpeed(this.getSpeed() * 1.07);// speeding up the rocket

		}
		super.update(deltaMillis);
	}

	private void explode() {

		PlayerProjectile projectile = new RocketExplosion(getPosition().clone(), 80, this.getEngine());
		this.getEngine().addTaskForEndOfUpdate(() -> {
			this.getEngine().addEntity(GameDrawing.FOREGROUND, projectile);
			Sounds.instance().explosion.seek(Duration.ZERO);
			Sounds.instance().explosion.play();
		});

		this.getEngine().removeEntity(this);
	}

	@Override
	public void collidedWith(CustomEntity<?, ?> other) {
		if (other.getType() == EntityType.ENEMY) {
			explode();
		} else if (other.getType() == EntityType.WALL) {
			explode();
		}

	}
}
