package screens;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import asciiPanel.AsciiPanel;
import engine.GameEngine;
import figure.Figure;
import figure.Figure.Stat;

public class FigureScreen implements Screen {

	private Figure figure;
	private Screen options;
	private int third = GameEngine.getTerminal().getWidthInCharacters() / 3;
	
	private class FigureOptions extends MenuBlock{

		List<ScreenOption> options;
		
		public FigureOptions(int max, int x, int y, int i, int j) {
			super(max, x, y, i, j);
			this.options = new ArrayList<>();
			this.setupScreens();
		}
		
		private void setupScreens(){
			this.options.add(new ScreenOption("Equip", null){
				@Override
				public Screen getScreen() {
					return null;
				}
			});
			
			this.options.add(new ScreenOption("Send to Reserves", null){
				@Override
				public Screen getScreen() {
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
	}
	
	private void displayFigure(){
		AsciiPanel terminal = GameEngine.getTerminal();
		int offset = this.third+2;
		int height = 1;
		
		terminal.write(this.figure.getName(), offset, height++);
		terminal.write(this.figure.getGender().name, offset, height++);
		terminal.write(this.figure.getJob().getName(), offset, height++);
		height++;
		terminal.write("Stats:", offset, height++);
		
		for(Stat s : Stat.values())
			terminal.write(s.name + ": " + this.figure.getStat(s), offset+1, height++);
	
		height++;
		terminal.write("Equipment:", offset, height++);
		terminal.write("Main: " + this.figure.getMainhand().getName(), offset+1, height++);
		terminal.write("Offhand: " + this.figure.getOffhand().getName(), offset+1, height++);
	}
	
	private void displayBorders(){		
		GameEngine.displayBorders(this.third, 0, GameEngine.getTerminal().getWidthInCharacters()-1, GameEngine.getTerminal().getHeightInCharacters()-1);
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		GameEngine.clearScreen();
		this.options.displayOutput(terminal);
		this.displayFigure();
		this.displayBorders();
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		this.options.respondToUserInput(key);
		
		if(key.getKeyCode() == KeyEvent.VK_ESCAPE)
			return null;
		return this;
	}

}
