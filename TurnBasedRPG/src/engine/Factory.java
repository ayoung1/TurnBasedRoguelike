package engine;

import java.util.Random;

import asciiPanel.AsciiPanel;
import figure.*;
import figure.Figure.Gender;
import icon.Icon;

public class Factory {

	private static Random random = new Random(System.currentTimeMillis());
	
	private static String[] names = new String[] {"Phiy", "Maz", "Neyn", "Scheec", "Oquea", "Zag", "Threal",
			"Cur", "Len", "Naq", "Rodl", "Iaro", "Akiny", "Ehato", "Perl", "Danrd", "Crier", "Uskz", "End", "Theest",
			"Untn", "Lerk", "Kall", "Risb", "Ards", "Eima", "Sayk", "Nysr", "Lef", "Uendu", "Ked", "Atone", "Enth", "Enthm", 
			"Vurr", "Whos", "Roord", "Delk", "Yarde", "Isami", "Cial", "Deek", "Ykina", "Whoold", "Laep", "Samd", "Untck", "Iory", 
			"Irlt", "Dreyck", "Urnll", "Etw", "Choep", "Oestu", "Dans", "Ingsh", "Iari", "Poum", "Udane", "Oacki", "Ted", "Whees",
			"Laeb", "Cheygh", "Vest", "Eissa", "Yk", "Caef", "Iveri", "Riv", "Tanrt", "Achst", "Samch", "Drog", "Iar", "Quish", 
			"Atasy", "Boz", "Abela", "Atoni", "Uraya", "Oinau", "Thock", "Awrr", "Kelm", "Teech", "Darnn", "Aughk", "Warc", "Ildc",
			"Ainai", "Belt", "Aengo", "Emsh", "Aoldo", "Acho", "Seuc", "Dyh", "Rakw", "Iagey", "Kaz", "Drad", "Nas", "Zhoth", "Iloro",
			"Ebele", "Lin", "Jog", "Angg", "Den", "Delr", "Ibanu", "Nap", "Undrr", "Ewore", "Chenn", "Imosy", "Ashnt", "Uskl"};
	
	public static Figure makeFigure(boolean ally){
		Figure figure;
		int maxlvl = GameEngine.getMainFigure().getLevel(), level;
		String name = names[random.nextInt(names.length)];
		Job job;
		Gender gender = Gender.values()[random.nextInt(2)];
		Icon icon;
		
		switch(random.nextInt(2)){
			case 0:
				job = new Warrior();
				break;
			case 1:
				job = new Rogue();
				break;
			default:
				job = new Warrior();
		}
		
		if(ally)
			icon = new Icon(job.getName().charAt(0), AsciiPanel.brightCyan);
		else
			icon = new Icon(job.getName().charAt(0), AsciiPanel.brightRed);
		
		figure = new Figure(name, icon, job, gender);
		
		level = random.nextInt((maxlvl - (maxlvl-3) + 1)) + (maxlvl-3);
		if(level > 1)
			figure.levelUp(level-1);
		
		return figure;
	}
}