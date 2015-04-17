package ynachum;

import junit.framework.TestCase;
import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.launcher.Main;

public class TestColumnToColumnMove extends TestCase{

	public void testValidColumnToColumnMove() {
		Nestor nestor = new Nestor();
		GameWindow gw = Main.generateWindow(nestor, Deck.OrderBySuit);
		
		// get rid of cards so that a column to column move is revealed
		new PileToColumnMove(nestor.reserves[1], nestor.reserves[1].get(), nestor.columns[3]).doMove(nestor);
		
		Card card1 = nestor.columns[3].peek(); // will be a 4 of hearts
		Card card2 = nestor.columns[5].get();  // will be a 4 of diamonds
		
		ColumnToColumnMove move = new ColumnToColumnMove(nestor.columns[5], card2, nestor.columns[3]);
		
		// check that valid move works
		assertEquals(card1.getRank() == card2.getRank(), move.valid(nestor));
		
		move.doMove(nestor);
		
		// check that doMove works
		assertEquals(nestor.columns[5].peek().getRank(), 5);
		assertEquals(nestor.columns[3].peek().getRank(), 5);
		
		move.undo(nestor);
		
		// check that undo works
		assertEquals(nestor.columns[5].peek().getRank(), 4);
		assertEquals(nestor.columns[3].peek().getRank(), 4);
	}
	
	public void testInvalidColumnToColumnMove() {
		Nestor nestor = new Nestor();
		GameWindow gw = Main.generateWindow(nestor, Deck.OrderBySuit);
		
		Card card1 = nestor.columns[0].peek(); // will be a 8 of spades
		Card card2 = nestor.columns[1].get();  // will be a 2 of spades
		
		ColumnToColumnMove move = new ColumnToColumnMove(nestor.columns[1], card2, nestor.columns[0]);
		
		assertEquals(card1.getRank() == card2.getRank(), move.valid(nestor));
	
		move.doMove(nestor);
		
		// check that doMove works as intended
		assertEquals(nestor.columns[0].peek().getRank(), 8);
		assertEquals(nestor.columns[1].peek().getRank(), 2);
	}

}
