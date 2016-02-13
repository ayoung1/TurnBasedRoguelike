package items;

public abstract class Item {
	private String name;
	
	public Item(String _name){
		assert(_name != null);
		this.name = _name;
	}
	
	public String getName(){return this.name;}
	
	public abstract String listView();
	public abstract void view();
}
