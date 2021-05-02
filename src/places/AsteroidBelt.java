package places;

import interfaces.Steppable;
import main.Game;
import view.AsteroidBeltView;
import view.Controller;

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

    private final AsteroidBeltView view;

    /**
     * Visszatér az AsteroidBelt osztály egyetlen objektumával
     * @return asteroidbelt: az egyetlen AsteroidBelt objektum
     */
    public static AsteroidBelt getInstance() {
        if(instance == null)
            instance = new AsteroidBelt();

        return instance;
    }


    /**
     * Konstruktor
     */
    private AsteroidBelt() {
        this.asteroids = new ArrayList<>();

        this.view = new AsteroidBeltView(this, 0);
        Controller.getInstance().AddDrawable(this.view);
    }


    /**
     * Lépteti az aszteroidamezőt, aki választhat, hogy vagy nem fog történni
     * semmi, vagy napvihart kelt bizonyos aszteroidákon, vagy napközelbe kerül az
     * aszteroidamező összes aszteroidája.
     */
    @Override
    public void Step() {
        Random random = new Random();
        int rand = random.nextInt(5);

        //kisebb ai
        if(rand == 0){
            System.out.println("napközel"); // debug
            this.NearSun();
        }
        else if(rand == 1){
            System.out.println("napvihar"); // debug
            this.SolarFlare();
        }
    }

    @Override
    public int GetSteppablePriority() {
        return 4;
    }

    /**
     * Eltávolítja a paraméterül kapott
     * aszteroidát az aszteroidamezőből, vagyis törli a nyilvántartásból az aszteroidát, illetve
     * frissíti az érintett aszteroidák szomszédsági listáját.
     * @param asteroid: a felrobbant aszteroida
     */
    public void AsteroidExploded(Asteroid asteroid) {
        this.asteroids.remove(asteroid);
    }

    /**
     * Az aszteroidamező napközelbe került, vagyis meghívja az
     * aszteroidamezőben található összes aszteroidának a NearSun metódusát.
     */
    private void NearSun() {
        Random random = new Random();
        int numberOfAsteroidsInvolved = (int) (asteroids.size() * 0.2);
        int i = 0;

        while(i < numberOfAsteroidsInvolved && asteroids.size() != 0){
            int idx = random.nextInt(asteroids.size() -1);
            asteroids.get(idx).NearSun();
            ++i;
        }

        this.view.NearSun();
    }

    /**
     * Az aszteroidamező néhány aszteroidája napviharba került, vagyis
     * meghívja a napviharba kerület aszterodiáknak a SolarFlare metódusát.
     */
    private void SolarFlare() {
        Random random = new Random();
        int numberOfAsteroidsInvolved = (int) (asteroids.size() * 0.2);
        int i = 0;

        while(i < numberOfAsteroidsInvolved && asteroids.size() != 0){
            int idx = random.nextInt(asteroids.size() -1);
            asteroids.get(idx).SolarFlare();
            ++i;
        }

        this.view.SolarFlare();
    }

    /**
     * Hozzáad egy aszteroida az aszteroidamezőhöz
     * @param asteroid: az új aszteroida
     */
    public void AddAsteroid(Asteroid asteroid) {
        asteroids.add(asteroid);
    }

    public boolean BFS() {
        boolean[] visited = new boolean[asteroids.size()];
        Queue<Asteroid> queue = new LinkedList<>();

        Random random = new Random();
        Asteroid s = asteroids.get(random.nextInt(asteroids.size()));

        visited[asteroids.indexOf(s)] = true;
        queue.add(s);

        while(queue.size() != 0) {
            s = queue.poll();

            Iterator<Asteroid> i = s.GetNeighboringAsteroids().listIterator();
            while (i.hasNext()) {
                Asteroid n = i.next();
                if (!visited[asteroids.indexOf(n)]) {
                    visited[asteroids.indexOf(n)] = true;
                    queue.add(n);
                }
            }
        }

        for(boolean b : visited) {
            if (!b) {
                return false;
            }
        }
        return true;
    }
}
