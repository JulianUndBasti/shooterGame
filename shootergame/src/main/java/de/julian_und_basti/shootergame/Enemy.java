package de.julian_und_basti.shootergame;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.controls.TypeEntity;
import de.basti.game_framework.controls.Updatable;
import de.basti.game_framework.drawing.Rectangle;
import de.basti.game_framework.math.Vector2D;
import javafx.scene.paint.Color;

public class Enemy extends TypeEntity<Rectangle, BoxCollider, EntityType> implements Updatable {

	private int width = 20;
	private int height = 20;

	private double speed = 0.05;

	private Player playerToFollow;

	private Vector2D lastTranslate;

	public Enemy(Vector2D position, Player playerToFollow) {
		super(position, null, null, EntityType.ENEMY);

		Rectangle rect = new Rectangle(position, width, height);
		this.setDrawable(rect);
		this.getDrawable().setFillColor(Color.RED);
		this.getDrawable().setShouldFill(true);

		this.setCollider(new BoxCollider(position, width, height));

		this.playerToFollow = playerToFollow;

	}

	@Override
	public void update(long deltaMillis) {
		Vector2D direction = this.getPosition().clone();
		direction.translate(this.getCollider().getWidth()/2,this.getCollider().getHeight()/2);
		direction.scale(-1);
		
		Vector2D playerCenter = playerToFollow.getPosition().clone();
		playerCenter.translate(playerToFollow.getCollider().getWidth()/2,playerToFollow.getCollider().getHeight()/2);
		
		direction.translate(playerCenter);

		direction.normalize();

		direction.scale(speed * deltaMillis);
		this.translate(direction);
	}

	public Player getPlayerToFollow() {
		return playerToFollow;
	}

	public void setPlayerToFollow(Player playerToFollow) {
		this.playerToFollow = playerToFollow;
	}

}
