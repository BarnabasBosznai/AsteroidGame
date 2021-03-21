package materials;

import Skeleton.Skeleton;

/**
 * Az Urán az egyik nyersanyag amit ki lehet bányászni a játékban. Ha napközelben van és teljesen meg van fúrva az
 * aszteroida akkor felrobban, rajta lévő telepesek mind meghalnak, robotok pedig egy másik aszteroidán landolnak.
 */
public class Uranium extends RadioactiveMaterial {
    /**
     * Növeli a paraméterként kapott számlálóban a típusához tartozó értéket.
     * @param counter
     */
    @Override
    public void Count(MaterialCounter counter) {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.tabIncrement();
        skeleton.Print(this,"Count(" +counter.getClass().getSimpleName()+ ")");

        counter.Count(Uranium.class);

        skeleton.tabDecrement();
    }
}
