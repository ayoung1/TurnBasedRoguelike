package screens;

import icon.Icon;
import world.World;

public class Cursor extends Icon{

	private World world;
	private int x;
	private int y;
	
	public Cursor(Icon icon, World world, int x, int y) {
		super(icon.getCharacter(), icon.getColor());
		this.world = world;
		this.x = x;
		this.y = y;
	}
	
	public int getX(){return this.x;}
	public int getY(){return this.y;}
	
	public void update(int dx, int dy){
		int x = dx, y = dy;
		
		if(dx > 0)
			x = 1;
		else if(dx < 0)
			x = -1;
		if(dy > 0)
			y = 1;
		else if(dy < 0)
			y = -1;
		
		if(world.isPathable(this.x + x, this.y + y)){
			this.x += x;
			this.y += y;
		}
	}
}
