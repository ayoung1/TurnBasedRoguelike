package icon;

import java.awt.Color;

import screens.Printable;
import asciiPanel.AsciiPanel;

public class Icon implements Printable{

	private char character;
	private Color color;
	
	public Icon(char _character, Color _color){
		this.character = _character;
		this.color = _color;
	}
	
	public char getCharacter(){return this.character;}
	
	public void printToTerminal(AsciiPanel _terminal, int _x, int _y){
		_terminal.write(this.character, _x, _y, this.color);
	}
}
