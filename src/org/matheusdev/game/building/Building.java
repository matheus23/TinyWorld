package org.matheusdev.game.building;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.matheusdev.res.ResLoader;

public abstract class Building {
	
	protected BufferedImage img;
	protected int x;
	protected int y;
	protected int yoffset;
	
	public Building(int x, int y, int yoffset) {
		this.x = x;
		this.y = y;
		this.yoffset = yoffset;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics2D g, int wx, int wy) {
		g.drawImage(img, (x*ResLoader.TILE_SIZE)-wx, ((y*ResLoader.TILE_SIZE)+yoffset)-wy, null);
	}

}
