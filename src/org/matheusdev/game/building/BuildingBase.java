package org.matheusdev.game.building;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import org.matheusdev.game.TextParticle;
import org.matheusdev.game.entity.Player;
import org.matheusdev.input.Input;
import org.matheusdev.res.ResLoader;
import org.matheusdev.screens.ScreenGame;
import org.matheusdev.screens.gui.GuiStorage;
import org.matheusdev.screens.gui.Stack;

public class BuildingBase extends Building {
	
	public static final Color perfectGray = new Color(0x2a2a2a);
	
	private GuiStorage storage;
	private Stack wood1;
	private Stack wood2;
	private Stack wood3;
	private Input in;
	private boolean pressedShift = false;
	private Player p;
	private int count = 0;
	private ScreenGame game;

	public BuildingBase(int x, int y, Player p, Input in, ScreenGame game) {
		super(x, y, -16);
		this.game = game;
		this.in = in;
		this.p = p;
		img = ResLoader.get(ResLoader.BUILD_BASE);
		storage = new GuiStorage(232, 128, 4, 9, p, in);
		wood1 = new Stack(ResLoader.GUI_WOODITEM, 0);
		wood2 = new Stack(ResLoader.GUI_WOODITEM, 0);
		wood3 = new Stack(ResLoader.GUI_WOODITEM, 0);
	}
	
	public void tick() {
		if (count <= 0) {
			if (availableWood() >= 10) {
				takeWood(10);
				Random rand = new Random();
				int num = Math.abs(rand.nextInt()%10);
				if (num == 9) {
					storage.addItems(new Stack(ResLoader.GUI_FABRICITEM, 1));
					game.addParticle(new TextParticle("[BUILDER:] U're lucky: Produced a Fabric!", 
							x*ResLoader.TILE_SIZE-64, 
							y*ResLoader.TILE_SIZE-32, 300, game));
				} else if (num == 8) {
					storage.addItems(new Stack(ResLoader.GUI_BASEICON, 1));
					game.addParticle(new TextParticle("[BUILDER:] U're lucky: Produced a Builder!", 
							x*ResLoader.TILE_SIZE-64, 
							y*ResLoader.TILE_SIZE-32, 300, game));
				} else {
					storage.addItems(new Stack(ResLoader.TILE_TOWER, 1));
					game.addParticle(new TextParticle("[BUILDER:] Defensive Tower produced!", 
							x*ResLoader.TILE_SIZE-64, 
							y*ResLoader.TILE_SIZE-32, 300, game));
				}
			}
			count = 600;
		}
		count--;
	}
	
	public void tickMenu() {
		if (in.keys[Input.SHIFT] && !pressedShift) {
			tryAddWood(p.items);
			if (p.items != null && p.items.getNumber() <= 0) {
				p.items = null;
			}
			pressedShift = true;
		}
		if (!in.keys[Input.SHIFT] && pressedShift) {
			pressedShift = false;
		}
	}
	
	private void tryAddWood(Stack s) {
		wood1.tryAddStack(s);
		wood2.tryAddStack(s);
		wood3.tryAddStack(s);
	}
	
	private void takeWood(int num) {
		int totake = num;
		totake = Math.max(0, wood3.popItems(totake));
		totake = Math.max(0, wood2.popItems(totake));
		totake = Math.max(0, wood1.popItems(totake));
	}
	
	private int availableWood() {
		return wood1.getNumber()+wood2.getNumber()+wood3.getNumber();
	}
	
	public GuiStorage getStorage() {
		return storage;
	}
	
	public void renderMenu(Graphics2D g, int guix, int guiy) {
		wood1.renderIcon(g, guix+256, guiy+75);
		wood2.renderIcon(g, guix+256, guiy+91);
		wood3.renderIcon(g, guix+256, guiy+107);
		wood1.renderNumber(g, guix+256, guiy+75);
		wood2.renderNumber(g, guix+256, guiy+91);
		wood3.renderNumber(g, guix+256, guiy+107);
		float fill = (count/600f)*103f;
		g.setColor(perfectGray);
		g.fillRect(guix+132, guiy+96, 105, 8);
		g.setColor(BuildingTower.perfectOrange);
		g.fillRect(guix+133+(int)(fill), guiy+97, (int)(103-fill), 6);
	}
	
}
