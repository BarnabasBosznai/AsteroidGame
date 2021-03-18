package materials;

public class Coal extends Material {
    @Override
    public void Count(MaterialCounter counter) {
        counter.Count(Coal.class);
    }
}
