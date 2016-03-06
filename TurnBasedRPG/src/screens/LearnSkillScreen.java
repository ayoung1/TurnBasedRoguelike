package screens;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import asciiPanel.AsciiPanel;
import figure.Figure;
import skills.Skill;

public class LearnSkillScreen extends MenuBlock {
	
	private Figure figure;
	private Screen skillDiscription;
	private int width = 30;
	
	public LearnSkillScreen(Figure figure) {
		super(exclude(figure.getJob().getSkills(), figure.getKnownSkills()), 10, 5, 40, 20);
		this.figure = figure;
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		super.displayOutput(terminal);
		if(this.getSelectedOption() != -1){
			this.skillDiscription = new ScrollableTextView((Skill)this.getList().get(this.getSelectedOption()), this.point2[0]+1, this.point1[1], this.point2[0]+this.width, this.point2[1]);
			this.skillDiscription.displayOutput(terminal);
		}else
			this.skillDiscription = null;
	}
	
	@Override
	public Screen respondToUserInput(KeyEvent key){
		int index = this.getSelectedOption();
		Skill skill;
		
		if(this.figure.getSkillPoints() != 0 && key.getKeyCode() == KeyEvent.VK_ENTER && index > -1){
			skill = (Skill)this.getList().remove(index);
			this.figure.learnSkill(skill);
			this.figure.decrimentSkillPoints();
		}
		
		if(this.skillDiscription != null)
			this.skillDiscription.respondToUserInput(key);
		
		if(this.figure.getSkillPoints() == 0 || this.getList().size() == 0)
			return null;
		
		super.respondToUserInput(key);
		
		if(key.getKeyCode() == KeyEvent.VK_ESCAPE)
			return null;
		return this;
	}
	
	private static List<Skill> exclude(List<Skill> all, List<Skill> known){
		List<Skill> temp = new ArrayList<>();
		
		for(Skill s : all){
			if(!known.contains(s))
				temp.add(s);
		}
		
		return temp;
	}
}
