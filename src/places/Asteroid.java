package places;

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

    public boolean drilled() {
        if((thickness - 1) == 0) {
            thickness--;
            return true;
        } else {
            return false;
        }
    }

    public Material removeMaterial() {
        if(thickness == 0 && material != null) {
            Material ret = material;
            material = null;
            return ret;
        } else {
            return null;
        }
    }

    public Boolean placeMaterial(Material material) {
        /**
         * Settler-nek kell, ezért létrehoztam. (5.3.2)
         * Törölje majd ezt a függvényt megvalósító!
         *  Bobó
         */
        return true;
    }

    public Boolean Drilled(){
        /**
         * Settler-nek kell, ezért létrehoztam. (5.3.3)
         * Törölje majd ezt a függvényt megvalósító!
         *  Bobó
         */
        return true;
    }

    public void solarFlare() {
        for(Character character : characters)
            character.HitByStorm();
    }

    public void takeOff(Character character) {
        this.characters.remove(character);
    }

    public void placeTeleport(TeleportGate teleportGate) {
        this.teleportGates.add(teleportGate);
        teleportGate.setAsteroid(this);
    }

    public void addNeighbors(Asteroid asteroid) {
        this.neighbors.add(asteroid);
    }

    public void removeNeighbor(Asteroid asteroid) {
        this.neighbors.remove(asteroid);
    }

    public void explosion() {
        for(Character character : characters)
            character.HitByExplosion();

        AsteroidBelt.getInstance().asteroidExploded(this);

        for(TeleportGate teleportGate : teleportGates)
            teleportGate.removeFromAsteroid();
    }

    public void nearSun() {
        if(thickness == 0 && material != null)
            material.onNearSun(this);

    }

    public void removeTeleportGate(TeleportGate teleportGate) {
        this.teleportGates.remove(teleportGate);
    }

    public List<Place> getNeighbors() {
        List<Place> ret = new ArrayList<>(neighbors);
        ret.addAll(teleportGates);

        return ret;
    }

    @Override
    public boolean move(Character character) {
        this.characters.add(character);
        character.setAsteroid(this);
        return true;
    }

}
