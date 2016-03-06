package skills;

import java.util.ArrayList;
import java.util.List;

import damage.Damage;
import engine.GameEngine;
import figure.Combatant;
import figure.Figure;
import screens.MenuBlock.MenuSelection;
import screens.Printable;

public abstract class Skill implements MenuSelection, Printable {

	private String name;
	private int cost;
	private int range;
	private int area;
	private boolean targeted;
	
	public Skill(String name, int cost, int range, int area, boolean targeted){
		this.name = name;
		this.cost = cost;
		this.range = range;
		this.area = area;
		this.targeted = targeted;
	}
	
	public int getCost() {return this.cost;}
	public String getName() {return this.name;}
	public int getRange(){return this.range;}
	public int getArea(){return this.area;}
	public boolean isTargeted(){return this.targeted;}
	
	@Override
	public void printMenuRepresentation(int offset, int height) {
		GameEngine.getTerminal().write(this.name, offset, height);
	}

	@Override
	public int menuOptionHeight() {
		return 1;
	}
	
	@Override
	public List<String> getLines() {
		List<String> list = new ArrayList<>();
		
		list.add(this.getName());
		list.add("Cost: " + this.getCost());
		list.add("Range: " + this.getRange());
		list.add("Area: " + this.getRange());
		list.add("Targeted: " + this.isTargeted());
		list.add(" ");
		
		list = this.getDiscriptionList(list);
		
		return list;
	}
	
	public abstract List<String> getDiscriptionList(List<String> list);
	public abstract void onCast(Combatant caster, Combatant target);
	public abstract void onHit(Combatant attacker, Combatant attacked, Damage damage);
	public abstract void onTakeDamage(Combatant attacked, Combatant attacker, Damage damage);
	public abstract void onAquire(Figure figure);
	public abstract void onLoss(Figure figure);
}
