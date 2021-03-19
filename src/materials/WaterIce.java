package materials;

import Skeleton.Skeleton;
import places.Asteroid;

public class WaterIce extends Material {
    @Override
    public void OnNearSun(Asteroid asteroid) {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.tabIncrement();
        skeleton.Print(this,"OnNearSun("+asteroid.getClass().getSimpleName()+")");

        asteroid.RemoveMaterial();

        skeleton.tabDecrement();
    }
    @Override
    public void Count(MaterialCounter counter) {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.tabIncrement();
        skeleton.Print(this,"Count("+counter.getClass().getSimpleName()+")");

        counter.Count(WaterIce.class);

        skeleton.tabDecrement();
    }
}
