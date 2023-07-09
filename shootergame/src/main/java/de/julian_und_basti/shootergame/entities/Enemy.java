package de.julian_und_basti.shootergame.entities;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.controls.TypeEntity;
import de.basti.game_framework.controls.Updatable;
import de.basti.game_framework.drawing.Rectangle;
import de.basti.game_framework.math.Vector2D;
import javafx.scene.paint.Color;

public class Enemy extends TypeEntity<Rectangle, BoxCollider, EntityType> implements Updatable {

	private int width = 20;
	private int height = 20;

	private double speed = 0.15;
	
	private double hp = 40;
	private double damage;
	
	private Player playerToFollow;

	private Vector2D lastTranslate;

	public Enemy(Vector2D position, Player playerToFollow) {
		super(position, null, null, EntityType.ENEMY);

		Rectangle rect = new Rectangle(position.clone(), width, height);
		this.setDrawable(rect);
		this.getDrawable().setFillColor(Color.RED);
		this.getDrawable().setShouldFill(true);

		this.setCollider(new BoxCollider(position.clone(), width, height));

		this.playerToFollow = playerToFollow;

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

	public double getHp() {
		return hp;
	}

	public void setHp(double hp) {
		this.hp = hp;
	}

	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}
	

}
