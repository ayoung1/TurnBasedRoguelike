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
	private int offset;
	private World world;
	private PriorityQueue<Combatant> combatants;
	private Combatant turn;
	private Screen subscreen;
	
	public BattleScreen(){
		this.world = new World(this.width, this.height);
		this.combatants = new PriorityQueue<>();
		
		for(Figure figure : GameEngine.getParty())
			this.combatants.add(new Combatant(figure, 1));
		for(Combatant combatant : this.combatants)
			combatant.addToWorld(world);
		
		this.combatants.peek().startTurn();
	}
	
	private void displayGraphics(AsciiPanel terminal){
		this.offset = (terminal.getWidthInCharacters()/4);
		
		this.world.printToTerminal(terminal, this.offset, 1);
		this.displayBorders(terminal, 0, 0);
		this.displayBorders(terminal, this.offset + this.world.getWidth(), 0);
		this.displayTurnOrder(terminal);
		
		if(this.subscreen == null)
			this.displayOptions(terminal);
	}
	
	private void displayBorders(AsciiPanel terminal, int x, int y){
		int width = x + (this.offset-1);
		int height = terminal.getHeightInCharacters()-1;
		GameEngine.displayBorders(x, y, width, height);
	}
	
	private void displayOptions(AsciiPanel terminal){		
		int offset = this.offset;
		
		terminal.write("Actions: ", offset, this.height+1);
		offset += 3;
		
		if(turn.hasMove()){
			terminal.write("m:move ", offset, this.height+2);
			offset += 7;
		}
		
		if(turn.hasAction()){
			terminal.write("a:action ", offset, this.height+2);
			offset += 9;
		}
		
		terminal.write("e:end turn", offset, this.height+2);
		terminal.write("l:look", this.offset+3, this.height+3);
		terminal.write("esc:menu", this.offset+10, this.height+3);
	}
	
	private void displayTurnOrder(AsciiPanel terminal){
		terminal.write("Order:", 1, 1);
		int i = 0;
		
		for(Combatant c : this.combatants){
			c.printToTerminal(terminal, 1, 2+i);
			i++;
		}
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		this.turn = this.combatants.peek();
		
		this.displayGraphics(terminal);
		
		if(this.subscreen != null)
			this.subscreen.displayOutput(terminal);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		if(this.subscreen != null)
			this.subscreen = this.subscreen.respondToUserInput(key);
		else{
			if(this.turn.hasMove() && key.getKeyCode() == KeyEvent.VK_M)
				this.subscreen = new MoveScreen(this.turn, this.world, this.offset);
			if(this.turn.hasAction() && key.getKeyCode() == KeyEvent.VK_A)
				this.subscreen = new ActionScreen(this.turn, this.world, this.offset);
			if(key.getKeyCode() == KeyEvent.VK_L)
				this.subscreen = new LookScreen(this.turn, this.world, this.offset);
			if(key.getKeyCode() == KeyEvent.VK_ESCAPE)
				this.subscreen = new MenuScreen();
			if(key.getKeyCode() == KeyEvent.VK_E){
				this.combatants.add(this.combatants.remove());
				this.combatants.peek().startTurn();
			}
		}
		return this;
	}

}
