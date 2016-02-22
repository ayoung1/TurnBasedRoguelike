package skills.warrior;

import skills.Skill;
import figure.Combatant;
import figure.Figure;
import figure.Figure.Stat;

public class PowerStrike extends Skill {

	public PowerStrike() {
		super("Power Strike", 7, 1, 0, true);
	}

	@Override
	public void onCast(Combatant caster, Combatant target) {
		int damage = caster.getFigure().getStat(Stat.STR);
		damage *= 2.0;
		target.takeDamage(caster, damage);
	}

	@Override
	public void onHit(Combatant attacker, Combatant attacked) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTakeDamage(Combatant attacked, Combatant attacker) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAquire(Figure figure) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLoss(Figure figure) {
		// TODO Auto-generated method stub

	}

}
