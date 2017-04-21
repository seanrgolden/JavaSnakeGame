import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/* Class that extends JPanel and serves as the content pane for the OpeningScreenFrame.
 * Serves 2 main purposes: 1. Displays a help text for those new to the game and 2. lets the user choose
 * which level they would like to play
 */

@SuppressWarnings("serial")
public class OpeningScreenPanel extends JPanel {
	// This is the block of text used to explain the rules of the game
	private String explanation = "\t                  How To Play:\n"
			+ "Use the arrow keys to move the snake in desired direction. \n"
			+ "The goal of the game is to eat the 'food' pieces (red blocks) as many times as \npossible. "
			+ "Each time you eat the 'food' piece you get 10 points and your \nsnake increases in size. "
			+ "If you run off screen or run over yourself, you \nwill lose the game. "
			+ "There are two different game types you can choose: \n"
			+ "\n\t Level One - Standard Snake Game:"
			+ "\nAs you eat 'food' the snake grows in size and the speed increases. \n"
			+ "\n\t Level Two - Advanced Snake Game: \nAs you eat 'food' the snake continues to grow, but instead \n"
			+ "of the snake increasing in speed, a blue 'wall' will appear on \n"
			+ "the screen. If you hit these 'walls' you will lose.";
	
	// The width and height of the panel
	private final int WIDTH = 500;
	private final int HEIGHT = 500;
	
	// width and height of the title
	private final int TITLE_WIDTH = 500;
	private final int TITLE_HEIGHT = 300;
	
	// button boundaries
	private final int BUTTON_SIDES = 100;
	private final int BUTTON_X = 100;
	private final int BUTTON_Y = 300;
	
	// if true; show the help text
	boolean showHelp = false;
	
	// frame in which the panel is in
	OpeningScreenFrame frame;
	// level that the user selects to run
	int levelToRun = 0;
	// list of GUIs
	// title of the game
	JLabel title;
	// button options for
	// level one
	JButton levelOneOption;
	// level two
	JButton levelTwoOption;
	// displaying how to play text
	JButton howToPlayOption;
	// the textArea of the how to play text
	JTextArea textArea;

	// constructor for the instantiation of the objects and the initialization of the GUI method
	public OpeningScreenPanel(OpeningScreenFrame hostFrame) {
		// set the host frame to the frame that created the panel
		this.frame = hostFrame;
		// create and set the text of the title to "Java Snake"
		title = new JLabel("Java Snake");
		// create and set the names of each button
		levelOneOption = new JButton("Play Level One");
		levelTwoOption = new JButton("Play Level Two");
		howToPlayOption = new JButton("How To Play");
		// create the text area
		textArea = new JTextArea();
		// initialize the GUIs
		initGUI();	
	}

	public void initGUI() {
		// set background of the panel to black
		setBackground(Color.BLACK);
		// use a boxLayout
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		// align the title in the center with calibri font and 90 size
		// make the color green and add it to the panel
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		title.setFont(new Font("Calibri", Font.PLAIN, 90));
		title.setForeground(Color.GREEN);
		title.setBounds(0, 0, TITLE_WIDTH, TITLE_HEIGHT);
		add(title);
		// align the buttons in the center with calibri font and 32 size with black text
		// each button gets added in the order they are to appear in the list with how-to-play first; followed by level one
		// and then level two
		levelOneOption.setAlignmentX(Component.CENTER_ALIGNMENT);
		levelOneOption.setFont(new Font("Calibri", Font.PLAIN, 32));
		levelOneOption.setForeground(Color.BLACK);
		levelOneOption.setBounds(BUTTON_X, BUTTON_Y, BUTTON_SIDES, BUTTON_SIDES);
		
		levelTwoOption.setAlignmentX(Component.CENTER_ALIGNMENT);
		levelTwoOption.setFont(new Font("Calibri", Font.PLAIN, 32));
		levelTwoOption.setForeground(Color.BLACK);
		levelTwoOption.setBounds(BUTTON_X, BUTTON_Y, BUTTON_SIDES, BUTTON_SIDES);
		
		howToPlayOption.setAlignmentX(Component.CENTER_ALIGNMENT);
		howToPlayOption.setAlignmentY(Component.CENTER_ALIGNMENT);
		howToPlayOption.setFont(new Font("Calibri", Font.PLAIN, 32));
		howToPlayOption.setForeground(Color.BLACK);
		howToPlayOption.setBounds(BUTTON_X, BUTTON_Y, BUTTON_SIDES, BUTTON_SIDES);
		
		// add the action listeners for each button
		levelOneOption.addActionListener(new ButtonListenerLevelOne());
		levelTwoOption.addActionListener(new ButtonListenerLevelTwo());	
		howToPlayOption.addActionListener(new ButtonListenerHowToPlay());
		// add the buttons to the panel
		add(howToPlayOption);
		add(levelOneOption);
		add(levelTwoOption);
		
		// set the text area's text to the explanation string and make it un-editable
		textArea.setText(explanation);
		textArea.setEditable(false);
		// set the bounds to fit in the "white space" underneath the buttons and set the color the green 
		// and the font to calibri and size 12
		textArea.setBounds(75, (HEIGHT/2) + 25, WIDTH - 50, HEIGHT - 50);
		textArea.setAlignmentX(Component.CENTER_ALIGNMENT);
		textArea.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		textArea.setFont(new Font("Calibri", Font.PLAIN, 12));
		textArea.setBackground(Color.BLACK);
		textArea.setForeground(Color.GREEN);
		
	}
	
	// wait for input re-updates the data variables so that the program responds when the buttons are actually clicked
	// by repainting the system updates the variables
	public void waitForInput() {
		while (levelToRun == 0) {
			repaint();
		}
	}
	
	// simple getter that gets the level that the user selects to play
	public int getLevelToRun() {
		return levelToRun;
	}
	
	// button listener implementation for the level one button
	class ButtonListenerLevelOne implements ActionListener {
		// when clicked just set the levelToRun to level 1 and update the frame with that information as well
		@Override
		public void actionPerformed(ActionEvent e) {
			levelToRun = 1;
			frame.setLevel(levelToRun);
		}
		
	};
	
	// button listener implementation for the level two button
	class ButtonListenerLevelTwo implements ActionListener {
		// when clicked just set the levelToRun to level 2 and update the frame with that information as well
		@Override
		public void actionPerformed(ActionEvent e) {
			levelToRun = 2;
			frame.setLevel(levelToRun);
		}
		
	};
	
	// button listener implementation for the how-to-play button
	class ButtonListenerHowToPlay implements ActionListener {
		// when clicked we want to show/hide the how-to-play text
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// if the help text is not being shown; then show it
			if (showHelp == false) {
				add(textArea);
				showHelp = true;
			} else {
				// if the text is being shown; hide it
				remove(textArea);
				showHelp = false;
			}
			// repaint the change
			repaint();
		}
		
	};
};
