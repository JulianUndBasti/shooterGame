package de.julian_und_basti.shootergame.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.basti.game_framework.controls.Engine;
import de.basti.game_framework.controls.Entity;
import de.basti.game_framework.controls.Updatable;
import de.basti.game_framework.controls.Updater;
import de.basti.game_framework.drawing.DrawingLayer;
import de.basti.game_framework.drawing.Sprite;
import de.basti.game_framework.drawing.Text;
import de.basti.game_framework.input.KeyInputListenerData;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.Images;
import de.julian_und_basti.shootergame.entities.CustomEntity;
import de.julian_und_basti.shootergame.entities.enemies.Enemy;
import de.julian_und_basti.shootergame.entities.enemies.WalkerEnemy;
import de.julian_und_basti.shootergame.entities.player.Player;
import de.julian_und_basti.shootergame.entities.player_projectiles.RocketPlayerProjectile;
import de.julian_und_basti.shootergame.weapons.RocketLauncher;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class Game {
	
	private Engine<CustomEntity<?, ?>> engine;
	
	private Player player;
	KeyInputListenerData keyData;
	
	private Text healthText = new Text(new Vector2D(10, 10), "");
	{
		healthText.setFont(Font.font("Calibri", FontWeight.BOLD, FontPosture.REGULAR, 36));
	}
	
	private Text fpsText = new Text(new Vector2D(750, 10), "");
	{
		fpsText.setFont(Font.font("Calibri", FontWeight.BOLD, FontPosture.REGULAR, 36));
	}


	private Sprite backgroundSprite = new Sprite(new Vector2D(), Images.images().background);

	private Background background;

	private int enemyDelay = 1000;
	private int minEnemyDelay = 300;

	private Updatable enemySpawner = new Updatable() {

		Random random = new Random();

		private int timeSinceLastSpawn = enemyDelay;

		@Override
		public void update(long deltaMillis) {
			timeSinceLastSpawn += deltaMillis;
			if (timeSinceLastSpawn < enemyDelay)
				return;

			timeSinceLastSpawn = 0;
			if (enemyDelay > minEnemyDelay) {
				enemyDelay = enemyDelay - 10;
			} else {
				enemyDelay = minEnemyDelay;
			}

			Vector2D enemyPos = new Vector2D(random.nextDouble() * 600 + 200, random.nextDouble() * 600 + 200);

			if (random.nextBoolean()) {
				enemyPos.setX(enemyPos.getX() * -1);
			}
			if (random.nextBoolean()) {
				enemyPos.setY(enemyPos.getY() * -1);
			}

			enemyPos.translate(player.getPosition());

			Enemy<?> enemy = new WalkerEnemy(enemyPos, player, engine);
			engine.addEntity(DrawingLayer.MIDDLE, enemy);

		}
	};

	
	private Updatable healthUpdate = new Updatable() {

		@Override
		public void update(long deltaMillis) {
			healthText.setText(player.getHealth() + "");

			if (player.getHealth() <= 0) {
				gameEnd();
			}
		}
	};
	
	private Updatable fpsUpdate = new Updatable() {

		@Override
		public void update(long deltaMillis) {
			fpsText.setText(""+(int)(1000/deltaMillis));
		}
	};
	
	
	
	private Updatable escapeUpdate = new Updatable() {
		
		
		
		@Override
		public void update(long deltaMillis) {
			if(keyData.isDown(KeyCode.ESCAPE)) {
				gameEnd();
				
			}
			
		}

	};
	
	private Updater generalUpdate = new Updater();
	{
		generalUpdate.add(healthUpdate);
		generalUpdate.add(enemySpawner);
		generalUpdate.add(escapeUpdate);
		generalUpdate.add(fpsUpdate);
		

	}
	
	private Runnable onGameEnd = ()->{};
	
	public Game(Engine<CustomEntity<?,?>> engine) {
		this.engine = engine;
		this.player = new Player(new Vector2D(engine.getWidth() / 2, engine.getHeight() / 2),
				new RocketLauncher(RocketPlayerProjectile::new, engine), engine);
		
		keyData = this.engine.getInputData().getKeyData();
		
		background = new Background(backgroundSprite, player);
		
		
	}
	

	private void gameEnd() {
		this.onGameEnd.run();
		
	}
	
	public void addToEngine() {
		player.setHealth(100);
		player.setPosition(new Vector2D(0, 0));
		
		this.engine.addCollisionHandler(CollisionHandling.handler);
		this.engine.addEntity(DrawingLayer.BACK_MIDDLE, player);
		this.engine.addDrawable(DrawingLayer.BACKGROUND, background);
		this.engine.addDrawable(DrawingLayer.ABSOLUTE, healthText);
		this.engine.addDrawable(DrawingLayer.ABSOLUTE, fpsText);
		
		this.engine.addUpdatable(generalUpdate);
		this.engine.stickCameraTo(player);
		
		
	}


	public void setOnGameEnd(Runnable onGameEnd) {
		this.onGameEnd = onGameEnd;
	}
	
	
	
}
