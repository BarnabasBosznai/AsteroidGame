package characters;

import interfaces.Item;

public class Robot extends Character implements Item {
    @Override
    public void step() {

    }

    @Override
    public void HitByExplosion() {
        //super.HitByExplosion();
    }

    private void controlRobot() {

    }

    @Override
    public boolean compatibleWith(Item item) {
        return this.getClass() == item.getClass();
    }
}
