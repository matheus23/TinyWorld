package org.matheusdev.screens.gui;

import java.awt.Color;
import java.awt.Graphics2D;

import org.matheusdev.GameCanvas;
import org.matheusdev.game.entity.Player;
import org.matheusdev.res.ResLoader;

public class Gui {
	
	private GameCanvas canvas;
	private Player p;
	
	public Gui(Player p, GameCanvas canvas) {
		this.canvas = canvas;
		this.p = p;
	}
	
	public void render(Graphics2D g) {
		g.drawImage(ResLoader.get(ResLoader.GUI), 0, 0, null);
		g.setColor(Color.WHITE);
		g.drawString(Long.toString(canvas.fps), 38, 13);
		if (p.items != null) {
			p.items.renderIcon(g, 58, 15);
			g.drawString(Integer.toString(p.items.getNumber()), 81, 27);
		} else {
			g.setColor(Color.WHITE);
			g.fillRect(58, 15, ResLoader.TILE_SIZE, ResLoader.TILE_SIZE);
		}
	}

}
