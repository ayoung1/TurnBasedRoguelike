package screens;

import java.awt.event.KeyEvent;
import java.util.List;

import asciiPanel.AsciiPanel;
import engine.GameEngine;
import items.Item;

public class InventoryScreen extends MenuBlock{

	private List<Item> inventory = GameEngine.getInventory();
	
	public InventoryScreen(int x, int y, int i, int j){
		super(GameEngine.getInventory(),x,y,i,j);
	}
	
	private void displayMembers(){
		int height = this.point1[1]+1;
		for(Item i : this.inventory)
			GameEngine.getTerminal().write(i.getName(), this.point1[0]+2, height++);
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		super.displayOutput(terminal);
		this.displayMembers();
	}
	
	@Override
	public Screen respondToUserInput(KeyEvent key) {
		super.respondToUserInput(key);
		if(key.getKeyCode() == KeyEvent.VK_ENTER)
			return null;
		if(key.getKeyCode() == KeyEvent.VK_ESCAPE)
			return null;
		return this;
	}
}