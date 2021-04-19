package view;

import places.TeleportGate;

public class TeleportGateView extends Drawable {

    private final TeleportGate teleportGate;

    public TeleportGateView(TeleportGate tg, Position pos, int z){
        this.teleportGate = tg;
        this.pos = pos;
        this.zIndex = z;
    }

    @Override
    public void Draw() {

    }
}
