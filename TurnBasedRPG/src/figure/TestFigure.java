package figure;

import asciiPanel.AsciiPanel;
import icon.Icon;
import items.Modification;
import items.Weapon;
import items.Weapon.Type;

public class TestFigure extends Figure {

	public TestFigure() {
		super("Test", new Icon('T', AsciiPanel.white));
	}
	
	public TestFigure(String name) {
		super(name, new Icon('T', AsciiPanel.white));
	}

	@Override
	protected void setupStats() {
		this.modifyStat(Stat.STR, 5);
		this.modifyStat(Stat.CON, 5);
		this.modifyStat(Stat.DEX, 5);
		this.modifyStat(Stat.INT, 5);
		this.modifyStat(Stat.WIS, 5);
		
		this.equipMain(new Weapon("Bow", Type.BOW, new Modification(){
			@Override
			public void onApply(Figure figure) {
				figure.modifyStat(Stat.DEX, 7);
			}

			@Override
			public void onRemove(Figure figure) {
				figure.modifyStat(Stat.DEX, -7);
			}
		}));
	}

	@Override
	public int calculateDamage() {
		return this.getStat(Stat.STR);
	}
}
