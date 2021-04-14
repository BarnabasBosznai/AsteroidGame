package characters;

import main.Game;
import places.Asteroid;
import interfaces.Steppable;
import places.Place;

import java.util.List;
import java.lang.Math;

/**
 * A játékban műveletek végrehajtására képes karakterek (Settler, Robot) abszrakt ősosztálya.
 */
public abstract class Character implements Steppable {
    /**
     *Az aktuális aszteroida ahol a karakter éppen tartózkodik
     */
    protected Asteroid asteroid;

    /**
     * Lereagálja, hogy felrobbant az aszteroida, amin éppen
     * tartózkodik.
     */
    abstract public void HitByExplosion();  // Nem általános egyikük függvénye sem.

    /**
     * Lereagálja, hogy napviharba került az aszteroida, amin éppen tartózkodik.
     */
    public void HitByStorm() { // Robot-nak ez a függvénye.
        Game.getInstance().RemoveSteppable(this);
        asteroid.TakeOff(this);
    }

    /**
     * Átviszi egy elérhető aszteroidára a karaktert.True ha sikerült false ha
     * nem.
     * @return
     */
    public boolean Move(Place place) { // Véletlenszerű mozgás
        List<Place> destinations = this.asteroid.GetNeighbors();

        double random = Math.random()*(destinations.size()-1);
        Place choosenDestination = destinations.get((int)random);

        Asteroid currentAsteroid = this.asteroid;

        if(choosenDestination.Move(this)){
            currentAsteroid.TakeOff(this);

            return true;
        }

        return false;
    }

    /**
     * Beállítja az aktuális aszteroida helyét.
     * @param asteroid
     */
    public void SetAsteroid(Asteroid asteroid){
        this.asteroid = asteroid;
    }

    public Asteroid GetAsteroid(){
        return this.asteroid;
    }
}
