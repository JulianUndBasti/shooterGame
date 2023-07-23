package de.basti.game_framework.math;

import java.util.Objects;

public class Vector2D {
	private double x = 0d;
	private double y = 0d;
	
	public Vector2D() {
		
	}
	
	public Vector2D(double x,double y) {
		this.x = x;
		this.y = y;
	}

	public void normalize() {
		double length = this.length();
		if(length==0)return;
		this.x = this.x/length;
		this.y = this.y/length;
		
	}
	
	public Vector2D normalized() {
		Vector2D v = this.clone();
		v.normalize();
		return v;
	}
	
	public void translate(Vector2D other) {
		this.translate(other.x,other.y);
	}
	
	public Vector2D translated(Vector2D other) {
		Vector2D v = this.clone();
		v.translate(other);
		return v;
	}
	
	public void translate(double x,double y) {
		this.x+=x;
		this.y+=y;
	}
	
	
	public double length() {
		return Math.sqrt((x*x)+(y*y));
	}
	
	public void scale(double factor) {
		this.x*=factor;
		this.y*=factor;
		
	}
	
	public Vector2D scaled(double factor) {
		Vector2D v = this.clone();
		v.scale(factor);
		return v;
	}
	
	
	public Vector2D clone() {
		return new Vector2D(x,y);
	}
	
	public void set(double x, double y) {
		this.setX(x);
		this.setY(y);
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector2D other = (Vector2D) obj;
		return Double.doubleToLongBits(x) == Double.doubleToLongBits(other.x)
				&& Double.doubleToLongBits(y) == Double.doubleToLongBits(other.y);
	}

	@Override
	public String toString() {
		return "Vector2D [x=" + x + ", y=" + y + "]";
	}
	
	
	
	
	
	
	
}
