package places;

import characters.Character;
import materials.Material;
import java.util.List;

public class Asteroid extends Place {
    private int thickness;
    private List<Asteroid> neighbors;
    private List<TeleportGate> teleportGates;

    public boolean drilled() {

    }

    public Material removeMaterial() {

    }

    public void solarFlare() {

    }

    public void takeOff(Character character) {

    }

    public void placeTeleport(TeleportGate teleportGate) {

    }

    public void addNeighbors(Asteroid asteroid) {

    }



    public void explosion() {

    }

    public List<Place> getNeighbors() {

    }

    public void nearSun() {

    }

    public void removeTeleportGate(TeleportGate teleportGate) {

    }

    @Override
    public boolean move(Character character) {
        return false;
    }
}
