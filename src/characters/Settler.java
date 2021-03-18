package characters;

import items.CraftingTable;
import items.Inventory;
import main.Game;
import materials.Material;
import places.Asteroid;
import places.Place;
import places.TeleportGate;

import java.util.List;

public class Settler extends Character {
    private Inventory inventory;

    @Override
    public void HitByExplosion() {
        Game.getInstance().removeSteppable(this);
        Game.getInstance().removeSettler(this);
    }

    @Override
    public void HitByStorm() {
        super.HitByStorm();
    }

    @Override
    public boolean Move() {
        List<Place> places = asteroid.getNeighbors();

        /**
         *  Ki kell választani, hogy melyik legyen a sok közül.
         *  Most csak az első lesz mindig kiválasztva.
         */

        Place choosen_place = places.get(0);

        Boolean success = choosen_place.move(this);

        if (success) {
            asteroid.takeOff(this);

            /**
             * Baj van talán!
             * Honnan szüljek neki Asteroid-ot, és nem csak egy Place-t?
             * Lehet ildomosabb lenne egy asteroidot visszaadni, vagy NULL-t.
             *      Bobó :(
             *
             * Asteroid állítja be a paraméterként kapott Settler helyét.
             * De ez meg faszság, mert utána takeOff-olok róla.
             * Szóval rossz. Kell egy visszaadott Asteroid.
             *
             *      Bobó :\
             */

            return true;
        }

        return false;
        //return super.move();
    }

    @Override
    public void setAsteroid(Asteroid asteroid) {
    }

    public boolean Mine() {

        Material material = asteroid.removeMaterial();

        if (material!=null){
            Boolean failed = inventory.addMaterial(material);

            if (failed) {
                asteroid.placeMaterial(material);
                return false;
            }

            return true;
        }

        return false;
    }


    public boolean CraftRobot() {
        return CraftingTable.getInstance().Craft(Robot.class, this);
    }

    public boolean CraftTeleportGates() {
        return CraftingTable.getInstance().Craft(TeleportGate.class, this);
    }

    public boolean PlaceTeleportGate() {

         return false;
    }

    public TeleportGate GetTeleportGate() {

        return null;
    }

    public Inventory GetInventory(){
        return this.inventory;
    }

    public Asteroid GetAsteroid(){
        return asteroid;
    }

    public boolean PlaceMaterial(Material material) {
        Material material_got = inventory.removeMaterial(material);

        // Még hátra
        return false;
    }

    public boolean Drill() {
        return asteroid.Drilled();
    }

    @Override
    public void Step() {

    }
}
