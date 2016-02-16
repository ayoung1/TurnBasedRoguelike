package figure;

import java.util.Random;

import figure.Figure.Stat;
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
	private int curHealth;
	private int curEnergy;
	
	public Combatant(Figure _figure, int _force){
		assert(_figure != null);
		this.figure = _figure;
		this.force = _force;
		this.priority = 0;
		this.hasAction = false;
		this.hasMove = false;
		this.curHealth = this.figure.getMaxHealth();
		this.curEnergy = this.figure.getMaxEnergy();
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
		double multiplier = 100 / (100 + this.figure.getStat(Stat.ARMOR));
		int damage = (int) (attacker.getFigure().calculateDamage() * multiplier);
		
		this.curHealth -= damage;
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
		terminal.write(this.figure.getName(), x, y++);
		if(this.force == 1)
			terminal.write("Ally", x, y++);
		else
			terminal.write("Enemy", x, y++);
		
		y++;
		
		terminal.write("Health: " + this.curHealth + "/" + this.figure.getMaxHealth(), x, y++);
		terminal.write("Energy: " + this.curEnergy + "/" + this.figure.getMaxEnergy(), x, y++);
		
		y++;
		
		for(Stat stat : Stat.values())
			terminal.write(stat.getName() + ": " + this.figure.getStat(stat), x, y++);
		
		y++;
		
		terminal.write("Equipment:", x++, y++);
		terminal.write("Mainhand: " + this.figure.getMainhand().getName(), x, y++);
		terminal.write("Offhand: " + this.figure.getOffhand().getName(), x, y++);
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
