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
	private Screen subscreen;
	private int third = GameEngine.getTerminal().getWidthInCharacters() / 3;
	
	private class FigureOptions extends MenuBlock{
		public FigureOptions(List<? extends MenuSelection> list, int x, int y, int i, int j) {
			super(list, x, y, i, j);
		}
	}
	
	public FigureScreen(Figure figure){
		assert(figure != null);
		this.figure = figure;
		this.options = new FigureOptions(setupScreens(), 0, 0, this.third-1, GameEngine.getTerminal().getHeightInCharacters()-1);
		this.figureFragment = new ScrollableTextView(figure, this.third, 0, GameEngine.getTerminal().getWidthInCharacters()-1, GameEngine.getTerminal().getHeightInCharacters()-1);
	}
	
	private List<ScreenOption> setupScreens(){
		List<ScreenOption> list = new ArrayList<>();
		
		list.add(new ScreenOption("Equip", null){
			@Override
			public Screen getScreen() {
				return new EquipScreen(GameEngine.getParty().get(options.getSelectedOption()));
			}
		});
		
		if(this.figure.getSkillPoints() > 0)
			list.add(new ScreenOption("Learn Skill", new LearnSkillScreen(this.figure)));
		
		if(GameEngine.getParty().contains(this.figure) && this.figure != GameEngine.getMainFigure()){
			list.add(new ScreenOption("Send to Reserves", null){
				@Override
				public Screen getScreen() {
					if(figure == GameEngine.getMainFigure())
						return null;
					GameEngine.removeFromParty(figure);
					GameEngine.addToReserves(figure);
					options = new FigureOptions(setupScreens(), 0, 0, third-1, GameEngine.getTerminal().getHeightInCharacters()-1);
					return null;
				}
			});
		}else if(GameEngine.getReserves().contains(this.figure)){
			list.add(new ScreenOption("Send to Party", null){
				@Override
				public Screen getScreen() {
					if(GameEngine.addToParty(figure))
						GameEngine.removeFromReserves(figure);
					options = new FigureOptions(setupScreens(), 0, 0, third-1, GameEngine.getTerminal().getHeightInCharacters()-1);
					return null;
				}
			});
		}
		return list;
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		GameEngine.clearScreen();
		if(this.subscreen instanceof LearnSkillScreen){
			this.options.displayOutput(terminal);
			this.figureFragment.displayOutput(terminal);
		}
		if(this.subscreen != null)
			this.subscreen.displayOutput(terminal);
		else{
			this.options.displayOutput(terminal);
			this.figureFragment.displayOutput(terminal);
		}
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {		
		this.figureFragment.respondToUserInput(key);
		if(this.subscreen != null)
			this.subscreen = this.subscreen.respondToUserInput(key);
		else{
			if(key.getKeyCode() == KeyEvent.VK_ENTER)
				this.subscreen = ((ScreenOption)this.options.getList().get(this.options.getSelectedOption())).getScreen();
			
			this.options.respondToUserInput(key);
			if(key.getKeyCode() == KeyEvent.VK_ESCAPE)
				return null;
		}
		return this;
	}

}
