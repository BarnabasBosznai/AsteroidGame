package places;

import Skeleton.Skeleton;
import characters.Character;
import materials.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public boolean Drilled() {
        Skeleton instance = Skeleton.getInstance();
        instance.tabIncrement();
        instance.Print(this, "Drilled()");
        boolean res = instance.GetInput("Az aszteroida le van fúrva? [I/N]: ").equalsIgnoreCase("i");
        instance.tabDecrement();
        return !res;
    }

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

    public void SolarFlare() {
        Skeleton instance = Skeleton.getInstance();
        instance.tabIncrement();
        instance.Print(this, "SolarFlare()");

        boolean res1 = instance.GetInput("Az aszteroida teljesen le van fúrva? [I/N]: ").equalsIgnoreCase("i");
        boolean res2 = instance.GetInput("Az aszteroida üreges? [I/N]: ").equalsIgnoreCase("i");
        if(!res1 || !res2)
            for (Character character : characters)
                character.HitByStorm();

        instance.tabDecrement();
    }

    public void TakeOff(Character character) {
        Skeleton instance = Skeleton.getInstance();
        instance.tabIncrement();
        instance.Print(this, "TakeOff("+ character.getClass().getSimpleName() +")");

        this.characters.remove(character);

        instance.tabDecrement();
    }

    public void PlaceTeleport(TeleportGate teleportGate) {
        Skeleton instance = Skeleton.getInstance();
        instance.tabIncrement();
        instance.Print(this, "PlaceTeleport(TeleportGate)");

        this.teleportGates.add(teleportGate);
        teleportGate.SetAsteroid(this);

        instance.tabDecrement();
    }

    public void AddNeighbors(Asteroid asteroid) {
        Skeleton instance = Skeleton.getInstance();
        instance.tabIncrement();
        instance.Print(this, "AddNeighbors(Asteroid)");

        this.neighbors.add(asteroid);

        instance.tabDecrement();
    }

    public void RemoveNeighbor(Asteroid asteroid) {
        Skeleton instance = Skeleton.getInstance();
        instance.tabIncrement();
        instance.Print(this, "RemoveNeighbor(Asteroid)");

        this.neighbors.remove(asteroid);

        instance.tabDecrement();
    }

    public void Explosion() {
        Skeleton instance = Skeleton.getInstance();
        instance.tabIncrement();
        instance.Print(this, "Explosion()");

        for(Character character : characters)
            character.HitByExplosion();

        AsteroidBelt.getInstance().AsteroidExploded(this);

        for(TeleportGate teleportGate : teleportGates)
            teleportGate.RemoveFromAsteroid();

        instance.tabDecrement();
    }

    public void NearSun() {
        Skeleton instance = Skeleton.getInstance();
        instance.tabIncrement();
        instance.Print(this, "NearSun()");
        if(!instance.GetInput("Az aszteroida üreges? [I/N]").equalsIgnoreCase("i")) {
            if (instance.GetInput("Az aszteroida teljesen le van fúrva? [I/N]").equalsIgnoreCase("i")) {
                String res = instance.GetInput("Mi az aszteroida nyersanyaga? [V/J/U/S]: ");
                if(res.equalsIgnoreCase("u"))
                    new Uranium().OnNearSun(this);
                else if(res.equalsIgnoreCase("v"))
                    new WaterIce().OnNearSun(this);
                else
                    new Coal().OnNearSun(this);
            }
        }
        instance.tabDecrement();
    }

    public void RemoveTeleportGate(TeleportGate teleportGate) {
        Skeleton instance = Skeleton.getInstance();
        instance.tabIncrement();
        instance.Print(this, "RemoveTeleportGate(TeleportGate)");

        this.teleportGates.remove(teleportGate);

        instance.tabDecrement();
    }

    public List<Place> getNeighbors() {
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
