import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

/* A Class that extends the JPanel class and is used for the majority of the computation in the game. In this class
 * we will use the BaricadePiece, EatablePiece, and SnakePiece to let the user play the game. We also handle the 
 * key listeners in this Class so we must initialize listeners as well as the GUI objects
 */

@SuppressWarnings("serial")
public class GamePanel extends JPanel {

	// The desired width and height of the Frame
	private final int WIDTH = 500;
	private final int HEIGHT = 500;
	
	// The unit size of the game - 10 pixels. We will think of the entire surface as a grid of 10x10 pixel blocks
	// to achieve the classic snake appearance. By using this unit size we will be able to line up the EatablePiece
	// perfectly with the SnakePiece and their borders will therein line up smoothly.
	private final int UNIT_SIZE = 10;
	
	// x/yDirection signifies the direction in which the snake will move in the next iteration of the startGame() loop
	int xDirection = UNIT_SIZE;
	int yDirection = 0;
	// the x/y value for the next location of the head of the snake
	int nextX = 250;
	int nextY = 250;
	
	// the level that is being ran
	int level;
	// boolean that signifies whether to check for snake collisions with itself or not -- only false for the first
	// iteration after adding a piece
	boolean doSnakeCollision = true;
	// boolean that tells the game whether to run another game after the user loses
	boolean runAgain = true;
	// boolean that tells the while loop whether the user has lost or not -- loop continues while true
	boolean gameOver = false;
	
	// vector that holds the SnakePieces that make the user controlled snake
	Vector<SnakePiece> snake;
	// vector that holds the BaricadePieces that make the "walls" for the user to avoid in level 2
	// we only instantiate this variable if we are in level 2
	Vector<BaricadePiece> baricades;
	// the eatablePiece that is the goal of the game to "consume"
	EatablePiece eatable;
	// Scoreboard that is at the top-center of the panel
	JLabel scoreboard;
	
	// the GameFrame in which the panel is placed on
	private GameFrame frame;
	
	// Constructor for the GamePanel
	// Here we just instantiate all of our member variables and call our initialization methods
	public GamePanel(GameFrame newFrame) {
		// set our frame to the GameFrame in which the constructor was called
		this.frame = newFrame;
		// get the level from the frame that is to be played
		level = frame.getLevel();
		// Instantiate our vector, create our first SnakePiece to be used as the head of the snake, and
		// add the head of snake to the vector
		snake = new Vector<SnakePiece>();
		SnakePiece snakeHead = new SnakePiece(this);
		snake.addElement(snakeHead);
		// Instantiate the Scoreboard with the score from the frame (which at this point will equal 0)
		scoreboard = new JLabel("Score: " + frame.getScore());
		// Instantiate our eatable piece
		eatable = new EatablePiece(this);
		// if we are running level two we must include "walls" and therefore want to create our baricade vector
		if (level == 2) {
			baricades = new Vector<BaricadePiece>();
		}
		
		// call our initialization methods -- both for the GUI and the listeners in this case
		initGUI();
		initListeners();
	}
	
	// simple method to initialize our GUI. Since most of the action in this panel is from the components I have created
	// we don't have much initialization to do here
	public void initGUI() {
		// set background color of the panel to Black
		setBackground(Color.BLACK);
		// set scoreboard font to Calibri and size 48
		// change the font color to white and set the bounds so that it is at the top of the screen
		scoreboard.setFont(new Font("Calibri", Font.PLAIN, 48));
		scoreboard.setForeground(Color.WHITE);
		scoreboard.setBounds(0, 0, WIDTH, HEIGHT/10);
		// add scoreboard to the panel
		add(scoreboard);
	}
	
	// method to initialize our listeners. In the game we only use keys so we only need to create a key listener.
	// we do so by overriding the KeyListener class and its methods
	public void initListeners() {
		// create our customized KeyListener called listener
		KeyListener listener = new KeyListener() {
			// required to include an override method here although we have no application for it
			@Override
			public void keyTyped(KeyEvent e) {
				// nothing
			}
			// same thing here... no application for our program
			@Override
			public void keyReleased(KeyEvent e) {
				// nothing
			}
			// This is the important part to us -- the keys that are pressed
			@Override
			public void keyPressed(KeyEvent e) {
				// if the key that was pressed was the left arrow then we want to move the snake to move left on the screen
				// if the snake currently moving to the right (xDirection == UNIT_SIZE) then moving to left would make it eat
				// itself and therefore lose the game. Therefore we first check to make sure that this operation will not 
				// cause the user to accidentally end their game. Next, after this check, to make the snake move left we set
				// the xDirection variable to the negative value of the UNIT_SIZE. This means that when the loop does it's next
				// iteration it will subtract 10 pixels from the xVal and give the appearance of leftward animation
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					if (xDirection != UNIT_SIZE) {
						xDirection = (-1)*UNIT_SIZE;
						yDirection = 0;
					}
				}
				// explanation above, directions are just different
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					if (xDirection != (-1)*UNIT_SIZE) {
						xDirection = UNIT_SIZE;
						yDirection = 0;
					}
				}
				// explanation above, directions are just different
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					if (yDirection != UNIT_SIZE) {
						xDirection = 0;
						yDirection = (-1)*UNIT_SIZE;
					}
				} 
				// explanation above, directions are just different
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					if (yDirection != (-1)*UNIT_SIZE) {
						xDirection = 0;
						yDirection = UNIT_SIZE;
					}
				}
			}
			
		};
		
		// add our customized KeyListener to the panel and make it the primary listener
		addKeyListener(listener);
		setFocusable(true);
	}
	
	// this is the majority of our game's process in one method. It takes aspects from each piece and the while loop
	// signifies majority of the gameplay. Essentially the method runs for as long as the gameOver boolean is false.
	// This boolean can become false whenever the snake ends the iteration of a loop in a position that calls the gameOver() 
	// method. This can happen in a few different ways -- all of which are thrown from within other methods that are called inside 
	// this one (i.e. movePiece(), checkBaricadeCollisions(), checkSnakeCollisions()). These methods are what make this loop finite.
	public void startGame() throws InterruptedException {
		// while the gameOver boolean is false run the loop
		while (!gameOver) {
			// calls the movePiece() method -- this will move the nextX/Y value to its next location
			movePiece();
			// calls the moveEatable() method -- this will move the eatable if the snakeHead is colliding with it
			// it also increments the score and adds a SnakePiece
			eatable.moveEatable();
			// if the user is playing level 2 we must check that for each baricade on the panel that the eatable piece 
			// does not overlap. If the baricade and eatable overlapped then the user would no longer be able to play the game
			// we do this by checking overlap for each baricade in the vector and then moving the eatable if there is an overlap
			if (level == 2) {
				for (BaricadePiece currentBaricade : baricades) {
					if (currentBaricade.overlap()) {
						eatable.moveEatable();
					}
				}
				// check to make sure that the snake has not collided with the any of the baricades as well
				checkBaricadeCollisions();
			}
			// if we are checking for snake collisions on this iteration -- check for them. These collisions are 
			// described as the collisions between the snakeHead and any other piece of the snake
			if (doSnakeCollision) {
				checkSnakeCollisions();
			} else {
				// if we didn't check for snake collisions on this iteration we want to make sure to check for them on the next
				doSnakeCollision = true;
			}
			// repaint the panel with all of the changes that we just made throughout this loop 
			repaint();
			
			// we have different amounts of time and ways we want our Thread to sleep to produce different animation
			// models. If we are running level one we want to do the sleep animation for level one and handle level two differently
			if (level == 1) {
				doSleepLevelOne();
			} else {
				doSleepLevelTwo();
			}
		}
	}
	
	// Level One's sleep animation -- as the user eats more pieces we want the game to speed up to make the game harder and harder
	public void doSleepLevelOne() throws InterruptedException {
		// each of these scores marks a score that if reached gives an increased speed
		int[] speedUpScores = {30, 70, 110, 140, 160, 180}; 
		// we make the animation appear "faster" by making the thread sleep for shorter amounts
		int[] sleepTimes = {150, 120, 100, 80, 60, 40, 30};
		
		// if the score of the game is less than our first score point, use our first speed
		if (frame.getScore() < speedUpScores[0]) {
			Thread.sleep(sleepTimes[0]);
		} else if (frame.getScore() < speedUpScores[1]) { // else if the score is less than the second score point, second speed
			Thread.sleep(sleepTimes[1]);
		} else if (frame.getScore() < speedUpScores[2]) { // etc...
			Thread.sleep(sleepTimes[2]);
		} else if (frame.getScore() < speedUpScores[3]) {
			Thread.sleep(sleepTimes[3]);
		} else if (frame.getScore() < speedUpScores[4]) {
			Thread.sleep(sleepTimes[4]);
		} else if (frame.getScore() < speedUpScores[5]) {
			Thread.sleep(sleepTimes[5]);
		} else { // if the score is greater than our set points then use our max speed 
			Thread.sleep(sleepTimes[6]);
		}
	}
	
	// Level Two's sleep animation -- Since we have "walls" consistently appearing we don't change the speed
	public void doSleepLevelTwo() throws InterruptedException {
		// have the thread sleep for 90 milliseconds
		int sleepTime = 90;
		Thread.sleep(sleepTime);
	}
	
	// method that moves the snake pieces in the array to visually display progressive movement
	// To do this we create a new piece that will be at the location of our current snakehead.
	// We then use the moveOptions() method to increment the x/y values to match the xDirection and yDirection
	// that the snake is moving. This will set our correct NextX/Y values and we will set our new snakehead to (NextX, NextY). 
	// We add this point to the back of our vector and remove the first index so that it looks as if the entire array moves.
	// this saves us a lot of processing power, especially as the snake gets longer and longer
	public void movePiece() {
		// make a new SnakePiece that will be the new head of the snake
		SnakePiece newSnakeHead = new SnakePiece(this);
		SnakePiece currentSnakeHead = snake.get(snake.size() -1);
		// set this new snakeHead to the current snakeHead's coordinates
		newSnakeHead.setX(currentSnakeHead.getX());
		newSnakeHead.setY(currentSnakeHead.getY());
		// change the x/y value by adding the xDirection/yDirection values
		moveOptions();
		// set the x/y to the nextX/Y
		newSnakeHead.setX(nextX);
		newSnakeHead.setY(nextY);
		// add this newSnakeHead to the vector
		snake.addElement(newSnakeHead);
		// remove the old piece
		snake.remove(0);
	}
	
	// takes the nextX/Y and adds the current direction that the snake is moving via the use of x/yDirection.
	// we only do this is the movement would keep our snake inside the bounds of the screen -- if the boundaries are being hit
	// then we call the gameOver() method ending the while loop
	public void moveOptions() {
		// if the iteration of this loop is within the boundaries of the game
		if ((nextX + xDirection >= 0) && (nextX + xDirection < WIDTH) 
				&& (nextY + yDirection >= 0) && (nextY + yDirection < HEIGHT)) {
			// add the movement of the snake to the nextX/Y values
			nextX += xDirection;
			nextY += yDirection;
		} else {
			// if we did hit the wall end the game
			gameOver();
		}
	}
	
	// method that adds a SnakePiece to the snake -- this gets called from the moveEatable() method in the EatablePiece class
	public void addPiece() {
		// creates a new SnakePiece and sets it to the currentSnakeHead's x/y coordinates
		// this piece will stay here until the entire snake runs over it, when it will then "appear" to the screen and move with
		// the rest of the snake
		SnakePiece newPiece = new SnakePiece(this);
		SnakePiece currentSnakeHead = snake.get(snake.size()-1);
		newPiece.setX(currentSnakeHead.getX());
		newPiece.setY(currentSnakeHead.getY());
		// add this piece to the back of the vector
		snake.addElement(newPiece);
		// since the new piece is on the same point as the head of the snake -- we would get a false error in checkSnakeCollisions()
		// to account for this we just tell the system to not check for collisions on the next iteration
		doSnakeCollision = false;
	}
	
	// method that checks the baricades vector to see if any of the baricades collide with the snakeHead
	public void checkBaricadeCollisions() {
		// for each BaricadePiece in baricades see if there is a collision
		for (BaricadePiece currentBaricade : baricades) {
			if (currentBaricade.collision()) {
				// if so, end the game
				gameOver();
			}
		}
	}
	
	// method that checks the entire snake vector to see if the head of the snake collides with any part of its "body"
	public void checkSnakeCollisions() {
		SnakePiece snakeHead = snake.get(snake.size() -1);
		// for each SnakePiece in the snake check to see if there is a collision between the snakeHead and the current
		// body part
		for (SnakePiece currentPiece : snake) {
			// if the snakeHead is the current piece then they are the same object and obviously they would collide, so we ignore this
			if (snakeHead == currentPiece) {
				// nothing 
			} else if (snakeHead.snakeCollision(currentPiece)) { 
				// if we do find a collision, end the game
				gameOver();
			} else {
				// no collision, continue to the next piece
				continue;
			}
		}
	}
	
	// method that adds a baricade to the panel
	public void addNewBaricade() {
		// create a new BaricadePiece object -- this is randomly placed via the BaricadePiece() constructor
		BaricadePiece newBaricade = new BaricadePiece(this);
		// add this object to the panel
		baricades.addElement(newBaricade);
	}
	
	// overriding the paint function. Here we must make sure to paint our created objects: SnakePieces, EatablePieces and if level
	// two, then the Baricades as well
	@Override
	public void paint(Graphics theGraphic) {
		// setting the stage for the addition of our objects
		super.paint(theGraphic);
		Graphics2D the2DGraphic = (Graphics2D) theGraphic;
		the2DGraphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// for each SnakePiece in the snake -- paint it
		for (SnakePiece currentPiece : snake) {
			currentPiece.paint(the2DGraphic);
		}
		// paint the eatable
		eatable.paint(the2DGraphic);
		// if it is level 2 then paint each BaricadePiece in the baricades vector
		if (level == 2) {
			for (BaricadePiece currentBaricade : baricades) {
				currentBaricade.paint(the2DGraphic);
			}
		}
	}
	
	// a method that handles when the user loses the game
	public void gameOver() {
		// if the gameOver screen has not showed yet --> there is potential for the user to lose in more than one way at the same time
		// but we only want to prompt the user once, so by using this boolean we can be certain to not berate the user with 
		// repetitive error messages
		if (gameOver == false) {
			// Creates a pop-up window that tells the user that they have lost and presents their score
			JOptionPane.showMessageDialog(frame, "Game Over! Your Score: " + frame.getScore());
			// after they click "Ok" another pop-up appears asking the user if they would like the play again
			// if yes then we set runAgain to true so that when the startGame() loop ends we end up re-running the game in
			// the next method
			if (JOptionPane.showConfirmDialog(frame, " Would you like to play again?", "Again?",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				runAgain = true;
			} else {
				runAgain = false;
			}	
			// set gameOver to true -- ending the while loop and making sure that we don't reprompt the user
			gameOver = true;
		}
	}
	
	// incrememnnets the score by ten each time the eatable is eaten
	public void incrementScore() {
		int pointsPerTimeEaten = 10;
		int oldScore = frame.getScore();
		// new score will be the oldscore plus the points you attain each time the eatable is "eaten"
		int newScore = oldScore + pointsPerTimeEaten; 
		// set the score of the game to the new score
		frame.setScore(newScore);
		// update the scoreboard GUI so that the user can see their score 
		scoreboard.setText("Score: " + frame.getScore());
	}
	
	// simple getter that returns the width of the screen
	public int getWidth() {
		return WIDTH;
	}
	
	// simple getter that returns the height of the screen
	public int getHeight() {
		return HEIGHT;
	}
	
	// simple getter that returns the level being played by the user
	public int getLevel() {
		return level;
	}
	
	// simple getter that returns true/false whether the game should be re-run
	public boolean getRunAgain() {
		return runAgain;
	}
	
};
