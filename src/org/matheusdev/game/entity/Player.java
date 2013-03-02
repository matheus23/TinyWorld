package org.matheusdev.game.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import org.matheusdev.game.building.Building;
import org.matheusdev.game.building.BuildingBase;
import org.matheusdev.game.building.BuildingFabric;
import org.matheusdev.game.building.BuildingHome;
import org.matheusdev.game.building.BuildingTower;
import org.matheusdev.game.building.BuildingTree;
import org.matheusdev.game.tile.TileGrass;
import org.matheusdev.game.tile.TileSand;
import org.matheusdev.input.Input;
import org.matheusdev.res.ResLoader;
import org.matheusdev.screens.ScreenBase;
import org.matheusdev.screens.ScreenFabric;
import org.matheusdev.screens.ScreenGame;
import org.matheusdev.screens.ScreenGameOver;
import org.matheusdev.screens.ScreenGameWon;
import org.matheusdev.screens.ScreenHome;
import org.matheusdev.screens.gui.Stack;

public class Player {
	
	public static final int frameTime = 6;
	public static final int normaldelay = 6;
	
	public Stack items = null;
	private BufferedImage[] img;
	public int x;
	public int y;
	private int count = 0;
	private int frame = 0;
	private Input in;
	private int animDir = 0;
	private int delay = 0;
	private ScreenGame map;
	private boolean pressedSpace;
	private Random rand;
	
	public Player(int x, int y, Input in, ScreenGame game, Random rand) {
		this.x = x;
		this.y = y;
		this.in = in;
		this.rand = rand;
		map = game;
		img = ResLoader.anim(ResLoader.ANIM_PLAYER, animDir);
	}
	
	public void tick() {
		if (x == 1 || y == 1 || x == map.getW()-1 || y == map.getH()-1) {
			map.getCanvas().setScreen(new ScreenGameWon(in, map.getCanvas()));
			return;
		}
		boolean moved = false;
		count++;
		if (count == frameTime) {
			increaseFrame();
			count = 0;
		}
		if (in.keys[Input.LEFT] && delay == 0 && map.isWalkable(x-1, y)) {
			x--;
			animDir = ResLoader.L;
			img = ResLoader.anim(ResLoader.ANIM_PLAYER, animDir);
			moved = true;
		}
		if (in.keys[Input.RIGHT] && delay == 0 && map.isWalkable(x+1, y)) {
			x++;
			animDir = ResLoader.R;
			img = ResLoader.anim(ResLoader.ANIM_PLAYER, animDir);
			moved = true;
		}
		if (in.keys[Input.UP] && delay == 0 && map.isWalkable(x, y-1)) {
			y--;
			animDir = ResLoader.U;
			img = ResLoader.anim(ResLoader.ANIM_PLAYER, animDir);
			moved = true;
		}
		if (in.keys[Input.DOWN] && delay == 0 && map.isWalkable(x, y+1)) {
			y++;
			animDir = ResLoader.D;
			img = ResLoader.anim(ResLoader.ANIM_PLAYER, animDir);
			moved = true;
		}
		if (moved) {
			delay = normaldelay;
		}
		if (delay != 0) {
			delay--;
		}
		if (in.keys[Input.SPACE] && !pressedSpace) {
			if (map.getBuild(x, y) instanceof BuildingTree && (items == null || items.getType() == ResLoader.GUI_WOODITEM)) {
				removeTreeAction(x, y);
			} else if (map.getBuild(x, y) instanceof BuildingTower && (items == null || items.getType() == ResLoader.TILE_TOWER)) {
				if (items == null) {
					items = new Stack(ResLoader.TILE_TOWER, 1);
				} else {
					items.pushItems(1);
				}
				map.removeBuild(x, y);
			}
			if (items != null && items.getType() == ResLoader.GUI_FABRICITEM) {
				if (map.fromBuildOkey(x, y)) {
					map.setBuild(x, y, new BuildingFabric(x, y, this, in, rand, map));
					items.popItems(1);
				}
			} if (items != null && items.getType() == ResLoader.GUI_BASEICON) {
				if (map.fromBuildOkey(x, y)) {
					map.setBuild(x, y, new BuildingBase(x, y, this, in, map));
					items.popItems(1);
				}
			} if (items != null && items.getType() == ResLoader.TILE_TOWER) {
				if (map.fromBuildOkey(x, y)) {
					map.setBuild(x, y, new BuildingTower(x, y, map));
					items.popItems(1);
				}
			} else {
				placeGrass(x, y, 4);
			}
			pressedSpace = true;
		}
		if (!in.keys[Input.SPACE] && pressedSpace) {
			pressedSpace = false;
		}
		Building b = map.getBuild(x, y);
		if (b != null) {
			buildingAction(b);
		}
		if (items != null && items.getNumber() == 0) {
			items = null;
		}
		if (!map.getTile(x, y).walkable()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			map.getCanvas().setScreen(new ScreenGameOver(in, map.getCanvas()));
		}
	}
	
	private void removeTreeAction(int x, int y) {
		map.removeBuild(x, y);
		if (items == null) {
			items = new Stack(ResLoader.GUI_WOODITEM, Math.abs(rand.nextInt()%3)+1);
		} else {
			items.pushItems(Math.abs(rand.nextInt()%3)+1);
		}
	}
	
	private void buildingAction(Building b) {
		if (b instanceof BuildingHome) {
			map.getCanvas().setScreen(new ScreenHome(map.getCanvas(), map, in));
		} else if (b instanceof BuildingFabric) {
			map.getCanvas().setScreen(new ScreenFabric(map.getCanvas(), map, in));
		} else if (b instanceof BuildingBase) {
			map.getCanvas().setScreen(new ScreenBase(map.getCanvas(), map, in));
		}
	}
	
	private void placeGrass(int bx, int by, int radius) {
		if (items != null) {
			int beginx = bx-radius;
			int beginy = by-radius;
			int endx = bx+radius+1;
			int endy = by+radius+1;
			beginx = Math.max(0, beginx);
			beginy = Math.max(0, beginy);
			endx = Math.min(map.getW(), endx);
			endy = Math.min(map.getH(), endy);
			for (int x = beginx; x < endx; x++) {
				for (int y = beginy; y < endy; y++) {
					if (getDistance(bx, by, x, y) <= radius && items.getNumber() > 0) {
						if (!map.getTile(x, y).walkable()) {
							switch(items.getType()) {
							case ResLoader.TILE_GRASS:
								map.setTile(x, y, new TileGrass(x, y, rand, map));
								break;
							case ResLoader.TILE_SAND:
								map.setTile(x, y, new TileSand(x, y));
								break;
							default: return;
							}
							items.popItems(1);
							if (items.getNumber() == 0) {
								items = null;
								return;
							}
						}
					}
				}
			}
		}
	}
	
	public int getDistance(int x1, int y1, int x2, int y2) {
		int dx = x2-x1;
		int dy = y2-y1;
		return (int)Math.round(Math.sqrt(dx*dx + dy*dy));
	}
	
	private void increaseFrame() {
		frame++;
		if (frame >= img.length) {
			frame = 0;
		}
	}

	public void render(Graphics2D g, int wx, int wy) {
		g.drawImage(img[frame], x * ResLoader.TILE_SIZE - wx, y * ResLoader.TILE_SIZE - wy, null);
		if (items != null && items.getType() == ResLoader.TILE_TOWER) {
			g.setColor(BuildingTower.perfectOrange);
			g.drawOval((int)(x*ResLoader.TILE_SIZE-8-wx-BuildingTower.getRadius())+ResLoader.TILE_SIZE/2, 
					(int)(y*ResLoader.TILE_SIZE-8-wy-BuildingTower.getRadius())+ResLoader.TILE_SIZE/2, 
					(int)(BuildingTower.getRadius()*2)+ResLoader.TILE_SIZE/2, 
					(int)(BuildingTower.getRadius()*2)+ResLoader.TILE_SIZE/2);
		}
	}

}
