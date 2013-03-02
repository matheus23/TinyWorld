package org.matheusdev.game.tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.matheusdev.res.ResLoader;

public abstract class Tile {

	protected BufferedImage img;
	protected int x;
	protected int y;
	
	public Tile(int x, int y) {
		this.x = x*ResLoader.TILE_SIZE;
		this.y = y*ResLoader.TILE_SIZE;
	}
	
	public boolean walkable() {
		return false;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics2D g, int wx, int wy) {
		g.drawImage(img, x-wx, y-wy, null);
	}
	
	public BufferedImage getImg() {
		return img;
	}
}
