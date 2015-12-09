package demo;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import hw3.Renderer;
import hw3.Sprite;
import sun.lwawt.PlatformComponent;

/**
 * Basic example using Platforms and Enemies.
 */
public class PlatformExample extends JPanel
{
  // Window size, fixed for simplicity
  private static final int WIDTH = 600;
  private static final int HEIGHT = 400;
  
  // Basic dimensions for objects
  private static final int SIZE = 30;
  
  /**
   * List of sprites.
   */
  private ArrayList<Platform>  platforms;
  
  /**
   * Timer for animation
   */
  private Timer timer;
  
  /**
   * Interval in milliseconds between timer callbacks.
   */
  private int interval = 40;
  
  /**
   * Constructor creates and starts the timer.
   */
  public PlatformExample()
  {
    timer = new Timer(interval, new TimerAction());
    
    // create some platforms
    platforms = new ArrayList<>();
    Renderer r = new SolidRenderer(Color.GREEN);
    Platform p0 = new Platform(100, 200, SIZE * 4, SIZE * 2, r);
    platforms.add(p0);
    Platform p2 = new Platform(200, 300, SIZE * 5, SIZE, r);
    platforms.add(p2);
    p2.setBounds(200, 500);
    p2.setDirection(2, 0);  // moving platform

    
    // add some enemies
    r = new SolidRenderer(Color.RED);
    int x = p0.getX();
    int y = p0.getY() - SIZE;
    Enemy e = new Enemy(x, y, SIZE, SIZE, r);
    e.setDirection(2.0, 0.0);
    p0.addChild(e);
    
    x = p2.getX();
    y = p2.getY() - SIZE;
    e = new Enemy(x, y, SIZE, SIZE, r);
    e.setDirection(2, 0);
    p2.addChild(e);
   
    // a second enemy on p2
    r = new SolidRenderer(Color.YELLOW);
    e = new Enemy(x + p2.getWidth() / 2, y, SIZE, SIZE, r);
    p2.addChild(e);
    
    // start the timer
    timer.start();
  }
  
  /**
   * Overridden paintComponent method is called by the Swing
   * framework when this component needs to be redrawn. NEVER
   * call this method yourself.
   */
  @Override    
  public void paintComponent(Graphics g)
  {    
    Color savedColor = g.getColor();
    
    // draw background
    ((Graphics2D) g).setBackground(Color.BLACK);
    g.clearRect(0, 0, getWidth(), getHeight());

    // draw sprites
    for (Sprite s : platforms)
    {
      // this will also draw the enemies for each platform
      s.draw(g);
    }
    
    // restore
    g.setColor(savedColor);
  }


  
  /**
   * Boilerplate for starting up any Swing application.
   */
  public static void main(String[] args)
  {
    Runnable r = new Runnable()
    {
      public void run()
      {
        createAndShow();
      }
    };
    SwingUtilities.invokeLater(r);
  }
  
  /**
   * Static helper method creates the frame and
   * makes it visible.
   */
  private static void createAndShow()
  {
    JFrame frame = new JFrame();
    PlatformExample test = new PlatformExample();   
    frame.getContentPane().add(test); //, BorderLayout.CENTER);    
    test.setFocusable(true);
    frame.setSize(WIDTH, HEIGHT);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }
  
  /**
   * Action listener for timer callbacks. All we do here is update
   * instance variables and call repaint() to trigger a repaint
   * event to be scheduled.  NEVER do any drawing in this callback.
   */
  class TimerAction implements ActionListener
  {
    public void actionPerformed(ActionEvent event)
    {
      // update state, then call repaint()
      
      for (Sprite s : platforms)
      {
        // will also update enemies on the platforms
        s.update();
      }

      repaint();
    }

  }

  
}
