package de.basti.game_framework.drawing.gui;

import java.util.ArrayList;
import java.util.List;

import de.basti.game_framework.controls.Updatable;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.input.InputListenerData;
import de.basti.game_framework.math.Vector2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

public abstract class Component implements Drawable {
	private Vector2D position;
	private double width;
	private double height;
	private List<MouseListener> mouseListeners = new ArrayList<>();
	private List<KeyListener> keyListeners = new ArrayList<>();

	public Component(Vector2D position, double width, double height) {
		this.position = position;
		this.width = width;
		this.height = height;
	}

	public boolean isInside(Vector2D v) {

		return (v.getX() >= this.position.getX() && v.getX() <= this.position.getX() + width)
				&& (v.getY() >= this.position.getY() && v.getY() <= this.position.getY() + height);

	}

	@Override
	public void translate(Vector2D vector) {
		this.position.translate(vector);

	}

	@Override
	public Vector2D getPosition() {
		return position;
	}

	public void setPosition(Vector2D position) {
		this.position = position;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public void fireMouseButtonDown(MouseButton button, Vector2D position) {
		for (MouseListener l : this.mouseListeners) {
			l.onMouseDown(button, position);
		}
	}

	public void fireMouseButtonPressed(MouseButton button, Vector2D position) {
		for (MouseListener l : this.mouseListeners) {
			l.onMousePressed(button, position);
		}
	}

	public void fireMouseButtonReleased(MouseButton button, Vector2D position) {
		for (MouseListener l : this.mouseListeners) {
			l.onMouseReleased(button, position);
		}
	}

	public void fireKeyDown(KeyCode code) {
		for (KeyListener l : this.keyListeners) {
			l.onKeyDown(code);
		}
	}

	public void fireKeyPressed(KeyCode code) {
		for (KeyListener l : this.keyListeners) {
			l.onKeyPressed(code);
		}
	}

	public void fireKeyReleased(KeyCode code) {
		for (KeyListener l : this.keyListeners) {
			l.onKeyReleased(code);
		}
	}
	
	public void addMouseListener(MouseListener l) {
		this.mouseListeners.add(l);
	}
	
	public boolean removeMouseListener(MouseListener l) {
		return this.mouseListeners.remove(l);
	}
	
	public void addKeyListener(KeyListener l) {
		this.keyListeners.add(l);
	}
	
	public boolean removeKeyListener(KeyListener l) {
		return this.keyListeners.remove(l);
	}
	

}
