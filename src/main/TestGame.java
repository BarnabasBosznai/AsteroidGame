package main;

import characters.Character;
import characters.Robot;
import characters.Settler;
import characters.UFO;
import interfaces.Steppable;
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

    private final Map<String, Steppable> steppableMap;
    private final Map<String, TeleportGate> characterMap;
    private final Map<String, TeleportGate> miningCharacterMap;


    public TestGame() {
        super();

        this.asteroids = new HashMap<>();
        this.settlers = new HashMap<>();
        this.robots = new HashMap<>();
        this.ufos = new HashMap<>();
        this.teleportgates = new HashMap<>();

        this.steppableMap = new HashMap<>();
        this.characterMap = new HashMap<>();
        this.miningCharacterMap = new HashMap<>();
    }

    private Map<String, Character> GetMiningCharacters(){
        Map<String, Character> landableCharacters = new HashMap<>();

        for(String settlerID : settlers.keySet())
            landableCharacters.put(settlerID, settlers.get(settlerID));

        for(String robotID : robots.keySet())
            landableCharacters.put(robotID, robotID.get(robotID));

        for(String ufoID : ufos.keySet())
            landableCharacters.put(ufoID, ufoID.get(ufoID));

        return landableCharacters;
    }

    private Map<String, Steppable> GetSteppables(){
        Map<String, Steppable> steppables = new HashMap<>();

        for(String settlerID : settlers.keySet())
            steppables.put(settlerID, settlers.get(settlerID));

        for(String robotID : robots.keySet())
            steppables.put(robotID, robotID.get(robotID));

        for(String ufoID : ufos.keySet())
            steppables.put(ufoID, ufoID.get(ufoID));

        for(String teleportgateID : teleportgates.keySet())
            steppables.put(teleportgateID, teleportgates.get(teleportgateID));

        return steppables;
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
            this.AddSettler(settler);
            this.AddSteppable(settler);
        }
        else if(objectType == TeleportGate.class){
            TeleportGate teleportGate = new TeleportGate();

            this.teleportgates.put(objectID, teleportGate);
            this.AddSteppable(teleportGate);
        }
        else if(objectType == Robot.class){
            Robot robot = new Robot();

            this.robots.put(objectID, robot);
            this.AddSteppable(robot);
        }
        else if(objectType == UFO.class){
            UFO ufo = new UFO();

            this.ufos.put(objectID, ufo);
            this.AddSteppable(ufo);
        }
    }

    public void Land(String asteroidID, String characterID){
        Asteroid asteroid = this.asteroids.get(asteroidID);

        var miningCharacters = this.GetMiningCharacters();
        Character character = miningCharacters.get(characterID);

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
        TeleportGate teleportGate1 = this.teleportgates.get(teleportID1);
        TeleportGate teleportGate2 = this.teleportgates.get(teleportID2);

        teleportGate1.SetPair(teleportGate2);
        teleportGate2.SetPair(teleportGate1);
    }

    public void PlaceTeleport(String teleportID, String asteroidID){
        TeleportGate teleportGate = this.teleportgates.get(teleportID);
        Asteroid asteroid = this.asteroids.get(asteroidID);

        asteroid.PlaceTeleport(teleportGate);
    }

    public void SetNeighbours(String asteroidID1, String asteroidID2){
        Asteroid asteroid1 = this.asteroids.get(asteroidID1);
        Asteroid asteroid2 = this.asteroids.get(asteroidID2);

        asteroid1.AddNeighbor(asteroid2);
        asteroid2.AddNeighbor(asteroid1);
    }

    //WTF nemakarom
    public void Step(String command, List<String> parameters){

        if(parameters.size() == 0){
            Steppable steppable = this.GetSteppables().get(command);
        }
        else{

        }
    }

    public void ListAsteroids(){

    }

    public void ListSettlers(){

    }

    public void ListRobots(){

    }

    public void ListUFOs(){

    }

    public void ListTeleportGates(){

    }

    public void public void GameStatus(){
        boolean gameStatus = this.CheckGameOver();

        //meg lekezelem kulon majd h settlers lost, az jelenleg nincs
        String print = gameStatus ? "settlers won" : "not ended";
        System.out.println(print);
    }

    @Override
    public void RemoveSteppable(Steppable steppable){
        Class<? extends Steppable> type = steppable.getClass();

        if (Settler.class.equals(type)) {
            this.settlers.values().remove(steppable);
        }
        if (Robot.class.equals(type)) {
            this.robots.values().remove(steppable);
        }
        if (UFO.class.equals(type)) {
            this.ufos.values().remove(steppable);
        }
        if (TeleportGate.class.equals(type)) {
            this.teleportgates.values().remove(steppable);
        }
    }

    public void RemoveAsteroid(Asteroid asteroid){
        this.asteroids.values().remove(asteroid);
    }
}
