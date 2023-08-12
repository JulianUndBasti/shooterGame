package de.julian_und_basti.shootergame.entities.enemies;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.controls.Engine;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.drawing.DrawableRectangle;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.entities.CustomEntity;
import de.julian_und_basti.shootergame.entities.EntityType;
import de.julian_und_basti.shootergame.entities.player.Player;
import de.julian_und_basti.shootergame.entities.player_projectiles.PlayerProjectile;
import javafx.scene.paint.Color;

public class WalkerEnemy extends Enemy<DrawableRectangle> {

	public static final double DEFAULT_SPEED = 0.15;
	public static final int DEFAULT_HEALTH = 40;
	public static final int DEFAULT_WEIGHT = 40;

	private int width = 20;
	private int height = 20;

	private Player playerToFollow;

	public WalkerEnemy(Vector2D position, Player playerToFollow,
			Engine<CustomEntity<? extends Drawable, ? extends BoxCollider>> game) {
		super(position, null, null, game);

		DrawableRectangle rect = new DrawableRectangle(position.clone(), width, height);
		this.setDrawable(rect);
		this.getDrawable().setFillColor(Color.RED);
		this.getDrawable().setShouldFill(true);

		this.setCollider(new BoxCollider(position.clone(), width, height));

		this.playerToFollow = playerToFollow;

		this.setSpeed(DEFAULT_SPEED);
		this.setHealth(DEFAULT_HEALTH);
		this.setWeight(DEFAULT_WEIGHT);

	}

	// defined here for memory management
	private Vector2D movement;

	@Override
	public void update(long deltaMillis) {

		movement = this.getMovementDirection(playerToFollow.getPosition());

		movement.normalize();
		movement.scale(this.getSpeed() * deltaMillis);
		this.translate(movement);

	}

	public Player getPlayerToFollow() {
		return playerToFollow;
	}

	public void setPlayerToFollow(Player playerToFollow) {
		this.playerToFollow = playerToFollow;
	}

	@Override
	public void hit(PlayerProjectile p) {
		this.setHealth(this.getHealth() - p.getDamage());
		if (this.getHealth() <= 0) {
			this.getEngine().removeEntity(this);
		}
	}

}
