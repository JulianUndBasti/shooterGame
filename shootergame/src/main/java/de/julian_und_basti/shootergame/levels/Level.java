package de.julian_und_basti.shootergame.levels;

import java.util.ArrayList;
import java.util.List;

import de.basti.game_framework.drawing.DrawingLayer;
import de.julian_und_basti.shootergame.Game;
import de.julian_und_basti.shootergame.entities.enemies.Enemy;
import de.julian_und_basti.shootergame.entities.player.Player;

public abstract class Level {

	private Player player;
	private List<Enemy<?>> enemies = new ArrayList<>();
	private List<Wall> walls = new ArrayList<>();

	public Level(Player player) {
		this.player = player;
	}

	public void buildLevel() {
		Game.addEntity(DrawingLayer.MIDDLE, player);

		for (Enemy<?> enemy : this.enemies) {
			Game.addEntity(DrawingLayer.BACK_MIDDLE, enemy);

		}

		for (Wall wall : this.walls) {
			Game.addEntity(DrawingLayer.FORE_MIDDLE, wall);

		}

	}

	protected void addWall(Wall wall) {
		this.walls.add(wall);
	}

	protected void addEnemy(Enemy<?> enemy) {
		this.enemies.add(enemy);
	}
	
	protected void setPlayer(Player Player) {
		this.player = Player;
	}
	
	

}
