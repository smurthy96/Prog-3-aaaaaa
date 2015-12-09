package demo;

import java.awt.Color;
import java.awt.Graphics;

import hw3.Renderer;
import hw3.Sprite;

public class SolidRenderer implements Renderer
{
  private Color c;
  public SolidRenderer(Color givenColor)
  {
    c = givenColor;
  }
  
  //@Override
  public void render(Graphics g, Sprite s)
  {
    this.render(g, s, 0, 0);
  }

  //@Override
  public void render(Graphics g, Sprite s, int parentX, int parentY)
  {
    Color savedColor = g.getColor();
    g.setColor(c);
    g.fillRect(s.getX() + parentX, s.getY() + parentY, s.getWidth(), s.getHeight());
    g.setColor(savedColor);    
  }

}
