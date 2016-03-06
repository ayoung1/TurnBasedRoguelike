package items;

import screens.MenuBlock.MenuSelection;
import screens.Screen;

public abstract class Item implements MenuSelection{
	private String name;
	
	public Item(String _name){
		assert(_name != null);
		this.name = _name;
	}
	
	public String getName(){return this.name;}
	
	public abstract String listView();
	public abstract Screen view();
}
