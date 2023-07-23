package de.julian_und_basti.shootergame.entities.player_projectiles;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.controls.Game;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.drawing.Rectangle;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.entities.CustomEntity;
import de.julian_und_basti.shootergame.entities.enemies.Enemy;
import javafx.scene.paint.Color;

public class SimplePlayerProjectile extends PlayerProjectile {

	private double height = 5;
	private double width = 5;


	public SimplePlayerProjectile(Vector2D shootPosition, Vector2D direction, PlayerProjectileStats stats,Game<CustomEntity<? extends Drawable, ? extends BoxCollider>> game) {
		super(shootPosition, null, null, stats,game);

		Rectangle rect = new Rectangle(shootPosition.clone(), width, height);

		this.setDrawable(rect);
		this.getDrawable().setFillColor(Color.BLUE);
		this.getDrawable().setShouldFill(true);

		this.setCollider(new BoxCollider(shootPosition.clone(), width, height));
		this.translate(new Vector2D(0 - width / 2, 0 - height / 2));

		this.setMovement(direction);
	}

	@Override
	public void update(long deltaMillis) {
		super.update(deltaMillis);
	}

	@Override
	public void hit(Enemy<?> enemy) {
		this.getGame().removeEntity(this);
	}

	@Override
	public void hitWall() {
		this.getGame().removeEntity(this);
	}

}