package characters;

import Skeleton.Skeleton;
import interfaces.Item;

public class Robot extends Character implements Item {
    @Override
    public void Step() {
        Skeleton.getInstance().tabIncrement();
        Skeleton.getInstance().Print(this, "Step()");

        ControlRobot();

        Skeleton.getInstance().tabDecrement();
    }


    public void HitByExplosion() {
        Skeleton.getInstance().tabIncrement();
        Skeleton.getInstance().Print(this, "HitByExplosion()");

        while(!Move());

        Skeleton.getInstance().tabDecrement();
    }

    private void ControlRobot() {
        Skeleton.getInstance().tabIncrement();
        Skeleton.getInstance().Print(this, "ControlRobot()");

        if (asteroid.Drilled()){
            while(!Move());
        }

        Skeleton.getInstance().tabDecrement();
    }

    @Override
    public boolean CompatibleWith(Item item) {
        Skeleton.getInstance().tabIncrement();
        Skeleton.getInstance().Print(this, "CompatibleWith("+item.getClass().getSimpleName()+")");

        Skeleton.getInstance().tabDecrement();
        return this.getClass() == item.getClass();
    }
}
