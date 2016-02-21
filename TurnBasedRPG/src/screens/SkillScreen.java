package screens;

import java.awt.event.KeyEvent;
import java.util.List;

import figure.Combatant;
import skills.Skill;
import world.World;
import asciiPanel.AsciiPanel;

public class SkillScreen implements Screen{

	private final String key = "abcdefghijklmnopqrstuvwxyz";
	
	private World world;
	private Combatant combatant;
	private Screen subscreen;
	private int offset;
	
	public SkillScreen(Combatant combatant, World world, int offset){
		assert(combatant != null);
		assert(world != null);
		
		this.combatant = combatant;
		this.world = world;
		this.offset = offset;
	}
	
	private void displaySkills(AsciiPanel terminal){
		int height = 1;
		char k;
		List<Skill> skills = this.combatant.getFigure().getJob().getSkills();
		
		terminal.write("Energy: " + this.combatant.getEnergy(), (this.offset*3)+4, height++);
		terminal.write("Skills", (this.offset*3)+1, height++);
		
		for(Skill s : skills){
			k = this.key.charAt(skills.indexOf(s));
			terminal.write(k + ":" + s.getName() + "-" + s.getCost(), (this.offset*3)+1, height++);
		}
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
		this.displaySkills(terminal);
		
		if(this.subscreen != null)
			this.subscreen.displayOutput(terminal);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		char k = key.getKeyChar();
		List<Skill> skills = this.combatant.getFigure().getJob().getSkills();
		
		if(this.subscreen != null){
			this.subscreen = this.subscreen.respondToUserInput(key);
		}
		else{
			if(this.key.indexOf(k) > -1 && this.key.indexOf(k) < skills.size()){
				Skill skill = skills.get(this.key.indexOf(k));
				
				if(this.combatant.getEnergy() >= skill.getCost())
					this.subscreen = new TargetScreen(this.combatant, skill, this.offset);
			}
			if(key.getKeyCode() == KeyEvent.VK_ESCAPE)
				return null;
		}
		if(!this.combatant.hasAction())
			return null;
		return this;
	}

}
