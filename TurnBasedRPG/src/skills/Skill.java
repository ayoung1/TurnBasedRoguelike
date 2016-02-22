package skills;

import figure.Combatant;
import figure.Figure;

public abstract class Skill {

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
	
	public abstract void onCast(Combatant caster, Combatant target);
	public abstract void onHit(Combatant attacker, Combatant attacked);
	public abstract void onTakeDamage(Combatant attacked, Combatant attacker);
	public abstract void onAquire(Figure figure);
	public abstract void onLoss(Figure figure);
}
