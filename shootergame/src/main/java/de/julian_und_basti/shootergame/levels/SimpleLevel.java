package de.julian_und_basti.shootergame.levels;

import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.entities.enemies.WalkerEnemy;
import de.julian_und_basti.shootergame.entities.player.Player;

public class SimpleLevel extends Level {

	public SimpleLevel(Player player) {
		super(player);
		player.setPosition(new Vector2D(100,100));
		
		int wallWidth = 50;
		
		this.addWall(new Wall(new Vector2D(0,-wallWidth), 800, wallWidth));
		this.addWall(new Wall(new Vector2D(-wallWidth,0), wallWidth, 600));
		this.addWall(new Wall(new Vector2D(800, -wallWidth), wallWidth, 600+wallWidth));
		this.addWall(new Wall(new Vector2D(0,600), 800, wallWidth));
		
		this.addEnemy(new WalkerEnemy(new Vector2D(600,500), player));
		this.addEnemy(new WalkerEnemy(new Vector2D(630,470), player));
		this.addEnemy(new WalkerEnemy(new Vector2D(650,530), player));
		
		
		
	
	}
	
}
