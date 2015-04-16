package ynachum;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.Pile;

public class PileToPileMove extends Move{

	Pile sourceReserve;
	Pile targetReserve;
	Card sourceCard;
	Card targetCard;
	
	/**
	 * Constructor for PileToPileMove
	 * 
	 * @param sourceReserve The reserve that the card is coming from.
	 * @param sourceCard The card that is being moved.
	 * @param targetReserve The reserve that the card is going to.
	 */
	public PileToPileMove(Pile sourceReserve, Card sourceCard, Pile targetReserve){
		this.sourceReserve = sourceReserve;
		this.sourceCard = sourceCard;
		this.targetReserve = targetReserve;
	}
	
	@Override
	public boolean doMove(Solitaire game) {
		if(this.valid(game)){
			// move is valid and the two cards are bing discarded
			this.targetCard = targetReserve.get();
			game.updateNumberCardsLeft(-2);
			game.updateScore(2);
			return true;
		}
		return false;
	}
	@Override
	public boolean undo(Solitaire game) {
		// returns the cards to their respective piles
		this.targetReserve.add(targetCard);
		this.sourceReserve.add(sourceCard);
		game.updateNumberCardsLeft(2);
		game.updateScore(-2);		
		return true;
	}
	@Override
	public boolean valid(Solitaire game) {
		// if the cards are of the same rank then it is a valid move
		if(sourceCard.getRank() == targetReserve.peek().getRank()){
			return true;
		}
		return false;
	}
}
