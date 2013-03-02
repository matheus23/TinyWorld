package org.matheusdev.res;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ResLoader {
	
	public static final int TILE_SIZE = 16;
	
	public static final int TILE_GRASS = 0;
	public static final int TILE_WATER0 = 1;
	public static final int TILE_WATER1 = 2;
	public static final int TILE_SAND = 3;
	public static final int TILE_TOWER = 4;
	public static final int TILE_TOWER_GLOW = 5;
	public static final int TILE_BULLET = 6;
	
	public static final int SCREEN_GAMEOVER = 16;
	public static final int SCREEN_GAMEWON = 17;
	public static final int SCREEN_TITLESCREEN = 18;
	public static final int SCREEN_TUT1 = 19;
	public static final int SCREEN_TUT2 = 20;
	public static final int SCREEN_TUT3 = 21;
	public static final int SCREEN_TUT4 = 22;
	public static final int SCREEN_TUT5 = 23;
	public static final int SCREEN_TUT6 = 24;
	public static final int SCREEN_TUT7 = 25;
	public static final int SCREEN_TUT8 = 26;
	
	public static final int BUILD_HOME = 32;
	public static final int BUILD_FABRIC = 33;
	public static final int BUILD_TREE = 34;
	public static final int BUILD_BASE = 35;
	
	public static final int GUI = 48;
	public static final int GUI_STORAGE = 49;
	public static final int GUI_SELECTOR = 50;
	public static final int GUI_FABRIC = 51;
	public static final int GUI_WOODITEM = 52;
	public static final int GUI_FABRICITEM = 53;
	public static final int GUI_BASEICON = 54;
	public static final int GUI_BASE = 55;
	
	public static final int ANIM_PLAYER = 0;
	public static final int ANIM_BAT = 1;
	public static final int ANIM_GLOB = 2;
	public static final int ANIM_BAT_EYES = 3;
	public static final int ANIM_GLOB_EYES = 4;
	public static final int ANIM_ARROW = 5;
	
	public static final int R = 0;
	public static final int L = 1;
	public static final int U = 2;
	public static final int D = 3;
	
	private static BufferedImage[] imgs;
	private static BufferedImage[][][] anims;
	
	public static void loadImgs() {
		imgs = new BufferedImage[64];
		anims = new BufferedImage[64][4][];
		
		imgs[TILE_GRASS] = loadImg("res/tile_img.png");
		imgs[TILE_WATER0] = loadImg("res/water_img0.png");
		imgs[TILE_WATER1] = loadImg("res/water_img1.png");
		imgs[TILE_SAND] = loadImg("res/sand.png");
		imgs[TILE_TOWER] = loadImg("res/tower.png");
		imgs[TILE_TOWER_GLOW] = loadImg("res/tower_glow.png");
		imgs[TILE_BULLET] = loadImg("res/tower_bullet.png");
		
		imgs[SCREEN_GAMEOVER] = loadImg("res/loose.png");
		imgs[SCREEN_GAMEWON] = loadImg("res/win.png");
		imgs[SCREEN_TITLESCREEN] = loadImg("res/titlescreen.png");
		imgs[SCREEN_TUT1] = loadImg("res/tutorial/pic1.png");
		imgs[SCREEN_TUT2] = loadImg("res/tutorial/pic2.png");
		imgs[SCREEN_TUT3] = loadImg("res/tutorial/pic3.png");
		imgs[SCREEN_TUT4] = loadImg("res/tutorial/pic4.png");
		imgs[SCREEN_TUT5] = loadImg("res/tutorial/pic5.png");
		imgs[SCREEN_TUT6] = loadImg("res/tutorial/pic6.png");
		imgs[SCREEN_TUT7] = loadImg("res/tutorial/pic7.png");
		imgs[SCREEN_TUT8] = loadImg("res/tutorial/pic8.png");
		
		imgs[BUILD_HOME] = loadImg("res/home.png");
		imgs[BUILD_FABRIC] = loadImg("res/fabric.png");
		imgs[BUILD_TREE] = loadImg("res/tree.png");
		imgs[BUILD_BASE] = loadImg("res/tower_base.png");
		
		imgs[GUI] = loadImg("res/gui.png");
		imgs[GUI_STORAGE] = loadImg("res/gui_storage.png");
		imgs[GUI_SELECTOR] = loadImg("res/selector.png");
		imgs[GUI_FABRIC] = loadImg("res/gui_fabric.png");
		imgs[GUI_WOODITEM] = loadImg("res/wood.png");
		imgs[GUI_FABRICITEM] = loadImg("res/fabric_icon.png");
		imgs[GUI_BASEICON] = loadImg("res/tower_base_icon.png");
		imgs[GUI_BASE] = loadImg("res/gui_base.png");
		
		anims[ANIM_PLAYER][R] = loadAnim("res/player_walk_right.png", 2);
		anims[ANIM_PLAYER][L] = loadAnim("res/player_walk_left.png", 2);
		anims[ANIM_PLAYER][U] = loadAnim("res/player_walk_up.png", 2);
		anims[ANIM_PLAYER][D] = loadAnim("res/player_walk_down.png", 2);
		
		anims[ANIM_BAT][D] = loadAnim("res/bat.png", 2);
		anims[ANIM_GLOB][D] = loadAnim("res/glob.png", 2);
		anims[ANIM_BAT_EYES][D] = loadAnim("res/bat_eyes.png", 2);
		anims[ANIM_GLOB_EYES][D] = loadAnim("res/glob_eyes.png", 2);
		anims[ANIM_ARROW][D] = loadAnim("res/arrow.png", 2);
	}
	
	public static BufferedImage get(int id) {
		return imgs[id];
	}
	
	public static BufferedImage[] anim(int id, int dir) {
		return anims[id][dir];
	}
	
	public static BufferedImage[] loadAnim(String filepath, int num) {
		BufferedImage[] animation = new BufferedImage[num];
		
		BufferedImage src = loadImg(filepath);
		int w = src.getWidth()/num;
		int h = src.getHeight();
		
		for (int i = 0; i < num; i++) {
			animation[i] = src.getSubimage(i*w, 0, w, h);
		}
		
		return animation;
	}
	
	public static BufferedImage loadImg(String filepath) {
		BufferedImage img = null;
		try {
			File file = new File(filepath);
			BufferedImage temp = ImageIO.read(file);
			img = new BufferedImage(
					temp.getWidth(), temp.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = (Graphics2D) img.getGraphics();
			g2d.drawImage(temp, 0, 0, null);
			g2d.dispose();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
}
