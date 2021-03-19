package materials;


import  Skeleton.Skeleton;

public class Coal extends Material {
    @Override
    public void Count(MaterialCounter counter) {
        Skeleton.getInstance().tabIncrement();
        Skeleton.getInstance().Print(this,"Count()");
        Skeleton.getInstance().tabDecrement();
        counter.Count(Coal.class);
    }
}
