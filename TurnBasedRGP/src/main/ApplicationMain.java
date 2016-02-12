package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import engine.GameEngine;
import figure.TestFigure;
import screens.Screen;
import screens.StartScreen;
import asciiPanel.AsciiPanel;

public class ApplicationMain extends JFrame implements KeyListener{
	
	private static final long serialVersionUID = -7895776404902719682L;

	private AsciiPanel terminal;
	private Screen screen;
	
	public ApplicationMain(){
		super();
		GameEngine.addToParty(new TestFigure());
		this.terminal = new AsciiPanel();
		add(this.terminal);
		pack();
		this.screen = new StartScreen();
		addKeyListener(this);
		repaint();
	}
	
	public void repaint(){
		terminal.clear();
		screen.displayOutput(terminal);
		super.repaint();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		screen = screen.respondToUserInput(arg0);
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	public static void main(String[] args) {
		ApplicationMain app = new ApplicationMain();
		
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);

	}

}
