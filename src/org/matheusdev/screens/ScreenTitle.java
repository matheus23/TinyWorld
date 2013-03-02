package org.matheusdev.screens;

import java.awt.Graphics2D;

import org.matheusdev.GameCanvas;
import org.matheusdev.input.Input;
import org.matheusdev.res.ResLoader;

public class ScreenTitle extends Screen {
	
	private GameCanvas canvas;
	private Input in;
	
	public ScreenTitle(GameCanvas canvas, Input in) {
		this.canvas = canvas;
		this.in = in;
		for (int i = 0; i < in.keys.length; i++) {
			in.keys[i] = false;
		}
	}

	public void tick() {
		if (in.keys[Input.ENTER]) {
			canvas.setScreen(new ScreenTutorial(canvas, in));
		}
	}

	public void render(Graphics2D g) {
		g.drawImage(ResLoader.get(ResLoader.SCREEN_TITLESCREEN), 0, 0, null);
	}

}
