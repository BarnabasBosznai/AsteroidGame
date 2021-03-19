package characters;

import items.CraftingTable;
import items.Inventory;
import main.Game;
import materials.Material;
import places.Asteroid;
import places.Place;
import places.TeleportGate;
import java.util.Scanner;

import java.util.List;

public class Settler extends Character {
    private Inventory inventory;


    // Privát függvényhívások a szekvenciadiagromokon kívül mennek.
    private void die() {
        Game.getInstance().RemoveSteppable(this);
        Game.getInstance().RemoveSettler(this);
        asteroid.TakeOff(this);
    }

    public void HitByExplosion() {
        die();
    }

    @Override
    public void HitByStorm() {
        die();
    }
    /// Eddig


    @Override
    public boolean Move() {
        List<Place> destinations = this.asteroid.getNeighbors();

        /**
         * Ezt a menüben ábrázolni kell.
         * Hogy tudni lehessen, hova lehet menni.
         */
        Place choosenDestination = destinations.get(0);
        Asteroid currentAsteroid = this.asteroid;

        if(choosenDestination.Move(this)){
            currentAsteroid.TakeOff(this);

            return true;
        }

        return false;
    }

    public boolean Mine() {
        Material material = asteroid.RemoveMaterial();

        if (material!=null){

            if (inventory.AddMaterial(material)) {

                asteroid.PlaceMaterial(material);
            }

            else {
                return true;
            }
        }
        return false;
    }

    public boolean CraftRobot() {

        return CraftingTable.getInstance().Craft(Robot.class, this);
    }

    public boolean CraftTeleportGates() {
        TeleportGate teleportGate = GetTeleportGate();
        if (teleportGate==null)
            return CraftingTable.getInstance().Craft(TeleportGate.class, this);
        return false;
    }

    public boolean PlaceTeleportGate() {
        TeleportGate teleportGate = GetTeleportGate();

        if (teleportGate!=null){
            asteroid.PlaceTeleport(teleportGate);

            inventory.RemoveItem(teleportGate);

            return true;
        }

        return false;
    }

    public TeleportGate GetTeleportGate() {
        /**
         * Ez még jó kérdés.
         * Jelenleg ilyen kérést nem tudok intézni az Inventory felé.
         *
         * Invetory-nak a RemoveItem-je csak void-os, és azzal nem lehet dolgozni.
         * Ha meg boolean-es lenne az meg nem jó.
         * Mert kiveszed az inventory-ból, majd újra meghívod később a RemoveItem-et.
         *
         * A RemoveItem arra épít, hogy tudom már, hogy van teleportGate-m.
         * Ezért kell valami más, amivel megkérdem az Inventory-t, hogy van-e.
         * Esetleg ha egy darabszámot adna vissza, akkor tudnánk a feladatleírásban lévő,
         * "max. 4 teleportkapu lehet egy telepesnél egyszerre" kitételt megvalósítani.
         * GetTeleportGateCount függvény teljesen megfelelne, csak nem OO.
         * De egy GetItem és egy RemoveItem talán még nem elvetendő.
         * A GetItem ad egy másolatot, amivel lehet dolgozni.
         * A RemoveItem meg eltávolítja az Itemet.
         *
         *      Bobó
         */

        // Ez csak a kód lefutásához kell, jelenleg rossz.
        return new TeleportGate();
    }

    public Inventory GetInventory(){
        return inventory;
    }

    public Asteroid GetAsteroid(){
        return asteroid;
    }

    public boolean PlaceMaterial(Material material) {
        Material pickedMaterial = inventory.RemoveMaterial(material);
        if (pickedMaterial!=null){
            if (!asteroid.PlaceMaterial(pickedMaterial)){
                inventory.AddMaterial(pickedMaterial);
                return false;
            }
            return true;
        }

        return false;
    }

    public boolean Drill() {
        return asteroid.Drilled();
    }

    @Override
    public void Step() {
        /**
         * Menü kerül ide.
         * Ez lenne a Use-Case vezérelte rész.
         * Ezt megvalósítom szívesen magam is, csak ne legyen ez probléma később.
         */

        int input = (int)(Math.random()*7);  // Ez a lényege majd a menünek.


        boolean failed = true;
        while (failed) {
            switch (input) {
                case (1): // Mozgás
                    failed = Move();
                    break;
                case (2): // Bányászás
                    failed = Mine();
                    break;
                case (3): // Fúrás
                    failed = Drill();
                    break;
                case (4): // Build Robot
                    failed = CraftRobot();
                    break;
                case (5): // Build Teleport
                    // Benne maradt a doksiban egy material paraméter
                    failed = CraftTeleportGates();
                    break;
                case (6): // Place Material
                    // Vagyis tudja a halál, de ez is a menüből kéne kiválasztani.
                    //Material choosenMaterial = new Coal();
                    //failed = PlaceMaterial(choosenMaterial);
                    break;
                case (7): // Install Teleport
                    failed = PlaceTeleportGate();
                    break;
            }
        }
    }
}
