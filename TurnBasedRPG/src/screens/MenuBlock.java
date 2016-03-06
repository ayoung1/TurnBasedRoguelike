package screens;

import java.awt.event.KeyEvent;
import java.util.List;

import asciiPanel.AsciiPanel;
import engine.GameEngine;

public abstract class MenuBlock extends FragmentScreen implements Screen {

	public interface MenuSelection{
		public void printMenuRepresentation(int offset, int height);
		public int menuOptionHeight();
	}
	
	public final AsciiPanel terminal = GameEngine.getTerminal();
	
	private int linesAvailable;
	private int optionsPerPage;
	private int option = 0;
	private int topElement = 0;
	private List<? extends MenuSelection> options;
	
	public MenuBlock(List<? extends MenuSelection> options, int x, int y, int i, int j){
		super(x,y,i,j);
		this.options = options;
		this.linesAvailable = (this.point2[1]) - (this.point1[1]);
	}
	
	public int getSelectedOption(){return this.options.size() > 0 ? this.option : -1;}
	public List<? extends MenuSelection> getList(){return this.options;}
	
	private void displayOptions(){
		int height = this.point1[1]+1;
		int offset = this.point1[0]+2;
		int index;
		int line = 0;
		
		if(this.option == this.options.size())
			this.option--;
		
		if(this.options.size() > 0)
			this.optionsPerPage = this.linesAvailable / this.options.get(0).menuOptionHeight();
		else
			this.optionsPerPage = 0;
		
		if(this.options.size() > this.optionsPerPage){
			if(this.option == this.options.size())
				this.option--;
			else if(this.option == 0)
				this.topElement = 0;
			else if(this.option == this.options.size()-1)
				this.topElement = (this.options.size()) - this.optionsPerPage;
			else if(this.option == this.topElement + this.optionsPerPage)
				this.topElement++;
			else if(this.option == this.topElement-1)
				this.topElement--;
		}
		index = this.topElement;
			
		do{
			if(index == this.option)
				this.displayCursor(offset-1, height+line);
			this.options.get(index).printMenuRepresentation(offset, height+line);
			line += this.options.get(index).menuOptionHeight();
			if(++index >= this.options.size())
				break;
		}while(line + this.options.get(index).menuOptionHeight() < this.linesAvailable);
	}
	
	private void displayCursor(int offset, int height){
		this.terminal.write(">", offset, height);
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		super.displayOutput(terminal);
		if(this.options.size() > 0)
			this.displayOptions();
	}
	
	private void updateCursor(KeyEvent key){
		int max = this.options.size();
		if(key.getKeyCode() == KeyEvent.VK_UP || key.getKeyCode() == KeyEvent.VK_W){
			this.option--;
			if(this.option < 0)
				this.option = max-1;
		}else if(key.getKeyCode() == KeyEvent.VK_DOWN || key.getKeyCode() == KeyEvent.VK_S){
			this.option++;
			if(this.option > max-1)
				this.option = 0;
		}
		if(this.option == max)
			this.option--;
	}
	
	@Override
	public Screen respondToUserInput(KeyEvent key) {
		this.updateCursor(key);
		return null;
	}
}