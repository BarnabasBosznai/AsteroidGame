package materials;

import places.Asteroid;

public abstract class Material {
    public void OnNearSun(Asteroid asteroid) {

    }

    public abstract void Count(MaterialCounter counter);

    public boolean CompatibleWith(Material material) {
        return this.getClass() == material.getClass();
    }
}
