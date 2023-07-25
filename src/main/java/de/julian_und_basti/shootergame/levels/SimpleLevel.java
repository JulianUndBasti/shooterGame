package de.julian_und_basti.shootergame.levels;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.controls.Engine;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.entities.CustomEntity;
import de.julian_und_basti.shootergame.entities.enemies.WalkerEnemy;
import de.julian_und_basti.shootergame.entities.player.Player;

public class SimpleLevel extends Level {
	
	private double width = 5000;
	private double height = 5000;
	
	
	private Vector2D playerPosition = new Vector2D(width/2,height/2);
	
	private double wallWidth = 100;
	
	public SimpleLevel(Player player,Engine<CustomEntity<? extends Drawable, ? extends BoxCollider>> game) {
		super(player,game);
		player.setPosition(playerPosition);
		
		this.addWall(new Wall(new Vector2D(-wallWidth,-wallWidth), width+wallWidth, wallWidth,getEngine()));
		this.addWall(new Wall(new Vector2D(-wallWidth,0), wallWidth, height,getEngine()));
		this.addWall(new Wall(new Vector2D(width, -wallWidth), wallWidth, height+wallWidth*2,getEngine()));
		this.addWall(new Wall(new Vector2D(-wallWidth,height), width+wallWidth, wallWidth,getEngine()));

		
		
	
	}
	
}
