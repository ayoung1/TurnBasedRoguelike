package screens;

import figure.Combatant;
import figure.Combatant.Action;
import icon.Cursor;
import icon.Icon;

import java.awt.event.KeyEvent;

import trig.Trig;
import world.World;
import asciiPanel.AsciiPanel;

public class FightScreen implements Screen {

	private Combatant combatant;
	private World world;
	private Cursor cursor;
	private int offset;
	
	public FightScreen(Combatant combatant, World world, int offset){
		assert(combatant != null);
		assert(world != null);
		
		this.combatant = combatant;
		this.world = world;
		this.offset = offset;
		this.cursor = new Cursor(new Icon('X', AsciiPanel.brightRed), this.world, this.combatant.getX(), this.combatant.getY());
	}
	
	private boolean inRange(int i, int j){
		double range = this.combatant.getFigure().getMainhand().getRange();
		int x = this.combatant.getX();
		int y = this.combatant.getY();
		
		return Trig.distanceBetweenPoints(x, y, i, j) <= range;
	}
	
	private void displayRange(AsciiPanel terminal){
		for(int i = 0; i < world.getWidth(); i++){
			for(int j = 0; j < this.world.getHeight(); j++){
				if(inRange(i,j) && world.isPathable(i, j))
					terminal.write((char)178, i+this.offset, j+1, AsciiPanel.white);
			}
		}
	}
	
	private void displayCursorInformation(AsciiPanel terminal){
		Combatant c = this.world.combatantAt(this.cursor.getX(), this.cursor.getY());
		if(c != null)
			c.displayInformation(terminal, (this.offset*3)+1, 1);
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		this.displayRange(terminal);
		this.displayCursorInformation(terminal);
		this.cursor.printToTerminal(terminal, this.cursor.getX() + this.offset, this.cursor.getY()+1);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		int x,y;
		Combatant c;
		this.cursor.update(key);
		
		x = this.cursor.getX();
		y = this.cursor.getY();
		c = this.world.combatantAt(x, y);
		
		if(c != null && key.getKeyCode() == KeyEvent.VK_ENTER){
			if(c != this.combatant && inRange(x, y))
				this.combatant.action(Action.ATTACK, c);
		}
		
		if(!this.combatant.hasAction() || key.getKeyCode() == KeyEvent.VK_ESCAPE)
			return null;
		return this;
	}

}
