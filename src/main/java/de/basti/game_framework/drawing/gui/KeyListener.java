package de.basti.game_framework.drawing.gui;

import javafx.scene.input.KeyCode;

public interface KeyListener {
	public void onKeyDown(KeyCode code);
	public void onKeyPressed(KeyCode code);
	public void onKeyReleased(KeyCode code);
}
