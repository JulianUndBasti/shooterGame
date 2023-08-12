package de.julian_und_basti.shootergame.levels;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.controls.Engine;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.drawing.DrawableRectangle;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.entities.EntityType;
import de.julian_und_basti.shootergame.entities.CustomEntity;
import javafx.scene.paint.Color;

public class Wall extends CustomEntity<DrawableRectangle, BoxCollider> {

	public Wall(Vector2D position, double width, double height,Engine<CustomEntity<? extends Drawable, ? extends BoxCollider>> game) {
		super(position, null, null, EntityType.WALL,game);
		BoxCollider collider = new BoxCollider(position.clone(), width, height);
		DrawableRectangle drawable = new DrawableRectangle(position.clone(), width, height);
		drawable.setFillColor(Color.BLACK);
		drawable.setShouldFill(true);
		
		this.setDrawable(drawable);
		this.setCollider(collider);
		
		
	}

	@Override
	public void update(long deltaMillis) {
		//nothing
		
	}

	

	@Override
	public void collidedWith(CustomEntity<?, ?> other) {
		// TODO Auto-generated method stub
		
	}

}
