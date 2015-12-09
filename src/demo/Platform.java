package demo;

import java.awt.Graphics;
import java.util.ArrayList;

import hw3.Renderer;
import hw3.Sprite;

public class Platform extends Sprite{


	private double x = 0;
	private double y = 0;
	private double givenDx = 0;
	private double givenDy = 0;
	ArrayList<Enemy> enemy = new ArrayList<Enemy>();
	
	public Platform (double x, double y, int width, int height, Renderer r) {
		// TODO Auto-generated constructor stub
		super(x,y,width,height,r);
		
		this.x = x;
		this.y = y;

	}
	
	public void setDirection(double givenDx, double givenDy){
		this.givenDx = givenDx;
		this.givenDy = givenDy;


	}
	public double getDx() {
		// TODO Auto-generated method stub
		return this.x+givenDx;
	}

	public double getDy() {
		// TODO Auto-generated method stub
		return this.y+givenDy;
	}
	public void setBounds(double left, double right){
		for(int i =0;i<enemy.size();i++){
			if(enemy.get(i).getX() > right || enemy.get(i).getX() < left)
				enemy.get(i).setPosition(left, right);
			}
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
		this.x += this.givenDx;
		this.y += this.givenDy;
		for(Enemy e: enemy){
			e.update();
		}
	}
	
	public void draw(Graphics g){
		for(int i = 0;i<enemy.size();i++)
		{
			enemy.get(i).draw(g);
		}
	}
	
	
}