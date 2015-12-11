package demo;

import java.awt.Graphics;

import hw3.Renderer;
import hw3.Sprite;

public class Projectile extends Sprite{
	
	private double x = 0;
	private double y = 0;
	private boolean ballistic;
	private double givenDx = 0;
	private double givenDy = 0;
	private double gravity;
	private Renderer r;
	public Projectile(int x, int y, int width, int hight, Renderer r) {

		super(x, y,width,hight,r);

	}

	public void setDirection(double givenDx, double givenDy){
		

		if(!(ballistic))
			this.givenDx = givenDx;
		this.givenDy = givenDy;
		

		
	}
	public double getDx() {
		// TODO Auto-generated method stub
		return this.x;
	}

	public double getDy() {
		// TODO Auto-generated method stub
		return this.y;
		
	}

	public void setGravity(double gravity) {
		// TODO Auto-generated method stub
		this.gravity = gravity;
		
	}

	public void setBallistic(boolean ballistic) {
		// TODO Auto-generated method stub
		this.ballistic = ballistic;
		
	}
	public boolean isBallistic(){
		return this.ballistic;
	}
	public void update()
	{
	    super.update();
	    double newX=super.getXExact()+givenDx;
	    double newY=super.getYExact()+givenDy;
	    super.setPosition(givenDx, givenDy+gravity);
	    super.setPosition(newX, newY);
	}

	





}
