package screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import engine.GameEngine;

public abstract class MenuBlock extends FragmentScreen implements Screen {

	public final AsciiPanel terminal = GameEngine.getTerminal();
	protected int option = 0;
	private int max;
	private int interval = 1;
	
	public MenuBlock(int max, int x, int y, int i, int j){
		super(x,y,i,j);
		this.max = max;
	}
	
	public void setInterval(int i){this.interval = i;}
	public int getSelectedOption(){return this.option;}
	public int getMax(){return this.max;}
	
	public void modifyMax(int i){
		this.max = i;
		if(this.max < 0)
			this.max = 0;
		
		if(this.option == this.max){
			this.option = this.max-1;
			if(this.option < 0)
				this.option = 0;
			this.displayCursor();
		}
	}
	
	private void displayCursor(){
		int height = this.point1[1] + 1;
		this.terminal.write(">", this.point1[0] + 1, height + (this.option*this.interval));
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		super.displayOutput(terminal);
		this.displayCursor();
	}
	
	private void updateCursor(KeyEvent key){
		if(this.max == 0)
			return;
		if(key.getKeyCode() == KeyEvent.VK_UP || key.getKeyCode() == KeyEvent.VK_W){
			this.option--;
			if(this.option < 0)
				this.option = this.max-1;
		}else if(key.getKeyCode() == KeyEvent.VK_DOWN || key.getKeyCode() == KeyEvent.VK_S){
			this.option++;
			if(this.option > this.max-1)
				this.option = 0;
		}
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		this.updateCursor(key);
		return null;
	}

}
