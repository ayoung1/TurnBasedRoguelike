package figure;

import java.util.List;
import java.util.Random;

import figure.Figure.Stat;
import asciiPanel.AsciiPanel;
import engine.GameEngine;
import screens.Printable;
import skills.Skill;
import world.World;

public class Combatant implements Comparable<Combatant>, Printable{
	public enum Action{
		ATTACK,
		ITEM;
	}
	
	private static Random random = new Random(System.currentTimeMillis());
	
	private Figure figure;
	private int moves;
	private int actions;
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
		this.actions = 0;
		this.moves = 0;
		this.curHealth = this.figure.getMaxHealth();
		this.curEnergy = this.figure.getMaxEnergy();
	}
	
	public Figure getFigure(){return this.figure;}
	public boolean hasAction(){return this.actions > 0;}
	public boolean hasMove(){return this.moves > 0;}
	public int getLife(){return this.curHealth;}
	public int getEnergy(){return this.curEnergy;}
	public int getForce(){return this.force;}
	public int getX(){return this.x;}
	public int getY(){return this.y;}
	
	public void startTurn(){
		if(this.actions > 0)
			this.actions = 1;
		else
			this.actions++;
		if(this.moves > 0)
			this.moves = 1;
		else
			this.moves++;
	}
	
	public void addToWorld(){
		World world = GameEngine.getWorld();
		do{
			this.x = random.nextInt(world.getWidth());
			this.y = random.nextInt(world.getHeight());
		}while(!world.addCombatant(this, this.x, this.y));
	}
	
	public void move(int x, int y){
		World world = GameEngine.getWorld();
		
		world.removeCombatant(this.x, this.y);
		this.x = x;
		this.y = y;
		world.addCombatant(this, this.x, this.y);
		
		this.moves--;
	}
	
	private void attack(Combatant target){
		for(Skill s : this.getFigure().getJob().getSkills())
			s.onHit(this, target);
		
		target.takeDamage(this, this.getFigure().calculateDamage());
		
		this.actions--;
	}
	
	public void takeDamage(Combatant attacker, int damage){
		double multiplier = 100 / (100 + this.figure.getStat(Stat.ARMOR));
		for(Skill s : this.getFigure().getJob().getSkills())
			s.onTakeDamage(this, attacker);
		
		damage *= (int) multiplier;
		
		this.curHealth -= damage;
		
		if(this.curHealth <= 0)
			GameEngine.getWorld().removeCombatant(this.x, this.y);			
	}
	
	public void useSkill(Skill skill, List<Combatant> targets){
		if(this.curEnergy < skill.getCost())
			return;
		this.actions--;
		for(Combatant c : targets)
			skill.onCast(this, c);
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
		return !this.hasAction() && !this.hasMove();
	}
	
	public void displayInformation(AsciiPanel terminal, int x, int y){
		terminal.write(this.figure.getName(), x, y++);
		terminal.write(this.figure.getJob().getName(), x, y++);
		if(this.force == 1)
			terminal.write("Ally", x, y++);
		else
			terminal.write("Enemy", x, y++);
		
		y++;
		
		terminal.write("Health: " + this.curHealth + "/" + this.figure.getMaxHealth(), x, y++);
		terminal.write("Energy: " + this.curEnergy + "/" + this.figure.getMaxEnergy(), x, y++);
		
		y++;
		
		for(Stat stat : Stat.values())
			terminal.write(stat.name + ": " + this.figure.getStat(stat), x, y++);
		
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