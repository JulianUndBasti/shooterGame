package de.julian_und_basti.shootergame.entities.player_projectiles;

import java.net.URL;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.controls.Engine;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.drawing.DrawableRectangle;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.Sounds;
import de.julian_und_basti.shootergame.entities.CustomEntity;
import de.julian_und_basti.shootergame.entities.EntityType;
import de.julian_und_basti.shootergame.entities.enemies.Enemy;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class RocketExplosion extends PlayerProjectile {

	private double height = 50;
	private double width = 50;

	private int lifespan = 500;

	private int timeAlive;

	public RocketExplosion(Vector2D position, int damage,
			Engine<CustomEntity<? extends Drawable, ? extends BoxCollider>> game) {
		super(position, null, null, new PlayerProjectileStats(damage, 0), game);

		DrawableRectangle rect = new DrawableRectangle(position.clone(), width, height);

		this.setDrawable(rect);
		this.getDrawable().setFillColor(Color.BLACK);
		this.getDrawable().setShouldFill(true);
		this.setCollider(new BoxCollider(position.clone(), width, height));

	}

	@Override
	public void update(long deltaMillis) {

		if (timeAlive >= lifespan) {

			this.getEngine().removeEntity(this);
			return;
		}

		timeAlive += deltaMillis;
	}

	@Override
	public void collidedWith(CustomEntity<?, ?> other) {
		if (other.getType() == EntityType.ENEMY) {
			this.getEngine().getCollisionSystem().remove(this);
		}

	}
}