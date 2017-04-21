import javax.swing.*;

/* This class is an extension of a JFrame class and is used as the Frame for the Running portion of the snake game.
 * Besides acting as the window for the GamePanel class, it has a few simple methods so that it can communicate
 * basic needs to the GameRunner class from the GamePanel, and vice versa.
 */

@SuppressWarnings("serial")
public class GameFrame extends JFrame{

	// Height and Width of the frame (500 pixels). The Spacing accounts for the vertical spacing that is necessary to scale
	// the vertical aspects of the Frame such that they are even.
	private final int HEIGHT = 500;
	private final int WIDTH = 500;
	private final int SPACING = 20;
	
	// The GamePanel that will be used to display the running game and its components
	public GamePanel panel;
	// The level of the game to be played -- default option uses level 1
	public int level = 1;
	// The running score of the game
	public int score;
	
	// Constructor for the GameFrame which takes a level (either 1 or 2) as a parameter and stores that as the
	// level to be paid
	public GameFrame(int newLevel) {
		// set level to the given level
		level = newLevel;
		// create a new GamePanel to use as the main content pane
		panel = new GamePanel(this);
		// set score initially to zero
		score = 0;
		// Begin creating the GUI
		initGUI();
	}
	
	// GUI initialization method. adds the panel GUI to our Frame and sets various aspects of the Frame
	public void initGUI() {
		// add the GamePanel to the Frame
		add(panel);
		// sets the title of the Window to "Java Snake"
		setTitle("Java Snake");
		// set the size of the window to the 500pixel by 500pixel. We add the Spacing amount to the Height to account for
		// the size of the title of the window
		setSize(WIDTH, HEIGHT + SPACING);
		// set the default close operation to exit the GUI when we close it
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// set the window to be a set size -- we don't let the user change the size of the Frame
		setResizable(false);
		// shows the Frame
		setVisible(true);
	}

	// A method that tells the GamePanel 'panel' to start the game execution
	public void startGame() throws InterruptedException {
		panel.startGame();
	}
	
	// A simpler getter to return the score of the game
	public int getScore() {
		return score;
	}
	
	// A simpler setter to set the score of the game
	public void setScore(int newScore) {
		score = newScore;
	}
	
	// A simple getter to get the level the user is trying to play
	public int getLevel() {
		return level;
	}
	
	// thrown when the game is over -- since the Frame itself does nothing, nothing happens here
	public void gameOver() {
		//thats the end of the thread
		
	}
	
	// simple getter that returns the width of the frame
	public int getWidth() {
		return WIDTH;
	}
	
	// simple getter than returns the height of the frame
	public int getHeight() {
		return HEIGHT;
	}
	
	// returns the boolean runAgain() from the panel. If this returns true, the game will restart on the given level
	public boolean getRunAgain() {
		return panel.getRunAgain();
	}
	
};
