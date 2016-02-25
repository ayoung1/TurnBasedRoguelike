package screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import engine.GameEngine;

public abstract class MenuBlock implements Screen {

	public final AsciiPanel terminal = GameEngine.getTerminal();
	public final int[] point1;
	public final int[] point2;
	protected int option = 0;
	private int max;
	
	public MenuBlock(int max, int x, int y, int i, int j){
		this.point1 = new int[]{x, y};
		this.point2 = new int[]{i, j};
		this.max = max;
	}
	
	private void displayCursor(){
		int height = this.point1[1] + 1;
		this.terminal.write(">", this.point1[0] + 1, height + this.option);
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		GameEngine.displayBorders(this.point1[0], this.point1[1], this.point2[0], this.point2[1]);
		this.displayCursor();
	}
	
	private void updateCursor(KeyEvent key){
		if(key.getKeyCode() == KeyEvent.VK_UP || key.getKeyCode() == KeyEvent.VK_W){
			this.option--;
			if(this.option < 0)
				this.option = this.max;
		}else if(key.getKeyCode() == KeyEvent.VK_DOWN || key.getKeyCode() == KeyEvent.VK_S){
			this.option++;
			if(this.option > this.max)
				this.option = 0;
		}
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		this.updateCursor(key);
		return null;
	}

}
