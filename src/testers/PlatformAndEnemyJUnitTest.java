package testers;
import org.junit.Before;
import org.junit.Test;

import demo.Enemy;
import demo.Platform;

import static org.junit.Assert.assertEquals;
import java.awt.Rectangle;
public class PlatformAndEnemyJUnitTest 
{

	private Platform p = new Platform(100.0, 120.0, 50, 10, null);

	private Enemy e = new Enemy(100.0, 110.0, 10, 10, null);

	private Rectangle rightP = new Rectangle(100, 120, 50, 10);

	private Rectangle rightE = new Rectangle(100, 110, 10, 10); 

	private static final double EPSILON = 10e-07;

	@Before
	public void setup()
	{
		p.addChild(e);
		for(int i=0; i<10; i+=1)
		{
			p.update();
		}
		p.setDirection(2.0, 4.0);
		p.setBounds(70.0, 130.0);
		e.setDirection(1.0, 3.0);
	}

	@Test
	public void testGet()
	{
		//Platform
		assertEquals(10, p.getTicks());
		assertEquals(100, p.getX());
		assertEquals(100.0, p.getXExact(), EPSILON);
		assertEquals(120, p.getY());
		assertEquals(120.0, p.getYExact(), EPSILON);
		assertEquals(50, p.getWidth());
		assertEquals(10, p.getHeight());
		assertEquals(rightP, p.getRect());
		assertEquals(2.0, p.getDx(), EPSILON);
		assertEquals(4.0, p.getDy(), EPSILON);

		//Enemy
		assertEquals(10, e.getTicks());
		assertEquals(100, e.getX());
		assertEquals(100.0, e.getXExact(), EPSILON);
		assertEquals(110, e.getY());
		assertEquals(110.0, e.getYExact(), EPSILON);
		assertEquals(10, e.getWidth());
		assertEquals(10, e.getHeight());
		assertEquals(rightE, e.getRect());
		assertEquals(1.0, e.getDx(), EPSILON);
		assertEquals(3.0, e.getDy(), EPSILON);
	}

	@Test
	public void testDirection()
	{	
		//reset
		p.setDirection(0.0, 0.0);
		e.setDirection(0.0, 0.0);
		p.setBounds(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
		p.setPosition(100.0, 120.0);
		e.setPosition(100.0, 110.0);
		p.update();

		p.setDirection(2.0, 0.0);
		for(int i=0; i<5; i+=1)
		{
			p.update();
		}
		assertEquals(110.0, p.getXExact(), EPSILON);
		assertEquals(120.0, p.getYExact(), EPSILON);

		//reset
		p.setDirection(0.0, 0.0);
		e.setDirection(0.0, 0.0);
		p.setBounds(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
		p.setPosition(100.0, 120.0);
		e.setPosition(100.0, 110.0);
		p.update();
		
		p.setBounds(90.0, 160.0);
		p.setDirection(2.0, 0.0);
		for(int i=0; i<7; i+=1)
		{
			p.update();
		}
		assertEquals(106.0, p.getXExact(), EPSILON);
		assertEquals(106.0, e.getXExact(), EPSILON);
		
		//reset
		p.setDirection(0.0, 0.0);
		e.setDirection(0.0, 0.0);
		p.setBounds(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
		p.setPosition(100.0, 120.0);
		e.setPosition(100.0, 110.0);
		p.update();		
		
		p.setBounds(50.0, 200.0);
		e.setPosition(105.0, 110.0);
		e.setDirection(3.0, 0.0);
		for(int i=0; i<10; i+=1)
		{
			p.update();
		}
		assertEquals(135.0, e.getXExact(), EPSILON);
		
		//reset
		p.setDirection(0.0, 0.0);
		e.setDirection(0.0, 0.0);
		p.setBounds(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
		p.setPosition(100.0, 120.0);
		e.setPosition(100.0, 110.0);
		p.update();
		
		p.setBounds(100.0, 200.0);
		p.setDirection(2.0, 0.0);
		for(int i=0; i<5; i+=1)
		{
			p.update();
		}
		assertEquals(110.0, p.getXExact(), EPSILON);
		
		//reset
		p.setDirection(0.0, 0.0);
		e.setDirection(0.0, 0.0);
		p.setBounds(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
		p.setPosition(100.0, 120.0);
		e.setPosition(100.0, 110.0);
		p.update();
		
		p.setBounds(50.0, 200.0);
		e.setPosition(140.0, 110.0);		
		p.setDirection(-3.0, 0.0);
		e.setDirection(-2.0, 0.0);	
		for(int i=0; i<14; i+=1)
		{
			p.update();
		}
		assertEquals(58.0, p.getXExact(), EPSILON);
		assertEquals(70.0, e.getXExact(), EPSILON);
		
		//reset
		p.setDirection(0.0, 0.0);
		e.setDirection(0.0, 0.0);
		p.setBounds(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
		p.setPosition(100.0, 120.0);
		e.setPosition(100.0, 110.0);
		p.update();
		
		p.setBounds(50.0, 200.0);
		p.setDirection(5.0, 0);
		for(int i=0; i<33; i+=1)
		{
			p.update();
		}
		assertEquals(65.0, p.getXExact(), EPSILON);
		assertEquals(65.0, e.getXExact(), EPSILON);
		
		//reset
		p.setDirection(0.0, 0.0);
		e.setDirection(0.0, 0.0);
		p.setBounds(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
		p.setPosition(100.0, 120.0);
		e.setPosition(100.0, 110.0);
		p.update();
	}


	@Test
	public void testChildren()
	{
		Enemy e2 = new Enemy(120.0, 110.0, 10, 10, null);
		Enemy e3 = new Enemy(140.0, 110.0, 10, 10, null);
		p.addChild(e2);
		p.addChild(e3);
		
		//reset
		p.setDirection(0.0, 0.0);
		e.setDirection(0.0, 0.0);
		p.setBounds(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
		p.setPosition(100.0, 120.0);
		e.setPosition(100.0, 110.0);
		e2.setPosition(120.0, 110.0);
		e3.setPosition(140.0, 110.0);
		p.update();
		
		p.setDirection(10.0, 0.0);
		p.update();
		p.update();
		assertEquals(120.0, e.getXExact(), EPSILON);
		assertEquals(140.0, e2.getXExact(), EPSILON);
		assertEquals(160.0, e3.getXExact(), EPSILON);
		
		//reset
		p.setDirection(0.0, 0.0);
		e.setDirection(0.0, 0.0);
		p.setBounds(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
		p.setPosition(100.0, 120.0);
		e.setPosition(100.0, 110.0);
		e2.setPosition(120.0, 110.0);
		e3.setPosition(134.0, 110.0);
		p.update();
		
		p.setDirection(10.0, 0.0);
		e.setDirection(1.0, 0.0);
		e2.setDirection(2.0, 0.0);
		e3.setDirection(3.0, 0.0);
		p.update();
		p.update();
		assertEquals(122.0, e.getXExact(), EPSILON);
		assertEquals(144.0, e2.getXExact(), EPSILON);
		assertEquals(160.0, e3.getXExact(), EPSILON);	
	}
}