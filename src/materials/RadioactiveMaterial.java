package materials;

import Skeleton.Skeleton;
import places.Asteroid;

public abstract class RadioactiveMaterial extends Material {
    @Override
    public void OnNearSun(Asteroid asteroid) {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.tabIncrement();
        skeleton.Print(this,"OnNearSun("+asteroid.getClass().getSimpleName()+")");

        asteroid.Explosion();

        skeleton.tabDecrement();
    }
}
