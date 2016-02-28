package screens;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import asciiPanel.AsciiPanel;
import engine.Factory;
import engine.GameEngine;
import figure.Figure;

public class RecruitScreen extends MenuBlock{

	private List<Figure> list;
	
	public RecruitScreen(int x, int y, int i, int j){
		super(5,x,y,i,j);
		this.setInterval(2);
		this.populateList();
	}
	
	private void populateList(){
		this.list = new ArrayList<>();
		for(int i = 0; i < this.getMax(); i++)
			this.list.add(Factory.makeFigure(true));
	}
	
	private void displayMembers(){
		int height = this.point1[1]+1;
		for(Figure f : this.list)
			height = f.printInfomation(this.point1[0]+2, height);
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		super.displayOutput(terminal);
		this.displayMembers();
	}
	
	@Override
	public Screen respondToUserInput(KeyEvent key) {
		super.respondToUserInput(key);
		if(key.getKeyCode() == KeyEvent.VK_ENTER && this.list.size() != 0){
			Figure temp = this.list.remove(this.option);
			if(!GameEngine.addToParty(temp))
				GameEngine.addToReserves(temp);
			this.modifyMax(this.list.size());
		}
		if(key.getKeyCode() == KeyEvent.VK_ESCAPE)
			return null;
		return this;
	}
}