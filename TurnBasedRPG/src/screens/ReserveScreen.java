package screens;

import java.awt.event.KeyEvent;
import java.util.List;

import asciiPanel.AsciiPanel;
import engine.GameEngine;
import figure.Figure;

public class ReserveScreen extends MenuBlock{

	private List<Figure> party = GameEngine.getReserves();
	
	public ReserveScreen(int x, int y, int i, int j){
		super(GameEngine.getReserves(),x,y,i,j);
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		super.displayOutput(terminal);
	}
	
	@Override
	public Screen respondToUserInput(KeyEvent key) {
		if(key.getKeyCode() == KeyEvent.VK_ENTER && this.getList().size() != 0)
			return new FigureScreen(this.party.get(this.getSelectedOption()));
		super.respondToUserInput(key);
		if(key.getKeyCode() == KeyEvent.VK_ESCAPE)
			return null;
		return this;
	}
}