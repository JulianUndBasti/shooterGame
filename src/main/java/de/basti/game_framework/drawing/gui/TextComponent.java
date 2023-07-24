package de.basti.game_framework.drawing.gui;

import de.basti.game_framework.drawing.Text;
import de.basti.game_framework.math.Vector2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class TextComponent extends Component{

	private String text = "";
	private Font font = Font.font("Calibri", FontWeight.NORMAL, FontPosture.REGULAR, 12);
	private Paint color = Color.BLACK;
	private Paint borderColor = Color.BLACK;
	private int borderWidth = 0;
	private Paint backgroundColor = Color.WHITE;
	
	
	
	
	public TextComponent(Vector2D position, double width, double height) {
		super(position, width, height);
		
	}

	@Override
	public void draw(GraphicsContext gc) {
		double x = this.getPosition().getX();
		double y = this.getPosition().getY();
		double w = this.getWidth();
		double h = this.getHeight();
		
		gc.setFill(this.backgroundColor);
		gc.fillRect(x, y, w, h);
		
		
		gc.setStroke(borderColor);
		gc.setLineWidth(borderWidth);
		gc.strokeRect(x, y, w, h);
		
		gc.setFill(color);
		gc.fillText(text, x, y+h, w);
	
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public Paint getColor() {
		return color;
	}

	public void setColor(Paint color) {
		this.color = color;
	}

	public Paint getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(Paint borderColor) {
		this.borderColor = borderColor;
	}

	public int getBorderWidth() {
		return borderWidth;
	}

	public void setBorderWidth(int borderWidth) {
		this.borderWidth = borderWidth;
	}

	public Paint getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Paint backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	
	


}
