package org.matheusdev.screens;

import java.awt.Graphics2D;

import org.matheusdev.GameCanvas;
import org.matheusdev.input.Input;
import org.matheusdev.res.ResLoader;

public class ScreenGameWon extends Screen {
	
	private Input in;
	private GameCanvas canvas;
	
	public ScreenGameWon(Input in, GameCanvas canvas) {
		this.in = in;
		this.canvas = canvas;
		for (int i = 0; i < in.keys.length; i++) {
			in.keys[i] = false;
		}
	}
	
	public void tick() {
		if (in.keys[Input.ANY]) {
			canvas.setScreen(new ScreenTitle(canvas, in));
		}
	}

	public void render(Graphics2D g) {
		g.drawImage(ResLoader.get(ResLoader.SCREEN_GAMEWON), 0, 0, null);
	}

}
