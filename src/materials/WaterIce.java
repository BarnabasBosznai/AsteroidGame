package materials;

import Skeleton.Skeleton;
import places.Asteroid;

public class WaterIce extends Material {
    @Override
    public void OnNearSun(Asteroid asteroid) {
        Skeleton.getInstance().tabIncrement();
        Skeleton.getInstance().Print(this,"OnNearSun()");
        Skeleton.getInstance().tabDecrement();
        asteroid.RemoveMaterial();
    }
    @Override
    public void Count(MaterialCounter counter) {
        Skeleton.getInstance().tabIncrement();
        Skeleton.getInstance().Print(this,"Count()");
        Skeleton.getInstance().tabDecrement();
        counter.Count(WaterIce.class);
    }
}
