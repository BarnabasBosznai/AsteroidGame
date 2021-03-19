package materials;

import Skeleton.Skeleton;
import places.Asteroid;

public abstract class Material {
    public void OnNearSun(Asteroid asteroid) {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.tabIncrement();
        skeleton.Print(this,"OnNearSun(Asteroid asteroid)");

        skeleton.tabDecrement();
    }

    public abstract void Count(MaterialCounter counter);

    public boolean CompatibleWith(Material material) {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.tabIncrement();
        skeleton.Print(this,"CompatibleWith("+material.getClass().getSimpleName()+")");

        skeleton.tabDecrement();

        return this.getClass() == material.getClass();


    }
}
