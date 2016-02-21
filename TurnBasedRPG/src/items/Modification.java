package items;

import figure.Figure;

public class Modification {
	private StatVal[] statVals;
	
	public Modification(StatVal ... statVals){
		this.statVals = statVals;
	}
	
	public void onApply(Figure figure){
		for(StatVal s : this.statVals){
			figure.modifyStat(s.stat, s.value);
		}
	}
	
	public void onRemove(Figure figure){
		for(StatVal s : this.statVals){
			figure.modifyStat(s.stat, -s.value);
		}
	}
}
