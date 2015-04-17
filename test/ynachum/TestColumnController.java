package ynachum;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

import junit.framework.TestCase;
import ks.client.gamefactory.GameWindow;
import ks.common.model.Deck;
import ks.launcher.Main;

import org.junit.Before;
import org.junit.Test;

public class TestColumnController extends TestCase{

	Nestor nestor;
	GameWindow gw;
	int sleepTime = 500;
	
	@Before
	public void setUp() throws Exception {
		nestor = new Nestor();
		gw = Main.generateWindow(nestor, Deck.OrderBySuit);
	}

	@Test
	public void testValidColumnController() throws AWTException, InterruptedException{
		// make move
		Robot bot = new Robot();
	    Thread.sleep(sleepTime);
	    bot.mouseMove(350, 250);    
	    bot.mousePress(InputEvent.BUTTON1_MASK);
	    Thread.sleep(sleepTime);
	    bot.mouseMove(350, 400);
	    Thread.sleep(sleepTime);
	    bot.mouseRelease(InputEvent.BUTTON1_MASK);
	    Thread.sleep(sleepTime);
	    
	    // validate that move works as intended
	    assertEquals(nestor.columns[3].peek().getRank(), 4);
	    assertEquals(nestor.reserves[1].count(), 0);
	}
	
	@Test
	public void testInvalidColumnController() throws AWTException, InterruptedException{
		// make move
		Robot bot = new Robot();
	    Thread.sleep(sleepTime);
	    bot.mouseMove(350, 250);    
	    bot.mousePress(InputEvent.BUTTON1_MASK);
	    Thread.sleep(sleepTime);
	    bot.mouseMove(450, 250);
	    Thread.sleep(sleepTime);
	    bot.mouseRelease(InputEvent.BUTTON1_MASK);
	    Thread.sleep(sleepTime);
	    
	    // validate that move works as intended
	    assertEquals(nestor.columns[3].peek().getRank(), 3);
	    assertEquals(nestor.columns[4].peek().getRank(), 10);
	}
}
