package screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import figure.Combatant;

public class LookScreen extends CursorScreen implements Screen{

	public LookScreen(Combatant combatant, int offset){
		super(combatant, offset);
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
