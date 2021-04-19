package view;

import places.Asteroid;

public class AsteroidView extends Drawable {

    private final Asteroid asteroid;

    public AsteroidView(Asteroid a, Position pos, int z){
        this.asteroid = a;
        this.pos = pos;
        this.zIndex = z;
    }

    @Override
    public void Draw() {

    }
}
