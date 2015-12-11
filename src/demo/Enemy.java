package demo;

import hw3.Renderer;
import hw3.Sprite;

public class Enemy extends Sprite{

	private double givenDx = 0;
	private double givenDy = 0;
	private double left = 0;
	private double right= 0;

	public Enemy(double x, double y, int width, int height, Renderer givenRenderer) {
		super(x, y, width, height, givenRenderer);
		left = Double.POSITIVE_INFINITY;
		right = Double.NEGATIVE_INFINITY;
 
	}
	public void setDirection(double givenDx, double givenDy){
		this.givenDx = givenDx;
		this.givenDy = givenDy;


	}
	public double getDx() {
		// TODO Auto-generated method stub
		return this.givenDx;
	}

	public double getDy() {
		// TODO Auto-generated method stub
		return this.givenDy;
	}
	public void setBounds(double left, double right){
		this.left = left;
		this.right = right;
	}
	public void setParent(Platform parent){
		parent.addChild(this);
		setBounds(parent.getXExact()+parent.getWidth(),parent.getYExact()+parent.getHeight());
		this.givenDx = parent.getDx();
		this.givenDy = parent.getDy();
	}
	public void update(){
		super.update();
		double newX=super.getXExact()+givenDx;
		double newY=super.getYExact()+givenDy;
		super.setPosition(newX, newY);
		this.setDirection(givenDx, givenDy);
	}
}
