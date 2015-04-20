package ynachum;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

import junit.framework.TestCase;
import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.launcher.Main;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestColumnController extends TestCase{

	Nestor nestor;
	GameWindow gw;
	int sleepTime = 100;
	
	@Before
	public void setUp() throws Exception {
		nestor = new Nestor();
		gw = Main.generateWindow(nestor, Deck.OrderBySuit);
		
		nestor.columns[2].get();
		nestor.columns[2].add(new Card(3, 2));
	}
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	@After
	public void tearDown(){
		gw.dispose();
	}

	@Test
	public void testValidColumnToPileController() throws AWTException, InterruptedException{
		// make move
		Robot bot = new Robot();
		bot.setAutoDelay(sleepTime);
	    bot.mouseMove(350, 250);    
	    bot.mousePress(InputEvent.BUTTON1_MASK);
	    RobotDrag.robotDragMouse(bot, 350, 250, 350, 400, sleepTime, 50, sleepTime);
	    bot.mouseRelease(InputEvent.BUTTON1_MASK);
	    
	    // validate that move works as intended
	    assertEquals(nestor.columns[3].peek().getRank(), 4);
	    assertEquals(nestor.reserves[1].count(), 0);
	}
	
	@Test
	public void testValidColumnToColumnController() throws AWTException, InterruptedException{
		// make move
		Robot bot = new Robot();
		bot.setAutoDelay(sleepTime);
	    bot.mouseMove(350, 250);    
	    bot.mousePress(InputEvent.BUTTON1_MASK);
	    RobotDrag.robotDragMouse(bot, 350, 250, 250, 250, sleepTime, 50, sleepTime);
	    bot.mouseRelease(InputEvent.BUTTON1_MASK);
	    
	    // validate that move works as intended
	    assertEquals(nestor.columns[3].peek().getRank(), 4);
	    assertEquals(nestor.columns[2].peek().getRank(), 10);
	}
	
	@Test
	public void testInvalidColumnController() throws AWTException, InterruptedException{
		// make move
		Robot bot = new Robot();
		bot.setAutoDelay(sleepTime);
	    bot.mouseMove(350, 250);    
	    bot.mousePress(InputEvent.BUTTON1_MASK);
	    RobotDrag.robotDragMouse(bot, 350, 250, 450, 250, sleepTime, 50, sleepTime);
	    bot.mouseMove(450, 250);
	    bot.mouseRelease(InputEvent.BUTTON1_MASK);

	    
	    // validate that move works as intended
	    assertEquals(nestor.columns[3].peek().getRank(), 3);
	    assertEquals(nestor.columns[4].peek().getRank(), 10);
	}
}
