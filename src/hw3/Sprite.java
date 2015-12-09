package hw3;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Base class for simple objects in a video game.  
 */
public abstract class Sprite
{
  /**
   * Horizontal coordinate of the upper-left corner.
   */
  private double x;
  
  /**
   * Vertical coordinate of the upper-left corner.
   */
  private double y;
  
  /**
   * Width of this object, normally assumed to be in pixels.
   */
  private int width;
  
  /**
   * Height of this object, normally assumed to be in pixels.
   */
  private int height;
  
  /**
   * Flag indicating whether this object has been marked for deletion.
   */
  private boolean delete;
  
  /**
   * Number of times the update() method has been called.
   */
  private int ticks;
  
  /**
   * A Renderer for drawing this object using a graphics context.
   */
  private Renderer renderer;
  
  /**
   * Constructs a new Sprite.
   * @param x
   * @param y
   * @param width
   * @param height
   */
  protected Sprite(double x, double y, int width, int height, Renderer givenRenderer)
  {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    renderer = givenRenderer;
  }
  
  /**
   * Returns the x-coordinate rounded to an integer.
   * @return
   *   x-coordinate rounded to an integer
   */
  public int getX()
  {
    return (int) Math.round(x);
  }

  /**
   * Returns the y-coordinate rounded to an integer.
   * @return
   *   y-coordinate rounded to an integer
   */
  public int getY()
  {
    return (int) Math.round(y);
  }

  /**
   * Returns the x-coordinate's exact value.
   * @return
   *   the x-coordinate
   */
  public double getXExact()
  {
    return x;
  }

  /**
   * Returns the y-coordinate's exact value.
   * @return
   *   the y-coordinate
   */
  public double getYExact()
  {
    return y;
  }

  /**
   * Returns the width.
   * @return
   *   width of this object
   */
  public int getWidth()
  {
    return width;
  }
  
  /**
   * Returns the height.
   * @return
   *   height of this object
   */
  public int getHeight()
  {
    return height;
  }
  
  /**
   * Returns the bounding rectangle for this object.
   * @return
   *   bounding rectangle 
   */
  public Rectangle getRect()
  {
    return new Rectangle(getX(), getY(), width, height);
  }
  
  /**
   * Sets the position of this object.
   * @param newX
   *   the new x-coordinate
   * @param newY
   *   the new y-coordiante
   */
  public void setPosition(double newX, double newY)
  {
    x = newX;
    y = newY;
  } 
  
  /**
   * Returns true if this object has been marked for deletion.
   * @return
   */
  public boolean shouldDelete()
  {
    return delete;
  }
  
  /**
   * Marks this object for deletion.
   */
  public void markForDeletion()
  {
    delete = true;
  }

  /**
   * Determines whether this object overlaps the given object.
   * @param other
   * @return
   */
  public boolean collides(Sprite other)
  {
     return this.getRect().intersects(other.getRect());
  }
  
  /**
   * Uses this object's Renderer to draw the object.
   * @param g
   *   graphics context for rendering
   */
  public void draw(Graphics g)
  {
    renderer.render(g, this);
  }  
  
  /**
   * Returns the number of times that update() has been invoked for this
   * object.
   * @return
   *   elapsed ticks
   */
  public int getTicks()
  {
    return ticks;
  }
  
  /**
   * Updates this object's attributes for the next frame.
   */
  public void update()
  {
    ticks += 1;
  }
}
