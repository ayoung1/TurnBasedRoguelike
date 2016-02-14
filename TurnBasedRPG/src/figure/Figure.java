package figure;

import asciiPanel.AsciiPanel;
import screens.Printable;
import icon.Icon;
import items.*;

public abstract class Figure implements Printable{
	private String name;
	private Icon icon;
	private Weapon mainhand;
	private Weapon offhand;
	private int movement = 3;
	
	public Figure(String _name, Icon _icon){
		assert(_name != null);
		assert(_icon != null);
		
		this.name = _name;
		this.icon = _icon;
		this.mainhand = new Unarmed();
		this.offhand = new Unarmed();
	}
	
	public String getName(){return this.name;}
	public Icon getIcon(){return this.icon;}
	public Weapon getMainhand(){return this.mainhand;}
	public Weapon getOffhand(){return this.offhand;}
	public int getMovement(){return this.movement;}
	
	public void equipMain(Weapon weapon){
		this.unequipMain();
		this.mainhand = weapon;
		this.mainhand.getEquip().onEquip(this);
	}
	
	public void unequipMain(){
		this.mainhand.getEquip().onUnequip(this);
		this.mainhand = new Unarmed();
	}
	
	public void unequipOff(){
		this.offhand = new Unarmed();
	}
	
	@Override
	public void printToTerminal(AsciiPanel _terminal, int _x, int _y) {
		icon.printToTerminal(_terminal, _x, _y);
	}
}
