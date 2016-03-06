package figure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import figure.Figure.Stat;
import asciiPanel.AsciiPanel;
import damage.Damage;
import damage.Damage.Type;
import engine.GameEngine;
import skills.Debuff;
import skills.Skill;
import world.World;

public class Combatant implements Comparable<Combatant>{
	public enum Action{
		ATTACK,
		ITEM;
	}
	
	private static Random random = new Random(System.currentTimeMillis());
	
	private List<Debuff> debuffs;
	private List<Debuff> toRemoveDebuffs;	
	
	private Figure figure;
	private int moves;
	private int actions;
	private int force;
	private int x;
	private int y;
	private int curHealth;
	private int curEnergy;
	
	public static List<Combatant> combatant(List<Figure> list, int force){
		List<Combatant> combatants = new ArrayList<>();
		
		for(Figure f : list)
			combatants.add(new Combatant(f, force));
		
		return combatants;
	}
	
	public Combatant(Figure _figure, int _force){
		assert(_figure != null);
		this.figure = _figure;
		this.force = _force;
		this.actions = 0;
		this.moves = 0;
		this.curHealth = this.figure.getMaxHealth();
		this.curEnergy = this.figure.getMaxEnergy();
		this.debuffs = new ArrayList<>();
		this.toRemoveDebuffs = new ArrayList<>();
	}
	
	public Figure getFigure(){return this.figure;}
	public boolean hasAction(){return this.actions > 0;}
	public boolean hasMove(){return this.moves > 0;}
	public int getLife(){return this.curHealth;}
	public int getEnergy(){return this.curEnergy;}
	public int getForce(){return this.force;}
	public int getX(){return this.x;}
	public int getY(){return this.y;}
	
	public void modifyMoves(int amount){
		this.moves += amount;
	}
	
	public void modifyActions(int amount){
		this.actions += amount;
	}
	
	public void addDebuff(Debuff debuff){
		this.debuffs.add(debuff);
	}
	
	public void dispellDebuff(){
		if(this.debuffs.size() == 0)
			return;
		this.debuffs.remove(random.nextInt(this.debuffs.size()));
	}
	
	public void removeDebuff(Debuff debuff){
		this.toRemoveDebuffs.add(debuff);
	}
	
	private int calculatePriority(){
		int pri = this.figure.getStat(Stat.DEX) + this.figure.getStat(Stat.MOV);
		pri = pri / 2;
		
		return (int)pri;
	}
	
	public boolean isDead(){
		return this.curHealth <= 0;
	}
	
	private void debuffHandler(){
		for(Debuff d : this.debuffs)
			d.update();
		for(Debuff d : this.toRemoveDebuffs)
			d.onRemove();
		
		this.debuffs.removeAll(this.toRemoveDebuffs);
		this.toRemoveDebuffs.clear();
	}
	
	public void startTurn(){
		if(this.actions > 0)
			this.actions = 1;
		else
			this.actions++;
		if(this.moves > 0)
			this.moves = 1;
		else
			this.moves++;
		
		this.debuffHandler();
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
		Damage damage = new Damage(this.getFigure().calculateDamage(), Type.PHYSICAL);
		
		for(Skill s : this.getFigure().getJob().getSkills())
			s.onHit(this, target, damage);
		
		target.takeDamage(this, damage);
		
		this.actions--;
	}
	
	public void takeDamage(Combatant attacker, Damage damage){
		double multiplier = 100 / (100 + this.figure.getStat(Stat.ARMOR));
		int d = damage.damage;
		for(Skill s : this.getFigure().getJob().getSkills())
			s.onTakeDamage(this, attacker, damage);
		
		d *= (int) multiplier;
		
		this.curHealth -= d;
		
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
		return arg0.calculatePriority() - this.calculatePriority();
	}

	public void printToTerminal(AsciiPanel terminal, int x, int y) {
		terminal.write("f" + this.force + " :" + this.figure.getIcon().getCharacter() + ": " + this.figure.getName(), x, y);
	}
}