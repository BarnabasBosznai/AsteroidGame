package materials;

import places.Asteroid;

public abstract class RadioactiveMaterial extends Material {
    @Override
    public void OnNearSun(Asteroid asteroid) {
        asteroid.Explosion();
    }
}
