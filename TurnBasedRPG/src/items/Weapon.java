package items;

import screens.Screen;

public class Weapon extends Item {

	public enum Type{
		FLESH("Flesh", 1),
		SWORD("Sword", 1),
		AXE("Axe", 1),
		SPEAR("Spear", 2),
		BOW("Bow", 5),
		STAFF("Staff", 2),
		SHIELD("Shield", 1);
		
		private final String name;
		private final int range;
		
		private Type(String name, int range){
			this.name = name;
			this.range = range;
		}
		
		public String toString(){return this.name;}
		public int getRange(){return this.range;}
	}
	
	private final Type type;
	private final int range;
	private final Modification equip;
	
	public Weapon(String name, Type type, Modification equip) {
		super(name);
		this.type = type;
		this.range = type.range;
		this.equip = equip;
	}

	public Type getType(){return this.type;}
	public int getRange(){return this.range;}
	public Modification getEquip(){return this.equip;}
	
	@Override
	public String listView() {
		return this.getName() + " " + this.type.toString();
	}

	@Override
	public Screen view() {
		return null;
	}

}
