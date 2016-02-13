package figure;

import asciiPanel.AsciiPanel;
import screens.Printable;
import icon.Icon;

public abstract class Figure implements Printable{
	private String name;
	private Icon icon;
	private int movement = 3;
	
	public Figure(String _name, Icon _icon){
		assert(_name != null);
		assert(_icon != null);
		
		this.name = _name;
		this.icon = _icon;
	}
	
	public String getName(){return this.name;}
	public Icon getIcon(){return this.icon;}

	public int getMovement(){return this.movement;}
	
	
	@Override
	public void printToTerminal(AsciiPanel _terminal, int _x, int _y) {
		icon.printToTerminal(_terminal, _x, _y);
	}
	
	public boolean isNull(){return false;}
}
