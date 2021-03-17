package materials;

public class Iron extends Material {
    @Override
    public void count(MaterialCounter counter) {
        counter.count("iron");
    }
}
