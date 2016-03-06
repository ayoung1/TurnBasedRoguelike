package screens;

import figure.Combatant;
import figure.Combatant.Action;
import java.awt.event.KeyEvent;

import trig.Trig;
import asciiPanel.AsciiPanel;

public class FightScreen extends CursorScreen implements Screen {
	
	public FightScreen(Combatant combatant, int offset){
		super(combatant, offset);
	}
	
	private boolean inRange(int i, int j){
		double range = this.getCombatant().getFigure().getMainhand().getRange();
		int x = this.getCombatant().getX();
		int y = this.getCombatant().getY();
		
		return Trig.manhattanDistanceBetweenPoints(x, y, i, j) <= range;
	}
	
	private void displayRange(AsciiPanel terminal){
		for(int i = 0; i < this.getWorld().getWidth(); i++){
			for(int j = 0; j < this.getWorld().getHeight(); j++){
				if(inRange(i,j) && this.getWorld().isPathable(i, j))
					terminal.write((char)178, i+this.getOffset(), j+1, AsciiPanel.white);
			}
		}
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		this.displayRange(terminal);
		
		super.displayOutput(terminal);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		super.respondToUserInput(key);
		int x,y;
		Combatant c;
		
		x = this.getCursor().getX();
		y = this.getCursor().getY();
		c = this.getWorld().combatantAt(x, y);
		
		if(c != null && key.getKeyCode() == KeyEvent.VK_ENTER){
			if(c != this.getCombatant() && inRange(x, y))
				this.getCombatant().action(Action.ATTACK, c);
		}
		
		if(!this.getCombatant().hasAction() || key.getKeyCode() == KeyEvent.VK_ESCAPE)
			return null;
		return this;
	}

}
