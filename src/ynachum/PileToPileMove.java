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
	
	/* (non-Javadoc)
	 * @see ks.common.model.Move#doMove(ks.common.games.Solitaire)
	 * 
	 * Does a pile to pile move if it is valid.
	 */
	@Override
	public boolean doMove(Solitaire game) {
		if(this.valid(game)){
			// move is valid and the two cards are bing discarded
			this.targetCard = targetReserve.get();
			game.updateNumberCardsLeft(-2);
			game.updateScore(2);
			return true;
		}
		sourceReserve.add(sourceCard);
		return false;
	}
	
	/* (non-Javadoc)
	 * @see ks.common.model.Move#undo(ks.common.games.Solitaire)
	 * 
	 * Undoes the effects of the move.
	 */
	@Override
	public boolean undo(Solitaire game) {
		// returns the cards to their respective piles
		this.targetReserve.add(targetCard);
		this.sourceReserve.add(sourceCard);
		game.updateNumberCardsLeft(2);
		game.updateScore(-2);		
		return true;
	}
	
	/* (non-Javadoc)
	 * @see ks.common.model.Move#valid(ks.common.games.Solitaire)
	 * 
	 * Validates whether or not the move is allowed to be done.
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
