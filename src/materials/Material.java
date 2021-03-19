package materials;

import Skeleton.Skeleton;
import places.Asteroid;

public abstract class Material {
    public void OnNearSun(Asteroid asteroid) {
        Skeleton.getInstance().tabIncrement();
        Skeleton.getInstance().Print(this,"OnNearSun()");
        Skeleton.getInstance().tabDecrement();
    }

    public abstract void Count(MaterialCounter counter);

    public boolean CompatibleWith(Material material) {
        Skeleton.getInstance().tabIncrement();
        Skeleton.getInstance().Print(this,"CompatibleWith()");
        Skeleton.getInstance().tabDecrement();
        return this.getClass() == material.getClass();
    }
}
