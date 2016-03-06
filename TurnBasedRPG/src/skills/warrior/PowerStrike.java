package skills.warrior;

import skills.Skill;

import java.util.List;

import damage.*;
import figure.Combatant;
import figure.Figure;
import figure.Figure.Stat;

public class PowerStrike extends Skill {

	private static Stat stat = Stat.STR;
	private static int cost = 7;
	private static int range = 1;
	private static int area = 0;
	private static Damage.Type damageType = Damage.Type.PHYSICAL;
	private static double scale = 2.0;
	private static boolean targeted = true;
	
	public PowerStrike() {
		super("Power Strike", cost, range, area, targeted);
	}

	@Override
	public void onCast(Combatant caster, Combatant target) {
		int damage = caster.getFigure().getStat(stat);
		damage *= scale;
		target.takeDamage(caster, new Damage(damage, damageType));
	}
	
	@Override
	public List<String> getDiscriptionList(List<String> list) {
		
		list.add("Deals " + scale + " times " + stat.name + " in " + damageType.getName() + 
				" damage to the targeted unit");
		
		return list;
	}
	
	@Override
	public void onAquire(Figure figure) {}
	@Override
	public void onLoss(Figure figure) {}
	public void onHit(Combatant attacker, Combatant attacked, Damage damage) {}
	@Override
	public void onTakeDamage(Combatant attacked, Combatant attacker, Damage damage) {}
}
