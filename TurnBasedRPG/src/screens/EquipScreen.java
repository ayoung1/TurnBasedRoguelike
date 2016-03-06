package screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import engine.GameEngine;
import figure.Figure;

public class EquipScreen implements Screen{

	private class EquipOptions extends MenuBlock{
		public EquipOptions(int x, int y, int i, int j) {
			super(GameEngine.getInventory(), x, y, i, j);
		}
	}
	
	private Figure figure;
	private Screen screen;
	private Screen figureFragment;
	private int third = GameEngine.getTerminal().getWidthInCharacters() / 3;
	
	public EquipScreen(Figure figure){
		assert(figure != null);
		this.figure = figure;
		this.screen = new EquipOptions(0,0,this.third-1,GameEngine.getTerminal().getHeightInCharacters()-1);
		this.figureFragment = new ScrollableTextView(this.figure, this.third, 0, GameEngine.getTerminal().getWidthInCharacters()-1,(GameEngine.getTerminal().getHeightInCharacters()/2));
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
		this.figureFragment.respondToUserInput(key);
		if(key.getKeyCode() == KeyEvent.VK_ESCAPE)
			return null;
		return this;
	}
}