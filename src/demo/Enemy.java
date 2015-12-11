package demo;

import hw3.Renderer;
import hw3.Sprite;

public class Enemy extends Sprite{

	private double givenDx = 0;
	private double givenDy = 0;
	private double left = 0;
	private double right= 0;
	private Platform parent;

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
		setBounds(parent.getXExact(),parent.getXExact()+parent.getWidth());
		this.givenDx = parent.getDx();
		this.givenDy = parent.getDy();
		
	}
	public void update()
	{
		super.update();
		double nextX, nextY;
		nextY=this.getYExact()+this.getDy();
		if (parent != null)
			nextX=this.getXExact()+parent.getDx()+this.getDx();
		else
			nextX=this.getXExact()+this.getDx();
		//Handle case when x position goes out of bounds
		if (nextX+this.getWidth()>=right)
		{
			nextX=this.right-this.getWidth();
			this.setDirection(-this.getDx(), this.getDy());
		}
		else if (nextX<=left)
		{
			nextX=right;
			this.setDirection(-this.getDx(), this.getDy());
		}
		this.setPosition(nextX, nextY);
		
	}
}
