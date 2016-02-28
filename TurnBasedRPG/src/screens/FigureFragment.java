package screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import engine.GameEngine;
import figure.Figure;
import figure.Figure.Stat;

public class FigureFragment extends FragmentScreen {

	private Figure figure;
	private AsciiPanel terminal = GameEngine.getTerminal();
	
	public FigureFragment(Figure figure, int x, int y, int x2, int y2) {
		super(x, y, x2, y2);
		this.figure = figure;
	}

	private void display(){
		int offset = this.point1[0]+1;
		int height = this.point1[1]+1;
		
		height = this.displayFigure(height, offset);
		
		if(height >= this.point2[1]){
			height = this.point1[1]+1;
			offset = ((this.point2[0] + this.point1[0]) /2);
		}
		
		height = this.displayStats(height, offset);
		
		if(height >= this.point2[1]){
			height = this.point1[1]+1;
			offset = ((this.point2[0] + this.point1[0]) /2);
		}
		
		height = this.displayEquipment(height, offset);
	}
	
	private int displayFigure(int height, int offset){
		terminal.write(this.figure.getName(), offset, height++);
		terminal.write(this.figure.getGender().name, offset, height++);
		terminal.write(this.figure.getJob().getName(), offset, height++);
		
		return ++height;
	}
	
	private int displayStats(int height, int offset){
		terminal.write("Health: " + this.figure.getMaxHealth(), offset, height++);
		terminal.write("Energy: " + this.figure.getMaxEnergy(), offset, height++);
		height++;
		terminal.write("Stats:", offset, height++);

		for(Stat s : Stat.values())
			terminal.write(s.name + ": " + this.figure.getStat(s), offset+1, height++);
	
		return ++height;
	}
	
	private int displayEquipment(int height, int offset){
		terminal.write("Equipment:", offset, height++);
		terminal.write("Main: " + this.figure.getMainhand().getName(), offset+1, height++);
		terminal.write("Offhand: " + this.figure.getOffhand().getName(), offset+1, height++);
		
		return ++height;
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal){
		this.display();
		super.displayOutput(terminal);
	}
	
	@Override
	public Screen respondToUserInput(KeyEvent key) {
		return null;
	}

}
