package materials;

public class Coal extends Material {
    @Override
    public void count(MaterialCounter counter) {

    }

    @Override
    public boolean compatibleWith(Material material) {
        return false;
    }
}
