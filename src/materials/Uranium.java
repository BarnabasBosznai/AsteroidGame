package materials;

public class Uranium extends RadioactiveMaterial {
    @Override
    public void Count(MaterialCounter counter) {
        counter.Count(Uranium.class);
    }
}
