package skills;

import figure.Combatant;

public abstract class Debuff {
	private int duration;
	private Combatant caster;
	private Combatant target;
	
	public Debuff(Combatant caster, Combatant target, int duration){
		this.caster = caster;
		this.target = target;
		this.duration = duration;
		this.target.addDebuff(this);
		this.onApply();
	}
	
	public Combatant getCaster(){return this.caster;}
	public Combatant getTarget(){return this.target;}
	public int getDuration(){return this.duration;}
	public void retuceDuration(){this.duration--;}
	
	public abstract void onApply();
	public abstract void onRemove();
	public abstract void update();
}
