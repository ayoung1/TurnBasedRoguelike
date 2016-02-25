package engine;

import java.util.ArrayList;
import java.util.List;

import asciiPanel.AsciiPanel;
import figure.Figure;
import items.Item;
import world.World;

public class GameEngine {

	private static AsciiPanel terminal;
	private static World world;
	private static GameEngine gameEngine;
	private static int maxParty = 5;
	private Figure mainCharacter;
	private List<Figure> party;
	private List<Figure> reserves;
	private List<Figure> graveyard;
	private List<Item> inventory;
	
	public static void startEngine(){
		gameEngine = new GameEngine();
	}
	
	private GameEngine(){
		this.party = new ArrayList<Figure>();
		this.graveyard = new ArrayList<Figure>();
		this.reserves = new ArrayList<Figure>();
		this.inventory = new ArrayList<>();
	}
	
	public static List<Figure> getParty(){return gameEngine.party;}
	public static List<Figure> getGraveyard(){return gameEngine.graveyard;}
	public static List<Item> getInventory(){return gameEngine.inventory;}
	public static AsciiPanel getTerminal(){return GameEngine.terminal;}
	public static World getWorld(){return world;}
	public static Figure getMainFigure(){return gameEngine.mainCharacter;}
	
	public static void addMainFigure(Figure figure){
		assert(figure != null);
		gameEngine.mainCharacter = figure;
	}
	
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
	
	public static void clearScreen(){
		for(int i = 0; i < terminal.getWidthInCharacters()-1; i++)
			for(int j = 0; j < terminal.getHeightInCharacters()-1; j++)
				terminal.write(" ", i, j);
	}
	
	public static void setTerminal(AsciiPanel terminal){
		assert(terminal != null);
		GameEngine.terminal = terminal;
	}
	
	public static void removeFromParty(Figure figure){
		assert(figure != null);
		gameEngine.party.remove(figure);
	}
	
	public static void addToReserves(Figure figure){
		assert(figure != null);
		gameEngine.reserves.add(figure);
	}
	
	public static void removeFromReserves(Figure figure){
		assert(figure != null);
		gameEngine.reserves.remove(figure);
	}
	
	public static void addToGraveyard(Figure figure){
		assert(figure != null);
		gameEngine.graveyard.add(figure);
	}
	
	public static boolean addToParty(Figure figure){
		assert(figure != null);
		if(gameEngine.party.size() < maxParty){
			gameEngine.party.add(figure);
			return true;
		}
		return false;
	}
}
