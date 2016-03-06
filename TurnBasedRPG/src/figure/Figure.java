package figure;

import screens.Printable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import asciiPanel.AsciiPanel;
import engine.GameEngine;
import icon.Icon;
import items.*;
import screens.MenuBlock.MenuSelection;
import skills.Skill;

public class Figure implements MenuSelection, Printable{
	public enum Gender{
		MALE("Male"),
		FEMALE("Female");
		
		public final String name;
		private Gender(String name){this.name = name;}
	}
	
	public enum Stat{
		STR("Str"),
		CON("Con"),
		DEX("Dex"),
		INT("Int"),
		WIS("Wis"),
		ARMOR("Armor"),
		MOV("Movement");
		
		public final String name;
		private Stat(String name){
			this.name = name;
		}
	}
	
	private final int BASE_HP = 10;
	private final int HP_LVL = 3;
	private final int MAX_LVL = 10;
	private final int SKILL_LVL = 2;
	
	private static Random random = new Random(System.currentTimeMillis());
	
	private String name;
	private int level;
	private int skillPoints = 0;
	private Icon icon;
	private Job job;
	private Map<Stat, Integer> stats;
	private List<Skill> knownSkills;
	private Gender gender;
	private Weapon mainhand;
	private Weapon offhand;
	private boolean canDualWield;
	
	public Figure(String _name, Icon _icon, Job job, Gender gender){
		assert(_name != null);
		assert(_icon != null);
		assert(job != null);
		
		this.name = _name;
		this.gender = gender;
		this.icon = _icon;
		this.job = job;
		this.job.setFigure(this);
		this.mainhand = new Unarmed();
		this.offhand = new Unarmed();
		this.canDualWield = false;
		this.level = 1;
		this.stats = new HashMap<>();
		this.knownSkills = new ArrayList<>();
		
		this.initializeStats();
	}
	
	public String getName(){return this.name;}
	public int getLevel(){return this.level;}
	public int getSkillPoints(){return this.skillPoints;}
	public Icon getIcon(){return this.icon;}
	public Job getJob(){return this.job;}
	public Gender getGender(){return this.gender;}
	public Weapon getMainhand(){return this.mainhand;}
	public Weapon getOffhand(){return this.offhand;}
	public List<Skill> getKnownSkills(){return this.knownSkills;}
	public boolean canDualWield(){return this.canDualWield;}
	
	private void initializeStats(){
		Map<Stat, Integer> m = this.job.baseStats();
		Random rnd = Figure.random;
		
		for(Stat s : Stat.values()){
			this.stats.put(s, 0);
			if(s != Stat.ARMOR && s != Stat.MOV)
				this.modifyStat(s, rnd.nextInt(5)+1);
			this.modifyStat(s, m.get(s));
		}
	}
	
	public void levelUp(int levels){
		for(int i = 0; i < levels; i++){
			if(this.level == this.MAX_LVL)
				break;
			this.levelUp();
		}
	}
	
	public void levelUp(){
		this.level++;
		if(this.level % this.SKILL_LVL == 0)
			this.skillPoints++;
		for(Stat s : Stat.values()){
			this.modifyStat(s, this.job.statGrowth().get(s));
		}
	}
	
	public void decrimentSkillPoints(){this.skillPoints = this.skillPoints == 0 ? 0 : this.skillPoints - 1;}
	
	public boolean learnSkill(Skill skill){
		assert(skill != null);
		if(this.knownSkills.contains(skill))
			return false;
		this.knownSkills.add(skill);
		return true;
	}
	
	public int getMaxHealth(){
		return ((this.level - 1) * this.HP_LVL) + this.BASE_HP + (int) (this.stats.get(Stat.CON) * 2.2);
	}
	
	public int getMaxEnergy(){
		return (int) (this.stats.get(Stat.WIS) * 2.75);
	}
	
	public int getStat(Stat stat){
		return this.stats.get(stat);
	}
	
	public void setDualWield(boolean value){
		this.canDualWield = value;
	}
	
	public void modifyStat(Stat stat, int value){
		int oldval = this.stats.get(stat);
		
		this.stats.replace(stat, oldval + value);
	}
	
	private boolean canEquip(Weapon weapon){
		return this.job.canEquip(weapon);
	}
	
	public void equipMain(Weapon weapon){
		if(!this.canEquip(weapon))
			return;
		
		this.unequipMain();
		
		if(weapon.isTwoHand())
			this.unequipOff();
			
		this.mainhand = weapon;
		this.mainhand.getEquip().onApply(this);
	}
	
	public void unequipMain(){
		this.mainhand.getEquip().onRemove(this);
		this.mainhand = new Unarmed();
	}
	
	public void unequipOff(){
		this.offhand.getEquip().onRemove(this);
		this.offhand = new Unarmed();
	}
	
	public void printToTerminal(AsciiPanel _terminal, int _x, int _y) {
		icon.printToTerminal(_terminal, _x, _y);
	}
	
	public int calculateDamage(){
		return this.job.calculateDamage();
	}

	@Override
	public void printMenuRepresentation(int offset, int height) {
		String str = " ";
		GameEngine.getTerminal().write(this.name + " " + this.gender.name + " L:" + this.level + " " + this.job.getName(), offset, height++);
		for(Stat s : Stat.values()){
			if(s != Stat.ARMOR && s != Stat.MOV)
			str += s.name + ":" + this.stats.get(s) + " ";
		}
		GameEngine.getTerminal().write(str, offset, height);	
	}

	@Override
	public int menuOptionHeight() {
		return 2;
	}

	@Override
	public List<String> getLines() {
List<String> text = new ArrayList<>();
		
		text.add(getName());
		text.add(getGender().name);
		text.add(getJob().getName());
		text.add("Skill Points: " + getSkillPoints());
		text.add(" ");
		text.add("Health: " + getMaxHealth());
		text.add("Energy: " + getMaxEnergy());
		text.add("Stats:");
		for(Stat s : Stat.values())
			text.add(" " + s.name + ": " + getStat(s));
		text.add(" ");
		text.add("Equiptment:");
		text.add(" Main: " + getMainhand().getName());
		text.add(" Offhand: " + getOffhand().getName());
		
		return text;
	}
}
