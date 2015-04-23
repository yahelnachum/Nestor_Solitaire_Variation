package ynachum;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

import junit.framework.TestCase;
import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Deck;
import ks.common.model.Pile;
import ks.launcher.Main;

import org.junit.Test;

public class TestGameWon extends TestCase {
	
	@Test
	public void testGameWon() throws InterruptedException, AWTException{
		Nestor nestor = new Nestor();
		GameWindow gw = Main.generateWindow(nestor, Deck.OrderBySuit);
		int sleepTime = 100;
		
		for(Column c: nestor.columns){
			while(!c.empty()){
				c.get();
			}
		}
		
		for(Pile r: nestor.reserves){
			r.get();
		}
		
		nestor.columns[0].add(new Card(1, 1));
		nestor.columns[1].add(new Card(1, 2));
		nestor.updateNumberCardsLeft(-50);
		
		// make move
		Robot bot = new Robot();
		bot.setAutoDelay(sleepTime);
	    bot.mouseMove(50, 100);    
	    bot.mousePress(InputEvent.BUTTON1_MASK);
	    RobotDrag.robotDragMouse(bot, 50, 100, 150, 100, sleepTime, 50, sleepTime);
	    bot.mouseRelease(InputEvent.BUTTON1_MASK);
		
	    for(Column c: nestor.columns){
	    	assertEquals(c.count(), 6);
	    }
	    for(Pile r: nestor.reserves){
	    	assertEquals(r.count(), 1);
	    }	    
	}

}
