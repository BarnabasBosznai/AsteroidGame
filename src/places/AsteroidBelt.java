package places;

import interfaces.Steppable;
import main.Game;

import java.util.*;

/**
 * Az aszteroidamezőt reprezentáló osztály, menedzseli az aszteroidamezőben található
 * aszteroidákat, állapotukat, illetve a közöttük lévő kapcsolatokat.
 */
public class AsteroidBelt implements Steppable {
    private static AsteroidBelt instance;

    /**
     * Az aszteroidamezőben található aszteroidák gyűjteménye.
     */
    private final List<Asteroid> asteroids;

    public static AsteroidBelt getInstance() {
        if(instance == null)
            instance = new AsteroidBelt();

        return instance;
    }


    private AsteroidBelt() {
        this.asteroids = new ArrayList<>();
    }


    /**
     * Lépteti az aszteroidamezőt, aki választhat, hogy vagy nem fog történni
     * semmi, vagy napvihart kelt bizonyos aszteroidákon, vagy napközelbe kerül az
     * aszteroidamező összes aszteroidája.
     */
    @Override
    public void Step() {

    }

    /**
     * Eltávolítja a paraméterül kapott
     * aszteroidát az aszteroidamezőből, vagyis törli a nyilvántartásból az aszteroidát, illetve
     * frissíti az érintett aszteroidák szomszédsági listáját.
     * @param asteroid
     */
    public void AsteroidExploded(Asteroid asteroid) {
        this.asteroids.remove(asteroid);

        Game.getInstance().RemoveAsteroid(asteroid);
    }

    /**
     * Az aszteroidamező napközelbe került, vagyis meghívja az
     * aszteroidamezőben található összes aszteroidának a NearSun metódusát.
     */
    /*NEW*/ //igazabol nem, ez sztem maradhat
    public void NearSun() {
        Random random = new Random();
        int numberOfAsteroidsInvolved = (int) (asteroids.size() * 0.2);
        int i = 0;

        while(i < numberOfAsteroidsInvolved && asteroids.size() != 0){
            int idx = random.nextInt(asteroids.size() -1);
            asteroids.get(idx).NearSun();
            ++i;
        }
    }

    /**
     * Az aszteroidamező néhány aszteroidája napviharba került, vagyis
     * meghívja a napviharba kerület aszterodiáknak a SolarFlare metódusát.
     */
    public void SolarFlare() {
        Random random = new Random();
        int numberOfAsteroidsInvolved = (int) (asteroids.size() * 0.2);
        int i = 0;

        while(i < numberOfAsteroidsInvolved && asteroids.size() != 0){
            int idx = random.nextInt(asteroids.size() -1);
            asteroids.get(idx).SolarFlare();
            ++i;
        }
    }

    public void AddAsteroid(Asteroid asteroid) {
        asteroids.add(asteroid);
    }

    public void ClearAsteroids() {
        asteroids.clear();
    }
}
