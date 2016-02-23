package screens;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import engine.GameEngine;
import figure.*;
import figure.Figure.Stat;
import icon.Icon;
import asciiPanel.AsciiPanel;

public class CharacterCreationScreen implements Screen {
	private final String validChars = "abcdefghijklmnopqrstuvwxyz ";
	private final String keys = "1234567890";
	private final int maxLength = 10;
	private final List<Job> jobs;
	
	private String userInputName = "";
	private Figure.Gender gender;
	private Job job;
	private int third = GameEngine.getTerminal().getWidthInCharacters() / 3;
	private int twoThird = (third * 2)+1;
	private Screen subscreen;
	
	public CharacterCreationScreen(){
		this.jobs = new ArrayList<>();
		this.jobs.add(new Warrior());
		this.jobs.add(new Rogue());
	}
	
	private void setupBorders(){
		GameEngine.displayBorders(0, 0, third-1, GameEngine.getTerminal().getHeightInCharacters()-1);
		GameEngine.displayBorders(twoThird+1, 0, GameEngine.getTerminal().getWidthInCharacters()-1, GameEngine.getTerminal().getHeightInCharacters()-1);
		GameEngine.displayBorders(third, 0, twoThird, 5);
		GameEngine.displayBorders(third, 6, twoThird, 11);
		GameEngine.displayBorders(third, 12, twoThird, 17);
		GameEngine.displayBorders(third, 18, twoThird, GameEngine.getTerminal().getHeightInCharacters()-2);
		
		if(this.userInputName.length() > 0 && this.gender != null && this.job != null)
			GameEngine.getTerminal().writeCenter("Enter: Submit", 20);
	}
	
	private void displayJobInformation(AsciiPanel terminal){
		int height = 1;
		
		terminal.write("Job Information", twoThird+7, height++);
		if(this.job == null)
			return;
		height++;
		terminal.write("Name: " + this.job.getName(), twoThird+3, height++);
		terminal.write("Base Stats: ", twoThird+3, height++);
		Map<Stat, Integer> list = this.job.baseStats();
		Map<Stat, Integer> growth = this.job.statGrowth();
		
		for(Stat s : Figure.Stat.values())
			terminal.write(s.name + ": " + list.get(s) + " + " + growth.get(s), twoThird+4, height++);
	}
	
	private void displayJobs(AsciiPanel terminal){
		int index = 0;
		int height = 4;
		
		terminal.writeCenter("Job", 12);
		if(this.job != null)
			terminal.writeCenter(this.job.getName(), 14);
		
		terminal.write("Jobs:", 2, height-1);
		
		for(Job j : this.jobs){
			terminal.write(this.keys.charAt(index++) + ": " + j.getName(), 3, height++);
		}
	}
	
	private void displayGender(AsciiPanel terminal){
		terminal.write(" <-  Male : Female ->", 1,1);
		
		terminal.writeCenter("Gender", 6);
		if(this.gender != null)
			terminal.writeCenter(this.gender.name, 8);
	}
	
	private void displayName(AsciiPanel terminal){
		int height = 0;

		terminal.writeCenter("Character Name", height++);
		terminal.writeCenter(this.userInputName, ++height);
	}
	
	private Screen submitCharacter(){
		if(this.userInputName.length() > 0 &&
				this.gender != null &&
				this.job != null){
			Figure figure = new Figure(this.userInputName, new Icon(this.job.getName().charAt(0), AsciiPanel.brightCyan), this.job, this.gender);
			GameEngine.addToParty(figure);
			GameEngine.addMainFigure(figure);
			return new BattleScreen();
		}
		
		return this;
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {		
		this.setupBorders();
		this.displayName(terminal);
		this.displayGender(terminal);
		this.displayJobs(terminal);
		this.displayJobInformation(terminal);
		
		if(this.subscreen != null)
			this.subscreen.displayOutput(terminal);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		char k = key.getKeyChar();
		if(this.subscreen != null)
			this.subscreen.respondToUserInput(key);
		else{
			if(this.userInputName.length() < this.maxLength && this.validChars.contains(("" + key.getKeyChar()).subSequence(0, 1)) ||
					this.validChars.toUpperCase().contains(("" + key.getKeyChar()).subSequence(0, 1)))
				this.userInputName += key.getKeyChar();
			else if(this.userInputName.length() > 0 && key.getKeyCode() == KeyEvent.VK_BACK_SPACE)
				this.userInputName = this.userInputName.substring(0, this.userInputName.length()-1);
			
			if(key.getKeyCode() == KeyEvent.VK_LEFT)
				this.gender = Figure.Gender.MALE;
			else if(key.getKeyCode() == KeyEvent.VK_RIGHT)
				this.gender = Figure.Gender.FEMALE;
			
			if(this.keys.indexOf(k) > -1 && this.keys.indexOf(k) < this.jobs.size()){
				this.job = this.jobs.get(this.keys.indexOf(k));
			}
			
			if(key.getKeyCode() == KeyEvent.VK_ENTER)
				return this.submitCharacter();
			
			if(key.getKeyCode() == KeyEvent.VK_ESCAPE)
				this.subscreen = new MenuScreen();
		}
		
		return this;
	}

}
