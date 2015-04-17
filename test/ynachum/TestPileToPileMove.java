package ynachum;

import junit.framework.TestCase;
import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.launcher.Main;

import org.junit.Test;

public class TestPileToPileMove extends TestCase{

	@Test
	public void testValidPileToPileMove() {
		Nestor nestor = new Nestor();
		GameWindow gw = Main.generateWindow(nestor, Deck.OrderByRank);
		//gw.
		Card card1 = nestor.reserves[0].peek();	// will be a a of spades
		Card card2 = nestor.reserves[1].get();  // will be a a of hearts
		
		PileToPileMove move = new PileToPileMove(nestor.reserves[1], card2, nestor.reserves[0]);
		
		// check that valid move works
		assertTrue(move.valid(nestor));
		
		move.doMove(nestor);
		
		// check that doMove works
		assertEquals(nestor.reserves[0].count(), 0);
		assertEquals(nestor.reserves[1].count(), 0);
		
		move.undo(nestor);
		
		// check that undo works
		assertEquals(nestor.reserves[0].peek().getRank(), 1);
		assertEquals(nestor.reserves[1].peek().getRank(), 1);
	}
	
	@Test
	public void testInvalidPileToPileMove() {
		Nestor nestor = new Nestor();
		GameWindow gw = Main.generateWindow(nestor, Deck.OrderBySuit);
		
		Card card1 = nestor.reserves[0].peek(); // will be a 4 of clubs
		Card card2 = nestor.reserves[1].get();  // will be a 3 of clubs
		
		PileToPileMove move = new PileToPileMove(nestor.reserves[1], card2, nestor.reserves[0]);
		
		assertFalse(move.valid(nestor));
	
		move.doMove(nestor);
		
		// check that doMove works as intended
		assertEquals(nestor.reserves[0].peek().getRank(), 4);
		assertEquals(nestor.reserves[1].peek().getRank(), 3);
	}
	
	@Test
	public void testInvalidPileToSamePileMove() {
		Nestor nestor = new Nestor();
		GameWindow gw = Main.generateWindow(nestor, Deck.OrderBySuit);
		
		Card card2 = nestor.reserves[1].get();  // will be a 3 of clubs
		
		PileToPileMove move = new PileToPileMove(nestor.reserves[1], card2, nestor.reserves[1]);
		
		assertFalse(move.valid(nestor));
	
		move.doMove(nestor);
		
		// check that doMove works as intended
		assertEquals(nestor.reserves[1].peek().getRank(), 3);
	}

}
