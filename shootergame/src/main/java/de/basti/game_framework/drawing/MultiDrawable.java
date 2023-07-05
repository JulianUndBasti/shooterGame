package de.basti.game_framework.drawing;

import de.basti.game_framework.math.Vector2D;
import javafx.scene.canvas.GraphicsContext;
 
/**
 * Implementation of {@code Drawable} which allows multiple other {@code Drawable} to be added.
 * On {@code draw(GraphicsContext)}, {@code draw(GraphicsContext)} is called on every given {@code Drawable}.
 * 
 * @see Drawable
 */
public class MultiDrawable implements Drawable{
	private Vector2D position;
	private Drawable[] drawables;
	
	public MultiDrawable(Vector2D position,Drawable... drawables) {
		this.position = position;
		this.drawables = drawables;
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		for(Drawable d:drawables) {
			d.draw(gc);
		}
		
	}

	@Override
	public Vector2D getPosition() {
		return position;
	}

	@Override
	public void translate(Vector2D vector) {
		position.translate(vector);
		for(Drawable d:drawables) {
			d.translate(vector);
		}
	}
	
}
