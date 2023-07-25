package de.basti.game_framework.drawing.gui;

import java.util.ArrayList;
import java.util.List;

import de.basti.game_framework.controls.Updatable;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.input.InputListenerData;
import de.basti.game_framework.math.Vector2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

public class GUI implements Drawable, Updatable {
	private Vector2D position;
	private InputListenerData inputData = new InputListenerData(new Scene(new Group()));

	private List<Component> components = new ArrayList<Component>();

	public GUI(Vector2D position, InputListenerData inputData) {
		super();
		this.position = position;
		this.inputData = inputData;

	}

	private static interface ComponentUser {
		public void use(Component c);
	}

	private List<ComponentUser> events;

	@Override
	public void update(long deltaMillis) {
		events = new ArrayList<>();
		addKeyEvents();
		addMouseEvents();
		for (Component comp : this.components) {
			for (ComponentUser user : events) {
				user.use(comp);
			}
		}

	}

	private void addKeyEvents() {
		for (KeyCode code : this.inputData.getKeyData().getKeysDown()) {
			events.add(c -> c.fireKeyDown(code));
		}

		for (KeyCode code : this.inputData.getKeyData().getKeysPressed()) {
			events.add(c -> c.fireKeyPressed(code));
		}

		for (KeyCode code : this.inputData.getKeyData().getKeysReleased()) {
			events.add(c -> c.fireKeyReleased(code));
		}

	}

	private void addMouseEvents() {
		Vector2D mPos = this.inputData.getMouseData().getAbsoluteMousePosition();
		for (MouseButton button : this.inputData.getMouseData().getButtonsDown()) {
			events.add(c -> {
				if (!c.isInside(mPos))
					return;
				c.fireMouseButtonDown(button, mPos);
			});
		}

		for (MouseButton button : this.inputData.getMouseData().getButtonsPressed()) {
			events.add(c -> {
				if (!c.isInside(mPos))
					return;
				c.fireMouseButtonPressed(button, mPos);
			});
		}

		for (MouseButton button : this.inputData.getMouseData().getButtonsReleased()) {
			events.add(c -> {
				if (!c.isInside(mPos))
					return;
				c.fireMouseButtonReleased(button, mPos);
			});
		}
		events.add(c -> {
			if (c.isInside(mPos)) {
				c.fireMouseInside();
			} else {

				c.fireMouseOutside();
			}
		});
	}

	@Override
	public void draw(GraphicsContext gc) {
		for (Component c : this.components) {
			c.draw(gc);
		}
	}

	@Override
	public Vector2D getPosition() {
		return this.position;
	}

	@Override
	public void translate(Vector2D vector) {
		this.position.translate(vector);
	}

	public InputListenerData getInputData() {
		return inputData;
	}

	public void setInputData(InputListenerData inputData) {
		this.inputData = inputData;
	}

	public void setPosition(Vector2D position) {
		this.position = position;
	}

	public int size() {
		return components.size();
	}

	public boolean remove(Component c) {
		return components.remove(c);
	}

	public void add(Component c) {
		this.components.add(c);
	}

}
