package de.basti.game_framework.drawing.gui;

import java.util.ArrayList;
import java.util.List;

import de.basti.game_framework.math.Vector2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

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
			this.setColor(clickColor);
			this.setBorderColor(clickBorderColor);
			this.setBackgroundColor(clickBackgroundColor);

		} else {
			this.setColor(noClickColor);
			this.setBorderColor(noClickBorderColor);
			this.setBackgroundColor(noClickBackgroundColor);

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

}
