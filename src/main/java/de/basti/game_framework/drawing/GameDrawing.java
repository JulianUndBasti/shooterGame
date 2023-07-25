package de.basti.game_framework.drawing;


import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


import de.basti.game_framework.controls.Updatable;
import de.basti.game_framework.math.Vector2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Affine;

/**
 * Wrapper around {@code GraphicsContext} and {@code Drawable}, which draws all added {@code Drawable} onto the {@code GraphicsContext} with their {@code draw(GraphicsContext)} method.
 * The {@code Drawable}s have to be added together with a {@code DrawingLayer}, which determines the order in which they are drawn.
 *  
 * 
 * @see javafx.scene.canvas.GraphicsContext
 * @see javafx.scene.canvas.Canvas
 * @see Drawable
 * @see DrawingLayer
 * 
 * 
 */
public class GameDrawing implements Updatable{
	
	private GraphicsContext graphicsContext;
	
	private Vector2D cameraTransform = new Vector2D(0,0);
	
	
	private Set<Drawable> allDrawables = new HashSet<>();
	
	private Map<DrawingLayer,Set<Drawable>> drawingLayers = new HashMap<>();
	
	public GameDrawing(GraphicsContext graphicsContext) {
		super();
		this.graphicsContext = graphicsContext;
		this.drawingLayers.put(DrawingLayer.BACKGROUND,new HashSet<Drawable>());
		this.drawingLayers.put(DrawingLayer.BACK_MIDDLE,new HashSet<Drawable>());
		this.drawingLayers.put(DrawingLayer.MIDDLE,new HashSet<Drawable>());
		this.drawingLayers.put(DrawingLayer.FORE_MIDDLE,new HashSet<Drawable>());
		this.drawingLayers.put(DrawingLayer.FOREGROUND,new HashSet<Drawable>());
		this.drawingLayers.put(DrawingLayer.ABSOLUTE,new HashSet<Drawable>());
		
	}
	
	
	public boolean add(DrawingLayer layer, Drawable drawable) {
		if(this.allDrawables.add(drawable)) {
			this.drawingLayers.get(layer).add(drawable);
			return true;
		}
		return false;
	}
	
	public boolean remove(Drawable drawable) {
		if(this.allDrawables.remove(drawable)) {
			for(Set<Drawable> set:drawingLayers.values()) {
				if(set.remove(drawable))return true;
			}
		}
		return false;
	}

	@Override
	public void update(long deltaMillis) {
		graphicsContext.clearRect(0, 0, graphicsContext.getCanvas().getWidth(), graphicsContext.getCanvas().getHeight());
		this.graphicsContext.save();
		this.graphicsContext.setTransform(new Affine(Affine.translate(cameraTransform.getX(), cameraTransform.getY())));
		drawingLayers.get(DrawingLayer.BACKGROUND).stream().forEach((d)->d.draw(graphicsContext));
		drawingLayers.get(DrawingLayer.BACK_MIDDLE).stream().forEach((d)->d.draw(graphicsContext));
		drawingLayers.get(DrawingLayer.MIDDLE).stream().forEach((d)->d.draw(graphicsContext));
		drawingLayers.get(DrawingLayer.FORE_MIDDLE).stream().forEach((d)->d.draw(graphicsContext));
		drawingLayers.get(DrawingLayer.FOREGROUND).stream().forEach((d)->d.draw(graphicsContext));
		
		this.graphicsContext.restore();
		
		drawingLayers.get(DrawingLayer.ABSOLUTE).stream().forEach((d)->d.draw(graphicsContext));
		
		
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
		// TODO Auto-generated method stub
		
	}
	
	
	
}
