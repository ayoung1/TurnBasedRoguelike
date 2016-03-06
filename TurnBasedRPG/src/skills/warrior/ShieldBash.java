package skills.warrior;

import java.util.List;

import damage.Damage;
import figure.Combatant;
import figure.Figure;
import skills.Debuff;
import skills.Skill;

public class ShieldBash extends Skill {

	public ShieldBash() {
		super("Shield Bash", 15, 1, 0, true);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<String> getDiscriptionList(List<String> list) {
		// TODO Auto-generated method stub
		return list;
	}

	@Override
	public void onCast(Combatant caster, Combatant target) {
		new Debuff(caster, target, 2){

			@Override
			public void onApply() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onRemove() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void update() {
				super.getTarget().modifyActions(-1);
				super.getTarget().modifyMoves(-1);
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
