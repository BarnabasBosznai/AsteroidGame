package view;

import characters.UFO;

public class UFOView extends Drawable{

    private final UFO ufo;

    public UFOView(UFO u, Position pos, int z){
        this.ufo = u;
        this.pos = pos;
        this.zIndex = z;
    }

    @Override
    public void Draw() {

    }
}
