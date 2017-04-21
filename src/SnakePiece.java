import java.awt.*;

/* Class that depicts the pieces of the snake that the user controls.
 * Each piece has a xVal and a yVal that changes depending on user input.
 * Most of these computations happen outside of the class itself however
 */

public class SnakePiece {
	
	// width and height of the SnakePiece
	private final int WIDTH = 10;
	private final int HEIGHT = 10;
	// thickness of the Border [1 pixel]
	private final int BORDER_SPACING = 1;
	// default x/y value (right in the center of the screen)
	private static final int STARTING_X = 250;
	private static final int STARTING_Y = 250;
	
	// x and y Cartesian values
	int xVal;
	int yVal;
	
	// panel on which the piece is being drawn to
	private GamePanel panel;
	
	// constructor that initializes the x and y values as well as points the piece to the panel
	public SnakePiece(GamePanel newPanel) {
		this.panel = newPanel;
		xVal = STARTING_X;
		yVal = STARTING_Y;
	}
	
	// simple getter for the x value
	public int getX() {
		return xVal;
	}
	
	// simple getter for the y value
	public int getY() {
		return yVal;
	}
	
	// simple setter for the x value
	public void setX(int newX) {
		xVal = newX;
	}
	
	// simple setter for the y value
	public void setY(int newY) {
		yVal = newY;
	}
	
	// returns the Rectangular bounds of the snake piece
	public Rectangle getBounds() {
		return new Rectangle(xVal, yVal, WIDTH, HEIGHT);
	}
	
	// the paint method for how the snake piece looks 
	public void paint(Graphics2D controllableGraphic) {
		// create a 10x10 pixel black box at the x/y value given
		controllableGraphic.setColor(Color.BLACK);
		controllableGraphic.fillRect(xVal, yVal, WIDTH, HEIGHT);
		// create a 8x8 pixel green box that is perfectly in the center of the black box creating the image of a black border
		controllableGraphic.setColor(Color.GREEN);
		controllableGraphic.fillRect(xVal + BORDER_SPACING, yVal + BORDER_SPACING,
				WIDTH - (BORDER_SPACING*2), HEIGHT - (BORDER_SPACING*2));
	}
	
	// returns true/false if the current SnakePiece is colliding with the given the SnakePiece
	// useful in tests within the GamePanel methods
	public boolean snakeCollision(SnakePiece testingPiece) {
		return panel.snake.get(panel.snake.size()-1).getBounds().intersects(testingPiece.getBounds());
	}
	
};
