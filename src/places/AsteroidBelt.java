package places;

import Skeleton.Skeleton;
import interfaces.Steppable;
import materials.MaterialStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AsteroidBelt implements Steppable {
    private static AsteroidBelt instance;
    private final List<Asteroid> asteroids;

    public static AsteroidBelt getInstance() {
        if(instance == null)
            instance = new AsteroidBelt();

        return instance;
    }

    private AsteroidBelt() {

        Skeleton skeleton = Skeleton.getInstance();
        skeleton.tabIncrement();
        skeleton.Print(this, "create(" + AsteroidBelt.class.getSimpleName() + ")");

        this.asteroids = new ArrayList<>();

        skeleton.tabDecrement();
    }

    @Override
    public void Step() {

    }

    public void AsteroidExploded(Asteroid asteroid) {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.tabIncrement();
        skeleton.Print(this, "AsteroidExploded("+ asteroid.getClass().getSimpleName() +" )");

        this.asteroids.remove(asteroid);

        skeleton.tabDecrement();
    }

    private List<Integer> RandomAsteroids(){
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.tabIncrement();
        skeleton.Print(this, "RandomAsteroids()");
        List<Integer> indexes = new ArrayList<Integer>();
        for (int i = 0; i < asteroids.size(); ++i) {
            indexes.add(i);
        }

        Collections.shuffle(indexes);
        int num = (int) (asteroids.size() * 0.1);
        for(int i = 0; i < asteroids.size() - num; ++i){
            indexes.remove(i);
        }

        skeleton.tabDecrement();
        return indexes;
    }

    public void NearSun() {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.tabIncrement();
        skeleton.Print(this, "NearSun()");

        var indexes = this.RandomAsteroids();

        for(Integer idx : indexes){
            asteroids.get(idx).NearSun();
        }

        skeleton.tabDecrement();
    }

    public void SolarFlare() {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.tabIncrement();
        skeleton.Print(this, "SolarFlareSun()");

        var indexes = this.RandomAsteroids();

        for(Integer idx : indexes){
            asteroids.get(idx).SolarFlare();
        }

        skeleton.tabDecrement();
    }
}
