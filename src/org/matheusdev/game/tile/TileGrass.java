package org.matheusdev.game.tile;

import java.util.Random;

import org.matheusdev.game.building.BuildingTree;
import org.matheusdev.res.ResLoader;
import org.matheusdev.screens.ScreenGame;

public class TileGrass extends Tile {
	
	public TileGrass(int x, int y, Random rand, ScreenGame game) {
		super(x, y);
		img = ResLoader.get(ResLoader.TILE_GRASS);
		if (rand.nextInt()% 100 == 0 && game.fromBuildOkey(x, y)) {
			game.addTree(new BuildingTree(x, y, rand, game), x, y);
		}
	}
	
	public boolean walkable() {
		return true;
	}

}
