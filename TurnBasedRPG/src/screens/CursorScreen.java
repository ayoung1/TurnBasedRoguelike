package screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import engine.GameEngine;
import figure.Combatant;
import icon.Cursor;
import icon.Icon;
import world.World;

public abstract class CursorScreen implements Screen {

	private Combatant combatant;
	private World world;
	private Cursor cursor;
	private int offset;
	
	public CursorScreen(Combatant combatant, int offset){
		assert(combatant != null);
		assert(world != null);
		
		this.combatant = combatant;
		this.world = GameEngine.getWorld();
		this.offset = offset;
		this.cursor = new Cursor(new Icon('X', AsciiPanel.brightRed), this.world, this.combatant.getX(), this.combatant.getY());
		this.cursor.update(null);
	}
	
	public Combatant getCombatant(){return this.combatant;}
	public int getOffset(){return this.offset;}
	public World getWorld(){return this.world;}
	public Cursor getCursor(){return this.cursor;}
	
	private void displayInstructions(AsciiPanel terminal){
		int offset = this.offset;
		int height = this.world.getHeight();
		
		terminal.write("Actions: ", offset, ++height);
		offset += 3;
		terminal.write("w:up ", offset, ++height);
		terminal.write("a:left ");
		terminal.write("s:down ");
		terminal.write("d:right ");
		
		
		terminal.write("esc:return", offset, ++height);
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
		return this;
	}

}
