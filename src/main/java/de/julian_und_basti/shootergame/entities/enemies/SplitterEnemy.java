package de.julian_und_basti.shootergame.entities.enemies;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.controls.Engine;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.drawing.GameDrawing;
import de.basti.game_framework.drawing.Sprite;
import de.basti.game_framework.drawing.DrawableRectangle;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.Images;
import de.julian_und_basti.shootergame.entities.CustomEntity;
import de.julian_und_basti.shootergame.entities.EntityType;
import de.julian_und_basti.shootergame.entities.player.Player;
import de.julian_und_basti.shootergame.entities.player_projectiles.PlayerProjectile;
import javafx.scene.paint.Color;

public class SplitterEnemy extends Enemy<Sprite> {

	public static final double DEFAULT_SPEED = 0.12;
	public static final int DEFAULT_HEALTH = 60;
	public static final int DEFAULT_WEIGHT = 60;

	private int width = 24;
	private int height = 24;

	private Player playerToFollow;

	public SplitterEnemy(Vector2D position, Player playerToFollow,
			Engine<CustomEntity<? extends Drawable, ? extends BoxCollider>> game) {
		super(position, null, null, game);

		Sprite sprite = new Sprite(position.clone(), Images.instance().splitter);
		sprite.setWidth(width);
		sprite.setHeight(height);

		this.setDrawable(sprite);

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

	public void hitPlayerProjectile(PlayerProjectile p) {
		this.setHealth(this.getHealth() - p.getDamage());
		if (this.getHealth() <= 0) {
			this.getEngine().removeEntity(this);
			WalkerEnemy e1 = new WalkerEnemy(this.getPosition().clone(), playerToFollow, this.getEngine());
			WalkerEnemy e2 = new WalkerEnemy(this.getPosition().clone(), playerToFollow, this.getEngine());
			e1.translate(new Vector2D(0.01, 0));// translating a little bit, so they get unstuck through collision
			this.getEngine().addTaskForEndOfUpdate(() -> {
				getEngine().addEntity(GameDrawing.MIDDLE, e1);
				getEngine().addEntity(GameDrawing.MIDDLE, e2);

			});

		}
	}

	@Override
	public void collidedWith(CustomEntity<?, ?> other) {
		if (other.getType() == EntityType.PLAYER_PROJECTILE) {
			PlayerProjectile p = (PlayerProjectile) other;
			this.hitPlayerProjectile(p);
		}

	}

}
