package view;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Drawable {
    protected BufferedImage img;
    protected int zIndex;
    public abstract void Draw(Graphics2D graphics, Position cameraPos);
    public int GetZIndex(){
        return this.zIndex;
    }
}
