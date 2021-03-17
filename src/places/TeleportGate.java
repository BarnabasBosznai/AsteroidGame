package places;

import characters.Character;
import interfaces.Item;

public class TeleportGate extends Place implements Item {
    private Asteroid asteroid;
    private TeleportGate pair;

    public void setAsteroid(Asteroid asteroid) {
        this.asteroid = asteroid;
    }

    public void setPair(TeleportGate teleportGate) {
        this.pair = teleportGate;
    }

    public Asteroid getAsteroid() {
        return asteroid;
    }

    public void removeFromAsteroid() {
        pair.getAsteroid().removeTeleportGate(pair);
        pair.setAsteroid(null);
        asteroid.removeTeleportGate(this);
        asteroid = null;
    }

    @Override
    public boolean compatibleWith(Item item) {
        return this.getClass() == item.getClass();
    }

    @Override
    public boolean move(Character character) {
        Asteroid asteroid = pair.getAsteroid();
        return asteroid != null && asteroid.move(character);
        
    }
}
