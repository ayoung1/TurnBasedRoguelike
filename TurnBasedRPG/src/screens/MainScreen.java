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
	private int selectedOption = 0;
	
	private List<ScreenOption> menuOptions;
	
	public MainScreen(){
		this.menuOptions = new ArrayList<>();
		
		this.menuOptions.add(new ScreenOption("Party", new PartyScreen(widthThirds[0]+1, titleHeight+1, terminal.getWidthInCharacters()-1, terminal.getHeightInCharacters()-1)));
		this.menuOptions.add(new ScreenOption("Recruit", new RecruitScreen(widthThirds[0]+1, titleHeight+1, terminal.getWidthInCharacters()-1, terminal.getHeightInCharacters()-1)));
		this.menuOptions.add(new ScreenOption("Inventory",new InventoryScreen(widthThirds[0]+1, titleHeight+1, terminal.getWidthInCharacters()-1, terminal.getHeightInCharacters()-1)));
		this.menuOptions.add(new ScreenOption("Graveyard",new GraveScreen(widthThirds[0]+1, titleHeight+1, terminal.getWidthInCharacters()-1, terminal.getHeightInCharacters()-1)));
		
		this.menuOptions.add(new ScreenOption("To Battle", null){
			@Override
			public Screen getScreen() {
				return new BattleScreen();
			}
		});
		
		this.menuOptions.add(new ScreenOption("Quit", null){
			@Override
			public Screen getScreen() {
				System.exit(0);
				return null;
			}
		});
	}
	
	private void displayBorders(){
		GameEngine.displayBorders(0, 0, this.terminal.getWidthInCharacters()-1, this.titleHeight);
		GameEngine.displayBorders(0, 6, this.widthThirds[0], (this.titleHeight+2)+this.menuOptions.size());
	}
	
	private void showMenuOptions(){
		int height = this.titleHeight+1;
		this.terminal.write("Options", 2, height++);
		
		this.terminal.write(">", 1, height + this.selectedOption);
		
		for(ScreenOption s : this.menuOptions)
			this.terminal.write(s.name, 2, height++);
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		this.displayBorders();
		this.showMenuOptions();
		
		if(this.subscreen != null)
			this.subscreen.displayOutput(terminal);
	}

	private void moveCursor(KeyEvent key){
		if(key.getKeyCode() == KeyEvent.VK_UP){
			this.selectedOption--;
			if(this.selectedOption < 0)
				this.selectedOption = this.menuOptions.size()-1;
		}else if(key.getKeyCode() == KeyEvent.VK_DOWN){
			this.selectedOption++;
			if(this.selectedOption > this.menuOptions.size()-1)
				this.selectedOption = 0;
		}
	}
	
	@Override
	public Screen respondToUserInput(KeyEvent key) {
		if(this.subscreen != null)
			this.subscreen = this.subscreen.respondToUserInput(key);
		else{
			this.moveCursor(key);
			
			if(key.getKeyCode() == KeyEvent.VK_ENTER){
				Screen screen = this.menuOptions.get(this.selectedOption).getScreen();
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
