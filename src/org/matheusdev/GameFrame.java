package org.matheusdev;

import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class GameFrame extends Frame implements WindowListener {
	private static final long serialVersionUID = -8997558665137166538L;
	
	private GameCanvas canvas;
	
	public GameFrame() {
		setTitle("Ludum dare - Tiny World");
		canvas = new GameCanvas(this);
		setResizable(false);
		add(canvas);
		addWindowListener(this);
		pack();
		setVisible(true);
		canvas.start();
	}

	public void windowActivated(WindowEvent arg0) {
	}

	public void windowClosed(WindowEvent arg0) {
	}

	public void windowClosing(WindowEvent arg0) {
		canvas.stop();
	}

	public void windowDeactivated(WindowEvent arg0) {
	}

	public void windowDeiconified(WindowEvent arg0) {
	}

	public void windowIconified(WindowEvent arg0) {
	}

	public void windowOpened(WindowEvent arg0) {
	}
	
	public static void main(String[] args) {
		// 37 classes only!!!
		// even more sprites: 39
		new GameFrame();
	}

}
