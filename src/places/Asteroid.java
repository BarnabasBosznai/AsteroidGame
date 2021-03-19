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

    public boolean Drilled() {
        if((thickness - 1) == 0) {
            thickness--;
            return true;
        } else {
            return false;
        }
    }

    public Material RemoveMaterial() {
        if(thickness == 0 && material != null) {
            Material ret = material;
            material = null;
            return ret;
        } else {
            return null;
        }
    }

    public void SolarFlare() {
        for(Character character : characters)
            character.HitByStorm();
    }

    public void TakeOff(Character character) {
        this.characters.remove(character);
    }

    public void PlaceTeleport(TeleportGate teleportGate) {
        this.teleportGates.add(teleportGate);
        teleportGate.SetAsteroid(this);
    }

    public void AddNeighbors(Asteroid asteroid) {
        this.neighbors.add(asteroid);
    }

    public void RemoveNeighbor(Asteroid asteroid) {
        this.neighbors.remove(asteroid);
    }

    public void Explosion() {
        for(Character character : characters)
            character.HitByExplosion();

        AsteroidBelt.getInstance().AsteroidExploded(this);

        for(TeleportGate teleportGate : teleportGates)
            teleportGate.RemoveFromAsteroid();
    }

    public void NearSun() {
        if(thickness == 0 && material != null)
            material.OnNearSun(this);

    }

    public void RemoveTeleportGate(TeleportGate teleportGate) {
        this.teleportGates.remove(teleportGate);
    }

    public List<Place> getNeighbors() {
        List<Place> ret = new ArrayList<>(neighbors);
        ret.addAll(teleportGates);

        return ret;
    }

    @Override
    public boolean Move(Character character) {
        this.characters.add(character);
        character.SetAsteroid(this);
        return true;
    }

    public boolean PlaceMaterial(Material material) {
        if(thickness == 0 && this.material == null){
            this.material = material;
            return true;
        }
        else
            return false;
    }

}
