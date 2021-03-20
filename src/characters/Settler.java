package characters;

import Skeleton.Skeleton;
import interfaces.Item;
import items.CraftingTable;
import items.Inventory;
import main.Game;
import materials.Coal;
import materials.Material;
import places.Asteroid;
import places.Place;
import places.TeleportGate;
import java.util.Scanner;

import java.util.List;

public class Settler extends Character {
    private Inventory inventory;

    public Settler() {
        inventory = new Inventory();
    }

    // Privát függvényhívások a szekvenciadiagromokon kívül mennek.
    private void die() {
        Game.getInstance().RemoveSteppable(this);
        Game.getInstance().RemoveSettler(this);
        asteroid.TakeOff(this);
    }

    public void HitByExplosion() {
        Skeleton.getInstance().tabIncrement();
        Skeleton.getInstance().Print(this, "HitByExplosion()");

        die();

        Skeleton.getInstance().tabDecrement();
    }

    @Override
    public void HitByStorm() {
        Skeleton.getInstance().tabIncrement();
        Skeleton.getInstance().Print(this, "HitByStorm()");

        die();

        Skeleton.getInstance().tabDecrement();
    }
    /// Eddig


    @Override
    public boolean Move() {
        Skeleton.getInstance().tabIncrement();
        Skeleton.getInstance().Print(this, "Mine()");

        List<Place> destinations = this.asteroid.getNeighbors();

        Skeleton.getInstance().Print(this,"Hanyadik uticélt választod? (0-"+(destinations.size()-1)+")");
        String input = Skeleton.getInstance().GetInput("Adj meg egy sorszámot: ");

        Scanner scan = new Scanner(input);
        int index = scan.nextInt();

        Place choosenDestination = destinations.get(index);
        Asteroid currentAsteroid = this.asteroid;

        if(choosenDestination.Move(this)){
            currentAsteroid.TakeOff(this);

            Skeleton.getInstance().tabDecrement();
            return true;
        }

        Skeleton.getInstance().tabDecrement();
        return false;
    }

    public boolean Mine() {
        Skeleton.getInstance().tabIncrement();
        Skeleton.getInstance().Print(this, "Mine()");

        Material material = asteroid.RemoveMaterial();

        if (material!=null){

            if (inventory.AddMaterial(material)) {

                asteroid.PlaceMaterial(material);
            }

            else {

                Skeleton.getInstance().tabDecrement();
                return true;
            }
        }

        Skeleton.getInstance().tabDecrement();
        return false;
    }

    public boolean CraftRobot() {
        Skeleton.getInstance().tabIncrement();
        Skeleton.getInstance().Print(this, "CraftRobot()");

        boolean ret = CraftingTable.getInstance().Craft(Robot.class, this);
        Skeleton.getInstance().tabDecrement();
        return ret;
    }

    public boolean CraftTeleportGates() {
        Skeleton.getInstance().tabIncrement();
        Skeleton.getInstance().Print(this, "CraftTeleportGates()");

        TeleportGate teleportGate = GetTeleportGate();
        if (teleportGate==null) {
            boolean ret = CraftingTable.getInstance().Craft(TeleportGate.class, this);

            Skeleton.getInstance().tabDecrement();
            return ret;
        }

        Skeleton.getInstance().tabDecrement();
        return false;
    }

    public boolean PlaceTeleportGate() {
        Skeleton.getInstance().tabIncrement();
        Skeleton.getInstance().Print(this, "PlaceTeleportGate()");

        // Itt már van vagy nincs kérdés?
        TeleportGate teleportGate = GetTeleportGate();

        if (teleportGate!=null){
            asteroid.PlaceTeleport(teleportGate);

            inventory.RemoveItem(teleportGate);

            Skeleton.getInstance().tabDecrement();
            return true;
        }

        Skeleton.getInstance().tabDecrement();
        return false;
    }

    public TeleportGate GetTeleportGate() {
        Skeleton.getInstance().tabIncrement();
        Skeleton.getInstance().Print(this,"GetTeleportGate()");
        /**
         * Invetory-nak a RemoveItem-je csak void-os, és azzal nem lehet dolgozni.
         * Ha meg boolean-es lenne az meg nem jó.
         * Mert kiveszed az inventory-ból, majd újra meghívod később a RemoveItem-et.
         *
         * Esetleg ha egy darabszámot adna vissza, akkor tudnánk a feladatleírásban lévő,
         * "max. 4 teleportkapu lehet egy telepesnél egyszerre" kitételt megvalósítani.
         * GetTeleportGateCount függvény teljesen megfelelne, csak nem OO.
         * De egy GetItem és egy RemoveItem talán még nem elvetendő.
         * A GetItem ad egy másolatot, amivel lehet dolgozni.
         * A RemoveItem meg eltávolítja az Itemet.
         *
         *      Bobó v2
         */

        // Ilyet szabad, mármint a kasztolás
        TeleportGate retTeleportGate = (TeleportGate) this.inventory.GetItem(TeleportGate.class);

        Skeleton.getInstance().tabDecrement();

        return retTeleportGate;
    }

    public Inventory GetInventory(){
        Skeleton.getInstance().tabIncrement();
        Skeleton.getInstance().Print(this,"GetInventory()");

        Skeleton.getInstance().tabDecrement();
        return inventory;
    }

    public Asteroid GetAsteroid(){
        Skeleton.getInstance().tabIncrement();
        Skeleton.getInstance().Print(this,"GetAsteroid()");

        Skeleton.getInstance().tabDecrement();
        return asteroid;
    }

    public boolean PlaceMaterial(Material material) {
        Skeleton.getInstance().tabIncrement();
        Skeleton.getInstance().Print(this,"PlaceMaterial("+material.getClass().getSimpleName()+")");

        Material pickedMaterial = inventory.RemoveMaterial(material);
        if (pickedMaterial!=null){
            if (!asteroid.PlaceMaterial(pickedMaterial)){
                inventory.AddMaterial(pickedMaterial);

                Skeleton.getInstance().tabDecrement();
                return false;
            }

            Skeleton.getInstance().tabDecrement();
            return true;
        }

        Skeleton.getInstance().tabDecrement();
        return false;
    }

    public boolean Drill() {
        Skeleton.getInstance().tabIncrement();
        Skeleton.getInstance().Print(this,"Drill()");

        boolean ret = asteroid.Drilled();
        System.out.println(ret);
        Skeleton.getInstance().tabDecrement();
        return ret;
    }

    public void AddItem(Item item) {
        inventory.AddItem(item);
    }

    @Override
    public void Step() {
        /**
         * Menü kerül ide. De nem a Skeleton-ban, mert itt nincs Step, csak a UseCase-ek.
         * Ez lenne a Use-Case vezérelte rész.
         */

        int input = (int)(Math.random()*7);  // Ez a lényege majd a menünek. Jelenleg nem használjuk ezt a függvényt egyáltalán.


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
