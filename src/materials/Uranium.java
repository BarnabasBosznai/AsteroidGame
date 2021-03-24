package materials;

import Skeleton.Skeleton;
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

    /*NEW*/
    private int nearSuns;

    public Uranium(){
        super();
        nearSuns = 0;
    }

    @Override
    public void Count(MaterialCounter counter) {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.tabIncrement();
        skeleton.Print(this,"Count(" +counter.getClass().getSimpleName()+ ")");

        counter.Count(Uranium.class);

        skeleton.tabDecrement();
    }

    /*NEW*/
    @Override
    public void OnNearSun(Asteroid asteroid){
        ++nearSuns;
        if(nearSuns == 3)
            asteroid.Explosion();
    }
}
