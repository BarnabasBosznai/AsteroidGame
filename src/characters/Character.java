package characters;

import places.Asteroid;
import interfaces.Steppable;

public abstract class Character implements Steppable {
    protected Asteroid asteroid;

    public void HitByExplosion() {

    }

    public void HitByStorm() {

    }

    public boolean Move() {
        return false;
    }

    public void SetAsteroid(Asteroid asteroid){
        this.asteroid = asteroid;
    }
}
