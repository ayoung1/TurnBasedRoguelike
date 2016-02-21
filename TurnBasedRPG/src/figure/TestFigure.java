package figure;

import asciiPanel.AsciiPanel;
import icon.Icon;

public class TestFigure extends Figure {

	public TestFigure(Job job) {
		super("Test", new Icon('T', AsciiPanel.white), job, Gender.MALE);
	}
	
	public TestFigure(String name, Job job) {
		super(name, new Icon('T', AsciiPanel.white), job, Gender.MALE);
	}

	@Override
	public int calculateDamage() {
		return this.getStat(Stat.STR);
	}
}
