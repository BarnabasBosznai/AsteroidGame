package view;

import characters.Settler;

public class SettlerView extends Drawable {

    private final Settler settler;

    public SettlerView(Settler s, Position pos, int z){
        this.settler = s;
        this.pos = pos;
        this.zIndex = z;
    }

    @Override
    public void Draw() {

    }
}
