package de.julian_und_basti.shootergame.levels;

import java.util.ArrayList;
import java.util.List;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.controls.Game;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.drawing.DrawingLayer;
import de.julian_und_basti.shootergame.entities.CustomEntity;
import de.julian_und_basti.shootergame.entities.enemies.Enemy;
import de.julian_und_basti.shootergame.entities.player.Player;

public abstract class Level {

	private Game<CustomEntity<? extends Drawable, ? extends BoxCollider>> game;
	private Player player;
	private List<Wall> walls = new ArrayList<>();
	
	

	public Level(Player player,Game<CustomEntity<? extends Drawable, ? extends BoxCollider>> game) {
		this.player = player;
		this.game = game;
	}

	public void buildLevel() {
		this.game.addEntity(DrawingLayer.MIDDLE, player);

		for (Wall wall : this.walls) {
			this.game.addEntity(DrawingLayer.FORE_MIDDLE, wall);

		}

	}

	protected void addWall(Wall wall) {
		this.walls.add(wall);
	}

	protected void setPlayer(Player Player) {
		this.player = Player;
	}

	public Game<CustomEntity<? extends Drawable, ? extends BoxCollider>> getGame() {
		return game;
	}

	public void setGame(Game<CustomEntity<? extends Drawable, ? extends BoxCollider>> game) {
		this.game = game;
	}
	
	
	
	

}
