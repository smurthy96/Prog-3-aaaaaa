package demo;

import java.awt.Graphics;
import java.util.ArrayList;

import hw3.Renderer;
import hw3.Sprite;

public class Platform extends Sprite{



	private double givenDx = 0;
	private double givenDy = 0;
	private double left = 0;
	private double right = 0;
	Renderer r;
	ArrayList<Enemy> enemy = new ArrayList<Enemy>();
	
	public Platform (double x, double y, int width, int height, Renderer r) {
		// TODO Auto-generated constructor stub
		super(x,y,width,height,r);
		this.r = r;
		this.right = Double.POSITIVE_INFINITY;
		this.left = Double.NEGATIVE_INFINITY;
		
		
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
		this.right =right;
		
	}
	public void addChild(Enemy e){
		this.enemy.add(e);
		
	}
	public ArrayList<Enemy> getChildren(){
		return this.enemy;
	}
	public void deleteMarkedChildren()
	{
		for(int i = 0;i<enemy.size();i++){
			if(enemy.get(i).shouldDelete()){
				enemy.remove(i);
			}
		}
	}
	public void update(){

		super.update();
		double newX=super.getXExact()+givenDx;
		double newY=super.getYExact()+givenDy;
		super.setPosition(newX, newY);
		this.setDirection(givenDx, givenDy);
		

		for(Enemy e: enemy){
			e.update();
		}
		if (newX+this.getWidth()>=right)
		{
			newX=right-this.getWidth();
			reverseDirection();
		}
		else if (newX<=left)
		{
			newX=left;
			reverseDirection();
		}
		this.setPosition(newX, newY);
		
	}
	public void reverseDirection()
	{
		setDirection(-this.getDx(), this.getDy());
	}
	
	
	public void draw(Graphics g){
		r.render(g, this);
		for(int i = 0;i<enemy.size();i++)
		{
			enemy.get(i).draw(g);

		}
	}
	
	
	
}