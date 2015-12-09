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

/**
 * Basic example of a Projectile that can be moved and can "jump"
 * using key controls.  Jumping just means that we give the sprite
 * a negative y-velocity and positive gravity so it will eventually
 * come down.  (Remember that in this world, the positive y-direction
 * is "down".)
 * 
 * Use left/right arrow to move, 'a' key to jump.
 */
public class JumpOnEnemyExample extends JPanel
{
  // Window size, fixed for simplicity
  private static final int WIDTH = 600;
  private static final int HEIGHT = 400;
  
  // Dimensions for objects
  private static final int SIZE = 30;
  
  private static final double PLAYER_JUMP_VELOCITY = -12;
  private static final double GRAVITY = 0.8;
  private static final double PLAYER_SPEED = 2;
   
  /**
   * The projectile.
   */
  private Projectile player;
  
  /**
   * A platform for player to land on.
   */
  private Platform platform;
  
  /**
   * A guy for us to jump on.
   */
  private Enemy enemy;
  
  /**
   * Possible explosion, if we hit the enemy.
   */
  private Explosion explosion;
  
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
  public JumpOnEnemyExample()
  {
    KeyListener listener = new MyKeyListener();
    this.addKeyListener(listener);
    timer = new Timer(interval, new TimerAction());   

    Renderer r = new SolidRenderer(Color.GREEN);
    platform = new Platform(100, 300, 400, 20, r);
    
    r = new SolidRenderer(Color.RED);
    Enemy e = new Enemy(200, 300 - SIZE, SIZE, SIZE, r);
    e.setBounds(200, 300);
    e.setDirection(1, 0);
    platform.addChild(e);
    
    r = new SolidRenderer(Color.YELLOW);
    player = new Projectile(100, 300 - SIZE, SIZE, SIZE + SIZE/2, r);
    
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
    
    // paint background
    ((Graphics2D) g).setBackground(Color.BLACK);
    g.clearRect(0, 0, getWidth(), getHeight());

    // draw everybody
    for (Sprite s : getAllSprites())
    {
      s.draw(g);
    }
    
    // restore
    g.setColor(savedColor);
  }

  private ArrayList<Sprite> getAllSprites()
  {
    ArrayList<Sprite> arr = new ArrayList<>();
    arr.add(player);
    arr.add(platform);
    if (explosion != null) arr.add(explosion);
    return arr;
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
    JumpOnEnemyExample test = new JumpOnEnemyExample();   
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
      // update state and then call repaint()
      
      for (Sprite s : getAllSprites())
      {
        s.update();
      }
      
      if (player.collides(platform))
      {
        player.setBallistic(false);
        player.setPosition(player.getXExact(), platform.getYExact() - player.getHeight());
        player.setDirection(player.getDx(), 0);      
      }
      
      for (Sprite e : platform.getChildren())
      {
        if (player.collides(e))
        {
          e.markForDeletion();
          Renderer r = new ExplosionRenderer(40, Color.ORANGE, Color.BLACK);
          explosion = new Explosion(e.getX(), e.getY(), SIZE, SIZE, r, 40);
        }
      }
      
      platform.deleteMarkedChildren();
      if (explosion != null && explosion.shouldDelete()) explosion = null;
      
      repaint();
    }
  }
  
  private class MyKeyListener implements KeyListener
  {
    @Override
    public void keyPressed(KeyEvent event)
    {
      int ch = event.getKeyCode();
      if (ch == KeyEvent.VK_LEFT)
      {
        System.out.println("left");
        player.setDirection(-PLAYER_SPEED, player.getDy());
       }
      else if (ch == KeyEvent.VK_RIGHT)
      {
        System.out.println("right");
        player.setDirection(PLAYER_SPEED, player.getDy());
      }
      else if (ch == KeyEvent.VK_A)
      {
        System.out.println(ch);
        
        // jump
        if (!player.isBallistic())
        {
          player.setDirection(player.getDx(), PLAYER_JUMP_VELOCITY);
          player.setGravity(GRAVITY);
          player.setBallistic(true);
        }
      }

    }

    @Override
    public void keyReleased(KeyEvent event)
    {
      int ch = event.getKeyCode();
      if (ch == KeyEvent.VK_LEFT)
      {
        System.out.println("left released");
        player.setDirection(0, player.getDy());
      }
      else if (ch == KeyEvent.VK_RIGHT)
      {
        System.out.println("right released");
        player.setDirection(0, player.getDy());       
      }
    }

    @Override
    public void keyTyped(KeyEvent event)
    {
      //System.out.println("typed: " + event.getKeyChar());
    }
    
  }
  

}
