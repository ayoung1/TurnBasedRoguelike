package screens;

import engine.GameEngine;
import screens.MenuBlock.MenuSelection;

public class ScreenOption implements MenuSelection{
	
	public final String name;
	public final Screen screen;
	
	public ScreenOption(String name, Screen screen){
		this.name = name;
		this.screen = screen;
	}
	
	public Screen getScreen(){return this.screen;}

	@Override
	public void printMenuRepresentation(int offset, int height) {
		GameEngine.getTerminal().write(this.name, offset, height);
	}

	@Override
	public int menuOptionHeight() {
		return 1;
	}
}
