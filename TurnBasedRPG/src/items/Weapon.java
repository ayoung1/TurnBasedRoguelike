package items;

import screens.Screen;

public class Weapon extends Item {

	public enum Type{
		FLESH("Flesh"),
		SWORD("Sword"),
		AXE("Axe"),
		BOW("Bow"),
		STAFF("Staff"),
		SHIELD("Shield");
		private final String name;
		private Type(String name){this.name = name;}
		public String toString(){return this.name;}
	}
	
	private final Type type;
	private final int range;
	private final Equip equip;
	
	public Weapon(String name, Type type, int range, Equip equip) {
		super(name);
		this.type = type;
		this.range = range;
		this.equip = equip;
	}

	public Type getType(){return this.type;}
	public int getRange(){return this.range;}
	public Equip getEquip(){return this.equip;}
	
	@Override
	public String listView() {
		return this.getName() + " " + this.type.toString();
	}

	@Override
	public Screen view() {
		return null;
	}

}
