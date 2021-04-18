package materials;

import places.Asteroid;

/**
 * Az Urán az egyik nyersanyag amit ki lehet bányászni a játékban. Ha napközelben van és teljesen meg van fúrva az
 * aszteroida akkor felrobban, rajta lévő telepesek mind meghalnak, robotok pedig egy másik aszteroidán landolnak.
 */
public class Uranium extends RadioactiveMaterial {

    /**
     * Még hátralévő napközelek száma a robbanásig
     */
    private int nearSuns;

    /**
     * Default konstruktor
     */
    public Uranium(){
        super();
        nearSuns = 2;
    }

    /**
     * Konstruktor, a hátralévő napközelek száma állítható
     * (csak teszteléshez)
     * @param expositions: hátralévő napközelek
     */
    public Uranium(int expositions){
        super();
        nearSuns = expositions;
    }

    /**
     * Növeli a paraméterként kapott számlálóban a típusához tartozó értéket.
     * @param counter: a számoláshoz használt segédosztály
     */
    @Override
    public void Count(MaterialCounter counter) {
        counter.Count(Uranium.class);
    }

    /**
     * Kiírja a nyersanyag típusát
     * @return string: a nyersanyag típusa stringként a kiíratáshoz
     */
    @Override
    public String Print(){return "uranium"+(nearSuns);}

    /**
     * Napközelbe érve egy teljesen megfúrt aszteroidán felrobban, vele együtt az aszteroida is.
     * @param asteroid: a nyersanyagot tartalmazó aszteroida
     */
    @Override
    public void OnNearSun(Asteroid asteroid){
        nearSuns--;
        if(nearSuns < 0)
            asteroid.Explosion();

    }

    /**
     * Visszatér a még hátralévő napközelek számával a robbanásig
     * @return int: a még hátralévő napközelek száma a robbanásig
     */
    public int getNearSuns() {
        return nearSuns;
    }
}
