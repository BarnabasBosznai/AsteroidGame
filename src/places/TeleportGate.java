package places;

import characters.Character;
import interfaces.Item;

public class TeleportGate extends Place implements Item {
    private Asteroid asteroid;
    private TeleportGate pair;

    public void setAsteroid(Asteroid asteroid) {

    }

    public void setPair(TeleportGate teleportGate) {

    }

    public Asteroid getAsteroid() {
        return asteroid;
    }

    public void removeFromAsteroid() {

    }

    @Override
    public boolean compatibleWith(Item item) {
        return false;
    }

    @Override
    public boolean move(Character character) {
        return false;
    }
}
