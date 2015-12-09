package demo;

import hw3.Renderer;
import hw3.Sprite;

public class Enemy extends Sprite{

	private double x = 0;
	private double y = 0;
	private double givenDx = 0;
	private double givenDy = 0;
	public Enemy(double x, double y, int width, int height, Renderer givenRenderer) {
		super(x, y, width, height, givenRenderer);
		
		this.x = x;
		this.y = y;
	}
	public void setDirection(double givenDx, double givenDy){
		this.givenDx = givenDx;
		this.givenDy = givenDy;
		this.x += givenDx;
		this.y += givenDy;

	}
	public double getDx() {
		// TODO Auto-generated method stub
		return this.x;
	}

	public double getDy() {
		// TODO Auto-generated method stub
		return this.y;
	}
	public void setBounds(double left, double right){
		if(this.getX() > right || this.getX() < left)
			this.setPosition(left, right);
	}
	public void setParent(Platform parent){
		parent.addChild(this);
	}
	public void update(){
		this.x += this.givenDx;
		this.y += this.givenDy;
	}
}
