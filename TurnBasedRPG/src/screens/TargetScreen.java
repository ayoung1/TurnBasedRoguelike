package screens;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import asciiPanel.AsciiPanel;
import figure.Combatant;
import skills.Skill;
import trig.Trig;

public class TargetScreen extends CursorScreen implements Screen{

	private Skill skill;

	public TargetScreen(Combatant combatant, Skill skill, int offset){
		super(combatant, offset);
		
		this.skill = skill;
	}
	
	private void displayArea(AsciiPanel terminal){
		if(this.skill.getArea() <= 0)
			return;
		
		int x = this.getCursor().getX();
		int y = this.getCursor().getY();
		
		for(int i = 0; i < this.getWorld().getWidth(); i++){
			for(int j = 0; j < this.getWorld().getHeight(); j++){
				if(this.getWorld().isPathable(i, j) && Trig.manhattanDistanceBetweenPoints(x, y, i, j) <= this.skill.getArea())
					terminal.write((char)178, i+this.getOffset(), j+1, AsciiPanel.brightBlue);
			}
		}
	}
	
	private void displayRange(AsciiPanel terminal){
		int x = this.getCombatant().getX();
		int y = this.getCombatant().getY();
		
		for(int i = 0; i < this.getWorld().getWidth(); i++){
			for(int j = 0; j < this.getWorld().getHeight(); j++){
				if(this.getWorld().isPathable(i, j) && Trig.manhattanDistanceBetweenPoints(x, y, i, j) <= this.skill.getRange())
					terminal.write((char)178, i+this.getOffset(), j+1, AsciiPanel.white);
			}
		}
	}
	
	private List<Combatant> collectTargets(){
		List<Combatant> list = new ArrayList<>();
		int x = this.getCursor().getX();
		int y = this.getCursor().getY();
		
		if(Trig.manhattanDistanceBetweenPoints(x, y, this.getCombatant().getX(), this.getCombatant().getY()) > this.skill.getRange())
			return list;
		
		for(int i = 0; i < this.getWorld().getWidth(); i++){
			for(int j = 0; j < this.getWorld().getHeight(); j++){
				if(Trig.manhattanDistanceBetweenPoints(x, y, i, j) <= this.skill.getArea()){
					if(this.getWorld().combatantAt(i, j) != null)
						list.add(this.getWorld().combatantAt(i, j));
				}
			}
		}
		return list;
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		this.displayRange(terminal);
		this.displayArea(terminal);
		super.displayOutput(terminal);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		super.respondToUserInput(key);
		List<Combatant> targets = this.collectTargets();
		if(key.getKeyCode() == KeyEvent.VK_ENTER && targets.size() > 0){
			this.getCombatant().useSkill(this.skill, targets);
		}
		
		if(key.getKeyCode() == KeyEvent.VK_ESCAPE || !this.getCombatant().hasAction())
			return null;
		return this;
	}

}