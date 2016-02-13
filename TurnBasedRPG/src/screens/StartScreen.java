package screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class StartScreen implements Screen{

	@Override
	public void displayOutput(AsciiPanel terminal) {		
		terminal.writeCenter("Start Screen", 1);
		terminal.writeCenter("<-- Press [enter] to start -->", 22);		
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		return key.getKeyCode() == KeyEvent.VK_ENTER ? new BattleScreen() : this;
	}

}
