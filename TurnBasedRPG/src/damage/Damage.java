package damage;

public class Damage {
	public enum Type{
		PHYSICAL,
		MAGICAL;
	}
	
	public final int damage;
	public final Type type;
	
	public Damage(int damage, Type type){
		this.damage = damage;
		this.type = type;
	}
}
