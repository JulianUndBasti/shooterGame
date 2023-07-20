package de.basti.game_framework.input;

import java.util.HashSet;
import java.util.Set;

import de.basti.game_framework.controls.Updatable;
import de.basti.game_framework.math.Vector2D;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class MouseInputListenerData implements Updatable {
	private Set<MouseButton> buttonsDown = new HashSet<>();
	private Set<MouseButton> buttonsPressed = new HashSet<>();
	private Set<MouseButton> buttonsReleased = new HashSet<>();
	private Vector2D mousePosition = new Vector2D(0, 0);

	private Scene currentScene = new Scene(new Pane());// Scene is placeholder, so no null-checks are required

	private Vector2D cameraTransform = new Vector2D(0, 0);

	private EventHandler<MouseEvent> onMousePressed = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent event) {
			MouseButton b = event.getButton();
			buttonsDown.add(b);
			buttonsPressed.add(b);
		}

	};

	private EventHandler<MouseEvent> onMouseReleased = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent event) {

			MouseButton b = event.getButton();
			buttonsDown.remove(b);
			buttonsReleased.add(b);

		}

	};

	private EventHandler<MouseEvent> onMouseMoved = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent event) {

			mousePosition.setX(event.getX());
			mousePosition.setY(event.getY());

		}

	};

	public void listenTo(Scene newScene) {
		currentScene.removeEventHandler(MouseEvent.MOUSE_PRESSED, onMousePressed);
		currentScene.removeEventHandler(MouseEvent.MOUSE_RELEASED, onMouseReleased);
		currentScene.removeEventHandler(MouseEvent.MOUSE_RELEASED, onMouseMoved);
		currentScene.removeEventHandler(MouseEvent.MOUSE_DRAGGED, onMouseMoved);

		currentScene = newScene;
		currentScene.addEventHandler(MouseEvent.MOUSE_PRESSED, onMousePressed);
		currentScene.addEventHandler(MouseEvent.MOUSE_RELEASED, onMouseReleased);
		currentScene.addEventHandler(MouseEvent.MOUSE_MOVED, onMouseMoved);
		currentScene.addEventHandler(MouseEvent.MOUSE_DRAGGED, onMouseMoved);
	}

	public boolean isDown(MouseButton b) {
		return this.buttonsDown.contains(b);
	}

	public boolean isPressed(MouseButton b) {
		return this.buttonsPressed.contains(b);
	}

	public boolean isReleased(MouseButton b) {
		return this.buttonsReleased.contains(b);
	}

	public Vector2D getMousePosition() {
		return this.mousePosition.translated(this.cameraTransform.scaled(-1));
	}

	public void update(long deltaMillis) {
		this.buttonsPressed = new HashSet<>();
		this.buttonsReleased = new HashSet<>();
	}

	public Vector2D getTransform() {
		return cameraTransform;
	}

	public void translateTransform(Vector2D translation) {
		this.cameraTransform.translate(translation);

	}

	public void setTransform(Vector2D cameraTransform) {
		this.cameraTransform = cameraTransform;

	}

}
