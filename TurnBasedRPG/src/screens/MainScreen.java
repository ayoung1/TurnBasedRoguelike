package screens;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import asciiPanel.AsciiPanel;
import engine.GameEngine;

public class MainScreen implements Screen {

	private AsciiPanel terminal = GameEngine.getTerminal();
	private Screen subscreen;
	private int[] widthThirds = {terminal.getWidthInCharacters() / 3, ((terminal.getWidthInCharacters() / 3) * 2)+1};
	private int titleHeight = 5;
	private MenuBlock menuBlock;
	
	public MainScreen(){
		List<ScreenOption> list = this.getList();
		this.menuBlock = new MenuBlock(list, 0,6,this.widthThirds[0], (this.titleHeight+2) + list.size()){};
	}
	
	private List<ScreenOption> getList(){
		List<ScreenOption> menuOptions = new ArrayList<>();
		
		menuOptions.add(new ScreenOption("Party", new PartyScreen(widthThirds[0]+1, titleHeight+1, terminal.getWidthInCharacters()-1, terminal.getHeightInCharacters()-1)));
		menuOptions.add(new ScreenOption("Reserves", new ReserveScreen(widthThirds[0]+1, titleHeight+1, terminal.getWidthInCharacters()-1, terminal.getHeightInCharacters()-1)));
		menuOptions.add(new ScreenOption("Recruit", new RecruitScreen(widthThirds[0]+1, titleHeight+1, terminal.getWidthInCharacters()-1, terminal.getHeightInCharacters()-1)));
		menuOptions.add(new ScreenOption("Inventory",new InventoryScreen(widthThirds[0]+1, titleHeight+1, terminal.getWidthInCharacters()-1, terminal.getHeightInCharacters()-1)));
		menuOptions.add(new ScreenOption("Graveyard",new GraveScreen(widthThirds[0]+1, titleHeight+1, terminal.getWidthInCharacters()-1, terminal.getHeightInCharacters()-1)));
		
		menuOptions.add(new ScreenOption("To Battle", null){
			@Override
			public Screen getScreen() {
				return new BattleScreen();
			}
		});
		
		return menuOptions;
	}
	
	private void displayBorders(){
		GameEngine.displayBorders(0, 0, this.terminal.getWidthInCharacters()-1, this.titleHeight);
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		this.displayBorders();
		this.menuBlock.displayOutput(terminal);
		
		if(this.subscreen != null)
			this.subscreen.displayOutput(terminal);
	}

	private void moveCursor(KeyEvent key){
		this.menuBlock.respondToUserInput(key);
	}
	
	@Override
	public Screen respondToUserInput(KeyEvent key) {
		if(this.subscreen != null)
			this.subscreen = this.subscreen.respondToUserInput(key);
		else{
			this.moveCursor(key);
			
			if(key.getKeyCode() == KeyEvent.VK_ENTER){
				Screen screen = ((ScreenOption)this.menuBlock.getList().get(this.menuBlock.getSelectedOption())).getScreen();
				if(screen instanceof BattleScreen)
					return screen;
				else
					this.subscreen = screen;
			}
			
			if(key.getKeyCode() == KeyEvent.VK_ESCAPE)
				this.subscreen = new MenuScreen();
		}	
		return this;
	}
}