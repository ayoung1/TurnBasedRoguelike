package figure;

import java.util.HashMap;
import java.util.Map;

import figure.Figure.Stat;
import items.Weapon.Type;
import skills.warrior.*;

public class Warrior extends Job {

	private Figure figure;
	
	public Warrior() {
		super("Warrior");
		this.addWeaponTypes(Type.SWORD, Type.AXE, Type.SPEAR, Type.SHIELD);
		this.setupSkills();
	}

	private void setupSkills(){
		this.addSkills(new PowerStrike(), new Rend(), new ShieldBash());
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
	public Map<Stat, Integer> statGrowth(){
		Map<Stat, Integer> map = new HashMap<Stat, Integer>();
		
		map.put(Stat.STR, 4);
		map.put(Stat.CON, 3);
		map.put(Stat.DEX, 1);
		map.put(Stat.INT, 1);
		map.put(Stat.WIS, 1);
		map.put(Stat.MOV, 0);
		map.put(Stat.ARMOR, 0);
	
		return map;
	}
	
	@Override
	public int calculateDamage() {
		return (int) (this.figure.getStat(Stat.STR) * 1.4);
	}

	@Override
	public int getMaxEnergy() {
		return (int)(this.figure.getStat(Stat.CON) * 1.2);
	}
}
