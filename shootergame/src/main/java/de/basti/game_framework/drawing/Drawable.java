package de.basti.game_framework.drawing;

import de.basti.game_framework.math.Vector2D;
import javafx.scene.canvas.GraphicsContext;

/**
 * Thing which is able to be drawn onto a Javafx GraphicsContext.
 * @see Shape
 * @see MultiDrawable
 * @see Sprite
 */
public interface Drawable {
	public void draw(GraphicsContext gc);
	public Vector2D getPosition();
	public void translate(Vector2D vector);
}
