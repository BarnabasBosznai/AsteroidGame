package materials;

public class Iron extends Material {
    @Override
    public void Count(MaterialCounter counter) {
        counter.Count(Iron.class);
    }
}
