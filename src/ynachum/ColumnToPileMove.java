package ynachum;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.Pile;

public class ColumnToPileMove extends Move{

	Column sourceColumn;
	Pile targetReserve;
	Card sourceCard;
	Card targetCard;
	
	/**
	 * Constructor for ColumnToPileMove
	 * 
	 * @param sourceColumn The column that the card is coming from
	 * @param sourceCard The card that is being moved
	 * @param targetReserve The reserve that the card is going to
	 */
	public ColumnToPileMove(Column sourceColumn, Card sourceCard, Pile targetReserve){
		this.sourceColumn = sourceColumn;
		this.sourceCard = sourceCard;
		this.targetReserve = targetReserve;
	}
	
	/* (non-Javadoc)
	 * @see ks.common.model.Move#doMove(ks.common.games.Solitaire)
	 * 
	 * Does the column to pile move if it is valid. 
	 */
	@Override
	public boolean doMove(Solitaire game) {
		if(this.valid(game)){
			// the move is valid and the two cards are discarded
			this.targetCard = targetReserve.get();
			game.updateNumberCardsLeft(-2);
			game.updateScore(2);
			return true;
		}
		
		sourceColumn.add(sourceCard);
		return false;
	}
	
	/* (non-Javadoc)
	 * @see ks.common.model.Move#undo(ks.common.games.Solitaire)
	 * 
	 * Undos the effect of the move made.
	 */
	@Override
	public boolean undo(Solitaire game) {
		// return the cards to their respective pile/column
		this.targetReserve.add(targetCard);
		this.sourceColumn.add(sourceCard);
		game.updateNumberCardsLeft(2);
		game.updateScore(-2);		
		return true;
	}
	
	/* (non-Javadoc)
	 * @see ks.common.model.Move#valid(ks.common.games.Solitaire)
	 * 
	 * Validates whether or not the move is allowed to be made
	 */
	@Override
	public boolean valid(Solitaire game) {
		// if the cards are of the same rank then it is a valid move
		if(targetReserve.peek() != null && sourceCard.getRank() == targetReserve.peek().getRank()){
			return true;
		}
		return false;
	}
}
