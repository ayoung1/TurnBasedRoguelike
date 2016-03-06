package damage;

public class Damage {
	public enum Type{
		PHYSICAL("Physical"),
		MAGICAL("Magical");
		
		private final String name;
		private Type(String name){this.name = name;}
		public String getName(){return this.name;}
	}
	
	public final int damage;
	public final Type type;
	
	public Damage(int damage, Type type){
		this.damage = damage;
		this.type = type;
	}
}
