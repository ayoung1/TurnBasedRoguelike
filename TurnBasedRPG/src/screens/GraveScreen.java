package screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import engine.GameEngine;

public class GraveScreen extends MenuBlock{

	public GraveScreen(int x, int y, int i, int j){
		super(GameEngine.getGraveyard(),x,y,i,j);
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		super.displayOutput(terminal);
	}
	
	@Override
	public Screen respondToUserInput(KeyEvent key) {
		super.respondToUserInput(key);
		if(key.getKeyCode() == KeyEvent.VK_ESCAPE)
			return null;
		
		return this;
	}
}