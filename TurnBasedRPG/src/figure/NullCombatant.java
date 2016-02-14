package figure;

import asciiPanel.AsciiPanel;

public class NullCombatant extends Combatant {

	public NullCombatant(Figure _figure, int _force) {
		super(_figure, _force);
	}
	@Override
	public void printToTerminal(AsciiPanel terminal, int x, int y) {}
}
