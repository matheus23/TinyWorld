package org.matheusdev.input;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener {
	
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;
	public static final int ESC = 4;
	public static final int R = 5;
	public static final int F2 = 6;
	public static final int SPACE = 7;
	public static final int F = 8;
	public static final int SHIFT = 9;
	public static final int ANY = 10;
	public static final int ENTER = 11;
	
	public boolean[] keys = new boolean[64];
	
	public Input(Component c) {
		c.addKeyListener(this);
	}
	
	public void updateKeys(KeyEvent e, boolean to) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT: keys[LEFT] = to; break;
		case KeyEvent.VK_RIGHT: keys[RIGHT] = to; break;
		case KeyEvent.VK_UP: keys[UP] = to; break;
		case KeyEvent.VK_DOWN: keys[DOWN] = to; break;
		case KeyEvent.VK_ESCAPE: keys[ESC] = to; break;
		case KeyEvent.VK_R: keys[R] = to; break;
		case KeyEvent.VK_F2: keys[F2] = to; break;
		case KeyEvent.VK_SPACE: keys[SPACE] = to; break;
		case KeyEvent.VK_F: keys[F] = to; break;
		case KeyEvent.VK_SHIFT: keys[SHIFT] = to; break;
		case KeyEvent.VK_ENTER: keys[ENTER] = to; break;
		}
		keys[ANY] = to;
	}
	
	public void keyPressed(KeyEvent e) {
		updateKeys(e, true);
	}
	
	public void keyReleased(KeyEvent e) {
		updateKeys(e, false);
	}
	
	public void keyTyped(KeyEvent e) {
	}

}
