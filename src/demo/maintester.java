package demo;

import java.util.ArrayList;

public class maintester {

	public static void main(String[] args){
		
/////////////////////CHild Tester/////////////////////////		
//		Platform p = new Platform(0, 0, 0, 0,null);
//		Enemy e1 = new Enemy(0, 0, 0, 0, null);
//		p.addChild(e1);
//		Enemy e2 = new Enemy(0, 0, 0, 0, null);
//		p.addChild(e2);
//		ArrayList<Enemy> children = p.getChildren();
//		System.out.println(children.contains(e1)); // expected true
//		System.out.println(children.contains(e2)); // expected true
//		e1.markForDeletion();
//		p.deleteMarkedChildren();
//		children = p.getChildren();
//		System.out.println(children.contains(e1)); // expected false
//		System.out.println(children.contains(e2)); // expected true
//___________________________________________________________________	


/////////////////////Grav Tester/////////////////////////
//		Projectile p = new Projectile(0, 0, 0, 0, null);
//		p.setDirection(0, 0);
//		p.update();
//		p.update();
//		System.out.print(p.getY()+",");
//		System.out.print(p.getDy());
//		p.setGravity(5);
//		p.update();
//		p.update();
//		System.out.println();
//		System.out.print(p.getY()+",");
//		System.out.print(p.getDy());
//_______________________________________________________
		
////////////////////Mark for deletion tester////////////
//		Explosion e = new Explosion(0, 0, 0, 0, null, 3);
//		System.out.println(e.getCount());//expected 3
//		System.out.println(e.shouldDelete());// expected false
//		e.update();
//		System.out.println(e.getCount());  // expected 2
//		e.update();
//		e.update();
//		System.out.println(e.getCount());  // expected 0
//		System.out.println(e.shouldDelete()); // expected true
////////////////////////////////////////////////////////
		
//////////////////////Ballistic//////////////////////////
//		Projectile p = new Projectile(0, 0, 0, 0, null);
//		System.out.println(p.isBallistic()); // expected false
//		p.setDirection(2, 3);
//		System.out.println(p.getDx()); // expected 2
//		p.setBallistic(true);
//		System.out.println(p.isBallistic()); // expected true
//		p.setDirection(5, 10);
//		System.out.println(p.getDx()); // expected 2
//		p.setBallistic(false);
//		System.out.println(p.isBallistic()); // expected false
//		p.setDirection(5, 10);
//		System.out.println(p.getDx()); // expected 5
//////////////////////////////////////////////////////////
		
		
//////////////////////Enemy vs Plat//////////////////////////
//	    // left side at x = 0, right side at 50
//	    Platform p = new Platform(0, 100, 50, 10, null);
//	    
//	    // 10 x 10 enemy at x = 25
//	    Enemy e = new Enemy(25, 90, 10, 10, null);
//	    p.addChild(e);
//	    e.setDirection(-30, 0);
//	    
//	    // 1) update on platform should update enemy object too
//	    // 2) updating enemy should set enemy's bounds to be left and right side of platform
//	    // 3) hitting bound should limit enemy's position and reverse dx
//	    p.update();
//	    
//	    System.out.println(e.getX());  // should be 0
//	    System.out.println(e.getDx()); // should be +30    
//	    p.update();
//	    p.update();
//	    System.out.println(e.getX());  // should be 40
//	    System.out.println(e.getDx()); // should be -30
///////////////////////////////////////////////////////////////////
	
////////////////////////////getcount//////////////////////////////
		
//		Explosion e = new Explosion(0, 0, 0, 0, null, 3);
//		System.out.println(e.getCount());  // expected 3
//		System.out.println(e.shouldDelete()); // expected false
//		e.update();
//		System.out.println(e.getCount());  // expected 2
//		e.update();
//		e.update();
//		System.out.println(e.getCount());  // expected 0
//		System.out.println(e.shouldDelete()); // expected true
//////////////////////////////////////////////////////////////////
		
		
		Platform e = new Platform(100, 0, 10, 10, null);
		e.setBounds(90, 120);
		e.setDirection(15, 0);
		e.update();
		System.out.println(e.getX()); // should be 110, right side of object can't go past boundary
		System.out.println(e.getDx()); // should be -15
		e.update();
		e.update();
		System.out.println(e.getX()); // should be 90, left side of object can't go past boundary
		System.out.println(e.getDx()); // should be 15

		
	}
}
