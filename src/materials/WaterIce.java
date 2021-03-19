package materials;

import Skeleton.Skeleton;
import places.Asteroid;

public class WaterIce extends Material {
    @Override
    public void OnNearSun(Asteroid asteroid) {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.tabIncrement();
        skeleton.Print(this,"OnNearSun(Asteroid asteroid)");

        asteroid.RemoveMaterial();

        skeleton.tabDecrement();
    }
    @Override
    public void Count(MaterialCounter counter) {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.tabIncrement();
        skeleton.Print(this,"Count(MaterialCounter counter)");

        counter.Count(WaterIce.class);

        skeleton.tabDecrement();
    }
}
