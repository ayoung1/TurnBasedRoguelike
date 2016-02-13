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
		
		this.displayBorders(terminal);
		this.displayTurnOrder(terminal);
		
		if(this.subscreen == null)
			this.displayOptions(terminal);
		
		this.world.printToTerminal(terminal, this.offset, 1);
	}
	
	private void displayBorders(AsciiPanel terminal){
		int width = this.offset-1;
		int height = terminal.getHeightInCharacters()-1;
		
		terminal.write((char)201, 0, 0);
		terminal.write((char)187, width, 0);
		terminal.write((char)188, width, height);
		terminal.write((char)200, 0, height);
		
		for(int i = 1; i < width; i++){
			terminal.write((char)205, i, 0);
			terminal.write((char)205, i, height);
		}
		for(int i = 1; i < height; i++){
			terminal.write((char)186, 0, i);
			terminal.write((char)186, width, i);
		}
	}
	
	private void displayOptions(AsciiPanel terminal){		
		int offset = this.offset;
		
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
			if(key.getKeyCode() == KeyEvent.VK_M)
				this.subscreen = new MoveScreen(this.turn, this.world, this.offset);
			if(key.getKeyCode() == KeyEvent.VK_A)
				this.turn.action();
			if(key.getKeyCode() == KeyEvent.VK_E){
				this.combatants.add(this.combatants.remove());
				this.combatants.peek().startTurn();
			}
		}
		return this;
	}

}
