package ynachum;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

import ks.client.gamefactory.GameWindow;
import ks.common.model.Deck;
import ks.launcher.Main;

import org.junit.Before;
import org.junit.Test;

public class TestColumnController {

	Nestor nestor;
	GameWindow gw;
	
	@Before
	public void setUp() throws Exception {
		nestor = new Nestor();
		gw = Main.generateWindow(nestor, Deck.OrderBySuit);
	}

	@Test
	public void testValidColumnController() throws AWTException, InterruptedException{
		Robot bot = new Robot();
	    bot.mouseMove(350, 250);    
	    bot.mousePress(InputEvent.BUTTON1_MASK);
	    Thread.sleep(1000);
	    bot.mouseMove(350, 400);
	    Thread.sleep(1000);
	    bot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	@Test
	public void testInvalidColumnController() throws AWTException, InterruptedException{
		Robot bot = new Robot();
	    bot.mouseMove(350, 250);    
	    bot.mousePress(InputEvent.BUTTON1_MASK);
	    Thread.sleep(1000);
	    bot.mouseMove(450, 250);
	    Thread.sleep(1000);
	    bot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
}
