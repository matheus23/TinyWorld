package org.matheusdev.screens;

import java.awt.Graphics2D;

import org.matheusdev.GameCanvas;
import org.matheusdev.game.building.Building;
import org.matheusdev.game.building.BuildingBase;
import org.matheusdev.input.Input;
import org.matheusdev.res.ResLoader;

public class ScreenBase extends ScreenGameBasedMenu {
	
	private Input in;
	private GameCanvas canvas;
	private BuildingBase base;

	public ScreenBase(GameCanvas canvas, ScreenGame gamescreen, Input in) {
		super(canvas, gamescreen);
		this.in = in;
		this.canvas = canvas;
		Building b = gamescreen.getBuild(gamescreen.getPlayer().x, gamescreen.getPlayer().y);
		if (b instanceof BuildingBase) {
			base = ((BuildingBase) b);
		} else {
			System.err.println("ScreenBase invoked wrong!");
			System.exit(-1);
		}
	}

	public void tick() {
		if (in.keys[Input.ESC]) {
			canvas.setScreen(gamescreen);
			gamescreen.getPlayer().y += 1;
			in.keys[Input.ESC] = false; 
		}
		base.getStorage().tick();
		base.tickMenu();
	}

	public void renderMenu(Graphics2D g) {
		g.drawImage(ResLoader.get(ResLoader.GUI_BASE), 200, 100, null);
		base.getStorage().render(g);
		base.renderMenu(g, 200, 100);
	}

}
