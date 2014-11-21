package com.dynamicgraph;

public class Point {
	
	private int x;
	private double y;
	
	public Point(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public Point(int x, Double y) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
	}

	public int getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}

}
