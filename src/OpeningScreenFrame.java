import javax.swing.*;

/* Class that extends JFrame and serves as the window for the title screen or opening screen of the game
 * Allows the user to read the rules and pick between two levels
 */

@SuppressWarnings("serial")
public class OpeningScreenFrame extends JFrame {
	
	// The width and height of the Frame as well as the 20pixel spacing required to nullify the 20pixel title bar
	private final int WIDTH = 500;
	private final int HEIGHT = 500;
	private final int SPACING = 20;
	
	// Requires a content panel to hold information
	OpeningScreenPanel panel;
	// the level that the user chooses
	int levelToRun = 0;
	
	// constructor that creates the panel object and initializes the GUI
	OpeningScreenFrame() {
		panel = new OpeningScreenPanel(this);
		initGUI();
	}
	
	public void initGUI() {
		// adds the openingScreenPanel
		add(panel);
		// set the title of the frame the "Java Snake"
		setTitle("Java Snake");
		// set size of the frame
		setSize(WIDTH, HEIGHT + SPACING);
		// set the program to exit when the frame closes
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// don't allow the frame to be resized
		setResizable(false);
		// show frame
		setVisible(true);
		
		// starts while loop inside the panel
		panel.waitForInput();
	}
	
	// simple getter for the width of the frame
	public int getWidth() {
		return WIDTH;
	}
	
	// simple getter for the height of the frame
	public int getHeight() {
		return HEIGHT;
	}
	
	// simple setter for setting the level the user chooses
	public void setLevel(int newLevel) {
		levelToRun = newLevel;
	}
	
	// simple getter for the level being run
	public int getLevel() {
		return levelToRun;
	}
	
};
