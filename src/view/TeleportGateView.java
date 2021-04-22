package view;

import characters.Settler;
import places.Asteroid;
import places.TeleportGate;

public class TeleportGateView extends Drawable {

    private final TeleportGate teleportGate1;
    private final TeleportGate teleportGate2;
    private Settler settler;

    public TeleportGateView(TeleportGate tg1, TeleportGate tg2, int z){
        this.teleportGate1 = tg1;
        this.teleportGate2 = tg2;
        this.zIndex = z;
    }

    @Override
    public void Draw() {

    }

    public void TeleportGateDestroyed(){
        View.getInstance().TeleportGateDestroyed(this);
    }

    //majd vmit kell kezdeni azzal is, h kovesse le telepest
    public void TeleportGateMoved(Asteroid asteroid){
        View.getInstance().DrawAll();
    }
}
