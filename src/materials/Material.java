package materials;

import places.Asteroid;

public abstract class Material {
    public void onNearSun(Asteroid asteroid) {

    }

    public abstract void count(MaterialCounter counter);

    public boolean compatibleWith(Material material) {
        if (this.getClass() == material.getClass()) {
            return true;
        } else {
            return false;
        }
    }
}
