package de.basti.game_framework.drawing;

import java.util.logging.Level;
import java.util.logging.Logger;

import de.basti.game_framework.math.Vector2D;
import javafx.scene.canvas.GraphicsContext;
 
/**
 * Implementation of {@code Drawable} which allows multiple other {@code Drawable} to be added.
 * On {@code draw(GraphicsContext)}, {@code draw(GraphicsContext)} is called on every given {@code Drawable}.
 * 
 * @see Drawable
 */
public class MultiDrawable implements Drawable{
	private static final Logger LOGGER = Logger.getLogger(MultiDrawable.class.getName());
	static {
		LOGGER.setLevel(Level.INFO);
	}
	
	private Vector2D position;
	private Drawable[] drawables;
	
	public MultiDrawable(Vector2D position,Drawable... drawables) {
		LOGGER.finer("Construction " + this);
		this.position = position;
		this.drawables = drawables;
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		LOGGER.finest("draw() " + this + " to " + gc);
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
		LOGGER.finer("translate() of" + this + " by " + vector);
		position.translate(vector);
		for(Drawable d:drawables) {
			d.translate(vector);
		}
	}
	
}
