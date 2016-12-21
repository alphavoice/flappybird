package de.manuel_joswig.flappybird.game;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Represents a pipe
 * 
 * @author		Manuel Joswig
 * @copyright	2014 Manuel Joswig
 */
public class Pipe extends Entity {
	// size of the hole between the upper and lower part of the pipe
	public static final int GAP_SIZE = 70;
	
	private boolean isPassed;
	
	public Pipe(Vector2D v, int w, int h) {
		super(v, w, h);
		
		isPassed = false;
	}
	
	public boolean isPassed() {
		return isPassed;
	}
	
	public void setPassed(boolean isPassed) {
		this.isPassed = isPassed;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.GREEN);
		
		// upper part
		g.fillRect(v.getX(), v.getY(), width, height);
		
		// lower part
		g.fillRect(v.getX(), height + GAP_SIZE, width, 325 - height - GAP_SIZE);
		
		g.setColor(Color.BLACK);
		
		// border of the upper part
		g.drawRect(v.getX(), v.getY(), width, height);
		
		// border of the lower part
		g.drawRect(v.getX(), height + GAP_SIZE, width, 325 - height - GAP_SIZE);
	}
}
