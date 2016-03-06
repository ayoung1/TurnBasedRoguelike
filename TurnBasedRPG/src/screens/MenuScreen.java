package screens;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import engine.GameEngine;
import asciiPanel.AsciiPanel;

public class MenuScreen implements Screen {

	private MenuBlock menu;
	
	public MenuScreen(){
		int x = GameEngine.getTerminal().getWidthInCharacters()/4;
		int y = GameEngine.getTerminal().getHeightInCharacters()/4;
		List<ScreenOption> list = new ArrayList<>();
		
		list.add(new ScreenOption("Quit", null){
			@Override
			public Screen getScreen(){
				System.exit(0);
				return this.screen;
			}
		});
		
		this.menu = new MenuBlock(list, x, y, (x*3)-1, y*3){};
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		int x = terminal.getWidthInCharacters()/4;
		int y = terminal.getHeightInCharacters()/4;
		int x2 = (x*3)-1;
		int y2 = y*3;
		
		GameEngine.clearArea(x, y, x2, y2);

		this.menu.displayOutput(terminal);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		int index = this.menu.getSelectedOption();
		if(key.getKeyCode() == KeyEvent.VK_ENTER && index > -1)
			return ((ScreenOption)this.menu.getList().get(index)).getScreen();
		
		if(key.getKeyCode() == KeyEvent.VK_ESCAPE)
			return null;
		return this;
	}
}