package materials;

import Skeleton.Skeleton;
import places.Asteroid;

/**
 * A vízjég az egyik nyersanyag amit ki lehet bányászni a játékban. Napközelben és teljesen megfúrt
 * aszteroidán elpárolog.
 */
public class WaterIce extends Material {
    /**
     * Lereagálja, hogy a nyersanyagot tartalmazó aszteroida napközelbe került.
     * @param asteroid
     */
    @Override
    public void OnNearSun(Asteroid asteroid) {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.tabIncrement();
        skeleton.Print(this,"OnNearSun("+asteroid.getClass().getSimpleName()+")");

        asteroid.RemoveMaterial();

        skeleton.tabDecrement();
    }

    /**
     * Növeli a paraméterként kapott számlálóban a típusához tartozó értéket.
     * @param counter
     */
    @Override
    public void Count(MaterialCounter counter) {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.tabIncrement();
        skeleton.Print(this,"Count("+counter.getClass().getSimpleName()+")");

        counter.Count(WaterIce.class);

        skeleton.tabDecrement();
    }
}
