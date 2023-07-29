package de.julian_und_basti.shootergame.weapons;

import de.basti.game_framework.collision.BoxCollider;
import de.basti.game_framework.controls.Engine;
import de.basti.game_framework.drawing.Drawable;
import de.basti.game_framework.drawing.DrawingLayer;
import de.basti.game_framework.math.Vector2D;
import de.julian_und_basti.shootergame.Images;
import de.julian_und_basti.shootergame.entities.CustomEntity;
import de.julian_und_basti.shootergame.entities.player_projectiles.PlayerProjectile;
import de.julian_und_basti.shootergame.entities.player_projectiles.PlayerProjectileFactory;
import de.julian_und_basti.shootergame.entities.player_projectiles.PlayerProjectileStats;
import javafx.scene.image.Image;

public class MachineGun extends Weapon{
	
	
	
	public static final PlayerProjectileStats DEFAULT_STATS = new PlayerProjectileStats(5, 1.2);
	
	
	
	public MachineGun(PlayerProjectileFactory factory,Engine<CustomEntity<? extends Drawable, ? extends BoxCollider>> game) {
		super(60,factory,DEFAULT_STATS,game);
		this.setRadiansSpread(Math.toRadians(8));
		
	}
	
	public MachineGun(PlayerProjectileFactory factory, PlayerProjectileStats stats,Engine<CustomEntity<? extends Drawable, ? extends BoxCollider>> game) {
		super(60, factory, stats,game);
		this.setRadiansSpread(Math.toRadians(8));

	}


	@Override
	public Image getImage() {
		return Images.instance().background;
	}
	

}
