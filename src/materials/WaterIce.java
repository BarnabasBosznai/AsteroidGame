package materials;

import places.Asteroid;

public class WaterIce extends Material {
    @Override
    public void onNearSun(Asteroid asteroid) {
        asteroid.removeMaterial();
    }
    @Override
    public void count(MaterialCounter counter) {
        counter.count("waterice");
    }
}
