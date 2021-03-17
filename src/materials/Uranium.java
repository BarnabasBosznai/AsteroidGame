package materials;

public class Uranium extends RadioactiveMaterial {
    @Override
    public void count(MaterialCounter counter) {
        counter.count("uranium");
    }
}
