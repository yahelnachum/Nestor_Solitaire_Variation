package ynachum;

import heineman.Klondike;
import ks.common.games.Solitaire;
import ks.common.model.Column;
import ks.common.model.Deck;
import ks.common.model.Pile;
import ks.common.view.BuildablePileView;
import ks.common.view.CardImages;
import ks.common.view.ColumnView;
import ks.common.view.DeckView;
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
	
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "ynachum_Nestor";
	}

	@Override
	public boolean hasWon() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void initialize() {
		// initialize model
		initializeModel(getSeed());
		initializeView();
		initializeControllers();

		// prepare game by dealing facedown cards to all columns, then one face up
		/*for (int pileNum=1; pileNum <= 7; pileNum++) {
			for (int num = 2; num <= pileNum; num++) {
				Card c = deck.get();

				c.setFaceUp (false);
				piles[pileNum].add (c);
			}

			// This one is face up.
			piles[pileNum].add (deck.get());
		}*/
	}

	private void initializeControllers() {
		// TODO Auto-generated method stub
		
	}

	private void initializeView() {
		CardImages ci = getCardImages();

		for(int i = 0; i < columnViews.length; i++){
			columnViews[i] = new ColumnView (columns[i]);
			columnViews[i].setBounds (20*(i+1)+(i*ci.getWidth()),20, ci.getWidth(), ci.getHeight()+6*ci.getOverlap());
			container.addWidget (columnViews[i]);
		}
		
		for(int i = 0; i < pileViews.length; i++){
			pileViews[i] = new PileView (reserves[i]);
			pileViews[i].setBounds (20*(i+1)+(i*ci.getWidth())+((20+ci.getWidth())*2),20+ci.getHeight()+ci.getOverlap()*6, ci.getWidth(), ci.getHeight());
			container.addWidget (pileViews[i]);
		}
		
		scoreView = new IntegerView (getScore());
		scoreView.setFontSize (14);
		scoreView.setBounds (20*5+5*ci.getWidth(), 500, 100, 60);
		container.addWidget (scoreView);

		numLeftView = new IntegerView (getNumLeft());
		numLeftView.setFontSize (14);
		numLeftView.setBounds (20*7+7*ci.getWidth(), 500, 100, 60);
		container.addWidget (numLeftView);
	}

	private void initializeModel(int seed) {
		deck = new Deck("deck");
		deck.create(seed);;
		model.addElement(deck);
		
		for(int i = 0; i < columns.length; i++){
			columns[i] = new Column("column" + i);
			
			for(int j = 0; j < 6; j++){
				columns[i].add(deck.get());
			}
		}
		
		for(int i = 0; i < reserves.length; i++){
			reserves[i] = new Pile("reserve" + i);
			reserves[i].add(deck.get());
		}
		
		updateNumberCardsLeft(52);
		updateScore(0);
	}
	
	/** Code to launch solitaire variation. */
	public static void main (String []args) {
		// Seed is to ensure we get the same initial cards every time.
		// Here the seed is to "order by suit."
		Main.generateWindow(new Nestor(), Deck.OrderBySuit);
	}
}
