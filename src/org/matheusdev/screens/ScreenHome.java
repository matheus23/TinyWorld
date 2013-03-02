package org.matheusdev.screens;

import java.awt.Graphics2D;

import org.matheusdev.GameCanvas;
import org.matheusdev.game.building.Building;
import org.matheusdev.game.building.BuildingHome;
import org.matheusdev.input.Input;
import org.matheusdev.res.ResLoader;
import org.matheusdev.screens.gui.GuiStorage;

public class ScreenHome extends ScreenGameBasedMenu {
	
	private Input in;
	private GameCanvas canvas;
	private GuiStorage storage;

	public ScreenHome(GameCanvas canvas, ScreenGame gamescreen, Input in) {
		super(canvas, gamescreen);
		this.in = in;
		this.canvas = canvas;
		Building b = gamescreen.getBuild(gamescreen.getPlayer().x, gamescreen.getPlayer().y);
		if (b instanceof BuildingHome) {
			storage = ((BuildingHome) b).getStorage();
		} else {
			System.err.println("ScreenHome invoked wrong!");
			System.exit(-1);
		}
	}

	public void tick() {
		if (in.keys[Input.ESC]) {
			canvas.setScreen(gamescreen);
			gamescreen.getPlayer().y += 1;
			in.keys[Input.ESC] = false; 
		}
		storage.tick();
	}

	public void renderMenu(Graphics2D g) {
		g.drawImage(ResLoader.get(ResLoader.GUI_STORAGE), 200, 100, null);
		storage.render(g);
	}

}
