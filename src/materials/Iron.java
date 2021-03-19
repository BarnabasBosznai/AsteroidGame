package materials;

import Skeleton.Skeleton;

public class Iron extends Material {
    @Override
    public void Count(MaterialCounter counter) {
        Skeleton.getInstance().tabIncrement();
        Skeleton.getInstance().Print(this,"Count()");
        Skeleton.getInstance().tabDecrement();
        counter.Count(Iron.class);
    }
}
