package screens;

import java.awt.event.KeyEvent;
import java.util.List;

import asciiPanel.AsciiPanel;
import engine.GameEngine;
import figure.Figure;
import items.Item;

public class EquipScreen implements Screen{

	private class EquipOptions extends MenuBlock{

		List<Item> items;
		
		public EquipOptions(List<Item> items, int x, int y, int i, int j) {
			super(items.size(), x, y, i, j);
			this.items = items;
		}
		
		@Override
		public void displayOutput(AsciiPanel terminal){
			int height = this.point1[1]+1;
			int counter = 0;
			super.displayOutput(terminal);
			
			while(height < this.point2[1] && counter < this.items.size())
				terminal.write(this.items.get(counter++).getName(), this.point1[0]+2, height++);
		}
	}
	
	private Figure figure;
	private Screen screen;
	private Screen figureFragment;
	private int third = GameEngine.getTerminal().getWidthInCharacters() / 3;
	
	public EquipScreen(Figure figure){
		assert(figure != null);
		this.figure = figure;
		this.screen = new EquipOptions(GameEngine.getInventory(), 0,0,this.third-1,GameEngine.getTerminal().getHeightInCharacters()-1);
		this.figureFragment = new FigureFragment(figure, this.third, 0, GameEngine.getTerminal().getWidthInCharacters()-1,GameEngine.getTerminal().getHeightInCharacters()/2);
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		GameEngine.clearScreen();
		this.figureFragment.displayOutput(terminal);
		this.screen.displayOutput(terminal);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		this.screen.respondToUserInput(key);
		return null;
	}
}