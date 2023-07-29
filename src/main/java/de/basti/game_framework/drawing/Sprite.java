package de.basti.game_framework.drawing;

import java.util.logging.Level;
import java.util.logging.Logger;

import de.basti.game_framework.math.Vector2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 * Implementation of {@code Drawable} which represents a picture. The picture to
 * be drawn must be given as a {@code import javafx.scene.image.Image}.
 * 
 * @see Drawable
 * @see javafx.scene.image.Image
 */
public class Sprite implements Drawable {
	private static final Logger LOGGER = Logger.getLogger(Sprite.class.getName());
	static {
		LOGGER.setLevel(Level.INFO);
	}

	private Vector2D position;

	private ImageView imageView;
	private Image image;

	private SnapshotParameters params = new SnapshotParameters();

	private boolean spriteChanged = false;

	public Sprite(Vector2D position, Image image) {
		super();
		LOGGER.finer("Constructing " + this + " from " + image);
		this.position = position;
		this.imageView = new ImageView(image);
		this.image = image;
		imageView.setSmooth(false);

		this.imageView.setFitWidth(image.getWidth());
		this.imageView.setFitHeight(image.getHeight());

		params.setFill(Color.TRANSPARENT);

	}

	@Override
	public void draw(GraphicsContext gc) {
		LOGGER.finest("draw() " + this + " to " + gc);
		if (spriteChanged) {
			LOGGER.finer("Sprite data of " + this + " changed");
			this.updateImage();
			spriteChanged = false;
		}
		gc.drawImage(image, position.getX() - image.getWidth() / 2, position.getY() - image.getHeight() / 2);

	}

	@Override
	public Vector2D getPosition() {
		return position;
	}

	@Override
	public void translate(Vector2D vector) {
		LOGGER.finer("translate() of " + this + " by " + vector);
		this.position.translate(vector);
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		LOGGER.finer("setImage() of " + this + " to " + image);
		this.imageView = new ImageView(image);
		this.spriteChanged = true;
	}

	public void setPosition(Vector2D position) {
		LOGGER.finer("setPosition() of " + this + " to " + position);
		this.position = position;
	}

	public double getWidth() {
		return imageView.getFitWidth();

	}

	public void setWidth(double width) {
		LOGGER.finer("setWidth() of " + this + " to " + width);
		imageView.setFitWidth(width);
		this.spriteChanged = true;
	}

	public double getHeight() {
		return imageView.getFitHeight();
	}

	public void setHeight(double height) {
		LOGGER.finer("setHeight() of " + this + " to " + height);
		imageView.setFitHeight(height);
		this.spriteChanged = true;
	}

	public double getRotation() {
		return imageView.getRotate();
	}

	public void setRotation(double rotation) {
		LOGGER.finer("setRotation() of " + this + " to " + rotation);
		imageView.setRotate(rotation);
		this.spriteChanged = true;
	}

	private void updateImage() {
		LOGGER.finer("Making new ImageView.snapshot() for " + this);
		this.image = imageView.snapshot(params, null);
	}

}
