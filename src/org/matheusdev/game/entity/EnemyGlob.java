package org.matheusdev.game.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.matheusdev.res.ResLoader;
import org.matheusdev.screens.ScreenGame;

public class EnemyGlob extends Enemy {
	
	private BufferedImage[] img;
	private int frame;
	private int count;
	
	public EnemyGlob(int x, int y, ScreenGame game) {
		super(x, y, game);
		health = 50;
		img = ResLoader.anim(ResLoader.ANIM_GLOB, ResLoader.D);
	}
	
	protected int getSpeed() {
		return 1;
	}
	
	protected int getRadius() {
		return 4;
	}
	
	public int w() {
		return 32;
	}
	
	public int h() {
		return 32;
	}
	
	public void tick(boolean night) {
		super.tick(night);
		count++;
		if (count % 60 == 0) {
			count = 0;
			frame++;
			if (frame == img.length) {
				frame = 0;
			}
		}
	}
	
	public void render(Graphics2D g, int wx, int wy) {
		g.drawImage(img[frame], (int)x-wx, (int)y-wy, null);
	}
	
	public void renderEyes(Graphics2D g, int wx, int wy) {
		g.drawImage(ResLoader.anim(ResLoader.ANIM_GLOB_EYES, ResLoader.D)[frame], (int)x-wx, (int)y-wy, null);
	}

}
