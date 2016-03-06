package screens;

import java.awt.event.KeyEvent;
import java.util.List;

import figure.Combatant;
import skills.Skill;
import world.World;
import asciiPanel.AsciiPanel;
import engine.GameEngine;

public class SkillScreen implements Screen{
	
	private World world;
	private Combatant combatant;
	private Screen subscreen;
	private List<Skill> skills;
	private MenuBlock menuBlock;
	private int offset;
	
	public SkillScreen(Combatant combatant, World world, int offset){
		assert(combatant != null);
		assert(world != null);
		
		this.combatant = combatant;
		this.world = world;
		this.offset = offset;
		this.skills = combatant.getFigure().getKnownSkills();
		this.menuBlock = new MenuBlock(this.skills, this.offset + this.world.getWidth(), 3, GameEngine.getTerminal().getWidthInCharacters()-1, GameEngine.getTerminal().getHeightInCharacters()-1){};
		this.menuBlock.setBorders(false);
	}
	
	private void displaySkills(AsciiPanel terminal){
		int height = 1;
		
		terminal.write("Energy: " + this.combatant.getEnergy(), (this.offset*3)+4, height++);
		terminal.write("Skills", (this.offset*3)+1, height++);
	}
	
	private void displayOptions(AsciiPanel terminal){
		int height = world.getHeight();
		int offset = this.offset;
		
		terminal.write("Actions: ", offset, ++height);
		offset += 3;
		terminal.write("esc:return ", offset, height+1);
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		this.displayOptions(terminal);
		
		if(this.subscreen != null)
			this.subscreen.displayOutput(terminal);
		else{
			this.displaySkills(terminal);
			this.menuBlock.displayOutput(terminal);
		}
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		int index = this.menuBlock.getSelectedOption();
		
		if(this.subscreen != null){
			this.subscreen = this.subscreen.respondToUserInput(key);
		}
		else{
			if(key.getKeyCode() == KeyEvent.VK_ENTER && index > -1)
				this.subscreen = new TargetScreen(this.combatant, (Skill)this.menuBlock.getList().get(index), offset);
			this.menuBlock.respondToUserInput(key);
			if(key.getKeyCode() == KeyEvent.VK_ESCAPE)
				return null;
		}
		if(!this.combatant.hasAction())
			return null;
		return this;
	}
}
