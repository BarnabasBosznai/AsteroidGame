package materials;

public class Uranium extends RadioactiveMaterial {
    @Override
    public void count(MaterialCounter counter) {

    }

    @Override
    public boolean compatibleWith(Material material) {
        return false;
    }
}
