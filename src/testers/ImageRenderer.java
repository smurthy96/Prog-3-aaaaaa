package testers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import hw3.Renderer;
import hw3.Sprite;

/**
 * Renderer implementation that will draw a given image within
 * the sprite's bounding rectangle.  Draws a solid color if the
 * Image object is null.
 */
public class ImageRenderer implements Renderer
{
  private Image image;
  private Color defaultColor;
  
  public ImageRenderer(Image im, Color defaultColor)
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
      g.drawImage(image, r.x, r.y, s.getWidth(), s.getHeight(), null);
    }
    else
    {     
      g.setColor(defaultColor);
      g.fillRect(r.x, r.y, r.width, r.height);
    }
  }


}
