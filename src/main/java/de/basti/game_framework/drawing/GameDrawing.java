package de.basti.game_framework.drawing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.basti.game_framework.controls.Updatable;
import de.basti.game_framework.math.Vector2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Affine;

/**
 * Wrapper around {@code GraphicsContext} and {@code Drawable}, which draws all
 * added {@code Drawable} onto the {@code GraphicsContext} with their
 * {@code draw(GraphicsContext)} method. The {@code Drawable}s have to be added
 * together with a {@code DrawingLayer}, which determines the order in which
 * they are drawn.
 * 
 * 
 * @see javafx.scene.canvas.GraphicsContext
 * @see javafx.scene.canvas.Canvas
 * @see Drawable
 * @see DrawingLayer
 * 
 * 
 */
public class GameDrawing implements Updatable {

	public static final int BACKGROUND = 100;
	public static final int BACK_MIDDLE = 200;
	public static final int MIDDLE = 300;
	public static final int FORE_MIDDLE = 400;
	public static final int FOREGROUND = 500;

	private GraphicsContext graphicsContext;

	private Vector2D cameraTransform = new Vector2D(0, 0);

	private List<Integer> priorities = new ArrayList<>();
	private List<Set<Drawable>> drawingLayers = new ArrayList<>();
	
	private Set<Drawable> absoluteLayer = new HashSet<Drawable>();

	public GameDrawing(GraphicsContext graphicsContext) {
		super();
		this.graphicsContext = graphicsContext;

	}

	public boolean addRelative(int layer, Drawable drawable) {
		int index = Collections.binarySearch(priorities, layer);// negative if not found

		if (index < 0) {
			index = -index - 1; // insertion point
			this.priorities.add(index, layer);
			this.drawingLayers.add(index, new HashSet<Drawable>());
		}

		return this.drawingLayers.get(index).add(drawable);

	}
	
	public boolean addAbsolute(Drawable drawable) {
		return absoluteLayer.add(drawable);
	}
	
	

	public boolean remove(Drawable drawable) {

		for (Set<Drawable> set : drawingLayers) {
			if (set.remove(drawable)) {
				return true;
			}
				
		}
		return absoluteLayer.remove(drawable);
	}

	@Override
	public void update(long deltaMillis) {
		graphicsContext.clearRect(0, 0, graphicsContext.getCanvas().getWidth(),
				graphicsContext.getCanvas().getHeight());
		this.graphicsContext.save();
		this.graphicsContext.setTransform(new Affine(Affine.translate(cameraTransform.getX(), cameraTransform.getY())));
		
		this.drawingLayers.forEach(t -> t.forEach(d -> d.draw(graphicsContext)));

		this.graphicsContext.restore();

		this.absoluteLayer.forEach(d -> d.draw(graphicsContext));

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

	public void removeAll() {
		this.priorities.clear();
		this.drawingLayers.clear();
		this.absoluteLayer.clear();

	}

}
