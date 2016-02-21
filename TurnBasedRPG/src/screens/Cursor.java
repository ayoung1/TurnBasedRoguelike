package screens;

import icon.Icon;

public class Cursor extends Icon{

	private int x;
	private int y;
	
	public Cursor(Icon icon, int x, int y) {
		super(icon.getCharacter(), icon.getColor());
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
		
		this.x += x;
		this.y += y;
	}
}
