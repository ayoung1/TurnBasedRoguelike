package world;

import figure.*;
import icon.Icon;
import asciiPanel.AsciiPanel;

public class World {

	private Icon[][] tiles;
	private Combatant[][] figures;
	private Combatant nullC = new NullCombatant(new NullFigure(), -1);
	private int height;
	private int width;
	
	public World(int _height, int _width){
		this.height = _height > 0 ? _height : 1;
		this.width = _width > 0 ? _width : 1;
		this.tiles = new Icon[this.height][this.width];
		this.figures = new Combatant[this.height][this.width];
		
		this.setupWorld();
	}
	
	private void setupWorld(){
		for(int i = 0; i < this.height; i++){
			for(int j = 0; j < this.width; j++){
				this.tiles[i][j] = new Icon('w', AsciiPanel.brightGreen);
				this.figures[i][j] = this.nullC;
			}
		}
	}
	
	public int getHeight(){return this.width;}
	public int getWidth(){return this.height;}
	
	public boolean isInBounds(int x, int y){
		return !(x < 0 || y < 0 || x >= this.height || y >= this.width);
	}
	
	public boolean isPathable(int x, int y){
		return isInBounds(x, y) && this.figures[x][y].getForce() == -1;
	}
	
	public Combatant combatantAt(int x, int y){
		if(!this.isInBounds(x, y) || this.figures[x][y] == this.nullC)
			return null;
		return this.figures[x][y];
	}
	
	public boolean removeCombatant(int x, int y){
		if(this.isInBounds(x, y) && this.figures[x][y].getForce() != -1){
			this.figures[x][y] = this.nullC;
			
			return true;
		}
		return false;
	}
	
	public boolean addCombatant(Combatant figure, int x, int y){
		if(!this.isInBounds(x, y) || !this.isPathable(x, y))
			return false;
		
		this.figures[x][y] = figure;
		
		return true;
	}
	
	public void printToTerminal(AsciiPanel terminal, int x, int y){
		for(int i = 0; i < this.height; i++){
			for(int j = 0; j < this.width; j++){
				this.tiles[i][j].printToTerminal(terminal, i+x, j+y);
				this.figures[i][j].getFigure().printToTerminal(terminal, i+x, j+y);
			}
		}
	}
}
