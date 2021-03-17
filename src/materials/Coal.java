package materials;

public class Coal extends Material {
    @Override
    public void count(MaterialCounter counter) {
        counter.count("coal");
    }
}
