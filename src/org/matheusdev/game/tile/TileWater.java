package org.matheusdev.game.tile;

import java.util.Random;

import org.matheusdev.res.ResLoader;

public class TileWater extends Tile {
	
	private int count = 0;
	private int imgnum = 0;
	
	public TileWater(int x, int y) {
		super(x, y);
		Random rand = new Random();
		count = ((x+y)*12)+rand.nextInt()%8;
		img = ResLoader.get(ResLoader.TILE_WATER0);
	}
	
	public void tick() {
		count++;
		if ((count % 120) == 0) {
			if (imgnum == 0) {
				imgnum = 1;
				img = ResLoader.get(ResLoader.TILE_WATER1);
			} else if (imgnum == 1) {
				imgnum = 0;
				img = ResLoader.get(ResLoader.TILE_WATER0);
			}
		}
	}
}
