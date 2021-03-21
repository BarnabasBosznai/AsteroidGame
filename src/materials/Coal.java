package materials;

import  Skeleton.Skeleton;

/**
 * A szén az egyik nyersanyag amit ki lehet bányászni a játékban.
 */
public class Coal extends Material {
    /**
     * Növeli a paraméterként kapott számlálóban a típusához tartozó értéket.
     * @param counter
     */
    @Override
    public void Count(MaterialCounter counter) {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.tabIncrement();
        skeleton.Print(this,"Count(" +counter.getClass().getSimpleName()+ ")");

        counter.Count(Coal.class);

        skeleton.tabDecrement();
    }
}
