package skills;

import figure.Combatant;

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
}
