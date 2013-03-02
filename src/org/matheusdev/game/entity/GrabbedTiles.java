package org.matheusdev.game.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import org.matheusdev.game.Util;
import org.matheusdev.game.tile.Tile;
import org.matheusdev.game.tile.TileWater;
import org.matheusdev.res.ResLoader;
import org.matheusdev.screens.ScreenGame;

public class GrabbedTiles {
	
	private Tile[][] tiles;
	private ScreenGame game;
	private int radius;
	
	public GrabbedTiles(int x, int y, int radius, ScreenGame game) {
		tiles = new Tile[radius*2][radius*2];
		this.game = game;
		this.radius = radius;
		grab(x, y, radius);
	}
	
	private void grab(int bx, int by, int radius) {
		int beginx = bx-radius;
		int beginy = by-radius;
		int endx = bx+radius;
		int endy = by+radius;
		beginx = Math.max(0, beginx);
		beginy = Math.max(0, beginy);
		endx = Math.min(game.getW(), endx);
		endy = Math.min(game.getH(), endy);
		for (int x = beginx; x < endx; x++) { 
			for (int y = beginy; y < endy; y++) {
				if (game.getTile(x, y).walkable() && Util.distance(x-beginx, y-beginy, radius, radius) < radius) {
					tiles[x-beginx][y-beginy] = game.getTile(x, y);
					game.setTile(x, y, new TileWater(x, y));
					game.removeBuild(x, y);
				}
			}
		}
	}
	
	public void render(Graphics2D g, int px, int py, int wx, int wy) {
		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles[x].length; y++) {
				if (tiles[x][y] != null) {
					g.drawImage(tiles[x][y].getImg(), (x-radius)*ResLoader.TILE_SIZE+px-wx, (y-radius)*ResLoader.TILE_SIZE+py-wy, null);
					g.setColor(Color.BLACK);
					g.drawRect((x-radius)*ResLoader.TILE_SIZE+px-wx, (y-radius)*ResLoader.TILE_SIZE+py-wy, ResLoader.TILE_SIZE, ResLoader.TILE_SIZE);
				}
			}
		}
	}

}
