package materials;

import Skeleton.Skeleton;

public class Uranium extends RadioactiveMaterial {
    @Override
    public void Count(MaterialCounter counter) {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.tabIncrement();
        skeleton.Print(this,"Count(MaterialCounter counter)");

        counter.Count(Uranium.class);

        skeleton.tabDecrement();
    }
}
