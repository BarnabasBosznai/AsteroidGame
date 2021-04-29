package view;

import java.awt.*;

public abstract class Drawable {

    protected int zIndex;
    public abstract void Draw(Graphics2D graphics);
    public int GetZIndex(){
        return this.zIndex;
    }
}
