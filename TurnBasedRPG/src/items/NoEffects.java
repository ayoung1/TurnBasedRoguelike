package items;

import figure.Figure;
import figure.Figure.Stat;

public class NoEffects extends Modification {
	public NoEffects(){
		super(new StatVal(Stat.STR, 0));
	}
	
	@Override
	public void onApply(Figure figure) {}
	@Override
	public void onRemove(Figure figure) {}
}
