package de.julian_und_basti.shootergame.levels;

import java.util.ArrayList;
import java.util.List;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.controls.Engine;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.drawing.GameDrawing;
import de.julian_und_basti.shootergame.entities.CustomEntity;
import de.julian_und_basti.shootergame.entities.enemies.Enemy;
import de.julian_und_basti.shootergame.entities.player.Player;

public abstract class Level {

	private Engine<CustomEntity<? extends Drawable, ? extends BoxCollider>> engine;
	private Player player;
	private List<Wall> walls = new ArrayList<>();
	
	

	public Level(Player player,Engine<CustomEntity<? extends Drawable, ? extends BoxCollider>> game) {
		this.player = player;
		this.engine = game;
	}

	public void buildLevel() {
		this.engine.addEntity(GameDrawing.MIDDLE, player);

		for (Wall wall : this.walls) {
			this.engine.addEntity(GameDrawing.FORE_MIDDLE, wall);

		}

	}

	protected void addWall(Wall wall) {
		this.walls.add(wall);
	}

	protected void setPlayer(Player Player) {
		this.player = Player;
	}

	public Engine<CustomEntity<? extends Drawable, ? extends BoxCollider>> getEngine() {
		return engine;
	}

	public void setEngine(Engine<CustomEntity<? extends Drawable, ? extends BoxCollider>> engine) {
		this.engine = engine;
	}
	
	
	
	

}
