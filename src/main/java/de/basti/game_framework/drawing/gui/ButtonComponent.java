package de.basti.game_framework.drawing.gui;

import java.util.ArrayList;
import java.util.List;

import de.basti.game_framework.math.Vector2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class ButtonComponent extends TextComponent {

	private List<ActionListener> actionListeners = new ArrayList<>();

	private boolean isClickingOnThisButton = false;

	private Paint noClickColor = Color.BLACK;
	private Paint noClickBorderColor = Color.BLACK;
	private Paint noClickBackgroundColor = Color.WHITE;

	private Paint clickColor = Color.DARKGRAY;
	private Paint clickBorderColor = Color.DARKGRAY;
	private Paint clickBackgroundColor = Color.WHITE;

	{
		this.setColor(noClickColor);
		this.setBackgroundColor(noClickBackgroundColor);
		this.setBorderColor(noClickBorderColor);
		this.setBorderWidth(4);
	}

	private MouseListener privateMouseListener = new MouseListener() {

		@Override
		public void onMouseReleased(MouseButton button, Vector2D position) {
			if (isClickingOnThisButton) {
				fireAction();
				isClickingOnThisButton = false;
				updateColors();
			}

		}

		@Override
		public void onMousePressed(MouseButton button, Vector2D position) {
			isClickingOnThisButton = true;
			updateColors();

		}

		@Override
		public void onMouseDown(MouseButton button, Vector2D position) {

		}

		@Override
		public void onMouseInside() {

		}

		@Override
		public void onMouseOutside() {
			isClickingOnThisButton = false;
			updateColors();

		}
	};
	{
		this.addMouseListener(privateMouseListener);

	}

	private void updateColors() {
		if (isClickingOnThisButton) {
			super.setColor(clickColor);
			super.setBorderColor(clickBorderColor);
			super.setBackgroundColor(clickBackgroundColor);

		} else {
			super.setColor(noClickColor);
			super.setBorderColor(noClickBorderColor);
			super.setBackgroundColor(noClickBackgroundColor);

		}
	}

	public ButtonComponent(Vector2D position, double width, double height) {
		super(position, width, height);

	}

	public void fireAction() {
		for (ActionListener al : this.actionListeners) {
			al.onAction();
		}
	}

	public boolean addActionListener(ActionListener al) {
		return actionListeners.add(al);
	}

	public boolean removeActionListener(ActionListener al) {
		return actionListeners.remove(al);
	}

	public Paint getColor() {
		return noClickColor;
	}

	public void setColor(Paint noClickColor) {
		this.noClickColor = noClickColor;
		updateColors();
	}

	public Paint getBorderColor() {
		return noClickBorderColor;
	}

	public void setBorderColor(Paint noClickBorderColor) {
		this.noClickBorderColor = noClickBorderColor;
		updateColors();
	}

	public Paint getBackgroundColor() {
		return noClickBackgroundColor;
	}

	public void setBackgroundColor(Paint noClickBackgroundColor) {
		this.noClickBackgroundColor = noClickBackgroundColor;
		
	}

	public Paint getClickColor() {
		return clickColor;
	}

	public void setClickColor(Paint clickColor) {
		this.clickColor = clickColor;
	}

	public Paint getClickBorderColor() {
		return clickBorderColor;
	}

	public void setClickBorderColor(Paint clickBorderColor) {
		this.clickBorderColor = clickBorderColor;
	}

	public Paint getClickBackgroundColor() {
		return clickBackgroundColor;
	}

	public void setClickBackgroundColor(Paint clickBackgroundColor) {
		this.clickBackgroundColor = clickBackgroundColor;
	}

	
	
	

}
