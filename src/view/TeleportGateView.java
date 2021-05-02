package view;

import characters.Settler;
import places.Asteroid;
import places.TeleportGate;

import java.awt.*;

public class TeleportGateView extends Drawable {

    private final TeleportGate teleportGate1;
    private final TeleportGate teleportGate2;

    public TeleportGateView(TeleportGate tg1, TeleportGate tg2, int z){
        this.teleportGate1 = tg1;
        this.teleportGate2 = tg2;
        this.zIndex = z;
    }

    @Override
    public void Draw(Graphics2D graphics, Position cameraPos) {
        if (teleportGate1.GetAsteroid() != null && teleportGate2.GetAsteroid()!=null){
            graphics.setColor(Color.BLUE);
            AsteroidView ast1 = Controller.getInstance().GetAsteroidView(teleportGate1.GetAsteroid());
            AsteroidView ast2 = Controller.getInstance().GetAsteroidView(teleportGate2.GetAsteroid());
            graphics.drawLine(ast1.GetPos().x - cameraPos.x + 50,ast1.GetPos().y - cameraPos.y + 50,ast2.GetPos().x - cameraPos.x + 50,ast2.GetPos().y - cameraPos.y + 50);
        }
    }

    public void TeleportGateDestroyed(){
        Controller.getInstance().TeleportGateDestroyed(this);
    }
}
