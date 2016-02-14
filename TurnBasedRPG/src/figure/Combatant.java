package figure;

import java.util.Random;

import asciiPanel.AsciiPanel;
import screens.Printable;
import world.World;

public class Combatant implements Comparable<Combatant>, Printable{
	public enum Action{
		ATTACK,
		ITEM;
	}
	
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
		}while(!world.addCombatant(this, this.x, this.y));
	}
	
	public void move(World world, int x, int y){
		world.removeCombatant(this.x, this.y);
		this.x = x;
		this.y = y;
		world.addCombatant(this, this.x, this.y);
		
		this.hasMove = false;
	}
	
	private void attack(Combatant target){
		target.takeDamage(this);
		
		this.hasAction = false;
	}
	
	public void takeDamage(Combatant attacker){
		
	}
	
	public void action(Action action, Combatant target){
		switch(action){
			case ATTACK:
				this.attack(target);
				break;
			case ITEM:
				break;
			default:
				break;
		}
	}
	
	public boolean isTurnOver(){
		return !this.hasAction && !this.hasMove;
	}
	
	public void displayInformation(AsciiPanel terminal, int x, int y){
		terminal.write(this.figure.getName(), x+1, y);
		if(this.force == 1)
			terminal.write("Ally", x+1, y+1);
		else
			terminal.write("Enemy", x+1, y+1);
	}
	
	@Override
	public int compareTo(Combatant arg0) {
		return this.priority - arg0.priority;
	}

	@Override
	public void printToTerminal(AsciiPanel terminal, int x, int y) {
		terminal.write("f" + this.force + " :" + this.figure.getIcon().getCharacter() + ": " + this.figure.getName(), x, y);
	}
}
