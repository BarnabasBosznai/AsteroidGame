package characters;

import interfaces.Item;
import items.CraftingTable;
import items.Inventory;
import main.Game;
import materials.Material;
import places.Asteroid;
import places.Place;
import places.TeleportGate;
import java.util.Scanner;

import java.util.List;

/**
 * A telepeseket reprezentáló osztály. A játékos velük interaktál közvetlenül
 */
public class Settler extends MiningCharacter {
    /**
     * A játékos inventoryja, amiben a nála található dolgokat tárolja.
     */
    private Inventory inventory;

    /**
     * Settler konstruktora
     */
    public Settler() {
        inventory = new Inventory();
    }

    /**
     * Settler "destruktora"
     */
    // Privát függvényhívások a szekvenciadiagromokon kívül mennek.
    private void die() {
        Game.getInstance().RemoveSteppable(this);
        Game.getInstance().RemoveSettler(this);
        asteroid.TakeOff(this);
    }

    /**
     * Lereagálja, hogy az aszteroida, amin a telepes éppen
     * tartózkodik felrobbant. Törli magát mindenhonnan, ahol nyilván volt tartva.
     */
    public void HitByExplosion() {
        die();
    }

    /**
     * Lereagálja, hogy az aszteroida, amin a telepes éppen tartózkodik
     * napviharba került. Törli magát mindenhonnan, ahol nyilván volt tartva.
     */
    @Override
    public void HitByStorm() {
        die();
    }

    /**
     * A játékos mozgás metódusa, ami egyik aszteroidáról egy másikra
     * viszi át.Ha sikerült akkor true,ha nem akkor false.
     * @return
     */
    /*@Override
    public boolean Move() {
        List<Place> destinations = this.asteroid.GetNeighbors();

        System.out.println("Hanyadik uticélt választod? (0-"+(destinations.size() - 1)+")");
        System.out.println("Adj meg egy sorszámot: ");
        Scanner scan = new Scanner(System.in);
        int index = scan.nextInt();

        Place choosenDestination = destinations.get(index);
        Asteroid currentAsteroid = this.asteroid;

        if(choosenDestination.Move(this)){
            currentAsteroid.TakeOff(this);

            return true;
        }

        return false;
    }*/
    @Override
    public boolean Move(Place place) {
        Asteroid currentAsteroid = this.asteroid;
        if(place.Move(this)){
            currentAsteroid.TakeOff(this);
            return true;
        }
        return false;
    }

    /*
    /**
     * A telepes megpróbálja kinyeri az aszteroidában található
     * nyersanyagot. Ha az aszteroida magja üres, akkor False a visszatérési érték, ha sikerült
     * a bányászás, akkor True
     * @return

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
    }*/

    public boolean Mine() {
        return this.Mine(inventory);
    }

    /**
     * A telepes ezzel készít robotot. Ha sikerült a craftolás, a
     * visszatérési érték True, egyébként False.
     * @return
     */
    public boolean CraftRobot() {
        return CraftingTable.getInstance().Craft(Robot.class, this);
    }

    /**
     * A telepes ezzel készít teleportkapupárt. Ha sikerült a
     * craftolás, a visszatérési érték True, egyébként False.
     * @return
     */
    /*NEW*/
    public boolean CraftTeleportGates() {
        //TeleportGate teleportGate = GetTeleportGate();

        int numberOfTeleportGates = inventory.GetNumberOfItems(TeleportGate.class);
        if (numberOfTeleportGates <= 1) {
            return CraftingTable.getInstance().Craft(TeleportGate.class, this);
        }

        return false;
    }

    /**
     *  A telepes megpróbálja elhelyezni a nála található
     * teleportkapupár egyik tagját az aszteroidáján. Ha sikerrel járt, a visszatérési érték
     * True, egyébként False.
     * @return
     */
    public boolean PlaceTeleportGate() {
        TeleportGate teleportGate = GetTeleportGate();
        if (teleportGate!=null){
            asteroid.PlaceTeleport(teleportGate);
            inventory.RemoveItem(teleportGate);
            return true;
        }

        return false;
    }

    /**
     * Visszatér egy a telepesnél található teleportkapuval.
     * @return
     */
    public TeleportGate GetTeleportGate() {
        return (TeleportGate) this.inventory.GetItem(TeleportGate.class);
    }

    /**
     * Inventory gettere
     * @return
     */
    public Inventory GetInventory(){
        return inventory;
    }

    /**
     * Aktuális tartozkodási hely gettere
     * @return
     */
    public Asteroid GetAsteroid(){
        return asteroid;
    }

    /**
     * Elhelyezi az aszteroida magjába a
     * nyersanyagot tárolás céljából. Ha nem üres az aszteroida magja akkor false a
     * visszatérési érték, ha sikeresen lehelyeztük akkor true.
     * @param material
     * @return
     */
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

    /**
     * Eggyel csökkenti az aszteroida köpenyének rétegét.True a
     * visszatérési érték ha sikeresen végrehajtódott a művelet,false ha nem lehet tovább
     * fúrni az aszteroidán mivel nincs már rajta köpeny réteg.
     * @return
     */
    public boolean Drill() {
        return asteroid.Drilled();
    }

    /**
     * Az inventoryhoz hozzá ad egy itemet.
     * @param item
     */
    public void AddItem(Item item) {
        inventory.AddItem(item);
    }

    /**
     * A telepes lépésének végrehajtása. Itt választhat a játékos a végezhető
     * tevékenységek közül (pl: move, drill, placeteleport, stb.)
     */
    @Override
    public void Step() {

       /* int input = (int)(Math.random()*7);  // Ez a lényege majd a menünek. Jelenleg nem használjuk ezt a függvényt egyáltalán.


        boolean failed = true;
        while (failed) {
            switch (input) {
                case (1): // Mozgás
                    failed = Move();
                    break;
                case (2): // Bányászás
                    failed = Mine(inventory);
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
        }*/
    }
}
