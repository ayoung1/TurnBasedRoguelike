package screens;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import asciiPanel.AsciiPanel;
import engine.GameEngine;
import figure.Figure;

public class FigureScreen implements Screen {

	private Figure figure;
	private FigureOptions options;
	private Screen figureFragment;
	private int third = GameEngine.getTerminal().getWidthInCharacters() / 3;
	
	private class FigureOptions extends MenuBlock{

		public final List<ScreenOption> options;
		
		public FigureOptions(int max, int x, int y, int i, int j) {
			super(max, x, y, i, j);
			this.options = new ArrayList<>();
			this.setupScreens();
		}
		
		private void setupScreens(){
			this.options.add(new ScreenOption("Equip", null){
				@Override
				public Screen getScreen() {
					return new EquipScreen(GameEngine.getParty().get(option));
				}
			});
			
			this.options.add(new ScreenOption("Send to Reserves", null){
				@Override
				public Screen getScreen() {
					if(figure == GameEngine.getMainFigure())
						return null;
					GameEngine.removeFromParty(figure);
					GameEngine.addToReserves(figure);
					return null;
				}
			});
		}
		
		private void displayOptions(AsciiPanel terminal){
			int height = this.point1[1]+1;
			int offset = this.point1[0]+2;
			
			for(ScreenOption s : this.options){
				terminal.write(s.name, offset, height++);
			}
		}
		
		@Override
		public void displayOutput(AsciiPanel terminal){
			super.displayOutput(terminal);
			this.displayOptions(terminal);
		}
	}
	
	public FigureScreen(Figure figure){
		assert(figure != null);
		this.figure = figure;
		this.options = new FigureOptions(5, 0, 0, this.third-1, GameEngine.getTerminal().getHeightInCharacters()-1);
		this.figureFragment = new FigureFragment(figure, this.third, 0, GameEngine.getTerminal().getWidthInCharacters()-1, GameEngine.getTerminal().getHeightInCharacters()-1);
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		GameEngine.clearScreen();
		this.options.displayOutput(terminal);
		this.figureFragment.displayOutput(terminal);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		this.options.respondToUserInput(key);
		
		if(key.getKeyCode() == KeyEvent.VK_ENTER)
			return this.options.options.get(this.options.option).getScreen();
		
		if(key.getKeyCode() == KeyEvent.VK_ESCAPE)
			return null;
		return this;
	}

}
