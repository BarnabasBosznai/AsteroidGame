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
        this.animationCounterMax = Controller.getInstance().GetWindowSize().x;;

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

        int vx=Controller.getInstance().GetWindowSize().x;
        int vy=Controller.getInstance().GetWindowSize().y;

        if(nearSunHappening){
            graphics.setColor(new Color(1.0f, 0.369f, 0.075f, ((float)animationCounter/(float)animationCounterMax)));
            Position windowSize = Controller.getInstance().GetWindowSize();
            graphics.fillRect(0,0,windowSize.x,windowSize.y);

            graphics.setColor(new Color(255, 0, 0, 255));
            graphics.setFont(new Font("Monospaced", Font.BOLD, vx/10));
            FontMetrics metrics = graphics.getFontMetrics(new Font("Monospaced", Font.BOLD, vx/10));
            graphics.drawString("Near Sun!",vx/2- metrics.stringWidth("Near Sun!") / 2,vy/2);

            animationCounter+=vx/100;
            if(animationCounter == animationCounterMax){
                animationCounter = 0;
                nearSunHappening = false;
            }
        }
        if(solarFlareHappening){
            graphics.setColor(new Color(1.0f, 0.369f, 0.075f));
            int position= (int)((double)animationCounter*(vx/10)/(double)animationCounterMax)-vx;
            graphics.fillOval(position,Controller.getInstance().GetWindowSize().y/2-vy,2*vy,2*vy);

            graphics.setColor(new Color(255, 0, 0, 255));
            graphics.setFont(new Font("Monospaced", Font.BOLD, vx/10));
            FontMetrics metrics = graphics.getFontMetrics(new Font("Monospaced", Font.BOLD, vx/10));
            graphics.drawString("Solar Flare!",vx/2- metrics.stringWidth("Solar Flare!") / 2,vy/2);

            animationCounter+=vx/10;
            if(position >= Controller.getInstance().GetWindowSize().x){
                animationCounter = 0;
                solarFlareHappening = false;
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
