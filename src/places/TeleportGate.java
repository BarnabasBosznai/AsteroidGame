package places;

import characters.Character;
import interfaces.Item;

public class TeleportGate extends Place implements Item {
    private Asteroid asteroid;
    private TeleportGate pair;

    public void SetAsteroid(Asteroid asteroid) {
        this.asteroid = asteroid;
    }

    public void SetPair(TeleportGate teleportGate) {
        this.pair = teleportGate;
    }

    public Asteroid GetAsteroid() {
        return asteroid;
    }

    public void RemoveFromAsteroid() {
        pair.GetAsteroid().RemoveTeleportGate(pair);
        pair.SetAsteroid(null);
        asteroid.RemoveTeleportGate(this);
        asteroid = null;
    }

    @Override
    public boolean CompatibleWith(Item item) {
        return this.getClass() == item.getClass();
    }

    @Override
    public boolean Move(Character character) {
        Asteroid asteroid = pair.GetAsteroid();
        return asteroid != null && asteroid.Move(character);
        
    }
}
