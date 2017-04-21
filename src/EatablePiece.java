import java.awt.*;
import java.util.Random;

/* This class constitutes the "food" piece in the snake game which the users are trying to run into and "eat". There are 
 * a few basic things this class needs to accomplish: (1) The location of the eatable should be random, but appear on the
 * "grid" of the board. This grid is defined by the unit size of the snake (10pixels x 10pixels). (2) The eatable should 
 * move to a random place on the board whenever it is eaten. It should also then have the panel increment the score and 
 * increase the snake's size. 
 */
public class EatablePiece {

	// The width and height of the eatable piece -- matches that of the snake piece's inner boundaries (8pixels x 8pixels)
	private static final int WIDTH = 8;
	private static final int HEIGHT = 8;
	// The unit size of the pieces
	private static final int UNIT_SIZE = 10;
	// Spacing between xVal/yVal and the start of the eatable piece
	// This lines the eatable piece up perfectly with the snakePieces which have built in 1pixel borders
	private static final int SPACING = 1;
	// The x and y coordinate at which the eatable's top left corner is found on the board
	int xVal;
	int yVal;
	// the size of the panel (screen) divided by the unit size of the "grid"
	int boardWidthFactor;
	int boardHeightFactor;
	// These Random objects give us a random value for x and y, which is then moved to xVal and yVal
	Random randomizeX;
	Random randomizeY;
	
	// The panel at which the eatables are being painted to
	private GamePanel panel;
	
	// Simple constructor for an eatable object
	// Takes a GamePanel object as an parameter so that we can draw the 
	// eatable object back on the panel with the rest of the objects	
	public EatablePiece(GamePanel newPanel) {
		// set our panel to the game's current panel
		this.panel = newPanel;
		// Instantiate the Random objects for x and y
		randomizeX = new Random();
		randomizeY = new Random();
		// calls the initializer for the GUI objects
		initGUI();
	}
	
	// Sets the bounds of the GUI and gets our Random values of x and y to then be set to 
	// xVal and yVal
	public void initGUI() {
		// use the GamePanel's getter methods to get the screens height and width
		// so that we make sure to place the eatable on screen
		int boardWidth = panel.getWidth();
		int boardHeight = panel.getHeight();
		// Since we want the eatable to line up smoothly with the snake's movement we divide the panel's
		// height and width by the snake's unit size (10px x 10px). We subtract 2 from this value so that no eatable
		// is drawn at the edge of the board -- which would not display on the game. 
		boardWidthFactor = (boardWidth/UNIT_SIZE) - 2;
		boardHeightFactor = (boardHeight/UNIT_SIZE) - 2;		
		// Using our Random objects we get a random x and y value within our range of (0, boardFactor).
		// Lastly we add one so that the eatable cannot touch the edge of the screen on the north or west
		// boundaries. This allows the player to more readily navigate the board
		int randomXint = randomizeX.nextInt(boardWidthFactor) + 1;
		int randomYint = randomizeY.nextInt(boardHeightFactor) + 1;
		// Lastly we take our factored cartesian point and multiply it by our snake's unit size so that 
		// it gives a full sized x and y value. We set these equal to xVal and yVal
		xVal = randomXint * UNIT_SIZE;
		yVal = randomYint * UNIT_SIZE;
	}
	
	// This function is recursively called by the panel each iteration of the main loop and should
	// move the eatable object if there is a unit collision between the SnakePiece and the eatable
	// as well as notify the panel that the food was eaten and spawn another baricade if the user
	// if playing level 2
	public void moveEatable() {
		// if there is a boundary collision between the head of the snake and the eatable
		if (collision()) {
			// notify the GamePanel which iterates the score and adds another SnakePiece to the snake
			notifyPanelOfBeingEaten();
			// gets a new random x and y unit value for the eatable piece to be spawned at
			int randomXint = randomizeX.nextInt(boardWidthFactor) + 1;
			int randomYint = randomizeY.nextInt(boardWidthFactor) + 1;
			// multiply the unit value of the cartesian point by the grid's unit size (10pixels) to 
			// get the cartesian value for the x/y values
			xVal = randomXint * UNIT_SIZE;
			yVal = randomYint * UNIT_SIZE;
			// if the user is playing level 2 we must account for baricades as well
			if (panel.getLevel() == 2) {
				// firstly add a new baricade to the screen
				panel.addNewBaricade();
				// check to make sure all of the baricades do not cover the eatable piece. If the baricade covered the eatable
				// then the user would not be able to see the eatable and therefore be able to continue the game
				for (BaricadePiece currentBaricade : panel.baricades) {
					// if this current baricade overlaps with the eatable piece then set a new x/y value for the eatable
					// using the same methods as before
					if (currentBaricade.overlap()) {
						randomXint = randomizeX.nextInt(boardWidthFactor) + 1;
						randomYint = randomizeY.nextInt(boardHeightFactor) + 1;
						xVal = randomXint * UNIT_SIZE;
						yVal = randomYint * UNIT_SIZE;
					}
				}
			}
		} 

	}
	
	// This method does the notifications back to the panel that the eatable piece was just eaten.
	// This includes the panel adding a SnakePiece to the snake and the score incremention
	public void notifyPanelOfBeingEaten() {
		// have the panel add a piece to the snake making it one SnakePiece longer
		panel.addPiece();
		// have the panel increment the score
		panel.incrementScore();
	}
	
	// A method that detects whether the eatable's bounds collide with the bounds of the snake piece
	// It returns true if the bounds of the head of the snake intersect (collide) with the bounds of the
	// eatable piece.
	private boolean collision() {
		// The head of the snake is always the last object inside the snake Vector that is in our GamePanel object.
		// To get it, we use the size of the snake array and subtract one
		SnakePiece snakeHead = panel.snake.get(panel.snake.size() - 1);
		// return true/false if the bounds of the snakeHead intersect (collide) with the eatable's bounds
		return snakeHead.getBounds().intersects(getBounds());
	}
	
	// Returns the Rectangle object that constitutes the bounds of the eatable object
	// this rectangle starts at the xVal, yVal point and is the same width/height of the eatable object
	public Rectangle getBounds() {
		// Return this rectangle that covers the bounds of the eatable
		return new Rectangle(xVal, yVal, WIDTH, HEIGHT);
	}
	
	// The paint method for the eatable object. It takes in a single parameter: the 2-dimensional graphic that the rectangle is 
	// being painted to. It then changes the color being used to red so that the user can differentiate between the eatable and 
	// the snake/baricade pieces. A rectangle is drawn with the specified parameters that are calculated earlier in the initGUI()
	// function. Lastly we set the color back to green for the next object to be drawn, which is more than likely the snake.
	public void paint(Graphics2D myGraphic) {
		// set the color of the graphic to red
		myGraphic.setColor(Color.RED);
		// creates a red rectangle with the x/y values of xVal/yVal and the width and height to the specified values
		myGraphic.fillRect(xVal + SPACING, yVal + SPACING, WIDTH, HEIGHT);
		// set the graphics paint color back to green
		myGraphic.setColor(Color.GREEN);
	}
	
	
};
