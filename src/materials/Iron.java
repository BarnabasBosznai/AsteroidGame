package materials;

import Skeleton.Skeleton;

public class Iron extends Material {
    @Override
    public void Count(MaterialCounter counter) {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.tabIncrement();
        skeleton.Print(this,"Count("+counter.getClass().getSimpleName()+")");

        counter.Count(Iron.class);

        skeleton.tabDecrement();
    }
}
