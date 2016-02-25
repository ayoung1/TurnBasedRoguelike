package screens;

public class ScreenOption {
	
	public final String name;
	public final Screen screen;
	
	public ScreenOption(String name, Screen screen){
		this.name = name;
		this.screen = screen;
	}
	
	public Screen getScreen(){return this.screen;}
}
