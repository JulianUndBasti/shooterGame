package de.basti.game_framework.drawing.gui;

import de.basti.game_framework.math.Vector2D;
import javafx.scene.input.MouseButton;

public interface MouseListener {
	public void onMouseDown(MouseButton button, Vector2D position);
	public void onMousePressed(MouseButton button, Vector2D position);
	public void onMouseReleased(MouseButton button, Vector2D position);
	public void onMouseInside();
	public void onMouseOutside();
	
	
}
