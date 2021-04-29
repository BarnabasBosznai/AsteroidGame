package view;

import places.AsteroidBelt;

import java.awt.*;

public class AsteroidBeltView extends Drawable {

    private final AsteroidBelt asteroidBelt;

    public AsteroidBeltView(AsteroidBelt ab, int z){
        this.asteroidBelt = ab;
        this.zIndex = z;
    }

    @Override
    public void Draw(Graphics2D graphics, Position cameraPos) {

    }

    public void SolarFlare(){

    }

    public void NearSun(){

    }
}
