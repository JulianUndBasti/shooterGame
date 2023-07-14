package de.julian_und_basti.shootergame;

import java.util.Vector;

import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.drawing.Sprite;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.entities.player.Player;
import javafx.scene.canvas.GraphicsContext;

public class Background implements Drawable{
	
	private Sprite sprite;
	
	private Player playerToFollow;

	
	
	public Background(Sprite sprite, Player playerToFollow) {
		super();
		this.sprite = sprite;
		this.playerToFollow = playerToFollow;

	}

	@Override
	public void draw(GraphicsContext gc) {
		Vector2D pos = playerToFollow.getPosition();
		int middleIndexX = (int)Math.floor(pos.getX()/sprite.getWidth());
		int middleIndexY = (int)Math.floor(pos.getY()/sprite.getHeight());;
		
		
		drawAround(gc,middleIndexX,middleIndexY);
		

	}
	
	private void drawAround(GraphicsContext gc,int x, int y){
		drawAt(gc, x-1,y-1);
		drawAt(gc, x,y-1);
		drawAt(gc, x+1,y-1);
		
		drawAt(gc, x-1,y);
		drawAt(gc, x,y);
		drawAt(gc, x+1,y);
		
		drawAt(gc, x-1,y+1);
		drawAt(gc, x,y+1);
		drawAt(gc, x+1,y+1);
		

		
	}
	
	private void drawAt(GraphicsContext gc,int x, int y) {
		
		this.sprite.setPosition(new Vector2D(x*sprite.getWidth(),y*sprite.getHeight()));
		this.sprite.draw(gc);
	}

	@Override
	public Vector2D getPosition() {
		throw new RuntimeException("getPosition() not supported!");
	}

	@Override
	public void translate(Vector2D vector) {
		throw new RuntimeException("translate() not supported!");
		
	}

	public Player getPlayerToFollow() {
		return playerToFollow;
	}

	public void setPlayerToFollow(Player playerToFollow) {
		this.playerToFollow = playerToFollow;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	
	
	
	
	
	
}
