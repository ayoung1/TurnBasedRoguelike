package figure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import figure.Figure.Stat;
import items.Weapon;
import items.Weapon.Type;
import skills.Skill;

public abstract class Job{

	private String name;
	private Figure figure;
	private ArrayList<Type> weapons;
	private ArrayList<Skill> skills;
	
	public Job(String name) {
		assert(name != null);
		
		this.name = name;
		this.weapons = new ArrayList<>();
		this.skills = new ArrayList<>();
		this.weapons.add(Type.FLESH);
	}
	
	public String getName(){return this.name;}
	public ArrayList<Skill> getSkills(){return this.skills;}
	
	public void setFigure(Figure figure){
		assert(figure != null);
		this.figure = figure;
	}
	
	public void addWeaponTypes(Type ... types){
		for(Type t : types)
			this.weapons.add(t);
	}
	
	public void removeSkill(Skill skill){
		this.skills.remove(skill);
		skill.onLoss(this.figure);
	}
	
	public void addSkills(Skill ... skill){
		for(Skill s : skill){
			this.skills.add(s);
			s.onAquire(this.figure);
		}
	}
	
	public boolean canEquip(Weapon weapon){
		return this.weapons.contains(weapon.getType());
	}
	
	public Map<Stat, Integer> statGrowth(){
		Map<Stat, Integer> map = new HashMap<Stat, Integer>();
		
		map.put(Stat.STR, 0);
		map.put(Stat.CON, 0);
		map.put(Stat.DEX, 0);
		map.put(Stat.INT, 0);
		map.put(Stat.WIS, 0);
		map.put(Stat.MOV, 0);
		map.put(Stat.ARMOR, 0);
	
		return map;
	}
	
	public Map<Stat, Integer> baseStats(){
		Map<Stat, Integer> map = new HashMap<Stat, Integer>();
		
		map.put(Stat.STR, 0);
		map.put(Stat.CON, 0);
		map.put(Stat.DEX, 0);
		map.put(Stat.INT, 0);
		map.put(Stat.WIS, 0);
		map.put(Stat.MOV, 3);
		map.put(Stat.ARMOR, 2);
	
		return map;
	}
	
	public abstract int getMaxEnergy();
	public abstract int calculateDamage();
}
