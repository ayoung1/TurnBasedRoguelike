package screens;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import asciiPanel.AsciiPanel;

public class ScrollableTextView extends FragmentScreen{

	private List<String> text;
	private Printable printable;
	private int linesAvailable;
	private int charactersAvailable;
	private int topLine = 0;
	
	public ScrollableTextView(Printable printable, int x, int y, int x2, int y2) {
		super(x, y, x2, y2);
		this.linesAvailable = (this.point2[1]) - (this.point1[1]);
		this.charactersAvailable = this.point2[0] - this.point1[0];
		this.printable = printable;
	}

	private List<String> scaleStrings(List<String> list){
		List<String> text = new ArrayList<>();
		String temp;
		
		for(String s : list){
			if(s.length() < this.charactersAvailable)
				text.add(s);
			else{
				temp = s;
				do{
					text.add(temp.substring(0, this.charactersAvailable-1));
					temp = temp.substring(this.charactersAvailable-1);
				}while(!(temp.length() < this.charactersAvailable));
				text.add(temp);
			}
		}
		
		return text;
	}
	
	private void writeText(AsciiPanel terminal){
		int height = this.point1[1]+1;
		int offset = this.point1[0]+1;
		
		this.text = this.scaleStrings(printable.getLines());
		
		if(this.text.size() < this.linesAvailable){
			for(String s : this.text)
				terminal.write(s, offset, height++);
		}else{
			for(int i = this.topLine; i < this.linesAvailable + this.topLine - 1; i++){
				terminal.write(this.text.get(i), offset, height++);
			}
		}
	}
	
	public void displayOutput(AsciiPanel terminal) {
		super.displayOutput(terminal);
		this.writeText(terminal);
	}
	
	@Override
	public Screen respondToUserInput(KeyEvent key) {
		if(key.getKeyCode() == KeyEvent.VK_PAGE_DOWN)
			this.topLine++;
		else if(key.getKeyCode() == KeyEvent.VK_PAGE_UP)
			this.topLine--;
		
		if(this.topLine < 0)
			this.topLine = (this.text.size() - this.linesAvailable)+1;
		else if(this.topLine > (this.text.size() - this.linesAvailable)+1)
			this.topLine = 0;
		
		return this;
	}
}