package de.julian_und_basti.shootergame.entities.player;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.controls.Engine;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.drawing.MultiDrawable;
import de.basti.game_framework.drawing.DrawableRectangle;
import de.basti.game_framework.drawing.Sprite;
import de.basti.game_framework.drawing.animation.AnimatedSprite;
import de.basti.game_framework.drawing.animation.AnimationFrame;
import de.basti.game_framework.input.KeyInputListenerData;
import de.basti.game_framework.input.MouseInputListenerData;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.entities.EntityType;
import de.julian_und_basti.shootergame.entities.enemies.Enemy;
import de.julian_und_basti.shootergame.Images;
import de.julian_und_basti.shootergame.Sounds;
import de.julian_und_basti.shootergame.entities.CustomEntity;
import de.julian_und_basti.shootergame.weapons.Weapon;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Player extends CustomEntity<MultiDrawable, BoxCollider> {

	private AnimatedSprite playerSprite;
	private Sprite weaponSprite;

	private double width = 48;
	private double height = 48;

	private double weaponHeight = 12;
	private double weaponWidth = 48;

	private static final double DEFAULT_SPEED = 0.4;
	private static final int DEFAULT_WEIGHT = 500;

	private double speed = DEFAULT_SPEED;

	private MouseInputListenerData mouseData;
	private KeyInputListenerData keyData;

	private Weapon weapon;

	private int health = 100;

	private int hitDelay = 1000;
	private int timeSinceHit = 1000;

	private double rotation = -90;

	public Player(Vector2D position, Weapon weapon,
			Engine<CustomEntity<? extends Drawable, ? extends BoxCollider>> engine) {
		super(position, null, null, EntityType.PLAYER, engine);

		this.weapon = weapon;

		AnimationFrame frame1 = new AnimationFrame(100, new Sprite(null, Images.instance().player));
		AnimationFrame frame2 = new AnimationFrame(100, new Sprite(null, Images.instance().player));

		playerSprite = new AnimatedSprite(position.clone(), frame1, frame2);

		playerSprite.setWidth(width);
		playerSprite.setHeight(height);

		weaponSprite = new Sprite(position.clone(), weapon.getImage());
		weaponSprite.setWidth(weaponWidth);
		weaponSprite.setHeight(weaponHeight);

		MultiDrawable drawable = new MultiDrawable(position.clone(), playerSprite, weaponSprite);

		this.setDrawable(drawable);

		this.setCollider(new BoxCollider(position.clone(), width * 0.9, height * 0.9));
		this.mouseData = this.getEngine().getInputData().getMouseData();
		this.keyData = this.getEngine().getInputData().getKeyData();

		this.setWeight(DEFAULT_WEIGHT);

	}

	private Vector2D movement = new Vector2D();

	@Override
	public void update(long deltaMillis) {
		playerSprite.update(deltaMillis);

		if (timeSinceHit <= hitDelay) {
			timeSinceHit += deltaMillis;
		}

		Vector2D mousePosition = mouseData.getRelativeMousePosition();

		Vector2D diff = mousePosition.scaled(-1);
		diff.translate(this.getPosition());

		double weaponRotation = Math.toDegrees(Math.atan2(diff.getY(), diff.getX()));
		this.weaponSprite.setRotation(weaponRotation);

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
		if (!(movement.getX() == 0 && movement.getY() == 0)) {
			rotation = Math.toDegrees(Math.atan2(movement.getY(), movement.getX()));
		}

		playerSprite.setRotation(rotation);

		movement.scale(deltaMillis * speed);

		if (movement.getX() != 0 || movement.getY() != 0) {
			this.translate(movement);
		}

		this.weapon.update(deltaMillis);
		// shoot logic
		if (mouseData.isDown(MouseButton.PRIMARY)) {
			Vector2D shootPosition = this.getPosition().clone();

			double yTranslation = -Math.sin(Math.toRadians(weaponRotation)) * weaponWidth / 2;
			double xTranslation = -Math.cos(Math.toRadians(weaponRotation)) * weaponWidth / 2;

			shootPosition.translate(xTranslation, yTranslation);

			this.weapon.shootIfPossible(shootPosition,
					this.calculateRelativeMousePosition(mouseData.getRelativeMousePosition()));
		}

	}

	public Vector2D calculateRelativeMousePosition(Vector2D mousePosition) {
		Vector2D direction = new Vector2D(mousePosition.getX() - this.getPosition().getX(),
				mousePosition.getY() - this.getPosition().getY());
		direction.normalize();
		return direction;

	}

	public void hitByEnemy(Enemy<?> enemy) {
		if (timeSinceHit > hitDelay) {

			this.getEngine().addTaskForEndOfUpdate(() -> {
				Sounds.instance().hurt.seek(Duration.ZERO);
				Sounds.instance().hurt.play();

			});

			this.health -= enemy.getDamage();
			if (health < 0) {
				health = 0;

			}

			timeSinceHit = 0;
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

	@Override
	public void collidedWith(CustomEntity<?, ?> other) {
		if (other.getType() == EntityType.ENEMY) {
			this.hitByEnemy((Enemy<?>) other);
		}

	}

}
