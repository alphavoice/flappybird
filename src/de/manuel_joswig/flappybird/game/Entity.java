package de.manuel_joswig.flappybird.game;

/**
 * Represents an entity
 * 
 * @author		Manuel Joswig
 * @copyright	2014 Manuel Joswig
 */
public class Entity {
	protected int width, height;
	protected Vector2D v;
	
	public Entity(Vector2D v, int w, int h) {
		width = w;
		height = h;
		
		this.v = v;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public Vector2D getPosition() {
		return v;
	}
	
	public void setWidth(int w) {
		width = w;
	}
	
	public void setHeight(int h) {
		height = h;
	}
	
	public void setPosition(Vector2D v) {
		this.v = v;
	}
}
