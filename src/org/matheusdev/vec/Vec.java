package org.matheusdev.vec;

public class Vec {
	
	public float x;
	public float y;
	
	public Vec(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vec(float x1, float y1, float x2, float y2) {
		this.x = x2-x1;
		this.y = y2-y1;
	}
	
	public float length() {
		return (float)Math.sqrt(x*x+y*y);
	}
	
	public void normalize() {
		div(length());
	}
	
	public void div(float f) {
		x /= f;
		y /= f;
	}
	
	public void mul(float f) {
		x *= f;
		y *= f;
	}
	
	public void add(float f) {
		x += f;
		y += f;
	}
	
	public void sub(float f) {
		x -= f;
		y -= f;
	}
	
	public String toString() {
		return String.format("[Vec: [%G, %G]", x, y);
	}
	
	public static void main(String[] args) {
		Vec vec = new Vec(10000f, 1f);
		vec.normalize();
		System.out.println(vec);
	}

}
