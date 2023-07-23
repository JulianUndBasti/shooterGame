package de.julian_und_basti.shootergame.entities.player;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.controls.Game;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.drawing.Rectangle;
import de.basti.game_framework.input.KeyInputListenerData;
import de.basti.game_framework.input.MouseInputListenerData;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.entities.EntityType;
import de.julian_und_basti.shootergame.entities.enemies.Enemy;
import de.julian_und_basti.shootergame.entities.CustomEntity;
import de.julian_und_basti.shootergame.weapons.Weapon;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;

public class Player extends CustomEntity<Rectangle, BoxCollider> {

	private double width = 30;
	private double height = 30;

	private static final double DEFAULT_SPEED = 0.4;
	private static final int DEFAULT_WEIGHT = 500;

	private double speed = DEFAULT_SPEED;

	private MouseInputListenerData mouseData;
	private KeyInputListenerData keyData;

	private Weapon weapon;
	
	private int health = 100;
	
	private int hitDelay = 1000;
	private int timeSinceHit = 1000;

	public Player(Vector2D position, Weapon weapon,Game<CustomEntity<? extends Drawable, ? extends BoxCollider>> game) {
		super(position, null, null, EntityType.PLAYER,game);

		Rectangle rect = new Rectangle(position.clone(), width, height);
		this.setDrawable(rect);
		this.getDrawable().setFillColor(Color.BLUE);
		this.getDrawable().setShouldFill(true);

		this.setCollider(new BoxCollider(position.clone(), width, height));
		this.mouseData = this.getGame().getInputData().getMouseData();
		this.keyData = this.getGame().getInputData().getKeyData();

		this.weapon = weapon;

		this.setWeight(DEFAULT_WEIGHT);

	}

	private Vector2D movement = new Vector2D();

	@Override
	public void update(long deltaMillis) {
		if(timeSinceHit<=hitDelay) {
			timeSinceHit+=deltaMillis;
			this.getDrawable().setFillColor(Color.LIGHTBLUE);
		}else {
			this.getDrawable().setFillColor(Color.BLUE);
		}
		
		
		movement.set(0, 0);
		if (keyData.isDown(KeyCode.W)) {
			movement.translate(0, -1);
		}
		if (keyData.isDown(KeyCode.S)) {
			movement.translate(0, 1);
		}
		if (keyData.isDown(KeyCode.A)) {
			movement.translate(-1, 0);
		}
		if (keyData.isDown(KeyCode.D)) {
			movement.translate(1, 0);
		}
		movement.normalize();
		movement.scale(deltaMillis * speed);

		if (movement.getX() != 0 || movement.getY() != 0) {
			this.translate(movement);
		}

		
		this.weapon.update(deltaMillis);
		// shoot logic
		if (mouseData.isDown(MouseButton.PRIMARY)) {
			Vector2D shootPosition = this.getPosition().clone();
			shootPosition.translate(this.getCollider().getWidth() / 2, this.getCollider().getHeight() / 2);
			this.weapon.shootIfPossible(shootPosition, this.calculateProjectileDirection(mouseData.getMousePosition()));
		}

	}

	public Vector2D calculateProjectileDirection(Vector2D mousePosition) {
		Vector2D movement = new Vector2D(this.getPosition().getX() + width / 2 - mousePosition.getX(),
				this.getPosition().getY() + height / 2 - mousePosition.getY());
		movement.normalize();
		movement.scale(-1);
		return movement;

	}
	
	public void hitByEnemy(Enemy<?> enemy) {
		if(timeSinceHit>hitDelay) {
			this.health-=enemy.getDamage();
			if(health<0) {
				health=0;
				
			}
			System.out.println(health);
			
			timeSinceHit=0;
		}
		
		
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	

}