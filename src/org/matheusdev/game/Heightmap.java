package org.matheusdev.game;

import java.util.Random;

public class Heightmap {
	
	public float[][] heightmap;
	private Random rand;
	private float varyation;
	
	public Heightmap(int size, float seedmid, float varyation, Random rand) {
		this.rand = rand;
		this.varyation = varyation;
		heightmap = new float[size][size];
		int firststep = (size)/2;
		heightmap[0][0] = 0f;
		heightmap[size-1][0] = 0f;
		heightmap[0][size-1] = 0f;
		heightmap[size-1][size-1] = 0f;
		heightmap[firststep][firststep] = seedmid;
		noise(0, 0, firststep);
		noise(firststep, 0, firststep);
		noise(0, firststep, firststep);
		noise(firststep, firststep, firststep);
	}
	
	private void noise(int x, int y, int step) {
		if (step > 1) {
			int halfStep = step/2;
			float vary = step*varyation;
			
			heightmap[x+halfStep][y] = vary(mid(heightmap[x][y], heightmap[x+step][y]), vary);
			heightmap[x][y+halfStep] = vary(mid(heightmap[x][y], heightmap[x][y+step]), vary);
			
			heightmap[x+step][y+halfStep] = vary(mid(heightmap[x+step][y], heightmap[x+step][y+step]), vary);
			heightmap[x+halfStep][y+step] = vary(mid(heightmap[x][y+step], heightmap[x+step][y+step]), vary);
			
			heightmap[x+halfStep][y+halfStep] = vary(mid(
					heightmap[x][y],
					heightmap[x+halfStep][y],
					heightmap[x+step][y],
					heightmap[x][y+halfStep],
					heightmap[x+step][y+halfStep],
					heightmap[x][y+step],
					heightmap[x+halfStep][y+step],
					heightmap[x+step][y+step]), vary);
			
			noise(x, y, halfStep);
			noise(x+halfStep, y, halfStep);
			noise(x, y+halfStep, halfStep);
			noise(x+halfStep, y+halfStep, halfStep);
		}
	}
	
	public float randRange(float range, Random rand) {
		return rand.nextBoolean() ? -rand.nextFloat()*range : rand.nextFloat()*range;
	}
	
	public float vary(float val, float vary) {
		return val+randRange(vary, rand);
	}
	
	public float mid(float... vals) {
		float sum = 0;
		for (int i = 0; i < vals.length; i++) {
			sum += vals[i];
		}
		return sum / vals.length;
	}
	
}
