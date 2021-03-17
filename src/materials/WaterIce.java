package materials;

public class WaterIce extends Material {
    @Override
    public void count(MaterialCounter counter) {

    }

    @Override
    public boolean compatibleWith(Material material) {
        return false;
    }
}
