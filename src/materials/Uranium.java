package materials;

import Skeleton.Skeleton;

public class Uranium extends RadioactiveMaterial {
    @Override
    public void Count(MaterialCounter counter) {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.tabIncrement();
        skeleton.Print(this,"Count(" +counter.getClass().getSimpleName()+ ")");

        counter.Count(Uranium.class);

        skeleton.tabDecrement();
    }
}
