package de.julian_und_basti.shootergame.entities.player_projectiles;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.controls.Engine;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.drawing.DrawableRectangle;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.entities.CustomEntity;
import de.julian_und_basti.shootergame.entities.EntityType;
import de.julian_und_basti.shootergame.entities.enemies.Enemy;
import javafx.scene.paint.Color;

public class SimplePlayerProjectile extends PlayerProjectile {

	private double height = 5;
	private double width = 5;

	public SimplePlayerProjectile(Vector2D shootPosition, Vector2D movement, PlayerProjectileStats stats,
			Engine<CustomEntity<? extends Drawable, ? extends BoxCollider>> game) {
		super(shootPosition, null, null, stats, game);

		DrawableRectangle rect = new DrawableRectangle(shootPosition.clone(), width, height);

		this.setDrawable(rect);
		this.getDrawable().setFillColor(Color.BLUE);
		this.getDrawable().setShouldFill(true);

		this.setCollider(new BoxCollider(shootPosition.clone(), width, height));

		this.setMovement(movement);
	}

	@Override
	public void update(long deltaMillis) {
		super.update(deltaMillis);
	}

	@Override
	public void collidedWith(CustomEntity<?, ?> other) {
		if (other.getType() == EntityType.ENEMY) {
			this.getEngine().removeEntity(this);
		} else if (other.getType() == EntityType.WALL) {
			this.getEngine().removeEntity(this);
		}
	}

}
