//
// Project 2
// Name: Sean Golden
// E-mail: srg41@georgetown.edu
// Instructor: Singh
// COSC 150
//
// In accordance with the class policies and Georgetown's Honor Code,
// I certify that, with the exceptions of the lecture and Blackboard
// notes and those items noted below, I have neither given nor received
// any assistance on this project.
//
// Description: This program is a Java Snake Game where the user controls a green cube that is attempting to "consume" a randomly
// spawned red cube, or "eatable". Each time the user successfully collides, "eats", the eatable piece the user's controllable
// grows in size by one and depending on the level may have a more difficult time getting to its next eatable piece. The user loses 
// if they run off the screen, collides with themself, or if playing level 2 -- collides with the "walls" that are spawned
// each time you consume the eatable. Upon losing, the user is prompted whether they would like to play again or not.
//

/* This Class handles running the game. This includes the opening screen prompt as well as the game itself and whether
 * the user wishes to play again. It uses 3 different types of threads that handle 3 different operations: 1. the opening screen, 
 * the how-to-play menu, and the level choosing process 2. The game play itself and 3. the ability to replay the game
 */

public class GameRunner {
	// the level that the game is going to run
	int levelToRun = 0;
	// if true, the game will run
	static boolean run = true;
	 
	// main function -- creates an GameRunner object and starts the game
	public static void main(String[] args) {
		GameRunner game = new GameRunner();
		game.startGame();
	}
	
	// methods that starts the GUI by creating the two main threads -- the GameThread will not run until the OpeningThread has
	// completed and a level has been chosen by the user
	public void startGame() {
		OpeningThread opening = new OpeningThread();
		GameThread gameThread = new GameThread();
		//1 Run opening screen
		opening.run();
		//2 Run appropriate level
		gameThread.run();
	}
	
	// Our OpeningThread Class that implements runnable
	// This thread handles everything on the opening screen
	class OpeningThread implements Runnable {

		// override the run() method to work such that it handles the opening screen 
		@Override
		public void run() {
			// create a new OpeningScreenFrame
			OpeningScreenFrame openingFrame = new OpeningScreenFrame();
			// initialize the GUI -- this starts a while loop that does not end until a level is selected 
			// or the window is closed
			openingFrame.initGUI();
			// set the level to run as the level the user selected
			levelToRun = openingFrame.getLevel();
			// set this frame back to false so that we can pull up the game now
			openingFrame.setVisible(false);
		}
		
	};
	
	// GameThread Class handles everything about the game
	// when it is done running the game it then checks to see if it should run again depending on user input
	class GameThread implements Runnable {

		// overriding the run() method
		@Override
		public void run() {
			// if the level switched correctly
			if ((levelToRun == 1) || (levelToRun == 2)) {
				// make a new GameFrame
				GameFrame game = new GameFrame(levelToRun);
				try {
					// starts the game and the loop doesn't end until the gameOver() is called
					game.startGame();
					// if the user chooses to play again keep run to true and if not set it to false
					if (game.getRunAgain()) {
						run = true;
					} else {
						run = false;
						// exit the program
						System.exit(0);
					}
					// once the game ends, hide the frame 
					game.setVisible(false);
					// if the program hasn't ended then start a runAgain thread
					RunAgainThread runAgain = new RunAgainThread();
					runAgain.run();
				} catch (InterruptedException e) {
					System.out.println("Sleep was interrupted for some reason. Printing Stack Trace...");
					e.printStackTrace();
				}
			} else {
				System.out.println("Something terribly wrong here...");
			}
		}
		
	};
	
	// class that has one job - to rerun the program if the user decides to keep playing
	class RunAgainThread implements Runnable {

		@Override
		public void run() {
			// if the run boolean is still true, then rerun the program
			if (run) {
				startGame();
			}
			
		}
		
	};
	
};
