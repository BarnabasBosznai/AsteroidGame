package characters;

import Skeleton.Skeleton;
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
public class Settler extends Character {
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
        Skeleton.getInstance().tabIncrement();
        Skeleton.getInstance().Print(this, "HitByExplosion()");

        die();

        Skeleton.getInstance().tabDecrement();
    }

    /**
     * Lereagálja, hogy az aszteroida, amin a telepes éppen tartózkodik
     * napviharba került. Törli magát mindenhonnan, ahol nyilván volt tartva.
     */
    @Override
    public void HitByStorm() {
        Skeleton.getInstance().tabIncrement();
        Skeleton.getInstance().Print(this, "HitByStorm()");

        die();

        Skeleton.getInstance().tabDecrement();
    }
    /// Eddig

    /**
     * A játékos mozgás metódusa, ami egyik aszteroidáról egy másikra
     * viszi át.Ha sikerült akkor true,ha nem akkor false.
     * @return
     */
    @Override
    public boolean Move() {
        Skeleton.getInstance().tabIncrement();
        Skeleton.getInstance().Print(this, "Mine()");

        List<Place> destinations = this.asteroid.GetNeighbors();

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

    /**
     * A telepes megpróbálja kinyeri az aszteroidában található
     * nyersanyagot. Ha az aszteroida magja üres, akkor False a visszatérési érték, ha sikerült
     * a bányászás, akkor True
     * @return
     */
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

    /**
     * A telepes ezzel készít robotot. Ha sikerült a craftolás, a
     * visszatérési érték True, egyébként False.
     * @return
     */
    public boolean CraftRobot() {
        Skeleton.getInstance().tabIncrement();
        Skeleton.getInstance().Print(this, "CraftRobot()");

        boolean ret = CraftingTable.getInstance().Craft(Robot.class, this);
        Skeleton.getInstance().tabDecrement();
        return ret;
    }

    /**
     * A telepes ezzel készít teleportkapupárt. Ha sikerült a
     * craftolás, a visszatérési érték True, egyébként False.
     * @return
     */
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

    /**
     *  A telepes megpróbálja elhelyezni a nála található
     * teleportkapupár egyik tagját az aszteroidáján. Ha sikerrel járt, a visszatérési érték
     * True, egyébként False.
     * @return
     */
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

    /**
     * Visszatér egy a telepesnél található teleportkapuval.
     * @return
     */
    public TeleportGate GetTeleportGate() {
        Skeleton.getInstance().tabIncrement();
        Skeleton.getInstance().Print(this,"GetTeleportGate()");

        TeleportGate retTeleportGate = (TeleportGate) this.inventory.GetItem(TeleportGate.class);

        Skeleton.getInstance().tabDecrement();

        return retTeleportGate;
    }

    /**
     * Inventory gettere
     * @return
     */
    public Inventory GetInventory(){
        Skeleton.getInstance().tabIncrement();
        Skeleton.getInstance().Print(this,"GetInventory()");

        Skeleton.getInstance().tabDecrement();
        return inventory;
    }

    /**
     * Aktuális tartozkodási hely gettere
     * @return
     */
    public Asteroid GetAsteroid(){
        Skeleton.getInstance().tabIncrement();
        Skeleton.getInstance().Print(this,"GetAsteroid()");

        Skeleton.getInstance().tabDecrement();
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

    /**
     * Eggyel csökkenti az aszteroida köpenyének rétegét.True a
     * visszatérési érték ha sikeresen végrehajtódott a művelet,false ha nem lehet tovább
     * fúrni az aszteroidán mivel nincs már rajta köpeny réteg.
     * @return
     */
    public boolean Drill() {
        Skeleton.getInstance().tabIncrement();
        Skeleton.getInstance().Print(this,"Drill()");

        boolean ret = asteroid.Drilled();
        System.out.println(ret);
        Skeleton.getInstance().tabDecrement();
        return ret;
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
