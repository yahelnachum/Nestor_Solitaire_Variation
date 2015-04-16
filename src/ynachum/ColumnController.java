package ynachum;

import java.awt.event.MouseEvent;

import ks.common.controller.SolitaireReleasedAdapter;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Pile;
import ks.common.view.CardView;
import ks.common.view.ColumnView;
import ks.common.view.Container;
import ks.common.view.Widget;

public class ColumnController extends SolitaireReleasedAdapter {

	ColumnView col;
	
	/**
	 * Constructor for ColumnController class
	 * 
	 * @param theGame the nestor game
	 * @param col The column view that the controller uses
	 */
	public ColumnController(Nestor theGame, ColumnView col) {
		super(theGame);
		this.col = col;
	}
	
	/**
	 * Coordinate reaction to the beginning of a Drag Event.
	 * <p>
	 * @param me java.awt.event.MouseEvent
	 */
	public void mousePressed(MouseEvent me) {
	 
		// The container manages several critical pieces of information; namely, it
		// is responsible for the draggingObject; in our case, this would be a CardView
		// Widget managing the card we are trying to drag between two piles.
		Container c = theGame.getContainer();
		
		/** Return if there is no card to be chosen. */
		Column sourceColumn = (Column) col.getModelElement();
		if (sourceColumn.count() == 0) {
			c.releaseDraggingObject();
			return;
		}
	
		// Get a card to move from ColumnView. Note: this returns a CardView.
		CardView cardView = col.getCardViewForTopCard (me);
		
		// an invalid selection of some sort.
		if (cardView == null) {
			c.releaseDraggingObject();
			return;
		}
		
		// If we get here, then the user has indeed clicked on the top card in the ColumnView and
		// we are able to now move it on the screen at will. For smooth action, the bounds for the
		// cardView widget reflect the original card location on the screen.
		Widget w = c.getActiveDraggingObject();
		if (w != Container.getNothingBeingDragged()) {
			System.err.println ("ColumnController::mousePressed(): Unexpectedly encountered a Dragging Object during a Mouse press.");
			return;
		}
	
		// Tell container which object is being dragged, and where in that widget the user clicked.
		c.setActiveDraggingObject (cardView, me);
		
		// Tell container which source widget initiated the drag
		c.setDragSource (col);
	
		// The only widget that could have changed is ourselves. If we called refresh, there
		// would be a flicker, because the dragged widget would not be redrawn. We simply
		// force the image to be updated, but nothing is refreshed on the screen.
		// This is patently OK because the card has not yet been dragged away to reveal the
		// card beneath it.  A bit tricky and I like it!
		col.redraw();
	}
	
	/**
	 * Coordinate reaction to the completion of a Drag Event.
	 * <p>
	 * A bit of a challenge to construct the appropriate move, because cards
	 * can be dragged both from the Columns (as a CardView widget) and the 
	 * Reserves (as a CardView widget).
	 * <p>
	 * @param me java.awt.event.MouseEvent
	 */
	public void mouseReleased(MouseEvent me) {
		Container c = theGame.getContainer();

		/** Return if there is no card being dragged chosen. */
		Widget w = c.getActiveDraggingObject();
		if (w == Container.getNothingBeingDragged()) {
			c.releaseDraggingObject();		
			return;
		}

		/** Recover the from Column */
		Widget fromWidget = c.getDragSource();
		if (fromWidget == null) {
			System.err.println ("ColumnController::mouseReleased(): somehow no dragSource in container.");
			c.releaseDraggingObject();
			return;
		}

		// Determine the To Column
		Column targetColumn = (Column) col.getModelElement();

		// If the source was a column then make a column to column move
		if(fromWidget.getModelElement().getName().contains("column")){
			Column sourceColumn = (Column) fromWidget.getModelElement();
			Card sourceCard = (Card) w.getModelElement();
			
			ColumnToColumnMove move = new ColumnToColumnMove(sourceColumn, sourceCard, targetColumn);
			if(move.doMove(theGame)){
				theGame.pushMove(move);
				theGame.refreshWidgets();
			}
			else{
				// return the card to source column if move is invalid
				fromWidget.returnWidget(w);
			}
		}
		// else if the source was a reserve then make a pile to column move
		else{
			Pile sourceReserve = (Pile) fromWidget.getModelElement();
			Card sourceCard = (Card) w.getModelElement();
			
			PileToColumnMove move = new PileToColumnMove(sourceReserve, sourceCard, targetColumn);
			if(move.doMove(theGame)){
				theGame.pushMove(move);
				theGame.refreshWidgets();
			}
			else{
				// return the card to source pile if move is invalid
				fromWidget.returnWidget(w);
			}
		}
		
		c.releaseDraggingObject();
		c.repaint();
	}

}
