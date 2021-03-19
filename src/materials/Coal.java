package materials;


import  Skeleton.Skeleton;

public class Coal extends Material {
    @Override
    public void Count(MaterialCounter counter) {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.tabIncrement();
        skeleton.Print(this,"Count(MaterialCounter counter)");

        counter.Count(Coal.class);

        skeleton.tabDecrement();
    }
}
