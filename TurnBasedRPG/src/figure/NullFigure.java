package figure;

import icon.Icon;
import asciiPanel.AsciiPanel;

public class NullFigure extends Figure {

	public NullFigure() {
		super("Null", new Icon(' ', AsciiPanel.white));
	}

	@Override
	public void printToTerminal(AsciiPanel _terminal, int _x, int _y) {}
	@Override
	protected void setupStats() {}

	@Override
	public int calculateDamage() {
		// TODO Auto-generated method stub
		return 0;
	}
}
