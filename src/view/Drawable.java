package view;

public abstract class Drawable {

    protected int zIndex;
    public abstract void Draw();
    public int GetZIndex(){
        return this.zIndex;
    }
}
