package places;

import interfaces.Steppable;

import java.util.List;

public class AsteroidBelt implements Steppable {
    private static AsteroidBelt instance;
    private List<Asteroid> asteroids;

    public static AsteroidBelt getInstance() {
        if(instance == null)
            instance = new AsteroidBelt();

        return instance;
    }

    private AsteroidBelt() {

    }

    @Override
    public void step() {

    }

    public void asteroidExploded(Asteroid asteroid) {

    }

    private void nearSun() {

    }

    private void solarFlare() {

    }


}
