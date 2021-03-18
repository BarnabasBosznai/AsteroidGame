package characters;

import main.Game;
import places.Asteroid;
import interfaces.Steppable;
import places.Place;

import java.util.List;
import java.util.Random;
import java.lang.Math;

public abstract class Character implements Steppable {
    protected Asteroid asteroid;

    abstract public void HitByExplosion();  // Nem általános egyikük függvénye sem.

    public void HitByStorm() { // Robot-nak ez a függvénye.
        Game.getInstance().RemoveSteppable(this);
        asteroid.TakeOff(this);
    }

    public boolean Move() { // Véletlenszerű mozgás
        List<Place> destinations = this.asteroid.getNeighbors();

        double random = Math.random()*(destinations.size()-1);
        Place choosenDestination = destinations.get((int)random);
        // Akinek van jobb (jó) ötlete random számot generálni, arra nyitott vagyok.

        Asteroid currentAsteroid = this.asteroid;

        if(choosenDestination.Move(this)){
            currentAsteroid.TakeOff(this);

            return true;
        }

        return false;

        /**
         * Nem hívja meg senki ezt a függvényt kívülről.
         * Csak a Robotnál érdekes.
         * De így hagyom amíg felül nem bíráljuk.
         *      Bobó
         */

    }

    public void SetAsteroid(Asteroid asteroid){
        this.asteroid = asteroid;
    }
}
