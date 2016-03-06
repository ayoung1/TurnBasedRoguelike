package screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import engine.Factory;
import engine.GameEngine;
import figure.Figure;

public class RecruitScreen extends MenuBlock{

	private static final int amount = 10;
	
	public RecruitScreen(int x, int y, int i, int j){
		super(Factory.makeFigure(amount, true),x,y,i,j);
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		super.displayOutput(terminal);
	}
	
	@Override
	public Screen respondToUserInput(KeyEvent key) {
		if(key.getKeyCode() == KeyEvent.VK_ENTER && this.getList().size() != 0){
			Figure temp = (Figure) this.getList().remove(this.getSelectedOption());
			if(!GameEngine.addToParty(temp))
				GameEngine.addToReserves(temp);
		}
		super.respondToUserInput(key);
		if(key.getKeyCode() == KeyEvent.VK_ESCAPE)
			return null;
		return this;
	}
}