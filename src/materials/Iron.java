package materials;

import Skeleton.Skeleton;

/**
 * A vas az egyik nyersanyag amit ki lehet bányászni a játékban.
 */
public class Iron extends Material {
    /**
     * Növeli a paraméterként kapott számlálóban a típusához tartozó értéket.
     * @param counter
     */
    @Override
    public void Count(MaterialCounter counter) {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.tabIncrement();
        skeleton.Print(this,"Count("+counter.getClass().getSimpleName()+")");

        counter.Count(Iron.class);

        skeleton.tabDecrement();
    }
}
