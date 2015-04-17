package ynachum;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.Pile;

public class PileToColumnMove extends Move{

	Pile sourceReserve;
	Column targetColumn;
	Card sourceCard;
	Card targetCard;
	
	/**
	 * Constructor for the PileToColumnMove
	 * 
	 * @param sourceReserve The reserve that the card is coming from.
	 * @param sourceCard The card that is being moved.
	 * @param targetColumn The column that the card is going to.
	 */
	public PileToColumnMove(Pile sourceReserve, Card sourceCard, Column targetColumn){
		this.sourceReserve = sourceReserve;
		this.sourceCard = sourceCard;
		this.targetColumn = targetColumn;
	}
	
	@Override
	public boolean doMove(Solitaire game) {
		if(this.valid(game)){
			// the move is valid and the two 
			this.targetCard = targetColumn.get();
			game.updateNumberCardsLeft(-2);
			game.updateScore(2);
			return true;
		}
		
		sourceReserve.add(sourceCard);
		return false;
	}
	@Override
	public boolean undo(Solitaire game) {
		// Returns the cards to their repective pile/column
		this.targetColumn.add(targetCard);
		this.sourceReserve.add(sourceCard);
		game.updateNumberCardsLeft(2);
		game.updateScore(-2);		
		return true;
	}
	@Override
	public boolean valid(Solitaire game) {
		// if the cards are of the same rank then it is a valid move
		if(sourceCard.getRank() == targetColumn.peek().getRank()){
			return true;
		}
		return false;
	}
}
