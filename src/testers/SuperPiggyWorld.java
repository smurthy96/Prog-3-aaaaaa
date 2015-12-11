package testers;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
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

import demo.Enemy;
import demo.Explosion;
import demo.ExplosionRenderer;
import demo.Platform;
import demo.Projectile;
import demo.SolidRenderer;
import hw3.Renderer;
import hw3.Sprite;

/**
 * A simple platform-style game based on the hw3 sprite components.
 * Use left/right arrows to move, 'a' to jump, and 's' to start over.
 * 
 * Acknowledgment: this application was inspired by Paul Craven of Simpson
 * College, author of "Program Arcade Games with Python and Pygame", see
 * http://programarcadegames.com/
 */
public class SuperPiggyWorld extends JPanel
{
  // Window size, fixed for simplicity
  private static final int WIDTH = 800;
  private static final int HEIGHT = 600;

  // Basic dimensions to use for objects
  private static final int SIZE = 30;
  private static final int PLAYER_SIZE = 48;

  // Misc constants
  private static final double PLAYER_JUMP_VELOCITY = -12;
  private static final double GRAVITY = 0.8;
  private static final double PLAYER_SPEED = 2;
  private static final int EXPLOSION_LIFETIME = 40;
  private static final int ENEMY_COUNT = 1;
  private static final int ENEMY_HIT_SCORE = 1000;
  
  /**
   * Projectile for the player
   */
  private Projectile player;
  
  /**
   * List of platforms
   */
  private ArrayList<Platform>  platforms;
  
  /**
   * List of explosions
   */
  private ArrayList<Sprite> explosions;
  
  /**
   * Track the current platform, so we don't generate new enemies on it.
   */
  private Platform currentPlatform;

  /**
   * Count of number of frames right arrow key has been held down, to potentially speed up
   * player.
   */
  private int rightArrow;
  
  /**
   * Count of number of frames left arrow key has been held down, to potentially speed up
   * player.
   */
  private int leftArrow;
  
  /**
   * Record player's direction, to draw the image facing right or left.
   */
  private boolean playerFlipped;
  
  /**
   * Stop timer when game is over.
   */
  private boolean over;
  
  /**
   * Generator for random positions.
   */
  private Random rand;

  /**
   * Timer for animation
   */
  private Timer timer;

  /**
   * Interval in milliseconds between timer callbacks.
   */
  private int interval = 40;

  /**
   * Create this once, since we need it a lot.
   */
  private Renderer enemyRenderer;

  /**
   * Current score for the game.
   */
  private int score;
  
  /**
   * Constructor creates everything...
   */
  public SuperPiggyWorld()
  {
    rand = new Random();

    KeyListener listener = new MyKeyListener();
    this.addKeyListener(listener);
    timer = new Timer(interval, new TimerAction()); 
    
    // create all the sprites
    setupLevel();
    
    // go!
    timer.start();
  }

  private void setupLevel()
  {
    over = false;

    // load images
    Image playerImage = null;
    java.net.URL url = SuperPiggyWorld.class.getResource("pig_small_alpha.png");
    if (url != null)
    {
      playerImage = new ImageIcon(url).getImage();
    }

    Image enemyImage = null;
    url = SuperPiggyWorld.class.getResource("apple_small_alpha.png");
    if (url != null)
    {
      enemyImage = new ImageIcon(url).getImage();
    }
    
    // platforms
    platforms = new ArrayList<>();
    Renderer r = new SolidRenderer(Color.GREEN);
    Platform p0 = new Platform(100 - SIZE * 2, 340, SIZE * 2, SIZE * 2, r);
    platforms.add(p0);
    Platform p1 = new Platform(100, 400, SIZE * 5, SIZE, r);
    platforms.add(p1);
    Platform p2 = new Platform(200, 480, SIZE * 5, SIZE, r);
    platforms.add(p2);
    p2.setBounds(200, 500);
    p2.setDirection(2, 0);  // moving
    Platform p3 = new Platform(500, 400, SIZE * 5, SIZE, r);
    platforms.add(p3);
    Platform p4 = new Platform(200, 180, SIZE * 4, SIZE, r);
    platforms.add(p4);
    p4.setBounds(150, 350);
    p4.setDirection(2.5, 0);  // moving
    Platform p5 = new Platform(370, 280, SIZE * 4, SIZE / 2, r);
    platforms.add(p5);

    // player
    r = new PlayerImageRenderer(playerImage, Color.PINK);
    player = new Projectile(100, 100, PLAYER_SIZE, PLAYER_SIZE, r);   
    setPositionOnPlatform(p0, player);
    currentPlatform = p0;

    // platforms and enemies
    enemyRenderer = new ImageRenderer(enemyImage, Color.RED);
    setUpEnemies();

    // no explosions yet (but there will be...)
    explosions = new ArrayList<>();

  }



  /**
   * Overridden paintComponent method is called by the Swing
   * framework when this component needs to be redrawn. NEVER
   * call this method yourself.
   */
  @Override    
  public void paintComponent(Graphics g)
  {    

    Graphics2D g2 = (Graphics2D) g;
    Color savedColor = g2.getColor();

    // since this component is opaque, we have to paint the background
    g2.setBackground(Color.cyan);
    g2.clearRect(0, 0, getWidth(), getHeight());

    // draw everything
    for (Sprite s : getAllSprites())
    {
      s.draw(g);
    }
    
    // draw the score
    Font f = new Font(Font.SANS_SERIF, Font.PLAIN, 24);
    g2.setFont(f);
    g2.setColor(Color.BLACK);
    FontMetrics fm = g2.getFontMetrics(f);
    int h = fm.getHeight();   
    int textX = 10;
    int textY = 10 + h;
    g2.drawString("Score " + score, textX, textY);
    textY += h;
    
    // restore color
    g2.setColor(savedColor);

  }

  /**
   *  Set the sprite's position to a random location on the platform.
   */
  private void setPositionOnPlatform(Platform p, Sprite s)
  {
    int minX = p.getX();
    int maxX = p.getX() + p.getWidth() - s.getWidth();
    int x = rand.nextInt(maxX - minX) + minX;
    int y = p.getY() - s.getHeight();
    s.setPosition(x, y);
  }

  /**
   * Make sure there is an enemy on each platform other than the one the player is on
   */
  private void setUpEnemies()
  {
    for (Platform p : platforms)
    {
      // don't plop an enemy on top of the player
      if (p != currentPlatform)
      {
        if (p.getChildren().size() < ENEMY_COUNT)
        {
          Enemy e = new Enemy(0, 0, SIZE, SIZE, enemyRenderer);
          setPositionOnPlatform(p, e);
          p.addChild(e);
          double dx = 1 + rand.nextDouble();
          if (rand.nextInt(2) > 0) dx = -dx;
          e.setDirection(dx, 0);
        }
      }
    }
  }

  /**
   * Returns a list of all enemies.
   */
  private ArrayList<Sprite> getAllEnemies()
  {
    ArrayList<Sprite> arr = new ArrayList<>();
    for (Platform p : platforms)
    {
      arr.addAll(p.getChildren());
    }
    return arr;
  }

  /**
   * Returns a list of all sprites.
   */
  private ArrayList<Sprite> getAllSprites()
  {
    ArrayList<Sprite> arr = new ArrayList<>();
    arr.add(player);  
    arr.addAll(platforms);
    arr.addAll(explosions); // render last
    return arr;
  }

  /**
   * Determines whether the player is currently on a platform.
   */
  private boolean isOnPlatform()
  {
    double saved = player.getYExact();
    player.setPosition(player.getXExact(), saved + 2);
    for (Sprite s : platforms)
      if (player.collides(s))
      {
        player.setPosition(player.getXExact(), saved);
        return true;
      }
    return false;
  }

  /**
   * Update player's state appropriately in case of collision with a platform.
   * This could be from below, left, right, or above (landing on the platform).
   */
  private void checkCollisionWithPlatform(Platform s)
  {
    if (player.collides(s))
    {
      // First check whether we landed on it.  Undo the player's dy and
      // see whether we still collided
      boolean landed = false;
      double savedY = player.getYExact();
      player.setPosition(player.getXExact(), savedY - player.getDy());
      if (!player.collides(s) && player.isBallistic() && player.getDy() > 0)
      {
        // ok, we landed
        landed = true;
      }
      player.setPosition(player.getXExact(), savedY);

      if (landed)
      {
        //System.out.println("Landed");
        currentPlatform = s;
        player.setBallistic(false);
        player.setGravity(0);
       
        // This will add an enemy to whatever platform the player just left, if needed.
        setUpEnemies();

        // This is kind of tedious.  Since the player can't change dx when
        // ballistic, a release of the arrow key would not have been recorded
        // in player's dx.  So we have to explicitly check whether arrow key is 
        // down and set dx accordingly
        double dx = player.getDx();
        if (leftArrow == 0 && rightArrow == 0) 
        {
          player.setDirection(0, 0);
        }
        else if (leftArrow > 0)
        {
          if (dx < 0)
          {
            player.setDirection(dx, 0);
          }
          else
          {
            player.setDirection(-PLAYER_SPEED, 0);
          }
        }
        else if (rightArrow > 0)
        {
          if (dx > 0)
          {
            player.setDirection(dx, 0);
          }
          else
          {
            player.setDirection(PLAYER_SPEED, 0);
          }
        }
        player.setPosition(player.getXExact(), s.getY() - player.getRect().height);
      }
      else
      {
        // Player is not landing.  If this is a collision from below,
        // then reverse the vertical velocity, and if it's horizontal
        // then set horizontal velocity to zero.  Note that player may
        // be ballistic or not in a horizontal motion.
        
        // temporarily set ballistic false, since we have to adjust dx
        boolean jumping = player.isBallistic(); 
        player.setBallistic(false); 

        double newX = player.getXExact();
        double newY = player.getYExact();
        double dx = player.getDx();
        double dy = player.getDy();

        if (dy < 0 && player.getY() < s.getY() + s.getHeight())
        {
          // collide from below
          newY = s.getY() + s.getHeight();
          dy = -dy;
        }
        else if (dx > 0 && (player.getX() + player.getWidth()) > s.getX())
        {
          // collide from left
          newX = s.getX() - player.getWidth();
          dx = 0;
        }
        else if (dx < 0 && player.getX() < s.getX() + s.getWidth())
        {      
          // collide from right
          newX = s.getX() + s.getWidth();   
          dx = 0;
        }

        player.setPosition(newX, newY);
        player.setDirection(dx, dy);
        player.setBallistic(jumping);

      } // not landing
    } // if player collides 
  }
  
  /**
   * Check for player's collision with enemy.  If player lands on enemy from the
   * top, enemy dies; otherwise player dies.
   */
  private void checkCollidesWithEnemy(Sprite s)
  {
    if (player.collides(s))
    {
      // If we come in from top, we kill the enemy, but if we hit from side
      // then we lose.  So, if bottom of player is higher than half the enemy's height,
      // then it kills the enemy, otherwise it kills the player
      int playerBase = player.getY() + player.getHeight();
      int enemyBase = s.getY() + s.getHeight() / 2;
      if (playerBase < enemyBase)
      {
        //System.out.println("Enemy dies");
        s.markForDeletion();
        Explosion e = new Explosion(s.getX(), s.getY(), SIZE, SIZE, 
            new ExplosionRenderer(EXPLOSION_LIFETIME, Color.ORANGE, Color.CYAN), EXPLOSION_LIFETIME);
        explosions.add(e);
        score += ENEMY_HIT_SCORE;
      }
      else
      {
        //System.out.println("Player dies");
        over = true;

      }       
    } 
  }
  
  /**
   * Deletes all marked explosions or enemies.
   */
  private void deleteAllMarked()
  {
    ArrayList<Sprite> temp = new ArrayList<Sprite>();
    for (Sprite s : explosions)
    {
      if (!s.shouldDelete())
      {
        temp.add(s);
      }
    }
    explosions = temp;
    for (Platform p : platforms)
    {
      p.deleteMarkedChildren();
    }
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
    SuperPiggyWorld test = new SuperPiggyWorld();   
    frame.getContentPane().add(test);     
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
      if (over)
      {
        timer.stop();  // game over
      }

      for (Sprite s : getAllSprites())
      {
        s.update();
      }

      // if player is on a moving platform, adjust for motion
      if (!player.isBallistic() && currentPlatform.getDx() != 0)
      {
        player.setPosition(player.getXExact() + currentPlatform.getDx(), player.getYExact());
      }

      // adjust player speed if arrow key has been held down
      if (leftArrow > 0) 
      {
        leftArrow += 1;
        if (leftArrow > 5)
        {
          player.setDirection(-PLAYER_SPEED * 2, player.getDy());
        }
      }
      if (rightArrow > 0) 
      {
        rightArrow += 1;
        if (rightArrow > 5)
        {
          player.setDirection(PLAYER_SPEED * 2, player.getDy());
        }
      }
      
      for (Platform s : platforms)
      {
        checkCollisionWithPlatform(s);
      }
      
      // if we wandered off a platform, go ballistic
      if (!player.isBallistic() && ! isOnPlatform())
      {
        // I'm falling!!
        player.setGravity(GRAVITY);
        player.setBallistic(true);      
      }

      // in any case, check for collision with enemy
      ArrayList<Sprite> enemies = getAllEnemies();
      for (Sprite s : enemies)
      {
        checkCollidesWithEnemy(s);
      }

      // fall off screen?
      if (player.getY() + player.getHeight() > HEIGHT - SIZE)
      {
        over = true;
      }

      // take care of deletions
      deleteAllMarked();
      
      // last but not least...
      repaint();
    }

  }

  /**
   * Handler for key presses.
   */
  private class MyKeyListener implements KeyListener
  {
    @Override
    public void keyPressed(KeyEvent event)
    {
      // display the character typed and the source of the event
      int ch = event.getKeyCode();
      if (ch == KeyEvent.VK_LEFT)
      {
        //System.out.println("left");
        leftArrow += 1;
        rightArrow = 0;
        player.setDirection(-PLAYER_SPEED, player.getDy());
        playerFlipped = false;
      }
      else if (ch == KeyEvent.VK_RIGHT)
      {
        //System.out.println("right");
        rightArrow += 1;
        leftArrow = 0;
        player.setDirection(PLAYER_SPEED, player.getDy());
        playerFlipped = true;
      }
      else if (ch == KeyEvent.VK_A)
      {
        // jump
        if (!player.isBallistic())
        {
          double jumpVelocity = PLAYER_JUMP_VELOCITY;
          
          // if we're on a moving platform, we carry that velocity too
          double dx = player.getDx() + currentPlatform.getDx();
          
          // if we're moving fast, we can jump higher
          if (Math.abs(dx) >= PLAYER_SPEED * 2)
          {
            jumpVelocity *= 1.3;
          }
          player.setDirection(dx, jumpVelocity);
          player.setGravity(GRAVITY);
          player.setBallistic(true);
        }
      }
      else if (ch == KeyEvent.VK_S)
      {        
        // System.out.println(ch);
        // start over
        setupLevel();
        score = 0;
        timer.start();
      }
    }

    @Override
    public void keyReleased(KeyEvent event)
    {
      int ch = event.getKeyCode();
      if (ch == KeyEvent.VK_LEFT)
      {
        //System.out.println("left released");
        leftArrow = 0;
        player.setDirection(0, player.getDy());       
      }
      else if (ch == KeyEvent.VK_RIGHT)
      {
        //System.out.println("right released");
        rightArrow = 0;
        player.setDirection(0, player.getDy());
      }
    }

    @Override
    public void keyTyped(KeyEvent event)
    {
      //System.out.println("typed: " + event.getKeyChar());
      if (event.getKeyChar() == 'x') timer.stop();
    }

  }

  /**
   * Custom renderer for drawing the player.  This is an "inner class" that
   * has access to the instance variables of PiggyWorld, so we can draw
   * according the direction the player is currently facing.
   */
  private class PlayerImageRenderer implements Renderer
  {
    private Image image;
    private Color defaultColor;
    
    public PlayerImageRenderer(Image im, Color defaultColor)
    {
      image = im;
      this.defaultColor = defaultColor;
    }
    
    @Override
    public void render(Graphics g, Sprite s)
    {
      Rectangle r = s.getRect();

      if (image != null)
      {
        if (over)
        {
          g.drawImage(image, r.x, r.y + s.getHeight(), s.getWidth(), -s.getHeight(), SuperPiggyWorld.this);          
        }
        else if (playerFlipped)
        {
          g.drawImage(image, r.x + s.getWidth(), r.y, -s.getWidth(), s.getHeight(), SuperPiggyWorld.this);
        }
        else
        {
          g.drawImage(image, r.x, r.y, s.getWidth(), s.getHeight(), SuperPiggyWorld.this);
        }
      }
      else
      {     
        g.setColor(defaultColor);
        g.fillRect(r.x, r.y, r.width, r.height);
      }
    }

  }

}
