package org.matheusdev;

import java.awt.AWTException;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.matheusdev.input.Input;
import org.matheusdev.res.ResLoader;
import org.matheusdev.screens.Screen;
import org.matheusdev.screens.ScreenTitle;

public class GameCanvas extends Canvas implements Runnable {
	private static final long serialVersionUID = 8408780829498109905L;
	
	public static final String screendir = "screens/";
	
	private GameFrame frame;
	private Thread animator;
	private volatile boolean running = false;
	private BufferStrategy bs;
	private Graphics2D bg;
	private Screen screen;
	public long fps = 60;
	private Input in;
	private boolean pressedF2 = false;
	
	public GameCanvas(GameFrame frame) {
		this.frame = frame;
		Dimension size = new Dimension(800, 600);
		setSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setPreferredSize(size);
		setBackground(Color.BLACK);
		ResLoader.loadImgs();
		in = new Input(this);
		screen = new ScreenTitle(this, in);
	}
	
	public synchronized void start() {
		if (!running && animator == null) {
			animator = new Thread(this);
			animator.start();
		}
	}
	
	public synchronized void stop() {
		running = false;
	}
	
	public void run() {
		running = true;
		
		long before = System.currentTimeMillis();
		long takenTime = 0;
		
		long lastFps = System.currentTimeMillis();
		long time = System.currentTimeMillis();
		long frames = 0;
		
		while(running) {
			requestFocusInWindow();
			before = System.currentTimeMillis();
			tick();
			render();
			takenTime = System.currentTimeMillis()-before;
			
			if (16-takenTime > 0) {
				try {
					Thread.sleep(16-takenTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			frames++;
			time = System.currentTimeMillis();
			if (time-lastFps > 999) {
				lastFps = time;
				fps = frames;
				frames = 0;
			}
		}
		frame.dispose();
	}
	
	private void tick() {
		screen.tick();
	}
	
	private void render() {
		bs = getBufferStrategy();
		if (bs == null) { 
			createBufferStrategy(3);
			return;
		}
		bg = (Graphics2D) bs.getDrawGraphics();
		
		bg.clearRect(0, 0, getWidth(), getHeight());
		
		screen.render(bg);
		
		bs.show();
		
		if (in.keys[Input.F2] && !pressedF2) {
			screenshot();
			pressedF2 = true;
		} if (!in.keys[Input.F2] && pressedF2) {
			pressedF2 = false;
		}
	}
	
	public void screenshot() {
		File screendirectory = new File(screendir);
		if (!screendirectory.exists()) {
			screendirectory.mkdirs();
		}
		String screenname = screendir + "screenshot";
		String format = ".png";
		int i = 0;
		
		File file = null;
		
		do {
			file = new File(screenname + i + format);
			i++;
		} while(file.exists());
		try {
			file.createNewFile();
		} catch(IOException e) {
			e.printStackTrace();
		}
		saveScreenshot(file);
	}
	
	public void saveScreenshot(File file) {
		Rectangle window = new Rectangle(frame.getX(), frame.getY(), frame.getWidth(), frame.getHeight());
		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		BufferedImage img = robot.createScreenCapture(window);
		try {
			ImageIO.write(img, "PNG", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setScreen(Screen s) {
		screen = s;
	}
	
}
