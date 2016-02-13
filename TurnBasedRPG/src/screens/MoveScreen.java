package screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import figure.Combatant;
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
		
		for(int i = 0; i < world.getHeight(); i++){
			for(int j = 0; j < this.world.getWidth(); j++){
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
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		this.displayRange(terminal);
		this.cursor.printToTerminal(terminal, this.cursor.getX() + this.offset, this.cursor.getY()+1);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		if(key.getKeyCode() == KeyEvent.VK_W)
			this.cursor.update(0, -1);
		if(key.getKeyCode() == KeyEvent.VK_S)
			this.cursor.update(0, 1);
		if(key.getKeyCode() == KeyEvent.VK_D)
			this.cursor.update(1, 0);
		if(key.getKeyCode() == KeyEvent.VK_A)
			this.cursor.update(-1, 0);
		if(key.getKeyCode() == KeyEvent.VK_ENTER)
			this.moveCombatant();
		
		if(key.getKeyCode() == KeyEvent.VK_ESCAPE)
			return null;
		return this;
	}

}
