package de.manuel_joswig.flappybird.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Represents the flying bird
 * 
 * @author		Manuel Joswig
 * @copyright	2014 Manuel Joswig
 */
public class Bird extends Entity {
	// correction factor for precise upper pipe collision
	public static final int EPSILON = 10;
	
	private boolean isBot;
	
	private BufferedImage image;
	
	public Bird(Vector2D v, int w, int h, boolean isBot) {
		super(v, w, h);
		
		this.isBot = isBot;
		
		try {
			image = ImageIO.read(getClass().getResource("/bird.png"));
		}
		catch (IOException e) {
			System.out.println("Image not found");
		}
	}
	
	public boolean collidesWithGround() {
		return (!(v.getY() + getHeight() < 325));
	}
	
	public boolean collidesWithPipe(Pipe p) {
		int pipeX = p.getPosition().getX();
		
		if (v.getX() + getWidth() > pipeX && v.getX() < pipeX + p.getWidth()) {
			if (v.getY() + EPSILON > p.getHeight() && v.getY() + getHeight() < p.getHeight() + Pipe.GAP_SIZE) {
				return false;
			}
			
			return true;
		}
		
		return false;
	}
	
	public boolean hasPassedPipe(Pipe p) {
		return (v.getX() > p.getPosition().getX() + p.getWidth() / 3);
	}
	
	public boolean isBot() {
		return isBot;
	}
	
	public void setBot(boolean isBot) {
		this.isBot = isBot;
	}
	
	public void draw(Graphics g) {
		g.drawImage(image, v.getX(), v.getY(), null);
	}
}
