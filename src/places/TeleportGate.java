package places;

import Skeleton.Skeleton;
import characters.Character;
import interfaces.Item;

public class TeleportGate extends Place implements Item {
    private Asteroid asteroid;
    private TeleportGate pair;

    public void SetAsteroid(Asteroid asteroid) {
        Skeleton instance = Skeleton.getInstance();
        instance.tabIncrement();
        instance.Print(this, "SetAsteroid(Asteroid)");

        this.asteroid = asteroid;

        instance.tabDecrement();
    }

    public void SetPair(TeleportGate teleportGate) {
        Skeleton instance = Skeleton.getInstance();
        instance.tabIncrement();
        instance.Print(this, "SetPair(TeleportGate)");

        this.pair = teleportGate;

        instance.tabDecrement();
    }

    public Asteroid GetAsteroid() {
        Skeleton instance = Skeleton.getInstance();
        instance.tabIncrement();
        instance.Print(this, "GetAsteroid()");
        instance.tabDecrement();

        return asteroid;
    }

    public void RemoveFromAsteroid() {
        Skeleton instance = Skeleton.getInstance();
        instance.tabIncrement();
        instance.Print(this, "RemoveFromAsteroid()");

        pair.GetAsteroid().RemoveTeleportGate(pair);
        pair.SetAsteroid(null);
        asteroid.RemoveTeleportGate(this);
        asteroid = null;

        instance.tabDecrement();
    }

    @Override
    public boolean CompatibleWith(Item item) {
        Skeleton instance = Skeleton.getInstance();
        instance.tabIncrement();
        instance.Print(this, "CompatibleWith(" + item.getClass().getSimpleName() + ")");
        instance.tabDecrement();

        return this.getClass() == item.getClass();
    }

    @Override
    public boolean Move(Character character) {
        Skeleton instance = Skeleton.getInstance();
        instance.tabIncrement();
        instance.Print(this, "Move(" + character.getClass().getSimpleName() + ")");

        Asteroid asteroid = pair.GetAsteroid();

        if(instance.GetInput("A kapu párja már valamelyik aszteroidára le van téve? [I/N]: ").equalsIgnoreCase("i")) {
            boolean ret = asteroid.Move(character);
            instance.tabDecrement();
            return ret;
        } else {
            instance.tabDecrement();
            return false;
        }
    }
}
