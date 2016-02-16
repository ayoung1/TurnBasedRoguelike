package screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import figure.Combatant;
import icon.Cursor;
import icon.Icon;
import world.World;

public class LookScreen implements Screen{

	private Combatant combatant;
	private World world;
	private Cursor cursor;
	private int offset;

	public LookScreen(Combatant combatant, World world, int offset){
		assert(combatant != null);
		assert(world != null);
		
		this.combatant = combatant;
		this.world = world;
		this.offset = offset;
		this.cursor = new Cursor(new Icon('X', AsciiPanel.brightRed), this.world, this.combatant.getX(), this.combatant.getY());
		this.cursor.update(null);
	}
	
	private void displayInstructions(AsciiPanel terminal){
		int offset = this.offset;
		int height = this.world.getHeight();
		
		terminal.write("Actions: ", offset, height+1);
		offset += 3;
		
		terminal.write("esc:return", offset, height+2);
	}
	
	private void displayCursorInformation(AsciiPanel terminal){
		Combatant c = this.world.combatantAt(this.cursor.getX(), this.cursor.getY());
		if(c != null)
			c.displayInformation(terminal, (this.offset*3)+1, 1);
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		this.displayInstructions(terminal);
		this.displayCursorInformation(terminal);
		this.cursor.printToTerminal(terminal, this.cursor.getX() + this.offset, this.cursor.getY()+1);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		this.cursor.update(key);
		
		if(key.getKeyCode() == KeyEvent.VK_ESCAPE)
			return null;
		return this;
	}

}
