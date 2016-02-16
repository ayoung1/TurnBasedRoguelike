package figure;

import java.util.HashMap;
import java.util.Map;

import asciiPanel.AsciiPanel;
import screens.Printable;
import icon.Icon;
import items.*;

public abstract class Figure implements Printable{
	public enum Stat{
		STR("Str"),
		CON("Con"),
		DEX("Dex"),
		INT("Int"),
		WIS("Wis"),
		ARMOR("Armor"),
		MOV("Movement");
		
		private String name;
		private Stat(String name){
			this.name = name;
		}
		public String getName(){return this.name;}
	}
	
	private String name;
	private Icon icon;
	private Map<Stat, Integer> stats;
	private Weapon mainhand;
	private Weapon offhand;
	
	public Figure(String _name, Icon _icon){
		assert(_name != null);
		assert(_icon != null);
		
		this.name = _name;
		this.icon = _icon;
		this.mainhand = new Unarmed();
		this.offhand = new Unarmed();
		this.stats = new HashMap<Stat, Integer>();
		
		this.stats.put(Stat.STR, 0);
		this.stats.put(Stat.CON, 0);
		this.stats.put(Stat.DEX, 0);
		this.stats.put(Stat.INT, 0);
		this.stats.put(Stat.WIS, 0);
		this.stats.put(Stat.MOV, 3);
		this.stats.put(Stat.ARMOR, 0);
		
		this.setupStats();
	}
	
	public String getName(){return this.name;}
	public Icon getIcon(){return this.icon;}
	public Weapon getMainhand(){return this.mainhand;}
	public Weapon getOffhand(){return this.offhand;}
	
	public int getMaxHealth(){
		return (int) (this.stats.get(Stat.CON) * 2.2);
	}
	
	public int getMaxEnergy(){
		return (int) (this.stats.get(Stat.WIS) * 2.75);
	}
	
	public int getStat(Stat stat){
		return this.stats.get(stat);
	}
	
	public void modifyStat(Stat stat, int value){
		int oldval = this.stats.get(stat);
		
		this.stats.replace(stat, oldval + value);
	}
	
	public void equipMain(Weapon weapon){
		this.unequipMain();
		this.mainhand = weapon;
		this.mainhand.getEquip().onApply(this);
	}
	
	public void unequipMain(){
		this.mainhand.getEquip().onRemove(this);
		this.mainhand = new Unarmed();
	}
	
	public void unequipOff(){
		this.offhand = new Unarmed();
	}
	
	@Override
	public void printToTerminal(AsciiPanel _terminal, int _x, int _y) {
		icon.printToTerminal(_terminal, _x, _y);
	}
	
	protected abstract void setupStats();
	public abstract int calculateDamage();
}
