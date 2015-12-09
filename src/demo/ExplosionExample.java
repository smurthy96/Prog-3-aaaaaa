package demo;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import hw3.Renderer;
import hw3.Sprite;

/**
 * Example of using Explosion objects.
 */
public class ExplosionExample extends JPanel
{
  // Window size, fixed for simplicity
  private static final int WIDTH = 600;
  private static final int HEIGHT = 400;
  
  // Basic dimensions for objects
  private static final int SIZE = 30;
  
  // Lifetime of explosion object
  private static final int EXPLOSION_COUNT = 40;

  /**
   * List of sprites to draw.
   */
  private ArrayList<Sprite> explosions;
  
  /**
   * Renderer to use for all sprites.
   */
  Renderer renderer;
  
  /**
   * Timer for animation
   */
  private Timer timer;
  
  /**
   * Interval in milliseconds between timer callbacks.
   */
  private int interval = 40;

  /**
   * Random for generating positions.
   */
  private Random rand;
  
  /**
   * Constructor creates and starts the timer.
   */
  public ExplosionExample()
  {
    timer = new Timer(interval, new TimerAction());
    rand = new Random();
    explosions = new ArrayList<>();
    renderer = new ExplosionRenderer(EXPLOSION_COUNT, Color.ORANGE, Color.BLACK);
    
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
    for (Sprite s : explosions)
    {
      s.draw(g);
    }
    
    // restore color
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
    ExplosionExample test = new ExplosionExample();   
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
      
      // make a random explosion and add it to the list
      int x = rand.nextInt(WIDTH);
      int y = rand.nextInt(HEIGHT);
      Explosion e = new Explosion(x, y, SIZE, SIZE, renderer, EXPLOSION_COUNT);
      explosions.add(e);
      
      for (Sprite s : explosions)
      {
        // will also update enemies on the platforms
        s.update();
      }

      // remove all marked sprites
      ArrayList<Sprite> temp = new ArrayList<>();
      for (Sprite s : explosions)
      {
        if (!s.shouldDelete())
        {
          temp.add(s);
        }
      }
      explosions = temp;
      
      repaint();
    }
  }

  
}
