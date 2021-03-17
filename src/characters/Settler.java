package characters;

import items.CraftingTable;
import items.Inventory;
import materials.Material;
import places.Asteroid;
import places.TeleportGate;

public class Settler extends Character {
    private Inventory inventory;

    /// HAlihó

    @Override
    public void HitByExplosion() {
        super.HitByExplosion();
    }

    @Override
    public void HitByStorm() {
        super.HitByStorm();
    }

    @Override
    public boolean move() {
        return super.move();
    }

    public boolean mine() {

    }

    public boolean craftRobot() {

        return CraftingTable.getInstance().craft(Robot.class, this)
    }

    public boolean craftTeleportGates() {

        return CraftingTable.getInstance().craft(TeleportGate.class, this);
    }

    public boolean placeTeleportGate() {

    }

    public TeleportGate getTeleportGate() {

    }

    public Inventory getInventory(){
        return this.inventory;
    }

    public Asteroid getAsteroid(){
        return asteroid;
    }

    public boolean placeMaterial(Material material) {

    }

    public boolean drill() {

    }

    @Override
    public void step() {

    }
}
