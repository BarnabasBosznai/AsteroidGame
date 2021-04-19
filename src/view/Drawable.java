package view;

public abstract class Drawable {

    protected int zIndex;
    protected Position pos;
    public abstract void Draw();
    public int GetZIndex(){
        return this.zIndex;
    }
}
