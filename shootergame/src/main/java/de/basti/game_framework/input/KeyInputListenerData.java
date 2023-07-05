package de.basti.game_framework.input;

import java.util.HashSet;
import java.util.Set;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class KeyInputListenerData{
	private Set<KeyCode> keysDown = new HashSet<>();
	private Set<KeyCode> keysPressed = new HashSet<>();
	private Set<KeyCode> keysReleased = new HashSet<>();
	
	
	private Scene currentScene = new Scene(new Pane());//Scene is placeholder, so no null-checks are required
	
	private EventHandler<KeyEvent> onKeyPressed = new EventHandler<KeyEvent>() {

		@Override
		public void handle(KeyEvent event) {
			KeyCode k = event.getCode();
			keysDown.add(k);
			keysPressed.add(k);
		}
		
	};
	
	private EventHandler<KeyEvent> onKeyReleased = new EventHandler<KeyEvent>() {

		@Override
		public void handle(KeyEvent event) {
			KeyCode k = event.getCode();
			keysDown.remove(k);
			keysReleased.add(k);
			
		}
		
	};
	
	
	public void listenTo(Scene newScene) {
		currentScene.removeEventHandler(KeyEvent.KEY_PRESSED, onKeyPressed);
		currentScene.removeEventHandler(KeyEvent.KEY_RELEASED, onKeyReleased);
		currentScene = newScene;
		currentScene.addEventHandler(KeyEvent.KEY_PRESSED, onKeyPressed);
		currentScene.addEventHandler(KeyEvent.KEY_RELEASED, onKeyReleased);
	}
	
	
	
	
	public boolean isDown(KeyCode k) {
		return this.keysDown.contains(k);
	}
	
	public boolean isPressed(KeyCode k) {
		return this.keysPressed.contains(k);
	}
	
	public boolean isReleased(KeyCode k) {
		return this.keysReleased.contains(k);
	}
	
	public void pressesAndReleasesConsumed() {
		this.keysPressed = new HashSet<>();
		this.keysReleased = new HashSet<>();
	}	
}
