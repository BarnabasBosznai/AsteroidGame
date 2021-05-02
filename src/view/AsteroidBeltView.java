package view;

import places.AsteroidBelt;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AsteroidBeltView extends Drawable {

    private final AsteroidBelt asteroidBelt;

    private final List<Position> stars;

    private boolean solarFlareHappening;
    private boolean nearSunHappening;
    private int animationCounter;
    private final int animationCounterMax;

    public AsteroidBeltView(AsteroidBelt ab, int z){
        this.asteroidBelt = ab;
        this.zIndex = z;


        this.solarFlareHappening = false;
        this.nearSunHappening = false;
        this.animationCounter = 0;
        this.animationCounterMax = 35;

        this.stars = new ArrayList<>();

        Random random = new Random();
        for(int i = 0; i < 2000; ++i){
            stars.add(new Position(random.nextInt(2500*2)-2500, random.nextInt(2500*2)-2500));
        }
    }

    @Override
    public void Draw(Graphics2D graphics, Position cameraPos) {
        graphics.setColor(Color.WHITE);
        stars.forEach(star -> graphics.fillOval(star.x - cameraPos.x, star.y - cameraPos.y, 1, 1));

        if(solarFlareHappening || nearSunHappening){
            graphics.setColor(new Color(1.0f, 0.369f, 0.075f, ((float)animationCounter/(float)animationCounterMax)));
            graphics.fillRect(0,0,Controller.getInstance().windowSize.x,Controller.getInstance().windowSize.y);
            ++animationCounter;
            if(animationCounter == animationCounterMax){
                animationCounter = 0;
                solarFlareHappening = false;
                nearSunHappening = false;
            }
        }
    }

    public void SolarFlare(){
        this.solarFlareHappening = true;
    }

    public void NearSun(){
        this.nearSunHappening = true;
    }
}
