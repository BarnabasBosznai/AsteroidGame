package materials;

import places.Asteroid;

public class WaterIce extends Material {
    @Override
    public void onNearSun(Asteroid asteroid) {
        asteroid.removeMaterial();
    }
    @Override
    public void count(MaterialCounter counter) {

    }

    @Override
    public boolean compatibleWith(Material material) {
        if(this.getClass()==material.getClass()){
            return true;
        }
        else {return false;}
    }
}
