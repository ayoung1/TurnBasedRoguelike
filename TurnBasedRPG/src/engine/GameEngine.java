package engine;

import java.util.ArrayList;

import asciiPanel.AsciiPanel;
import figure.Figure;
import world.World;

public class GameEngine {

	private static AsciiPanel terminal;
	private static World world;
	private ArrayList<Figure> party;
	private static GameEngine gameEngine = new GameEngine();
	
	private GameEngine(){
		this.party = new ArrayList<Figure>();
	}
	
	public static ArrayList<Figure> getParty(){return gameEngine.party;}
	public static AsciiPanel getTerminal(){return GameEngine.terminal;}
	public static World getWorld(){return world;}
	
	public static World createWorld(int x, int y){
		world = new World(x, y);
		return world;
	}
	
	public static void displayBorders(int x, int y, int x2, int y2){
		terminal.write((char)201, x, y);
		terminal.write((char)187, x2, y);
		terminal.write((char)188, x2, y2);
		terminal.write((char)200, x, y2);
		
		for(int i = x+1; i < x2; i++){
			terminal.write((char)205, i, y);
			terminal.write((char)205, i, y2);
		}
		for(int i = y+1; i < y2; i++){
			terminal.write((char)186, x, i);
			terminal.write((char)186, x2, i);
		}
	}
	
	public static void setTerminal(AsciiPanel terminal){
		assert(terminal != null);
		GameEngine.terminal = terminal;
	}
	
	public static void removeFromParty(Figure figure){
		assert(figure != null);
		gameEngine.party.remove(figure);
	}
	
	public static void addToParty(Figure figure){
		assert(figure != null);
		gameEngine.party.add(figure);
	}
}
