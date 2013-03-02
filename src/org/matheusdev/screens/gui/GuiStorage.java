package org.matheusdev.screens.gui;

import java.awt.Graphics2D;

import org.matheusdev.game.entity.Player;
import org.matheusdev.input.Input;
import org.matheusdev.res.ResLoader;

public class GuiStorage {
	
	private static final int ISIZE = ResLoader.TILE_SIZE;
	private static final int delaytime = 6;
	
	private Stack[][] storage;
	
	private int screenx;
	private int screeny;
	private Player p;
	private Input in;
	private int count;
	private int cursorx;
	private int cursory;
	private boolean pressedSpace = false;
	private int w;
	private int h;
	
	public GuiStorage(int screenx, int screeny, int w, int h, Player p, Input in) {
		this.w = w;
		this.h = h;
		storage = new Stack[w][h];
		this.screenx = screenx;
		this.screeny = screeny;
		this.p = p;
		this.in = in;
	}
	
	public void tick() {
		if (count != 0) {
			count--;
		} else {
			if (in.keys[Input.LEFT]) {
				tryMoveCursor(cursorx-1, cursory);
				count = delaytime;
			}
			if (in.keys[Input.RIGHT]) {
				tryMoveCursor(cursorx+1, cursory);
				count = delaytime;
			}
			if (in.keys[Input.DOWN]) {
				tryMoveCursor(cursorx, cursory+1);
				count = delaytime;
			}
			if (in.keys[Input.UP]) {
				tryMoveCursor(cursorx, cursory-1);
				count = delaytime;
			}
		}
		if (in.keys[Input.SPACE] && !pressedSpace) {
			spaceInteract();
			pressedSpace = true;
		}
		if (!in.keys[Input.SPACE] && pressedSpace) {
			pressedSpace = false;
		}
	}
	
	private void spaceInteract() {
		if (storage[cursorx][cursory] != null) {
			if (p.items != null 
					&& storage[cursorx][cursory].getType() == p.items.getType() 
					&& !storage[cursorx][cursory].isFilled()) {
				storage[cursorx][cursory].tryAddStack(p.items);
			} else {
				Stack save = p.items;
				p.items = storage[cursorx][cursory];
				storage[cursorx][cursory] = save;
			}
		} else {
			storage[cursorx][cursory] = p.items;
			p.items = null;
		}
		if (p.items != null && p.items.getNumber() == 0) {
			p.items = null;
		}
	}
	
	private void tryMoveCursor(int x, int y) {
		cursorx = x;
		cursory = y;
		if (cursorx < 0) cursorx = 0;
		if (cursory < 0) cursory = 0;
		cursorx = Math.min(w-1, cursorx);
		cursory = Math.min(h-1, cursory);
	}
	
	public void addItems(Stack s) {
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				if (storage[x][y] == null) {
					storage[x][y] = s;
					return;
				}
				if (storage[x][y].tryAddStack(s) == 0) {
					return;
				}
			}
		}
	}
	
	public void render(Graphics2D g) {
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				if (storage[x][y] != null) {
					storage[x][y].renderIcon(g, screenx+x*ISIZE, screeny+y*ISIZE);
				}
			}
		}
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				if (storage[x][y] != null) {
					storage[x][y].renderNumber(g, screenx+x*ISIZE, screeny+y*ISIZE);
				}
			}
		}
		g.drawImage(ResLoader.get(ResLoader.GUI_SELECTOR), screenx+cursorx*ISIZE, screeny+cursory*ISIZE, null);
	}

}
