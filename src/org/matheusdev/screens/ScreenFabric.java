package org.matheusdev.screens;

import java.awt.Graphics2D;

import org.matheusdev.GameCanvas;
import org.matheusdev.game.building.Building;
import org.matheusdev.game.building.BuildingFabric;
import org.matheusdev.input.Input;
import org.matheusdev.res.ResLoader;
import org.matheusdev.screens.gui.GuiStorage;
import org.matheusdev.screens.gui.Stack;

public class ScreenFabric extends ScreenGameBasedMenu {
	
	private Input in;
	private GameCanvas canvas;
	private GuiStorage storage;
	private Stack wood;
	private boolean pressedShift = false;

	public ScreenFabric(GameCanvas canvas, ScreenGame gamescreen, Input in) {
		super(canvas, gamescreen);
		this.in = in;
		this.canvas = canvas;
		Building b = gamescreen.getBuild(gamescreen.getPlayer().x, gamescreen.getPlayer().y);
		if (b instanceof BuildingFabric) {
			BuildingFabric fabric = ((BuildingFabric) b); 
			storage = fabric.getStorage();
			wood = fabric.getWoodStack();
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
		if (in.keys[Input.SHIFT] && !pressedShift) {
			if (gamescreen.getPlayer().items != null) {
				if (gamescreen.getPlayer().items.getType() == ResLoader.GUI_WOODITEM) {
					wood.tryAddStack(gamescreen.getPlayer().items);
				}
			}
			if (gamescreen.getPlayer().items != null && gamescreen.getPlayer().items.getNumber() <= 0) {
				gamescreen.getPlayer().items = null;
			}
			pressedShift = true;
		}
		if (!in.keys[Input.SHIFT] && pressedShift) {
			pressedShift = false;
		}
		storage.tick();
	}

	public void renderMenu(Graphics2D g) {
		g.drawImage(ResLoader.get(ResLoader.GUI_FABRIC), 200, 100, null);
		storage.render(g);
		wood.renderIcon(g, 488, 192);
		wood.renderNumber(g, 488, 192);
	}

}
