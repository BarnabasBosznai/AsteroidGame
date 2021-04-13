package main;

import characters.Character;
import characters.Robot;
import characters.Settler;
import characters.UFO;
import items.Inventory;
import materials.*;
import places.Asteroid;
import places.AsteroidBelt;
import places.TeleportGate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestGame extends Game {

    private final Map<String, Asteroid> asteroids;
    private final Map<String, Settler> settlers;
    private final Map<String, Robot> robots;
    private final Map<String, UFO> ufos;
    private final Map<String, TeleportGate> teleportgates;

    public TestGame() {
        super();

        this.asteroids = new HashMap<>();
        this.settlers = new HashMap<>();
        this.robots = new HashMap<>();
        this.ufos = new HashMap<>();
        this.teleportgates = new HashMap<>();
    }

    private Map<String, Character> GetLandableCharacters(){
        Map<String, Character> landableCharacters = new HashMap<>();

        for(String settlerID : settlers.keySet())
            landableCharacters.put(settlerID, settlers.get(settlerID));
        for(String robotID : robots.keySet())
            landableCharacters.put(robotID, robotID.get(robotID));
        for(String ufoID : ufos.keySet())
            landableCharacters.put(ufoID, ufoID.get(ufoID));

        return landableCharacters;
    }

    public void Create(){

    }

    public void Add(Class<?> objectType, String objectID){
        if(objectType == Asteroid.class){
            Asteroid asteroid = new Asteroid();

            AsteroidBelt.getInstance().AddAsteroid(asteroid);
            this.asteroids.put(objectID, asteroid);
        }
        else if(objectType == Settler.class){
            Settler settler = new Settler();

            this.settlers.put(objectID, settler);
        }
        else if(objectType == TeleportGate.class){
            TeleportGate teleportGate = new TeleportGate();

            this.teleportgates.put(objectID, teleportGate);
        }
        else if(objectType == Robot.class){
            Robot robot = new Robot();

            this.robots.put(objectID, robot);
        }
        else if(objectType == UFO.class){
            UFO ufo = new UFO();

            this.ufos.put(objectID, ufo);
        }
    }

    public void Land(String asteroidID, String characterID){
        Asteroid asteroid = this.asteroids.get(asteroidID);

        var landableCharacters = this.GetLandableCharacters();
        Character character = landableCharacters.get(characterID);

        asteroid.Move(character);
    }

    public void AddTeleportGate(String settlerID, String teleportID){
        Settler settler = this.settlers.get(settlerID);
        TeleportGate teleportGate = this.teleportgates.get(teleportID);

        settler.AddItem(teleportGate);
    }

    public void AddMaterial(String settlerID, String materialType){
        Inventory settlerInventory = settlers.get(settlerID).GetInventory();

        Material material;
        if(materialType.equals("coal"))
            material = new Coal();

        else if(materialType.equals("iron"))
            material = new Iron();

        else if(materialType.equals("waterice"))
            material = new WaterIce();

        else if(materialType.equals("uranium0"))
            material = new Uranium(0);

        else if(materialType.equals("uranium1"))
            material = new Uranium(1);

        else if(materialType.equals("uranium2"))
            material = new Uranium(2);

        else
            return;

        settlerInventory.AddMaterial(material);
    }

    public void Set(String objectID, String variable, String value){
        //szopj le
    }

    public void Pair(String teleportID1, String teleportID2){

    }

    public void PlaceTeleport(String teleportID, String asteroidID){

    }

    public void SetNeighbours(String asteroidID1, String asteroidID2){

    }

    //WTF nemakarom
    public void Step(String command, List<String> parameters){

    }

    public List<Asteroid> GetAsteroids(){
        return (List<Asteroid>) this.asteroids.values();
    }

    public List<Settler> GetSettlers(){
        return (List<Settler>) this.settlers.values();
    }

    public List<Robot> GetUFOs(){
        return (List<Robot>) this.robots.values();
    }

    public List<TeleportGate> GetTeleportGates(){
        return (List<TeleportGate>) this.teleportgates.values();
    }

    public String GetGameStatus(){
        boolean gameStatus = this.CheckGameOver();

        //meg lekezelem kulon majd h settlers lost, az jelenleg nincs
        return gameStatus ? "not ended" : "settlers won";
    }
}
