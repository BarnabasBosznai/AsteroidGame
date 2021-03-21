package places;

import Skeleton.Skeleton;
import characters.Character;
import interfaces.Item;

/**
 * Egy teleportkaput reprezentáló osztály. A teleportkapuk segítségével el tudnak jutni a karakterek olyan
 * aszteroidákra is, amik alapból nem lennének szomszédosak. Ehhez szükséges a két aszteroidán elhelyezni a
 * teleportkapupár egy-egy elemét, a teleportkapupár ezután lesz működőképes állapotban.
 */
public class TeleportGate extends Place implements Item {
    private Asteroid asteroid;
    private TeleportGate pair;

    /**
     * Beállítja a paraméterként kapott aszteroidát a sajét aszteroidájaként.
     * @param asteroid
     */
    public void SetAsteroid(Asteroid asteroid) {
        Skeleton instance = Skeleton.getInstance();
        instance.tabIncrement();
        instance.Print(this, "SetAsteroid(Asteroid)");

        this.asteroid = asteroid;

        instance.tabDecrement();
    }

    /**
     * Beállítja a paraméterként kapott teleportkaput a párjaként.
     * @param teleportGate
     */
    public void SetPair(TeleportGate teleportGate) {
        Skeleton instance = Skeleton.getInstance();
        instance.tabIncrement();
        instance.Print(this, "SetPair(TeleportGate)");

        this.pair = teleportGate;

        instance.tabDecrement();
    }

    /**
     * Visszatért a teleportkapu aszteroidájával.
     * @return
     */
    public Asteroid GetAsteroid() {
        Skeleton instance = Skeleton.getInstance();
        instance.tabIncrement();
        instance.Print(this, "GetAsteroid()");
        instance.tabDecrement();

        return asteroid;
    }

    /**
     * Eltávolítja a teleportkaput a jelenlegi aszteroidájáról, illetve a párját is az övéről.
     */
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

    /**
     * Igazzal tér vissza, ha a paraméterként kapott Item ugyanolyan típusú, mint ő, egyébként hamis.
     * @param item
     * @return
     */
    @Override
    public boolean CompatibleWith(Item item) {
        Skeleton instance = Skeleton.getInstance();
        instance.tabIncrement();
        instance.Print(this, "CompatibleWith(" + item.getClass().getSimpleName() + ")");
        instance.tabDecrement();

        return this.getClass() == item.getClass();
    }

    /**
     * Megpróbálja átteleportálni a charactert a teleportkapu párjának az aszteroidájára.
     * @param character
     * @return Ha a párját már lehelyezték valahova, létrejön a teleportálás és visszatér True-val, ha nem, False-al.
     */
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
