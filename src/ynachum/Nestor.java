package ynachum;

import ks.common.controller.SolitaireMouseMotionAdapter;
import ks.common.games.Solitaire;
import ks.common.games.SolitaireUndoAdapter;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Deck;
import ks.common.model.Pile;
import ks.common.model.Stack;
import ks.common.view.CardImages;
import ks.common.view.ColumnView;
import ks.common.view.IntegerView;
import ks.common.view.PileView;
import ks.launcher.Main;

public class Nestor extends Solitaire {

	Deck deck;
	Column[] columns = new Column[8];
	Pile[] reserves = new Pile[4];
	
	ColumnView[] columnViews = new ColumnView[8];
	PileView[] pileViews = new PileView[4];
	IntegerView scoreView;
	IntegerView numLeftView;
	
	
	/* (non-Javadoc)
	 * @see ks.common.games.Solitaire#getName()
	 * 
	 * Returns the variation of the solitaire game.
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "ynachum_Nestor";
	}

	/* (non-Javadoc)
	 * @see ks.common.games.Solitaire#hasWon()
	 * 
	 * Returns whether or not the game has been won.
	 */
	@Override
	public boolean hasWon() {
		if(this.numLeft.getValue() <= 0){
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see ks.common.games.Solitaire#initialize()
	 * 
	 * Initializes the game so it can be played.
	 */
	@Override
	public void initialize() {
		// initialize model
		initializeModel(getSeed());
		initializeView();
		initializeControllers();
	}

	/**
	 * Initializes the controllers to their respective boundaries.
	 */
	private void initializeControllers() {
		// Initialize column controllers
		for(ColumnView c: columnViews){
			c.setMouseAdapter(new ColumnController(this, c));
			c.setMouseMotionAdapter(new SolitaireMouseMotionAdapter(this));
			c.setUndoAdapter(new SolitaireUndoAdapter(this));
		}
		
		// Initialize reserve controllers
		for(PileView p: pileViews){
			p.setMouseAdapter(new ReserveController(this, p));
			p.setMouseMotionAdapter(new SolitaireMouseMotionAdapter(this));
			p.setUndoAdapter(new SolitaireUndoAdapter(this));
		}
	}

	/**
	 * Initializes the boundaries with their respective entities
	 */
	private void initializeView() {
		CardImages ci = getCardImages();

		// Initialize columns boundaries
		for(int i = 0; i < columnViews.length; i++){
			columnViews[i] = new ColumnView (columns[i]);
			columnViews[i].setBounds (20*(i+1)+(i*ci.getWidth()),20, ci.getWidth(), ci.getHeight()+6*ci.getOverlap());
			container.addWidget (columnViews[i]);
		}
		
		// Initialize reserve boundaries
		for(int i = 0; i < pileViews.length; i++){
			pileViews[i] = new PileView (reserves[i]);
			pileViews[i].setBounds (20*(i+1)+(i*ci.getWidth())+((20+ci.getWidth())*2),20+ci.getHeight()+ci.getOverlap()*6, ci.getWidth(), ci.getHeight());
			container.addWidget (pileViews[i]);
		}
		
		// Initialize score count boundary
		scoreView = new IntegerView (getScore());
		scoreView.setFontSize (14);
		scoreView.setBounds (20*5+5*ci.getWidth(), 500, 100, 60);
		container.addWidget (scoreView);

		// Initialize cards left count boundary
		numLeftView = new IntegerView (getNumLeft());
		numLeftView.setFontSize (14);
		numLeftView.setBounds (20*7+7*ci.getWidth(), 500, 100, 60);
		container.addWidget (numLeftView);
	}

	/**
	 * Initialize entities
	 * 
	 * @param seed
	 */
	private void initializeModel(int seed) {
		// Initialize deck
		deck = new Deck("deck");
		deck.create(seed);
		model.addElement(deck);
		
		// TODO make new algorithm for setting up deck so that no column has the same rank in it.
		
		// Initialize columns with cards from deck
		for(int i = 0; i < columns.length; i++){
			columns[i] = new Column("column" + i);
			
			for(int j = 0; j < 6; j++){
				while(cardIsInColumn(deck.peek(), columns[i], j)){
					putTopCardAtBottomOfDeck(deck);
				}
				columns[i].add(deck.get());
			}
		}
		
		// Initialize reserves with remaining cards from deck
		for(int i = 0; i < reserves.length; i++){
			reserves[i] = new Pile("reserve" + i);
			reserves[i].add(deck.get());
		}
		
		// Update cards and score to initial states
		updateNumberCardsLeft(52);
		updateScore(0);
	}
	
	public boolean cardIsInColumn(Card card, Column column, int lastIndex){
		for(int i = 0; i < lastIndex; i++){
			if(card.sameRank(column.peek(i))){
				return true;
			}
		}
		
		return false;
	}
	
	public void putTopCardAtBottomOfDeck(Deck deck){
		Card card = deck.get();
		deck.select(deck.count());
		Stack stack = deck.getSelected();
		deck.add(card);
		deck.push(stack);
	}
	
	/** Code to launch solitaire variation. */
	public static void main (String []args) {
		// Seed is to ensure we get the same initial cards every time.
		// Here the seed is to "order by suit."
		Main.generateWindow(new Nestor(), Deck.OrderBySuit);
		
		/*Deck deck1 = new Deck("deck");
		deck1.create(0);
		
		System.out.println(deck1.peek().toString());
		Card c = deck1.get();
		deck1.select(deck1.count());
		Stack s = deck1.getSelected();
		deck1.add(c);
		deck1.push(s);
		System.out.println(deck1.peek());*/
		
	}
}
