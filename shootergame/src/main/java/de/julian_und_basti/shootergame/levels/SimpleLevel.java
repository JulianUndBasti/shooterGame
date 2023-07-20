package de.julian_und_basti.shootergame.levels;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.controls.Game;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.entities.CustomEntity;
import de.julian_und_basti.shootergame.entities.enemies.WalkerEnemy;
import de.julian_und_basti.shootergame.entities.player.Player;

public class SimpleLevel extends Level {

	public SimpleLevel(Player player,Game<CustomEntity<? extends Drawable, ? extends BoxCollider>> game) {
		super(player,game);
		player.setPosition(new Vector2D(100,100));
		
		int wallWidth = 50;
		
		this.addWall(new Wall(new Vector2D(-wallWidth,-wallWidth), 800+wallWidth, wallWidth,getGame()));
		this.addWall(new Wall(new Vector2D(-wallWidth,0), wallWidth, 600,getGame()));
		this.addWall(new Wall(new Vector2D(800, -wallWidth), wallWidth, 600+wallWidth*2,getGame()));
		this.addWall(new Wall(new Vector2D(-wallWidth,600), 800+wallWidth, wallWidth,getGame()));
		
		this.addEnemy(new WalkerEnemy(new Vector2D(600,500), player,getGame()));
		this.addEnemy(new WalkerEnemy(new Vector2D(630,470), player,getGame()));
		this.addEnemy(new WalkerEnemy(new Vector2D(650,530), player,getGame()));
		
		
		
	
	}
	
}
