package screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class StartScreen implements Screen{

	private Screen subscreen;
	@Override
	public void displayOutput(AsciiPanel terminal) {		
		if(this.subscreen == null){
			terminal.writeCenter("Start Screen", 1);
			terminal.writeCenter("<-- Press [enter] to start -->", 22);
		}else
			this.subscreen.displayOutput(terminal);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		if(this.subscreen == null)
			this.subscreen = key.getKeyCode() == KeyEvent.VK_ENTER ? new CharacterCreationScreen() : null;
		else
			this.subscreen = this.subscreen.respondToUserInput(key);	
		return this;
	}

}
