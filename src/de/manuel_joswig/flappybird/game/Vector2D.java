package de.manuel_joswig.flappybird.game;

/**
 * Represents a vector in IR²
 * 
 * @author		Manuel Joswig
 * @copyright	2014 Manuel Joswig
 */
public class Vector2D {
	private int x, y;
	
	public Vector2D(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getLength() {
		return (int) Math.sqrt(x * x + y * y);
	}
	
	public Vector2D add(Vector2D v) {
		return new Vector2D(x + v.getX(), y + v.getY());
	}
}
