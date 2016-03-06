package skills.warrior;

import java.util.List;

import damage.Damage;
import figure.Combatant;
import figure.Figure;
import figure.Figure.Stat;
import skills.Debuff;
import skills.Skill;

public class Rend extends Skill {

	private Stat stat = Stat.STR;
	private int duration = 4;
	private double scale = 0.25;
	private Damage.Type type = Damage.Type.PHYSICAL;
	
	public Rend() {
		super("Rend", 12, 1, 0, true);
	}

	@Override
	public List<String> getDiscriptionList(List<String> list) {
		// TODO Auto-generated method stub
		return list;
	}

	@Override
	public void onCast(Combatant caster, Combatant target) {
		new Debuff(caster, target, duration){

			@Override
			public void onApply() {}

			@Override
			public void onRemove() {}

			@Override
			public void update() {
				super.retuceDuration();
				int amount = (int) (super.getCaster().getFigure().getStat(stat) * scale);
				Damage damage = new Damage(amount, type);
				super.getTarget().takeDamage(super.getCaster(), damage);
				
				if(super.getDuration() == 0)
					super.getTarget().removeDebuff(this);
			}
			
		};
	}

	@Override
	public void onHit(Combatant attacker, Combatant attacked, Damage damage) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTakeDamage(Combatant attacked, Combatant attacker, Damage damage) {
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
