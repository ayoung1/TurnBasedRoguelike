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
	public boolean isNull(){return true;}
}
