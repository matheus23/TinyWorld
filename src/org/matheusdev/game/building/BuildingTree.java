package org.matheusdev.game.building;

import java.util.Random;

import org.matheusdev.game.tile.TileGrass;
import org.matheusdev.res.ResLoader;
import org.matheusdev.screens.ScreenGame;

public class BuildingTree extends Building {
	
	private Random rand;
	private ScreenGame game;
	private int count = 0;

	public BuildingTree(int x, int y, Random rand, ScreenGame game) {
		super(x, y, -8);
		this.game = game;
		this.rand = rand;
		count = Math.abs(rand.nextInt()%300)+1400;
		img = ResLoader.get(ResLoader.BUILD_TREE);
	}
	
	public void tick() {
		count--;
		if (count == 0) {
			tryPlant(x-1, y);
			tryPlant(x+1, y);
			tryPlant(x, y-1);
			tryPlant(x, y+1);
			count = Math.abs(rand.nextInt()%300)+1400;
		}
	}
	
	private void tryPlant(int x, int y) {
		if (game.fromBuildOkey(x, y) && game.getTile(x, y) instanceof TileGrass) {
			game.setBuild(x, y, new BuildingTree(x, y, rand, game));
		}
	}

}
