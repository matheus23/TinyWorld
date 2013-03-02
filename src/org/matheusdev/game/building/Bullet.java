package org.matheusdev.game.building;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import org.matheusdev.game.Util;
import org.matheusdev.game.entity.Enemy;
import org.matheusdev.res.ResLoader;
import org.matheusdev.screens.ScreenGame;
import org.matheusdev.vec.Vec;

public class Bullet {
	
	private static float speed = 6f;
	private float x;
	private float y;
	private ScreenGame game;
	private Vec velocity;
	private Rectangle rect;
	private Enemy following;
	
	public Bullet(float startx, float starty, Enemy following, ScreenGame game) {
		this.x = startx;
		this.y = starty;
		this.game = game;
		this.following = following;
		rect = new Rectangle();
		velocity = new Vec(startx, startx, following.midx(), following.midy());
		velocity.normalize();
		velocity.mul(speed);
		game.bullets.add(this);
	}
	
	public void tick() {
		if (following != null) {
			velocity.x = following.midx()-x;
			velocity.y = following.midy()-y;
		}
		velocity.normalize();
		velocity.mul(speed);
		x += velocity.x;
		y += velocity.y;
		rect.x = (int)x;
		rect.y = (int)y;
		if (Util.distance(x, y, following.midx(), following.midy()) < 6f) {
			following.hurt(10);
			game.bullets.remove(this);
		}
		if (x < 0 || y < 0 || x > game.getW()*ResLoader.TILE_SIZE || y > game.getH()*ResLoader.TILE_SIZE) {
			game.bullets.remove(this);
		}
	}
	
	public void render(Graphics2D g, int wx, int wy) {
		g.drawImage(ResLoader.get(ResLoader.TILE_BULLET), midx()-wx, midy()-wy, null);
	}
	
	private int midx() {
		return (int)x-ResLoader.TILE_SIZE/2;
	}
	
	private int midy() {
		return (int)y-ResLoader.TILE_SIZE/2;
	}

}
