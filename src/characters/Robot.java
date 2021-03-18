package characters;

import interfaces.Item;

public class Robot extends Character implements Item {
    @Override
    public void Step() {
        ControlRobot();
    }


    public void HitByExplosion() {
        while(!Move());
    }

    private void ControlRobot() {
        if (asteroid.Drilled()){
            while(!Move());
        }
    }

    @Override
    public boolean CompatibleWith(Item item) {
        return this.getClass() == item.getClass();
    }
}
