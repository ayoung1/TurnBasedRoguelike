package figure;

import asciiPanel.AsciiPanel;
import icon.Icon;

public class TestFigure extends Figure {

	public TestFigure() {
		super("Test", new Icon('T', AsciiPanel.white));
	}
	
	public TestFigure(String name) {
		super(name, new Icon('T', AsciiPanel.white));
	}
}
