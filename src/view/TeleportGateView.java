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

    }

    public void TeleportGateDestroyed(){
        Controller.getInstance().TeleportGateDestroyed(this);
    }
}
