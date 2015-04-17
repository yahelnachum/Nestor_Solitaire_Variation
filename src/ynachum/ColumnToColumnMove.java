package ynachum;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;

public class ColumnToColumnMove extends Move{

	Column sourceColumn;
	Column targetColumn;
	Card sourceCard;
	Card targetCard;
	
	/**
	 * Constructor for ColumnToColumnMove
	 * 
	 * @param sourceColumn The column that the card came from.
	 * @param sourceCard The card being moved.
	 * @param targetColumn The column that is recieving a card.
	 */
	public ColumnToColumnMove(Column sourceColumn, Card sourceCard, Column targetColumn){
		this.sourceColumn = sourceColumn;
		this.sourceCard = sourceCard;
		this.targetColumn = targetColumn;
	}
	
	@Override
	public boolean doMove(Solitaire game) {
		if(this.valid(game)){
			// move is valid and the two cards are discarded
			this.targetCard = targetColumn.get();
			game.updateNumberCardsLeft(-2);
			game.updateScore(2);
			return true;
		}
		sourceColumn.add(sourceCard);
		return false;
	}
	@Override
	public boolean undo(Solitaire game) {
		// return each card to their respective columns
		this.targetColumn.add(targetCard);
		this.sourceColumn.add(sourceCard);
		game.updateNumberCardsLeft(2);
		game.updateScore(-2);		
		return true;
	}
	@Override
	public boolean valid(Solitaire game) {
		// if the cards have the same rank then the move is valid
		if(sourceCard.getRank() == targetColumn.peek().getRank()){
			return true;
		}
		return false;
	}
}
