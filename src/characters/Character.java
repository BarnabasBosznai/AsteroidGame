package characters;

import Skeleton.Skeleton;
import main.Game;
import places.Asteroid;
import interfaces.Steppable;
import places.Place;

import java.util.List;
import java.lang.Math;

public abstract class Character implements Steppable {
    protected Asteroid asteroid;

    abstract public void HitByExplosion();  // Nem általános egyikük függvénye sem.

    public void HitByStorm() { // Robot-nak ez a függvénye.
        Skeleton.getInstance().tabIncrement();
        Skeleton.getInstance().Print(this,"HitByStorm()");

        Game.getInstance().RemoveSteppable(this);
        asteroid.TakeOff(this);

        Skeleton.getInstance().tabDecrement();
    }

    public boolean Move() { // Véletlenszerű mozgás
        Skeleton.getInstance().tabIncrement();
        Skeleton.getInstance().Print(this,"Move()");

        List<Place> destinations = this.asteroid.GetNeighbors();

        double random = Math.random()*(destinations.size()-1);
        Place choosenDestination = destinations.get((int)random);

        Asteroid currentAsteroid = this.asteroid;

        if(choosenDestination.Move(this)){
            currentAsteroid.TakeOff(this);

            Skeleton.getInstance().tabDecrement();
            return true;
        }

        Skeleton.getInstance().tabDecrement();
        return false;

        /**
         * Csak a Robotnál érdekes.
         *      Bobó v2
         */

    }

    public void SetAsteroid(Asteroid asteroid){
        Skeleton.getInstance().tabIncrement();
        Skeleton.getInstance().Print(this,"SetAsteroid(Asteroid)");

        this.asteroid = asteroid;

        Skeleton.getInstance().tabDecrement();
    }
}
