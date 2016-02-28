package screens;

import asciiPanel.AsciiPanel;
import engine.GameEngine;

public abstract class FragmentScreen implements Screen {

	public final int[] point1;
	public final int[] point2;
	
	public FragmentScreen(int x, int y, int x2, int y2){
		this.point1 = new int[]{x, y};
		this.point2 = new int[]{x2, y2};
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		GameEngine.displayBorders(this.point1[0], this.point1[1], this.point2[0], this.point2[1]);
	}
}
