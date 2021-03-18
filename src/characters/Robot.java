package characters;

import interfaces.Item;

public class Robot extends Character implements Item {
    @Override
    public void Step() {

    }

    @Override
    public void HitByExplosion() {
        super.HitByExplosion();
    }

    private void ControlRobot() {

    }

    @Override
    public boolean CompatibleWith(Item item) {
        return this.getClass() == item.getClass();
    }
}
