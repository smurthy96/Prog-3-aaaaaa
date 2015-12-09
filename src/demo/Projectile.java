package demo;

import hw3.Renderer;
import hw3.Sprite;

public class Projectile extends Sprite{
	

	private double x = 0;
	private double y = 0;
	private boolean ballistic;
	private double givenDx = 0;
	private double givenDy = 0;
	private int gravity;
	public Projectile(int x, int y, int width, int hight, Renderer r) {

		super(x, y,width,hight,r);
		
		this.x = x;
		this.y = y;
	}

	public void setDirection(double givenDx, double givenDy){
		
		this.givenDx = givenDx;
		this.givenDy = givenDy;
		if(!(ballistic))
			this.x = givenDx;
		this.y = givenDy;
		//System.out.println(givenDx+"<_____________>"+givenDy);
		
	}
	public double getDx() {
		// TODO Auto-generated method stub
		return this.x+givenDx;
	}

	public double getDy() {
		// TODO Auto-generated method stub
		return this.y+givenDy;
		
	}

	public void setGravity(double gravity) {
		// TODO Auto-generated method stub
		this.gravity += gravity;
		
	}

	public void setBallistic(boolean ballistic) {
		// TODO Auto-generated method stub
		this.ballistic = ballistic;
		
	}
	public boolean isBallistic(){
		return this.ballistic;
	}
	public void update(){
		if(!(ballistic))
			this.x += this.givenDx;
		this.y += this.givenDy;
		this.y += this.gravity;

	}
	





}
