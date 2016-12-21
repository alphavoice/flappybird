package de.manuel_joswig.flappybird.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents the game board
 * 
 * @author		Manuel Joswig
 * @copyright	2014 Manuel Joswig
 */
public class Board extends JPanel implements ActionListener, KeyListener {
	private static final long serialVersionUID = 1L;
	
	private static final int TIMER_INTERVAL = 15;
	private static final int BOT_TAP_TIMEOUT = 10;
	
	private static final int PIPE_WIDTH = 30;
	private static final int PIPE_HEIGHT_MIN = 50;
	private static final int PIPE_HEIGHT_MAX = 150;
	private static final int PIPE_DISTANCE = 130;
	
	private static final Vector2D IMPULSE = new Vector2D(0, -20);
	private static final Vector2D GRAVITY = new Vector2D(0, 1);
	
	private int score;
	private int viewport;
	private boolean gameOver;
	
	private Bird bird;
	private List<Pipe> pipes;
	
	private BufferedImage background;
	private Timer t;
	
	public Board() {
		setFocusable(true);
		addKeyListener(this);
		
		init();
	}
	
	public int getRandInt(int x, int y) {
		Random random = new Random();
		return random.nextInt((y + 1) - x) + x;
	}
	
	public void init() {
		score = 0;
		viewport = 0;
		gameOver = false;
		
		bird = new Bird(new Vector2D(25, 175), 25, 25, false);
		pipes = new ArrayList<Pipe>();
		
		try {
			background = ImageIO.read(getClass().getResource("/background.jpg"));
		}
		catch (IOException e) {
			System.out.println("Image not found");
		}
		
		t = new Timer(TIMER_INTERVAL, this);
		t.start();
	}
	
	// intelligent f(l)apping algorithm => needed for bot mode
	public void moveBird(Bird b) {
		int nextPipeIndex = 0;
		
		for (Pipe pipe : pipes) {
			// target next pipe if the bird has passed the end of a pipe
			if (pipe.isPassed() && b.getPosition().getX() > pipe.getPosition().getX() + pipe.getWidth()) nextPipeIndex++;
		}
		
		if (pipes.size() > 0) {
			Pipe p = pipes.get(nextPipeIndex);
			
			Vector2D birdMidpoint = new Vector2D(b.getPosition().getX() + 15, b.getPosition().getY() + 20);
			Vector2D pipeMidpoint = new Vector2D(p.getPosition().getX() + p.getWidth() / 2, p.getHeight() + Pipe.GAP_SIZE / 2);
			
			// dy between bird and gap after not tapping (=> add gravity vector)
			int dyg = Math.abs(pipeMidpoint.getY() - (birdMidpoint.getY() + GRAVITY.getY()));
			
			// dy between bird an gap after tapping (=> add impulse vector)
			int dyi = Math.abs(pipeMidpoint.getY() - (birdMidpoint.getY() + IMPULSE.getY()));
			
			// only tap if dy is lesser after tapping than after not tapping
			if (dyi < dyg) {
				// create a dummy bird for preventing a possible collision
				Bird dummy = new Bird(new Vector2D(b.getPosition().getX(), b.getPosition().getY() + IMPULSE.getY()), 25, 25, false);
				
				if (!dummy.collidesWithPipe(p)) b.setPosition(b.getPosition().add(IMPULSE));
			}
		}
	}
	
	public void keyReleased(KeyEvent e) { }
	
	public void keyTyped(KeyEvent e) { }
	
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		switch (keyCode) {
			// restart game
			case KeyEvent.VK_ENTER:
				if (!t.isRunning()) {
					init();
					t.start();
				}
				break;
			
			// flapping
			case KeyEvent.VK_SPACE:
				bird.setPosition(bird.getPosition().add(IMPULSE));
				break;
			
			// enable / disable bot mode
			case KeyEvent.VK_B:
				bird.setBot(!bird.isBot());
				break;
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if (!gameOver) {
			viewport++;
			
			if (bird.isBot() && viewport % BOT_TAP_TIMEOUT == 0) moveBird(bird);
			
			// apply gravity
			bird.setPosition(bird.getPosition().add(GRAVITY));
				
			// move pipes in the background (side scrolling effect)
			for (int i = 0; i < pipes.size(); i++) {
				Pipe pipe = pipes.get(i);
				Pipe movedPipe = new Pipe(pipe.getPosition().add(new Vector2D(-1, 0)), pipe.getWidth(), pipe.getHeight());
				
				// pipe has been already passed
				if (pipe.isPassed()) movedPipe.setPassed(true);
				
				pipes.set(i, movedPipe);
				
				if (bird.collidesWithPipe(movedPipe) || bird.collidesWithGround()) {
					gameOver = true;
				}
				else if (bird.hasPassedPipe(movedPipe) && !movedPipe.isPassed()) {
					movedPipe.setPassed(true);
					pipes.set(i, movedPipe);
					
					score++;
				}
			}
				
			// let a new pipe appear at the end of the screen
			if (viewport % PIPE_DISTANCE == 0 || viewport == 1) {
				pipes.add(new Pipe(new Vector2D(400, 0), PIPE_WIDTH, getRandInt(PIPE_HEIGHT_MIN, PIPE_HEIGHT_MAX)));
					
				// remove previous pipes
				if (pipes.size() > 5) pipes.remove(0);
			}
			
			repaint();
		}
 		else {
			t.stop();
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawImage(background, 0, 0, null);
		
		if (!gameOver) {
			bird.draw(g);
		
			for (Pipe pipe : pipes) {
				pipe.draw(g);
			}
			
			g.setFont(new Font("Verdana", Font.BOLD, 18));
			g.setColor(Color.BLUE);
			g.drawString("" + score, 200, 100);
		}
		else {
			g.setFont(new Font("Verdana", Font.BOLD, 26));
			g.setColor(Color.RED);
			g.drawString("Game Over", 116, 100);
			
			g.setFont(new Font("Verdana", Font.BOLD, 18));
			g.setColor(Color.BLACK);
			g.drawString("Score: " + score, 146, 165);
			
			g.setFont(new Font("Verdana", Font.PLAIN, 16));
			g.drawString("© 2014 Manuel Joswig", 102, 280);
		}
	}
}
