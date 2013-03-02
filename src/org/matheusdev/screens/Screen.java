package org.matheusdev.screens;

import java.awt.Graphics2D;

public abstract class Screen {
	
	public abstract void tick();
	public abstract void render(Graphics2D g);

}
