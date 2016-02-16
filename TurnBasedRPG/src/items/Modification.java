package items;

import figure.Figure;

public abstract class Modification {
	public abstract void onApply(Figure figure);
	public abstract void onRemove(Figure figure);
}
