package de.julian_und_basti.shootergame.entities.walls;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.drawing.Rectangle;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.entities.EntityType;
import de.julian_und_basti.shootergame.entities.UpdatableWeightTypeEntity;
import javafx.scene.paint.Color;

public class Wall extends UpdatableWeightTypeEntity<Rectangle, BoxCollider> {

	public Wall(Vector2D position, double width, double height) {
		super(position, null, null, EntityType.WALL);
		BoxCollider collider = new BoxCollider(position.clone(), width, height);
		Rectangle drawable = new Rectangle(position.clone(), width, height);
		drawable.setFillColor(Color.BLACK);
		drawable.setShouldFill(true);
		
		this.setDrawable(drawable);
		this.setCollider(collider);
		
		
	}

	@Override
	public void update(long deltaMillis) {
		//nothing
		
	}

}
