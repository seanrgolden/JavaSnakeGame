import java.awt.*;
import java.util.Random;

/* This class constitutes the "walls" or "baricades" that appear on level 2 of the game.
 * These baricades appear every time the "eatable" or "food" piece is "eaten" by the user controlled
 * snake. These baricades appear at a random location on the board, but should not cover the eatable piece.
 * It is okay for the baricades to overlap, so we will not check for their collision. Lastly, if the snake piece
 * collides with the baricade the game is over, and the panel should be alerted as such.
 */
public class BaricadePiece {

	// The width and height of the baricade piece (40 pixels by 40 pixels)
	private static final int WIDTH = 40;
	private static final int HEIGHT = 40;
	
	// The unit size of the snake (10 pixels by 10 pixels)
	private static final int UNIT_SIZE = 10;
	
	// The x and y coordinate at which the baricade's top left corner is found on the board
	int xVal;
	int yVal;
	
	// These Random objects give us a random value for x and y, which is then moved to xVal and yVal
	Random randomizeX;
	Random randomizeY;
	
	// The panel at which the baricades are being painted to
	private GamePanel panel;
	
	// Simple constructor for a baricade object
	// Takes a GamePanel object as an parameter so that we can draw the 
	// baricade object back on the panel with the rest of the objects
	public BaricadePiece(GamePanel newPanel) {
		// set our panel to the game's panel
		this.panel = newPanel;
		// Instantiate the Random Objects
		randomizeX = new Random();
		randomizeY = new Random();
		// Create the GUI object
		initGUI();
	}
	
	// Sets the bounds of the GUI and gets our Random values of x and y to then be set to 
	// xVal and yVal
	public void initGUI() {
		// use the GamePanel's getter methods to get the screens height and width
		// so that we make sure to place the baricade on screen
		int boardWidth = panel.getWidth();
		int boardHeight = panel.getHeight();
		// Since we want the baricade to line up smoothly with the snake's movement we divide the panel's
		// height and width by the snake's unit size (10px x 10px). We subtract 2 from this value so that no baricade
		// is drawn at the edge of the board -- which would not display on the game. 
		int boardWidthFactor = (boardWidth/UNIT_SIZE) - 2;
		int boardHeightFactor = (boardHeight/UNIT_SIZE) - 2;
		// Using our Random objects we get a random x and y value within our range of (0, boardFactor).
		// Lastly we add one so that the baricade cannot touch the edge of the screen on the north or west
		// boundaries. This allows the player to more readily navigate the board
		int randomXint = randomizeX.nextInt(boardWidthFactor) + 1;
		int randomYint = randomizeY.nextInt(boardHeightFactor) + 1;
		// Lastly we take our factored cartesian point and multiply it by our snake's unit size so that 
		// it gives a full sized x and y value. We set these equal to xVal and yVal
		xVal = randomXint * UNIT_SIZE;
		yVal = randomYint * UNIT_SIZE;
	}
	
	// A method that detects whether this current baricade's bounds collide with the bounds of the snake piece
	// It returns true if the bounds of the head of the snake intersect (collide) with the bounds of the
	// baricade piece.
	public boolean collision() {
		// The head of the snake is always the last object inside the snake Vector that is in our GamePanel object.
		// To get it, we use the size of the snake array and subtract one
		SnakePiece snakeHead = panel.snake.get(panel.snake.size() - 1);
		// return true/false if the bounds of the snakeHead intersect (collide) with the current baricades bounds
		return snakeHead.getBounds().intersects(getBounds());
	}
	
	// A method that detects whether this current baricade's bounds collide with the eatable or "food" piece
	// this is extremely important to check, because if the eatable piece is covered by a baricade the user can
	// never continue the game, because the eatable would not appear.
	public boolean overlap() {
		// return true/false if the eatable object, inside of the GamePanel, overlaps with the bounds of the 
		// the current baricade objects
		return (panel.eatable).getBounds().intersects(getBounds());
	}
	
	// Returns the Rectangle object that constitutes the bounds of the baricade object
	// this rectangle starts at the xVal, yVal point and is the same width/height of the baricade object
	public Rectangle getBounds() {
		// Return this rectangle that covers the bounds of the Baricade
		return new Rectangle(xVal, yVal, WIDTH, HEIGHT);
	}
	
	// The paint method for the baricade object. It takes in a single parameter: the 2-dimensional graphic that the rectangle is 
	// being painted to. It then changes the color being used to blue so that the user can differentiate between the baricade and 
	// the snake/eatable pieces. A rectangle is drawn with the specified parameters that are calculated earlier in the initGUI()
	// function. Lastly we set the color back to green for the next object to be drawn, which is more than likely the snake.
	public void paint(Graphics2D myGraphic) {
		// sets the graphics paint color to blue
		myGraphic.setColor(Color.BLUE);
		// creates a blue rectangle with the x/y values of xVal/yVal and the width and height to the specified values
		myGraphic.fillRect(xVal, yVal, WIDTH, HEIGHT);
		// sets the graphics paint color back to green
		myGraphic.setColor(Color.GREEN);
	}
};
