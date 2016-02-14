package screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import figure.Combatant;
import icon.Cursor;
import icon.Icon;
import trig.Trig;
import world.World;

public class MoveScreen implements Screen{

	private Combatant combatant;
	private World world;
	private Cursor cursor;
	private int offset;

	public MoveScreen(Combatant combatant, World world, int offset){
		assert(combatant != null);
		assert(world != null);
		
		this.combatant = combatant;
		this.world = world;
		this.offset = offset;
		this.cursor = new Cursor(new Icon('X', AsciiPanel.brightRed), this.world, this.combatant.getX(), this.combatant.getY());
	}
	
	private void displayRange(AsciiPanel terminal){
		double movement = this.combatant.getFigure().getMovement();
		int x = this.combatant.getX();
		int y = this.combatant.getY();
		
		for(int i = 0; i < world.getWidth(); i++){
			for(int j = 0; j < this.world.getHeight(); j++){
				if(world.isPathable(i, j) && Trig.distanceBetweenPoints(x, y, i, j) <= movement)
					terminal.write((char)178, i+this.offset, j+1, AsciiPanel.white);
			}
		}
	}
	
	private void moveCombatant(){
		double movement = this.combatant.getFigure().getMovement();
		int x = this.combatant.getX();
		int y = this.combatant.getY();
		
		if(Trig.distanceBetweenPoints(x, y, this.cursor.getX(), this.cursor.getY()) <= movement)
			this.combatant.move(this.world, this.cursor.getX(), this.cursor.getY());
	}
	
	private void displayInstructions(AsciiPanel terminal){
		int offset = this.offset;
		int height = this.world.getHeight();
		
		terminal.write("Actions: ", offset, height+1);
		offset += 3;
		
		terminal.write("w:up ", offset, height+2);
		offset += 5;
		
		terminal.write("a:left ", offset, height+2);
		offset += 7;
		
		terminal.write("s:down ", offset, height+2);
		offset += 7;
		
		terminal.write("d:right ", offset, height+2);
		offset += 8;
		
		terminal.write("enter:confirm ", offset, height+2);
		offset = this.offset + 3;
		
		terminal.write("esc:return", offset, height+3);
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		this.displayRange(terminal);
		this.displayInstructions(terminal);
		this.cursor.printToTerminal(terminal, this.cursor.getX() + this.offset, this.cursor.getY()+1);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		this.cursor.update(key);
		if(key.getKeyCode() == KeyEvent.VK_ENTER){
			if(this.world.isPathable(this.cursor.getX(), this.cursor.getY()))
				this.moveCombatant();
		}
		
		if(key.getKeyCode() == KeyEvent.VK_ESCAPE || !this.combatant.hasMove())
			return null;
		return this;
	}

}
