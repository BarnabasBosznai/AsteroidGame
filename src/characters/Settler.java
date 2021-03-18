package characters;

import items.CraftingTable;
import items.Inventory;
import materials.Material;
import places.Asteroid;
import places.TeleportGate;

public class Settler extends Character {
    private Inventory inventory;

    /// HAlihó

    /// Tesz1

    @Override
    public void HitByExplosion() {
        super.HitByExplosion();
    }

    @Override
    public void HitByStorm() {
        super.HitByStorm();
    }

    @Override
    public boolean Move() {
        return super.Move();
    }

    public boolean Mine() {

    }

    public boolean CraftRobot() {

        return CraftingTable.getInstance().Craft(Robot.class, this);
    }

    public boolean CraftTeleportGates() {

        return CraftingTable.getInstance().Craft(TeleportGate.class, this);
    }

    public boolean PlaceTeleportGate() {

    }

    public TeleportGate GetTeleportGate() {

    }

    public Inventory GetInventory(){
        return this.inventory;
    }

    public Asteroid GetAsteroid(){
        return asteroid;
    }

    public boolean PlaceMaterial(Material material) {

    }

    public boolean Drill() {

    }

    @Override
    public void Step() {

    }
}
