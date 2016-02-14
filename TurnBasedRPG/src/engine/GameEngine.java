package engine;

import java.util.ArrayList;

import asciiPanel.AsciiPanel;
import figure.Figure;

public class GameEngine {

	private AsciiPanel terminal;
	private ArrayList<Figure> party;
	private static GameEngine gameEngine = new GameEngine();
	
	private GameEngine(){
		this.party = new ArrayList<Figure>();
	}
	
	public static ArrayList<Figure> getParty(){return gameEngine.party;}
	public static AsciiPanel getTerminal(){return gameEngine.terminal;}
	
	public static void setTerminal(AsciiPanel terminal){
		assert(terminal != null);
		gameEngine.terminal = terminal;
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
