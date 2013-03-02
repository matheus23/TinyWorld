package org.matheusdev.screens;

import java.awt.Graphics2D;

import org.matheusdev.GameCanvas;

public abstract class ScreenGameBasedMenu extends Screen {
	
	protected ScreenGame gamescreen;
	
	public ScreenGameBasedMenu(GameCanvas canvas, ScreenGame gamescreen) {
		this.gamescreen = gamescreen;
	}
	
	public void render(Graphics2D g) {
		gamescreen.setBrightness(0.2f);
		gamescreen.render(g);
		renderMenu(g);
	}
	
	public abstract void renderMenu(Graphics2D g);
	
}
