package screens;

import java.awt.event.KeyEvent;
import java.util.List;

import asciiPanel.AsciiPanel;
import engine.GameEngine;
import figure.Figure;

public class PartyScreen extends MenuBlock{

	private List<Figure> party = GameEngine.getParty();
	
	public PartyScreen(int x, int y, int i, int j){
		super(GameEngine.getParty(),x,y,i,j);
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		super.displayOutput(terminal);
	}
	
	@Override
	public Screen respondToUserInput(KeyEvent key) {
		super.respondToUserInput(key);
		if(key.getKeyCode() == KeyEvent.VK_ENTER)
			return new FigureScreen(this.party.get(this.getSelectedOption()));
		if(key.getKeyCode() == KeyEvent.VK_ESCAPE)
			return null;
		return this;
	}
}