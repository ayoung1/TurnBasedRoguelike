package screens;

import java.awt.event.KeyEvent;
import java.util.PriorityQueue;

import engine.GameEngine;
import figure.Combatant;
import figure.Figure;
import world.World;
import asciiPanel.AsciiPanel;

public class BattleScreen implements Screen{

	private int height = 20;
	private int width = 40;
	private World world;
	private PriorityQueue<Combatant> combatants;
	private Combatant turn;
	
	public BattleScreen(){
		this.world = new World(this.width, this.height);
		this.combatants = new PriorityQueue<>();
		
		for(Figure figure : GameEngine.getParty())
			this.combatants.add(new Combatant(figure, 1));
		for(Combatant combatant : this.combatants)
			combatant.addToWorld(world);
		
		this.combatants.peek().startTurn();
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		this.turn = this.combatants.peek();
		int offset = terminal.getWidthInCharacters()/4;
		
		this.world.printToTerminal(terminal, offset, 1);
		turn.printToTerminal(terminal, 1, 1);
		terminal.write("Actions: ", offset, this.height+1);
		offset += 9;
		
		if(turn.hasMove()){
			terminal.write("m:move ", offset, this.height+2);
			offset += 7;
		}
		
		if(turn.hasAction()){
			terminal.write("a:action ", offset, this.height+2);
			offset += 9;
		}
		
		terminal.write("e:end turn", offset, this.height+2);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		if(key.getKeyCode() == KeyEvent.VK_M)
			this.turn.move();
		if(key.getKeyCode() == KeyEvent.VK_A)
			this.turn.action();
		if(key.getKeyCode() == KeyEvent.VK_E){
			this.combatants.add(this.combatants.remove());
			this.combatants.peek().startTurn();
		}
		
		return this;
	}

}
