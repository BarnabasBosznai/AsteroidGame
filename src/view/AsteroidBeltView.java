package view;

import places.AsteroidBelt;

public class AsteroidBeltView extends Drawable {

    private final AsteroidBelt asteroidBelt;

    public AsteroidBeltView(AsteroidBelt ab, Position pos, int z){
        this.asteroidBelt = ab;
        this.pos = pos;
        this.zIndex = z;
    }

    @Override
    public void Draw() {

    }
}
