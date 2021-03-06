package screens;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import engine.Factory;
import engine.GameEngine;
import figure.Combatant;
import figure.Figure;
import world.World;
import asciiPanel.AsciiPanel;

public class BattleScreen implements Screen{

	private static final int TEST_NUMBER = 1;
	
	private int height = 20;
	private int width = 40;
	private int offset;
	private World world;
	private List<Combatant> combatants;
	private Combatant turn;
	private Screen subscreen;
	
	public BattleScreen(){
		this.world = GameEngine.createWorld(this.width, this.height);
		this.combatants = new ArrayList<>();
		
		this.combatants.addAll(Combatant.combatant(Factory.makeFigure(TEST_NUMBER, false), 2));
		
		for(Figure figure : GameEngine.getParty())
			this.combatants.add(new Combatant(figure, 1));
		for(Combatant c : this.combatants)
			GameEngine.removeFromParty(c.getFigure());
		for(Combatant combatant : this.combatants)
			combatant.addToWorld();
		
		Collections.sort(this.combatants);
		
		this.combatants.get(0).startTurn();
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
		this.turn = this.combatants.get(0);
		
		this.displayGraphics(terminal);
		
		if(this.subscreen != null)
			this.subscreen.displayOutput(terminal);
	}

	private void removedDead(){
		int i = 0;
		
		do{
			if(this.combatants.get(i).isDead()){
				GameEngine.addToGraveyard(this.combatants.get(i).getFigure());
				this.combatants.remove(i);
				i = 0;
			}else
				i++;
		}while(i < this.combatants.size());
	}
	
	private int countForce(int force){
		int count = 0;
		
		for(Combatant c : this.combatants){
			if(c.getForce() == force)
				count++;
		}
		return count;
	}
	
	private boolean hasWon(){
		return this.countForce(2) == 0;
	}
	
	private boolean hasLost(){
		boolean isMainCharacterAlive = false;
		for(Combatant c : this.combatants){
			if(!isMainCharacterAlive)
				isMainCharacterAlive = c.getFigure() == GameEngine.getMainFigure();
		}
		
		return !isMainCharacterAlive;
	}
	
	private void cleanup(){
		for(Combatant c : this.combatants)
			GameEngine.addToParty(c.getFigure());
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
				this.subscreen = new LookScreen(this.turn, this.offset);
			if(key.getKeyCode() == KeyEvent.VK_ESCAPE)
				this.subscreen = new MenuScreen();
			if(key.getKeyCode() == KeyEvent.VK_E){
				this.combatants.add(this.combatants.remove(0));
				this.combatants.get(0).startTurn();
			}
		}
		this.removedDead();
		
		if(this.hasWon() || this.hasLost()){
			this.cleanup();
			return null;
		}
		return this;
	}

}
