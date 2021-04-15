package places;

import characters.Character;
import interfaces.Item;
import interfaces.Steppable;
import main.Game;

/**
 * Egy teleportkaput reprezentáló osztály. A teleportkapuk segítségével el tudnak jutni a karakterek olyan
 * aszteroidákra is, amik alapból nem lennének szomszédosak. Ehhez szükséges a két aszteroidán elhelyezni a
 * teleportkapupár egy-egy elemét, a teleportkapupár ezután lesz működőképes állapotban.
 */
public class TeleportGate extends Place implements Item, Steppable {
    private Asteroid asteroid;
    private TeleportGate pair;
    private boolean crazy;

    public TeleportGate(){
        crazy = false;      // Konstruktor így már kellhet
    }

    /**
     *  A teleportkapu magától mozgatja magát, ha megkergül.
     */
    @Override
    public void Step() {
        if (crazy){
            var asteroids = this.asteroid.GetNeighboringAsteroids();
            this.asteroid.RemoveTeleportGate(this);
            //vmi random, most elso
            asteroids.get(0).PlaceTeleport(this);
        }
    }


    /**
     * Beállítja a paraméterként kapott aszteroidát a sajét aszteroidájaként.
     * @param asteroid
     */
    public void SetAsteroid(Asteroid asteroid) {
        this.asteroid = asteroid;
    }

    /**
     * Beállítja a paraméterként kapott teleportkaput a párjaként.
     * @param teleportGate
     */
    public void SetPair(TeleportGate teleportGate) {
        this.pair = teleportGate;
    }

    /**
     * Visszatért a teleportkapu aszteroidájával.
     * @return
     */
    public Asteroid GetAsteroid() {
        return asteroid;
    }

    /**
     * Eltávolítja a teleportkaput a jelenlegi aszteroidájáról, illetve a párját is az övéről.
     */
    public void RemoveFromAsteroid() {
        pair.GetAsteroid().RemoveTeleportGate(pair);
        pair.SetAsteroid(null);
        if(asteroid != null)
            asteroid.RemoveTeleportGate(this);

        Game.getInstance().RemoveSteppable(this);
        Game.getInstance().RemoveSteppable(pair);
    }

    /**
     * Igazzal tér vissza, ha a paraméterként kapott Item ugyanolyan típusú, mint ő, egyébként hamis.
     * @param item
     * @return
     */
    @Override
    public boolean CompatibleWith(Item item) {
        return this.getClass() == item.getClass();
    }

    /**
     * Megpróbálja átteleportálni a charactert a teleportkapu párjának az aszteroidájára.
     * @param character
     * @return Ha a párját már lehelyezték valahova, létrejön a teleportálás és visszatér True-val, ha nem, False-al.
     */
    @Override
    public boolean Move(Character character) {
        Asteroid asteroid = pair.GetAsteroid();
        return asteroid != null && asteroid.Move(character);
    }

    /*NEW*/
    public void HitByStorm(){
        crazy = true;   // Ekkor csak megkergül, innentől minden körben odébb megy
    }

    public void SetCrazy(boolean crazy) {
        this.crazy = crazy;
    }

    public boolean GetCrazy(){
        return this.crazy;
    }
}