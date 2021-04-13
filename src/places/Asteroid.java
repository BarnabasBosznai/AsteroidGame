package places;

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
        if((thickness - 1) == 0) {
            thickness--;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Kiveszi az aszteroidában található nyersanyagot.
     * @return Ha az aszteroida magjában van nyersanyag, visszatér a nyersanyaggal, ha nem volt, null-al.
     */
    public Material RemoveMaterial() {
        if(thickness == 0 && material != null) {
            Material ret = material;
            material = null;
            return ret;
        } else {
            return null;
        }
    }

    /**
     * Napvihart kelt az aszteroidán. Meghívja az aszteroidán található karaktereken a saját HitByStorm metódusukat.
     */
    public void SolarFlare() {
        if(thickness != 0 || material != null) {
            int n = characters.size();
            List<Character> copy = List.copyOf(characters);
            for(int i = 0; i < n; i++)
                copy.get(i).HitByStorm();
        }

        for(TeleportGate teleportGate : teleportGates){
            teleportGate.HitByStorm();
        }
    }

    public void TakeOff(Character character) {
        this.characters.remove(character);
    }

    /**
     * Elhelyez egy teleportkaput az aszteroidán. Beállítja a teleportgate asteroid attribútumának saját magát.
     * @param teleportGate
     */
    public void PlaceTeleport(TeleportGate teleportGate) {
        this.teleportGates.add(teleportGate);
        teleportGate.SetAsteroid(this);
    }

    /**
     * Hozzáad egy aszteroidát az aszteroida szomszédsági listájához.
     * @param asteroid
     */
    public void AddNeighbor(Asteroid asteroid) {
        if(!neighbors.contains(asteroid))
            this.neighbors.add(asteroid);
    }

    /**
     * Törli a szomszédsági listából a paraméterként kapott aszteroidát.
     * @param asteroid
     */
    public void RemoveNeighbor(Asteroid asteroid) {
        this.neighbors.remove(asteroid);
    }

    /**
     *  Felrobbantja az aszteroidát. Meghívja a rajta található karaktereken a HitByExplosion metódusukat, jelez az
     *  asteriodbeltnek, illetve megszünteti a teleportkapu összeköttetést, ha volt rajta éles teleportkapu.
     */
    public void Explosion() {
        List<Character> copy = List.copyOf(characters);
        for(int i = 0; i < copy.size(); i++)
            copy.get(i).HitByExplosion();

        for(TeleportGate teleportGate : teleportGates)
            teleportGate.RemoveFromAsteroid();

        List<Asteroid> copyNeighbor = List.copyOf(neighbors);
        for(int i = 0; i < copyNeighbor.size(); ++i)
            copyNeighbor.get(i).RemoveNeighbor(this);

        AsteroidBelt.getInstance().AsteroidExploded(this);

    }

    /**
     * Ha az aszteroida teljesen le van fúrva, valamint a magja rendelkezik nyersanyaggal, meghívja a magjában
     * található nyersanyagon az OnNearSun metódust.
     */
    public void NearSun() {
        if(thickness == 0 && material != null)
            material.OnNearSun(this);
    }

    /**
     * Eltávolítja az aszteroidáról a paraméterként kapott teleportkaput.
     * @param teleportGate
     */
    public void RemoveTeleportGate(TeleportGate teleportGate) {
        this.teleportGates.remove(teleportGate);
    }

    /**
     * Visszatér az aszteroidáról elérhető helyekkel.
     * @return
     */
    public List<Place> GetNeighbors() {
        List<Place> ret = new ArrayList<>(neighbors);
        ret.addAll(teleportGates);

        return ret;
    }

    /*NEW*/
    public List<Asteroid> GetNeighboringAsteroids(){
        return this.neighbors;
    }

    @Override
    public boolean Move(Character character) {
        this.characters.add(character);
        character.SetAsteroid(this);
        return true;
    }

    /**
     * Visszahelyezi a paraméterül kapott nyersanyagot az aszteroida magjába.
     * @param material
     * @return True-val tér vissza, ha sikerült visszahelyezni, egyébként False.
     */
    public boolean PlaceMaterial(Material material) {
        if(thickness == 0 && this.material == null){
            this.material = material;
            return true;
        }
        else
            return false;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public void setThickness(int value) {
        this.thickness = value;
    }

}
