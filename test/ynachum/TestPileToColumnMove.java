package ynachum;

import junit.framework.TestCase;
import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.common.model.Move;
import ks.launcher.Main;

public class TestPileToColumnMove extends TestCase{

	public void testValidPileToColumnMove() {
		Nestor nestor = new Nestor();
		GameWindow gw = Main.generateWindow(nestor, Deck.OrderBySuit);
		
		Card card1 = nestor.columns[3].peek();	// will be a 3 of hearts
		Card card2 = nestor.reserves[1].get(); // will be a 3 of clubs
		
		PileToColumnMove move = new PileToColumnMove(nestor.reserves[1], card2, nestor.columns[3]);
		
		// check that valid move works
		assertTrue(move.valid(nestor));
		
		move.doMove(nestor);
		
		// check that doMove works
		assertEquals(nestor.columns[3].peek().getRank(), 4);
		assertEquals(nestor.reserves[1].count(), 0);
		
		move.undo(nestor);
		
		// check that undo works
		assertEquals(nestor.columns[3].peek().getRank(), 3);
		assertEquals(nestor.reserves[1].peek().getRank(), 3);
	}
	
	public void testInvalidPileToColumnMove() {
		Nestor nestor = new Nestor();
		GameWindow gw = Main.generateWindow(nestor, Deck.OrderBySuit);
		
		Card card1 = nestor.columns[2].peek(); // will be a 9 of hearts
		Card card2 = nestor.reserves[1].get(); // will be a 3 of clubs
		
		PileToColumnMove move = new PileToColumnMove(nestor.reserves[1], card2, nestor.columns[2]);
		
		assertFalse(move.valid(nestor));
	
		move.doMove(nestor);
		
		// check that doMove works as intended
		assertEquals(nestor.columns[2].peek().getRank(), 9);
		assertEquals(nestor.reserves[1].count(), 1);
	}

}
