package screens;

import java.awt.event.KeyEvent;

import figure.Combatant;
import world.World;
import asciiPanel.AsciiPanel;

public class ActionScreen implements Screen{

	private World world;
	private Combatant combatant;
	private int offset;
	
	public ActionScreen(Combatant combatant, World world, int offset){
		assert(combatant != null);
		assert(world != null);
		
		this.combatant = combatant;
		this.world = world;
		this.offset = offset;
	}
	
	private void displayOptions(AsciiPanel terminal){
		int height = world.getHeight();
		int offset = this.offset;
		
		terminal.write("Actions: ", offset, height+1);
		offset += 3;
		
		terminal.write("f:fight ", offset, height+2);
		offset += 8;
		
		terminal.write("s:skill ", offset, height+2);
		offset += 8;
		
		terminal.write("i:item ", offset, height+2);
		offset = this.offset + 3;
		
		terminal.write("esc:return ", offset, height+3);
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		this.displayOptions(terminal);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		return null;
	}

}
