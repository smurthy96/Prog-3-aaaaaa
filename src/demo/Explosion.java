package demo;

import hw3.Renderer;
import hw3.Sprite;

public class Explosion extends Sprite{

	private int initialCount;
	private int tempcount;

	public Explosion(int x, int y, int width, int hight, Renderer renderer, int initialCount) {
		// TODO Auto-generated constructor stub
		super(x, y, width, hight, renderer);
		this.initialCount = initialCount;
	}

	public void update(){
		this.tempcount++;
	}
	public int getCount(){
		return initialCount- tempcount;
	}


	public boolean shouldDelete() {
		// TODO Auto-generated method stub
		if(tempcount == initialCount)
			return true;
		else
			return false;
	}

}
