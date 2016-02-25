package screens;

import java.awt.event.KeyEvent;

import engine.GameEngine;
import asciiPanel.AsciiPanel;

public class MenuScreen implements Screen {

	@Override
	public void displayOutput(AsciiPanel terminal) {
		int x = terminal.getWidthInCharacters()/4;
		int y = terminal.getHeightInCharacters()/4;
		int x2 = (x*3)-1;
		int y2 = y*3;
		
		for(int i = y; i < y2; i++){
			for(int j = x; j < x2; j++)
				terminal.write(" ", j, i);
		}
		GameEngine.displayBorders(x, y, x2, y2);
		
		terminal.writeCenter("q:quit", --y2);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		if(key.getKeyCode() == KeyEvent.VK_Q)
			System.exit(0);
		
		if(key.getKeyCode() == KeyEvent.VK_ESCAPE)
			return null;
		return this;
	}
}
