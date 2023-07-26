package de.julian_und_basti.shootergame.entities;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.controls.Entity;
import de.basti.game_framework.controls.Engine;
import de.basti.game_framework.controls.Updatable;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.math.Vector2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class CustomEntity<D extends Drawable, C extends BoxCollider> extends Entity<D, C, EntityType> {

	private Engine<CustomEntity<? extends Drawable, ? extends BoxCollider>> engine;
	private int weight = 1;

	public CustomEntity(Vector2D position, C collider, D drawable, EntityType type,
			Engine<CustomEntity<? extends Drawable, ? extends BoxCollider>> engine) {
		super(position, collider, drawable, type);
		this.engine = engine;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		super.draw(gc);
		
		
		
		
		// for debug purposes
		gc.setFill(Color.LIGHTGREEN);
		for(Vector2D v:this.getCollider().getVectors()) {
			
			gc.fillRect(v.getX()-1.5, v.getY()-1.5, 3, 3);
			
		}
		System.out.println(this.getCollider().getVectors().length);
	}

	public Engine<CustomEntity<? extends Drawable, ? extends BoxCollider>> getEngine() {
		return engine;
	}

	public void setEngine(Engine<CustomEntity<? extends Drawable, ? extends BoxCollider>> game) {
		this.engine = game;
	}
	

}
