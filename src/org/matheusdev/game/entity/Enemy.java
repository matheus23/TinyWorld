package org.matheusdev.game.entity;

import java.awt.Graphics2D;
import java.awt.Point;

import org.matheusdev.res.ResLoader;
import org.matheusdev.screens.ScreenGame;
import org.matheusdev.vec.Vec;

public abstract class Enemy {
	
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;
	
	public float x;
	public float y;
	protected ScreenGame game;
	protected Vec v;
	protected GrabbedTiles grab;
	protected int health;
	
	public Enemy(int x, int y, ScreenGame game) {
		this.x = x;
		this.y = y;
		this.game = game;
		Point p = game.getRandomTile();
		p.x *= ResLoader.TILE_SIZE;
		p.y *= ResLoader.TILE_SIZE;
		v = new Vec(x, y, p.x, p.y);
		v.normalize();
		v.mul((float)getSpeed());
	}
	
	public float midx() {
		return x+(w()/2);
	}
	
	public float midy() {
		return y+(h()/2);
	}
	
	public void hurt(int num) {
		health -= num;
		if (health <= 0) {
			game.removeEnemy(this);
		}
	}
	
	public abstract int w();
	
	public abstract int h();
	
	protected abstract int getSpeed();
	
	protected abstract int getRadius();
	
	public void tick(boolean night) {
		x += v.x;
		y += v.y;
		if (grab == null) {
			int gx = Math.round(x/ResLoader.TILE_SIZE);
			int gy = Math.round(y/ResLoader.TILE_SIZE);
			if (gx >= 0 && gy >= 0 && gx < game.getW() && gy < game.getH()) {
				if (game.getTile(gx, gy).walkable()) {
					grab = new GrabbedTiles(gx, gy, getRadius(), game);
				}
			}
		}
		if (x < 0 || y < 0 || x > game.getW()*ResLoader.TILE_SIZE || y > game.getH()*ResLoader.TILE_SIZE) {
			game.removeEnemy(this);
		}
	}
	
	public void renderGrab(Graphics2D g, int wx, int wy) {
		if (grab != null) {
			grab.render(g, (int)x, (int)y, wx, wy);
		}
	}
	
	public abstract void render(Graphics2D g, int wx, int wy);
	
	public abstract void renderEyes(Graphics2D g, int wx, int wy);
	
}
