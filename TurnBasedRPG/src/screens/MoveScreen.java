package screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import figure.Combatant;
import figure.Figure.Stat;
import trig.Trig;
import world.World;

public class MoveScreen extends CursorScreen implements Screen{

	private double movement;

	public MoveScreen(Combatant combatant, World world, int offset){
		super(combatant, offset);
		this.movement = this.getCombatant().getFigure().getStat(Stat.MOV);
	}
	
	private void displayRange(AsciiPanel terminal){
		int x = this.getCombatant().getX();
		int y = this.getCombatant().getY();
		
		for(int i = 0; i < this.getWorld().getWidth(); i++){
			for(int j = 0; j < this.getWorld().getHeight(); j++){
				if(this.getWorld().isPathable(i, j) && Trig.manhattanDistanceBetweenPoints(x, y, i, j) <= this.movement)
					terminal.write((char)178, i+this.getOffset(), j+1, AsciiPanel.white);
			}
		}
	}
	
	private void moveCombatant(){
		int x = this.getCombatant().getX();
		int y = this.getCombatant().getY();
		
		if(Trig.manhattanDistanceBetweenPoints(x, y, this.getCursor().getX(), this.getCursor().getY()) <= this.movement)
			this.getCombatant().move(this.getCursor().getX(), this.getCursor().getY());
	}
	
	
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		this.displayRange(terminal);
		super.displayOutput(terminal);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		super.respondToUserInput(key);
		if(key.getKeyCode() == KeyEvent.VK_ENTER){
			if(this.getWorld().isPathable(this.getCursor().getX(), this.getCursor().getY()))
				this.moveCombatant();
		}
		
		if(key.getKeyCode() == KeyEvent.VK_ESCAPE || !this.getCombatant().hasMove())
			return null;
		return this;
	}

}
