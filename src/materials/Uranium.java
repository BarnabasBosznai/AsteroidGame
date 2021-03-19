package materials;

import Skeleton.Skeleton;

public class Uranium extends RadioactiveMaterial {
    @Override
    public void Count(MaterialCounter counter) {

        Skeleton.getInstance().tabIncrement();
        Skeleton.getInstance().Print(this,"Count()");
        Skeleton.getInstance().tabDecrement();
        counter.Count(Uranium.class);
    }
}
