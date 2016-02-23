package figure;

import java.util.HashMap;
import java.util.Map;

import figure.Figure.Stat;
import items.Weapon.Type;

public class Rogue extends Job {

	private Figure figure;
	
	public Rogue() {
		super("Rogue");
		this.addWeaponTypes(Type.DAGGER, Type.BOW);
	}

	public void setFigure(Figure figure){this.figure = figure;}
	
	@Override
	public Map<Stat, Integer> baseStats(){
		Map<Stat, Integer> map = super.baseStats();
		
		map.put(Stat.STR, 2);
		map.put(Stat.CON, 3);
		map.put(Stat.DEX, 7);
		map.put(Stat.INT, 2);
		map.put(Stat.WIS, 1);
		map.put(Stat.MOV, 4);
		map.put(Stat.ARMOR, 0);
	
		return map;
	}

	@Override
	public Map<Stat, Integer> statGrowth(){
		Map<Stat, Integer> map = new HashMap<Stat, Integer>();
		
		map.put(Stat.STR, 2);
		map.put(Stat.CON, 1);
		map.put(Stat.DEX, 3);
		map.put(Stat.INT, 1);
		map.put(Stat.WIS, 1);
		map.put(Stat.MOV, 0);
		map.put(Stat.ARMOR, 0);
	
		return map;
	}
	
	@Override
	public int calculateDamage() {
		return (int) ((this.figure.getStat(Stat.STR) + this.figure.getStat(Stat.DEX)) / 1.3);
	}

	@Override
	public int getMaxEnergy() {
		return (int)(this.figure.getStat(Stat.DEX) * 1.7);
	}
}
