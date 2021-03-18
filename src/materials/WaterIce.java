package materials;

import places.Asteroid;

public class WaterIce extends Material {
    @Override
    public void OnNearSun(Asteroid asteroid) {
        asteroid.RemoveMaterial();
    }
    @Override
    public void Count(MaterialCounter counter) {
        counter.Count(WaterIce.class);
    }
}
