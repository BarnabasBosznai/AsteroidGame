package materials;

import places.Asteroid;

/**
 * Az Urán az egyik nyersanyag amit ki lehet bányászni a játékban. Ha napközelben van és teljesen meg van fúrva az
 * aszteroida akkor felrobban, rajta lévő telepesek mind meghalnak, robotok pedig egy másik aszteroidán landolnak.
 */
public class Uranium extends RadioactiveMaterial {
    /**
     * Növeli a paraméterként kapott számlálóban a típusához tartozó értéket.
     * @param counter
     */

    private int nearSuns;

    public Uranium(){
        super();
        nearSuns = 2;
    }

    public Uranium(int expositions){
        super();
        nearSuns = expositions;
    }

    @Override
    public void Count(MaterialCounter counter) {
        counter.Count(Uranium.class);
    }

    @Override
    public String Print(){return "uranium"+(nearSuns);}

    /*NEW*/
    @Override
    public void OnNearSun(Asteroid asteroid){
        nearSuns--;
        if(nearSuns < 0)
            asteroid.Explosion();

    }

    @Override
    public boolean CompatibleWith(Material material) {
        return super.CompatibleWith(material) && this.nearSuns == ((Uranium)material).nearSuns;
    }
}
