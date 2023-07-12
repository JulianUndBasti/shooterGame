package de.julian_und_basti.shootergame.entities.enemies;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.drawing.Rectangle;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.Game;
import de.julian_und_basti.shootergame.entities.EntityType;
import de.julian_und_basti.shootergame.entities.player.Player;
import de.julian_und_basti.shootergame.entities.player_projectiles.PlayerProjectile;
import javafx.scene.paint.Color;

public class HeavyEnemy extends Enemy<Rectangle> {
	
	public static final double DEFAULT_SPEED = 0.08;
	public static final int DEFAULT_HEALTH = 80;
	public static final int DEFAULT_WEIGHT = 80;
	

	private int width = 30;
	private int height = 30;
	
	private Player playerToFollow;
	
	public HeavyEnemy(Vector2D position, Player playerToFollow) {
		super(position, null, null, EntityType.ENEMY);

		Rectangle rect = new Rectangle(position.clone(), width, height);
		this.setDrawable(rect);
		this.getDrawable().setFillColor(Color.DARKGREEN);
		this.getDrawable().setShouldFill(true);

		this.setCollider(new BoxCollider(position.clone(), width, height));

		this.playerToFollow = playerToFollow;
		
		this.setSpeed(DEFAULT_SPEED);
		this.setHealth(DEFAULT_HEALTH);
		this.setWeight(DEFAULT_WEIGHT);

	}
	//defined here for memory management
	Vector2D direction = new Vector2D();
	Vector2D playerCenter = new Vector2D();
	
	
	@Override
	public void update(long deltaMillis) {
		
		direction.set(this.getPosition().getX(), this.getPosition().getY());
		direction.translate(this.getCollider().getWidth()/2,this.getCollider().getHeight()/2);
		direction.scale(-1);
		
		playerCenter.set(playerToFollow.getPosition().getX(), playerToFollow.getPosition().getY());
		playerCenter.translate(playerToFollow.getCollider().getWidth()/2,playerToFollow.getCollider().getHeight()/2);
		
		direction.translate(playerCenter);

		
		direction = direction.clone();
		direction.normalize();
		direction.scale(this.getSpeed()*deltaMillis);
		this.translate(direction);
	
	}

	public Player getPlayerToFollow() {
		return playerToFollow;
	}

	public void setPlayerToFollow(Player playerToFollow) {
		this.playerToFollow = playerToFollow;
	}

	@Override
	public void hit(PlayerProjectile p) {
		this.setHealth(this.getHealth()-p.getDamage());
		if (this.getHealth() <= 0) {
			Game.removeEntity(this);
		}
	}

}
