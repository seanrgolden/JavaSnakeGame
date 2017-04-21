import static org.junit.Assert.*;
import org.junit.Test;

public class ProjectTwoTesting {

	@Test
	public void snakePieceSettersTest() {
		GameFrame testFrame = new GameFrame(1);
		GamePanel testPanel = new GamePanel(testFrame);
		SnakePiece testSnake = new SnakePiece(testPanel);
		
		int testValue = 5;
		testSnake.setX(testValue);
		testSnake.setY(testValue);
		
		assertEquals(testValue, testSnake.getX(), 0);
		assertEquals(testValue, testSnake.getY(), 0);
	}

	@Test
	public void gamePanelGetBoundsTest() {
		GameFrame testFrame = new GameFrame(1);
		GamePanel testPanel = new GamePanel(testFrame);
		
		int height = 500;
		int width = 500;
		
		assertEquals(height, testPanel.getHeight(), 0);
		assertEquals(width, testPanel.getWidth(), 0);
	}
	
	@Test
	public void gamePanelGetLevelTest() {
		int levelToPlay = 1;
		GameFrame testFrame = new GameFrame(levelToPlay);
		GamePanel testPanel = new GamePanel(testFrame);
		
		assertEquals(testPanel.getLevel(), levelToPlay, 0);
	}
	
	@Test 
	public void gameFrameGetScoreTest() {
		GameFrame testFrame = new GameFrame(1);
		
		int expectedScore = 0;
		
		assertEquals(testFrame.getScore(), expectedScore, 0);
	}
	
	@Test
	public void incrementScoreTest() {
		GameFrame testFrame = new GameFrame(1);
		GamePanel testPanel = new GamePanel(testFrame);
		
		testPanel.incrementScore();
		int expectedScore = 10;
		
		assertEquals(testFrame.getScore(), expectedScore, 0);
	}
	
};
