package places;

import Skeleton.Skeleton;
import characters.Character;
import materials.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Az Asteroid osztály egy aszteroidát reprezentál, ami karakterek gyűjtőhelye. Az aszteroidán lévő karakterek
 * különböző műveletek végzésére képesek.
 */
public class Asteroid extends Place {
    private int thickness;
    private Material material;
    private List<Asteroid> neighbors;
    private List<TeleportGate> teleportGates;
    private List<Character> characters;

    public Asteroid() {
        neighbors = new ArrayList<>();
        teleportGates = new ArrayList<>();
        characters = new ArrayList<>();

        Random random = new Random();
        switch (random.nextInt(4)) {
            case 0: material = new Coal(); break;
            case 1: material = new Iron(); break;
            case 2: material = new Uranium(); break;
            case 3: material = new WaterIce(); break;
        }

        thickness = random.ints(2,6).findFirst().getAsInt();
    }

    /**
     * Csökkenti az aszteroida vastagságát.
     * @return True-val tér vissza, ha a fúrás sikerrel járt(nem volt még teljesen lefúrva az aszteroida),
     * False-al, ha nem(az aszteroida köpenye már teljesen át volt fúrva).
     */
    public boolean Drilled() {
        Skeleton instance = Skeleton.getInstance();
        instance.tabIncrement();
        instance.Print(this, "Drilled()");
        boolean res = instance.GetInput("Az aszteroida le van fúrva? [I/N]: ").equalsIgnoreCase("n");
        instance.tabDecrement();
        return !res;
    }

    /**
     * Kiveszi az aszteroidában található nyersanyagot.
     * @return Ha az aszteroida magjában van nyersanyag, visszatér a nyersanyaggal, ha nem volt, null-al.
     */
    public Material RemoveMaterial() {
        Skeleton instance = Skeleton.getInstance();
        instance.tabIncrement();
        instance.Print(this, "RemoveMaterial()");
        if(instance.GetInput("Az aszteroida le van fúrva? [I/N]: ").equalsIgnoreCase("i")) {
            if (instance.GetInput("Az aszteroidában van nyersanyag? [I/N]: ").equalsIgnoreCase("i")) {
                Material ret = material;
                material = null;
                instance.tabDecrement();
                return ret;
            }
        }
        instance.tabDecrement();
        return null;
    }

    /**
     * Napvihart kelt az aszteroidán. Meghívja az aszteroidán található karaktereken a saját HitByStorm metódusukat.
     */
    public void SolarFlare() {
        Skeleton instance = Skeleton.getInstance();
        instance.tabIncrement();
        instance.Print(this, "SolarFlare()");

        if(!instance.GetInput("Az aszteroida teljesen le van fúrva? [I/N]: ").equalsIgnoreCase("i")) {
            int n = characters.size();
            for(int i = 0; i < n; i++)
                characters.get(0).HitByStorm();

            instance.tabDecrement();
            return;
        }

        if(!instance.GetInput("Az aszteroida üreges? [I/N]: ").equalsIgnoreCase("i")) {
            int n = characters.size();
            for(int i = 0; i < n; i++)
                characters.get(0).HitByStorm();


        }
        instance.tabDecrement();
    }

    public void TakeOff(Character character) {
        Skeleton instance = Skeleton.getInstance();
        instance.tabIncrement();
        instance.Print(this, "TakeOff("+ character.getClass().getSimpleName() +")");

        this.characters.remove(character);

        instance.tabDecrement();
    }

    /**
     * Elhelyez egy teleportkaput az aszteroidán. Beállítja a teleportgate asteroid attribútumának saját magát.
     * @param teleportGate
     */
    public void PlaceTeleport(TeleportGate teleportGate) {
        Skeleton instance = Skeleton.getInstance();
        instance.tabIncrement();
        instance.Print(this, "PlaceTeleport(TeleportGate)");

        this.teleportGates.add(teleportGate);
        teleportGate.SetAsteroid(this);

        instance.tabDecrement();
    }

    /**
     * Hozzáad egy aszteroidát az aszteroida szomszédsági listájához.
     * @param asteroid
     */
    public void AddNeighbor(Asteroid asteroid) {
        Skeleton instance = Skeleton.getInstance();
        instance.tabIncrement();
        instance.Print(this, "AddNeighbors(Asteroid)");

        this.neighbors.add(asteroid);

        AsteroidBelt asteroidBelt = AsteroidBelt.getInstance();

        instance.tabDecrement();
    }

    /**
     * Törli a szomszédsági listából a paraméterként kapott aszteroidát.
     * @param asteroid
     */
    public void RemoveNeighbor(Asteroid asteroid) {
        Skeleton instance = Skeleton.getInstance();
        instance.tabIncrement();
        instance.Print(this, "RemoveNeighbor(Asteroid)");

        this.neighbors.remove(asteroid);

        instance.tabDecrement();
    }

    /**
     *  Felrobbantja az aszteroidát. Meghívja a rajta található karaktereken a HitByExplosion metódusukat, jelez az
     *  asteriodbeltnek, illetve megszünteti a teleportkapu összeköttetést, ha volt rajta éles teleportkapu.
     */
    public void Explosion() {
        Skeleton instance = Skeleton.getInstance();
        instance.tabIncrement();
        instance.Print(this, "Explosion()");

        int n = characters.size();
        for(int i = 0; i < n; i++)
            characters.get(0).HitByExplosion();



        for(TeleportGate teleportGate : teleportGates)
            teleportGate.RemoveFromAsteroid();

        System.out.print("");
        for(Asteroid asteroid: neighbors)
            asteroid.RemoveNeighbor(this);

        AsteroidBelt.getInstance().AsteroidExploded(this);

        instance.tabDecrement();
    }

    /**
     * Ha az aszteroida teljesen le van fúrva, valamint a magja rendelkezik nyersanyaggal, meghívja a magjában
     * található nyersanyagon az OnNearSun metódust.
     */
    public void NearSun() {
        Skeleton instance = Skeleton.getInstance();
        instance.tabIncrement();
        instance.Print(this, "NearSun()");

        if (instance.GetInput("Az aszteroida teljesen le van fúrva? [I/N]").equalsIgnoreCase("i")) {
            if(!instance.GetInput("Az aszteroida üreges? [I/N]").equalsIgnoreCase("i")) {
                String res = instance.GetInput("Mi az aszteroida nyersanyaga? [V/J/U/S]: ");
                if(res.equalsIgnoreCase("u"))
                    this.material = new Uranium();
                else if(res.equalsIgnoreCase("j"))
                    this.material = new WaterIce();
                else
                    this.material = new Coal();

                material.OnNearSun(this);
            }
        }

        instance.tabDecrement();
    }

    /**
     * Eltávolítja az aszteroidáról a paraméterként kapott teleportkaput.
     * @param teleportGate
     */
    public void RemoveTeleportGate(TeleportGate teleportGate) {
        Skeleton instance = Skeleton.getInstance();
        instance.tabIncrement();
        instance.Print(this, "RemoveTeleportGate(TeleportGate)");

        this.teleportGates.remove(teleportGate);

        instance.tabDecrement();
    }

    /**
     * Visszatér az aszteroidáról elérhető helyekkel.
     * @return
     */
    public List<Place> GetNeighbors() {
        Skeleton instance = Skeleton.getInstance();
        instance.tabIncrement();
        instance.Print(this, "getNeighbors()");

        List<Place> ret = new ArrayList<>(neighbors);
        ret.addAll(teleportGates);

        instance.tabDecrement();

        return ret;
    }

    @Override
    public boolean Move(Character character) {
        Skeleton instance = Skeleton.getInstance();
        instance.tabIncrement();
        instance.Print(this, "Move(" + character.getClass().getSimpleName() + ")");

        this.characters.add(character);
        character.SetAsteroid(this);

        instance.tabDecrement();

        return true;
    }

    /**
     * Visszahelyezi a paraméterül kapott nyersanyagot az aszteroida magjába.
     * @param material
     * @return True-val tér vissza, ha sikerült visszahelyezni, egyébként False.
     */
    public boolean PlaceMaterial(Material material) {
        Skeleton instance = Skeleton.getInstance();
        instance.tabIncrement();
        instance.Print(this, "PlaceMaterial(" + material.getClass().getSimpleName() + ")");

        if(instance.GetInput("Le lehet helyezni nyersanyagot az aszteroidára? [I/N]: ").equalsIgnoreCase("i")) {
            this.material = material;
            instance.tabDecrement();
            return true;
        } else {
            instance.tabDecrement();
            return false;
        }
    }

}
