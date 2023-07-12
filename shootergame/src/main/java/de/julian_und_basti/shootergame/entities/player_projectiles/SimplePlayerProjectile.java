package de.julian_und_basti.shootergame.entities.player_projectiles;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.drawing.Rectangle;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.Game;
import de.julian_und_basti.shootergame.entities.enemies.Enemy;
import javafx.scene.paint.Color;

public class SimplePlayerProjectile extends PlayerProjectile {

	private double height = 10;
	private double width = 10;


	public SimplePlayerProjectile(Vector2D shootPosition, Vector2D direction, PlayerProjectileStats stats) {
		super(shootPosition, null, null, stats);

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
		Game.drawing.remove(this);
		Game.collisionSystem.remove(this);
		Game.loop.getUpdater().getList().remove(this);
	}

}
