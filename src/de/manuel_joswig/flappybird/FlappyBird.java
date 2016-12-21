package de.manuel_joswig.flappybird;

import de.manuel_joswig.flappybird.game.Board;

import javax.swing.JFrame;

/**
 * Initializes the window
 * 
 * @author		Manuel Joswig
 * @copyright	2014 Manuel Joswig
 */
public class FlappyBird extends JFrame {
	private static final long serialVersionUID = 1L;
	
	public FlappyBird() {
		setTitle("Flappy Bird");
		setSize(400, 400);
		
		// add game panel
		add(new Board());
		
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setResizable(false); 
        setLocationRelativeTo(null); 
        setVisible(true);
	}
	
	public static void main(String[] args) {
		new FlappyBird();
	}
}
