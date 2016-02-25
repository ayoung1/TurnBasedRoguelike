package screens;

import java.awt.event.KeyEvent;
import java.util.List;

import asciiPanel.AsciiPanel;
import engine.GameEngine;
import figure.Figure;

public class PartyScreen extends MenuBlock{

	private List<Figure> party = GameEngine.getParty();
	
	public PartyScreen(int size, int x, int y, int i, int j){
		super(size,x,y,i,j);
	}
	
	private void displayMembers(){
		int height = this.point1[1]+1;
		for(Figure f : this.party)
			height = f.printInfomation(this.point1[0]+2, height);
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		super.displayOutput(terminal);
		this.displayMembers();
	}
	
	@Override
	public Screen respondToUserInput(KeyEvent key) {
		super.respondToUserInput(key);
		if(key.getKeyCode() == KeyEvent.VK_ENTER)
			return new FigureScreen(this.party.get(super.option));
		if(key.getKeyCode() == KeyEvent.VK_ESCAPE)
			return null;
		return this;
	}
}