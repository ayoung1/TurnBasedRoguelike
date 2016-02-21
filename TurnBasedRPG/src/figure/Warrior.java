package figure;

import java.util.Map;

import figure.Figure.Stat;
import items.Weapon.Type;
import skills.Skill;

public class Warrior extends Job {

	private Figure figure;
	
	public Warrior() {
		super("Warrior");
		this.addWeaponTypes(Type.SWORD, Type.AXE, Type.SPEAR, Type.SHIELD);
		
		Skill skill = new Skill("Power Strike", 70, 0, 1, true){
			@Override
			public void onCast(Combatant caster, Combatant target) {
				int damage = caster.getFigure().getStat(Stat.STR);
				damage *= 2.0;
				target.takeDamage(caster, damage);
			}
		};
		
		this.addSkills(skill);
	}

	public void setFigure(Figure figure){this.figure = figure;}
	
	@Override
	public Map<Stat, Integer> baseStats(){
		Map<Stat, Integer> map = super.baseStats();
		
		map.put(Stat.STR, 5);
		map.put(Stat.CON, 5);
		map.put(Stat.DEX, 5);
		map.put(Stat.INT, 5);
		map.put(Stat.WIS, 5);
		map.put(Stat.MOV, 3);
		map.put(Stat.ARMOR, 0);
	
		return map;
	}

	@Override
	public int calculateDamage() {
		return (int) (this.figure.getStat(Stat.STR) * 1.4);
	}
}
