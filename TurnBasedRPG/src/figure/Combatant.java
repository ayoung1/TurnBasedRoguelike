package figure;

import java.util.Random;

import asciiPanel.AsciiPanel;
import screens.Printable;
import world.World;

public class Combatant implements Comparable<Combatant>, Printable{
	private static Random random = new Random(System.currentTimeMillis());
	
	private Figure figure;
	private boolean hasMove;
	private boolean hasAction;
	private int force;
	private int priority;
	private int x;
	private int y;
	
	public Combatant(Figure _figure, int _force){
		assert(_figure != null);
		this.figure = _figure;
		this.force = _force;
		this.priority = 0;
		this.hasAction = false;
		this.hasMove = false;
	}
	
	public Figure getFigure(){return this.figure;}
	public boolean hasAction(){return this.hasAction;}
	public boolean hasMove(){return this.hasMove;}
	public int getForce(){return this.force;}
	public int getX(){return this.x;}
	public int getY(){return this.y;}
	
	public void startTurn(){
		this.hasAction = true;
		this.hasMove = true;
	}
	
	public void addToWorld(World world){
		do{
			this.x = random.nextInt(world.getHeight());
			this.y = random.nextInt(world.getWidth());
		}while(!world.addFigure(figure, this.x, this.y));
	}
	
	public void move(World world, int x, int y){
		this.x = x;
		this.y = y;
		
		world.addFigure(this.figure, this.x, this.y);
		
		this.hasMove = false;
	}
	
	public void action(){
		this.hasAction = false;
	}
	
	public boolean isTurnOver(){
		return !this.hasAction && !this.hasMove;
	}
	
	@Override
	public int compareTo(Combatant arg0) {
		return this.priority - arg0.priority;
	}

	@Override
	public void printToTerminal(AsciiPanel terminal, int x, int y) {
		terminal.write(this.figure.getIcon().getCharacter() + " " + this.figure.getName() + " f" + this.force, x, y);
	}
}
