package ynachum;

import junit.framework.TestCase;
import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.common.model.Move;
import ks.launcher.Main;

public class TestColumnToPileMove extends TestCase{

	public void testValidColumnToPileMove() {
		Nestor nestor = new Nestor();
		GameWindow gw = Main.generateWindow(nestor, Deck.OrderBySuit);
		
		Card card1 = nestor.columns[3].get();	// will be a 3 of hearts
		Card card2 = nestor.reserves[1].peek(); // will be a 3 of clubs
		
		ColumnToPileMove move = new ColumnToPileMove(nestor.columns[3], card1, nestor.reserves[1]);
		
		// check that valid move works
		assertEquals(card1.getRank() == card2.getRank(), move.valid(nestor));
		
		move.doMove(nestor);
		
		// check that doMove works
		assertEquals(nestor.columns[3].peek().getRank(), 4);
		assertEquals(nestor.reserves[1].count(), 0);
		
		move.undo(nestor);
		
		// check that undo works
		assertEquals(nestor.columns[3].peek().getRank(), 3);
		assertEquals(nestor.reserves[1].peek().getRank(), 3);
	}
	
	public void testInvalidColumnToPileMove() {
		Nestor nestor = new Nestor();
		GameWindow gw = Main.generateWindow(nestor, Deck.OrderBySuit);
		
		Card card1 = nestor.columns[2].get(); // will be a 9 of hearts
		Card card2 = nestor.reserves[1].peek(); // will be a 3 of clubs
		
		ColumnToPileMove move = new ColumnToPileMove(nestor.columns[2], card1, nestor.reserves[1]);
		
		assertEquals(card1.getRank() == card2.getRank(), move.valid(nestor));
	
		move.doMove(nestor);
		
		// check that doMove works as intended
		assertEquals(nestor.columns[2].peek().getRank(), 9);
		assertEquals(nestor.reserves[1].count(), 1);
	}

}
