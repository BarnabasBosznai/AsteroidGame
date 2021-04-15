package main;

import characters.*;
import characters.Character;
import interfaces.Steppable;
import items.CraftingTable;
import items.Inventory;
import items.Recipe;
import materials.*;
import places.Asteroid;
import places.AsteroidBelt;
import places.TeleportGate;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestGame extends Game {

    private final Map<String, Asteroid> asteroids;
    private final Map<String, Settler> settlers;
    private final Map<String, Robot> robots;
    private final Map<String, UFO> ufos;
    private final Map<String, TeleportGate> teleportgates;

    private final Map<String, Steppable> steppableMap;
    private final Map<String, Character> characterMap;

    private boolean konzolos;

    public TestGame() {
        super();

        CraftingTable craftingTable = CraftingTable.getInstance();
        craftingTable.AddRecipe(new Recipe(Robot.class,
                Stream.of(new Coal(), new Iron(), new Uranium()).collect(Collectors.toList())));
        craftingTable.AddRecipe(new Recipe(TeleportGate.class,
                Stream.of(new Iron(), new Iron(), new WaterIce(), new Uranium()).collect(Collectors.toList())));

        this.konzolos = true;
        this.asteroids = new HashMap<>();
        this.settlers = new HashMap<>();
        this.robots = new HashMap<>();
        this.ufos = new HashMap<>();
        this.teleportgates = new HashMap<>();

        this.steppableMap = new HashMap<>();
        this.characterMap = new HashMap<>();
    }

    public void Create(){
        steppableMap.forEach((key, value) -> super.RemoveSteppable(value));
        settlers.forEach((key, value) -> super.RemoveSettler(value));

        asteroids.clear();
        settlers.clear();
        robots.clear();
        ufos.clear();
        teleportgates.clear();
        steppableMap.clear();
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
            this.steppableMap.put(objectID, settler);
            this.characterMap.put(objectID, settler);

            this.AddSettler(settler);
            this.AddSteppable(settler);
        }
        else if(objectType == TeleportGate.class){
            TeleportGate teleportGate = new TeleportGate();

            this.teleportgates.put(objectID, teleportGate);
            this.steppableMap.put(objectID, teleportGate);

            this.AddSteppable(teleportGate);
        }
        else if(objectType == Robot.class){
            Robot robot = new Robot();

            this.robots.put(objectID, robot);
            this.characterMap.put(objectID, robot);

            this.AddSteppable(robot);
        }
        else if(objectType == UFO.class){
            UFO ufo = new UFO();

            this.ufos.put(objectID, ufo);
            this.steppableMap.put(objectID, ufo);
            this.characterMap.put(objectID, ufo);

            this.AddSteppable(ufo);
        }
    }

    public void Land(String asteroidID, String characterID){
        Asteroid asteroid = this.asteroids.get(asteroidID);

        Character character = this.characterMap.get(characterID);

        asteroid.Move(character);
    }

    public void AddTeleportGate(String settlerID, String teleportID){
        Settler settler = this.settlers.get(settlerID);
        TeleportGate teleportGate = this.teleportgates.get(teleportID);

        settler.AddItem(teleportGate);
        super.AddSteppable(teleportGate);
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

    public void Set(String objectID, String variable, String value) {
        if(asteroids.containsKey(objectID)) {
            if(variable.equals("layer") || variable.equals("layers")) {
                asteroids.get(objectID).setThickness(Integer.parseInt(value));
            } else if(variable.equals("material")) {
                switch (value) {
                    case "coal":
                        asteroids.get(objectID).setMaterial(new Coal());
                        break;
                    case "iron":
                        asteroids.get(objectID).setMaterial(new Iron());
                        break;
                    case "waterice":
                        asteroids.get(objectID).setMaterial(new WaterIce());
                        break;
                    case "uranium0":
                        asteroids.get(objectID).setMaterial(new Uranium(0));
                        break;
                    case "uranium1":
                        asteroids.get(objectID).setMaterial(new Uranium(1));
                        break;
                    case "uranium2":
                        asteroids.get(objectID).setMaterial(new Uranium(2));
                        break;
                    case "null":
                        asteroids.get(objectID).setMaterial(null);
                        break;
                }
            }
        } else if(teleportgates.containsKey(objectID)) {
            if(variable.equals("crazy")) {
                teleportgates.get(objectID).SetCrazy(Boolean.parseBoolean(value));
            }
        }
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

    public void Step(String command, List<String> parameters){
        switch (command) {
            case "drill":
                if(parameters.size() == 1 && settlers.containsKey(parameters.get(0))) {
                    settlers.get(parameters.get(0)).Drill();
                }
                break;
            case "mine":
                if(parameters.size() == 1 && settlers.containsKey(parameters.get(0))) {
                    settlers.get(parameters.get(0)).Mine();
                }
                break;
            case "placematerial":
                if(parameters.size() == 2 && settlers.containsKey(parameters.get(0))) {
                    Material mat;
                    switch (parameters.get(1)) {
                        case "coal":
                            // Ne kérdezd xdd
                            mat = settlers.get(parameters.get(0)).GetInventory().GetMaterials().stream().filter(material -> material.CompatibleWith(new Coal())).findFirst().orElse(null);
                            if(mat != null)
                                settlers.get(parameters.get(0)).PlaceMaterial(mat);
                            break;
                        case "iron":
                            mat = settlers.get(parameters.get(0)).GetInventory().GetMaterials().stream().filter(material -> material.CompatibleWith(new Iron())).findFirst().orElse(null);
                            if(mat != null)
                                settlers.get(parameters.get(0)).PlaceMaterial(mat);
                            break;
                        case "waterice":
                            mat = settlers.get(parameters.get(0)).GetInventory().GetMaterials().stream().filter(material -> material.CompatibleWith(new WaterIce())).findFirst().orElse(null);
                            if(mat != null)
                                settlers.get(parameters.get(0)).PlaceMaterial(mat);
                            break;
                        case "uranium0":
                            mat = settlers.get(parameters.get(0)).GetInventory().GetMaterials().stream().filter(material -> material.CompatibleWith(new Uranium())).findFirst().orElse(null);
                            if(mat != null && ((Uranium)mat).getNearSuns() == 0)
                                settlers.get(parameters.get(0)).PlaceMaterial(mat);
                            break;
                        case "uranium1":
                            mat = settlers.get(parameters.get(0)).GetInventory().GetMaterials().stream().filter(material -> material.CompatibleWith(new Uranium(1))).findFirst().orElse(null);
                            if(mat != null && ((Uranium)mat).getNearSuns() == 1)
                                settlers.get(parameters.get(0)).PlaceMaterial(mat);
                            break;
                        case "uranium2":
                            mat = settlers.get(parameters.get(0)).GetInventory().GetMaterials().stream().filter(material -> material.CompatibleWith(new Uranium(2))).findFirst().orElse(null);
                            if(mat != null && ((Uranium)mat).getNearSuns() == 2)
                                settlers.get(parameters.get(0)).PlaceMaterial(mat);
                            break;
                    }
                }
                break;
            case "nearsun":
                for(String str : parameters) {
                    if(asteroids.containsKey(str)) {
                        asteroids.get(str).NearSun();
                    }
                }
                break;
            case "solarflare":
                for(String str : parameters) {
                    if(asteroids.containsKey(str)) {
                        asteroids.get(str).SolarFlare();
                    }
                }
                break;
            case "move":
                if(parameters.size() == 2 && settlers.containsKey(parameters.get(0))) {
                    if(asteroids.containsKey(parameters.get(1))) {
                        settlers.get(parameters.get(0)).Move(asteroids.get(parameters.get(1)));
                    } else if(teleportgates.containsKey(parameters.get(1))) {
                        settlers.get(parameters.get(0)).Move(teleportgates.get(parameters.get(1)));
                    }
                }
                break;
            case "placeteleport":
                if(parameters.size() == 2 && settlers.containsKey(parameters.get(0))) {
                    settlers.get(parameters.get(0)).PlaceTeleportGate();
                }
                break;
            case "craft":
                if(parameters.size() >= 3 && settlers.containsKey(parameters.get(1))) {
                    if(parameters.get(0).equals("teleportgates")) {

                        settlers.get(parameters.get(1)).CraftTeleportGates();
                        int[] index = {0};
                        List<TeleportGate> gates = settlers.get(parameters.get(1)).GetInventory().GetTeleportGates();
                        gates.forEach(teleportGate -> {
                            if(!teleportgates.containsValue(teleportGate)) {
                                teleportgates.put(parameters.get(2 + index[0]), teleportGate);
                                index[0]++;
                            }
                        });
                    } else if(parameters.get(0).equals("robot")) {
                        if(settlers.get(parameters.get(1)).CraftRobot()) {
                            Robot r = robots.remove("tempKey");
                            robots.put(parameters.get(2), r);
                            characterMap.put(parameters.get(2), r);
                        }
                    }
                }
                break;
            default:
                if(parameters.size() == 0) {
                    if(robots.containsKey(command)) {
                        robots.get(command).Step();
                    } else if(ufos.containsKey(command)) {
                        ufos.get(command).Step();
                    } else if(teleportgates.containsKey(command)) {
                        teleportgates.get(command).Step();
                    }
                }
                break;
        }
    }

    public void ListAsteroids() {
        int[] counter = {0};

        asteroids.forEach((key, value) -> {
            if (value.GetMaterial() == null)
                System.out.println("asteroid " + key + " " + value.GetThickness() + " null");
            else
                System.out.println("asteroid " + key + " " + value.GetThickness() + " " + value.GetMaterial().Print());
            System.out.println("neighbor ids:");
            asteroids.forEach((key2, value2) -> {
                if(!key.equals(key2) && value2.GetNeighbors().stream().filter(place -> place.equals(value)).findFirst().orElse(null) != null) {
                    System.out.print(key2 + " ");
                    counter[0]++;
                }
            });
            if(counter[0] == 0)
                System.out.print("-");
            System.out.println();

            counter[0] = 0;
            System.out.println("teleportgates’s pair’s asteroid’s id: ");
            teleportgates.forEach((key2, value2) -> {
                value.GetTeleportGates().forEach(tg -> {
                    if(value2.equals(tg)) {
                        asteroids.entrySet().stream().filter(entry -> tg.GetPair().GetAsteroid().equals(entry.getValue())).map(Map.Entry::getKey).findFirst().ifPresent(System.out::print);
                        counter[0]++;
                    }
                });
            });
            if(counter[0] == 0)
                System.out.print("-");
            System.out.println();

            counter[0] = 0;
            System.out.println("character ids:");
            characterMap.forEach((key2, value2) -> {
                value.GetCharacters().forEach(character -> {
                    if(value2.equals(character)) {
                        System.out.print(key2);
                        counter[0]++;
                    }
                });
            });
            if(counter[0] == 0)
                System.out.print("-");
            System.out.println();

            Map<Class<? extends Material>, Integer> matsOnAsteroid = new HashMap<>();
            System.out.println("total amount of materials by material on asteroid:");
            settlers.forEach((key2, value2) -> {
                if(value.GetCharacters().stream().filter(character -> character.equals(value2)).findFirst().orElse(null) != null)
                    value2.GetInventory().GetAmountOfMaterials().forEach(matsOnAsteroid::put);
            });
            if(matsOnAsteroid.containsKey(Coal.class))
                System.out.print(matsOnAsteroid.get(Coal.class) + " ");
            else
                System.out.print("0 ");

            if(matsOnAsteroid.containsKey(Iron.class))
                System.out.print(matsOnAsteroid.get(Iron.class) + " ");
            else
                System.out.print("0 ");

            if(matsOnAsteroid.containsKey(WaterIce.class))
                System.out.print(matsOnAsteroid.get(WaterIce.class) + " ");
            else
                System.out.print("0 ");

            if(matsOnAsteroid.containsKey(Uranium.class))
                System.out.println(matsOnAsteroid.get(Uranium.class));
            else
                System.out.println("0");
            System.out.println();
        });
    }

    public void ListSettlers(){
        settlers.forEach((key, value) -> {
            System.out.print("settler " + key + " ");
            asteroids.entrySet().stream().filter(entry -> value.GetAsteroid().equals(entry.getValue())).map(Map.Entry::getKey).findFirst().ifPresent(System.out::print);
            System.out.println();

            System.out.println("materials:");
            var mats = value.GetInventory().GetAmountOfMaterials();
            if(mats.containsKey(Coal.class))
                System.out.print(mats.get(Coal.class) + " ");
            else
                System.out.print("0 ");

            if(mats.containsKey(Iron.class))
                System.out.print(mats.get(Iron.class) + " ");
            else
                System.out.print("0 ");

            if(mats.containsKey(WaterIce.class))
                System.out.print(mats.get(WaterIce.class) + " ");
            else
                System.out.print("0 ");

            if(mats.containsKey(Uranium.class))
                System.out.println(mats.get(Uranium.class));
            else
                System.out.println("0");

            System.out.println("teleportgate ids:");
            int[] counter = {0};
            teleportgates.forEach((key2, value2) -> {
                value.GetInventory().GetTeleportGates().forEach(teleportGate -> {
                    if(teleportGate.equals(value2))
                        System.out.print(key2 + " ");
                    counter[0]++;
                });
            });
            if (counter[0] == 0)
                System.out.println("-");
            System.out.println();
        });
    }

    public void ListRobots(){
        if(this.robots.size() == 0){
            System.out.println("robot - -");
            System.out.println();
            return;
        }
        for(String robotID : robots.keySet()){
            Asteroid asteroid = this.robots.get(robotID).GetAsteroid();
            String asteroidID = "";

            for(var entry : asteroids.entrySet()){
                if(entry.getValue().equals(asteroid))
                    asteroidID = entry.getKey();
            }
            System.out.println("robot " + robotID + " " + asteroidID);

        }
        System.out.println();
    }

    public void ListUFOs(){
        if(this.ufos.size() == 0){
            System.out.println("ufo - -");
            System.out.println();
            return;
        }
        for(String ufoID : ufos.keySet()){
            Asteroid asteroid = this.ufos.get(ufoID).GetAsteroid();
            String asteroidID = "";

            for(var entry : asteroids.entrySet()){
                if(entry.getValue().equals(asteroid))
                    asteroidID = entry.getKey();
            }
            System.out.println("ufo " + ufoID + " " + asteroidID);

        }
        System.out.println();
    }

    public void ListTeleportGates(){
        if(this.teleportgates.size() == 0){
            System.out.println("teleportgate - -");
            System.out.println();
            return;
        }
        for(String teleportgateID : teleportgates.keySet()){
            Asteroid asteroid = this.teleportgates.get(teleportgateID).GetAsteroid();
            String asteroidID = "";

            for(var entry : asteroids.entrySet()){
                if(entry.getValue().equals(asteroid))
                    asteroidID = entry.getKey();
            }
            String crazy = teleportgates.get(teleportgateID).GetCrazy() ? "true" : "false";
            System.out.println("teleportgate " + teleportgateID + " " + asteroidID + " " + crazy);
        }
        System.out.println();
    }

    public void GameStatus(){
        GameState gameStatus = this.CheckGameStatus();

        String gameStatusString;
        switch (gameStatus){
            case SETTLERSLOST -> gameStatusString = "settlers lost";
            case SETTLERSWON -> gameStatusString = "settlers won";
            default -> gameStatusString = "not ended";
        }
        System.out.println("game " + gameStatusString);
    }

    @Override
    public void RemoveSteppable(Steppable steppable){
        Class<? extends Steppable> type = steppable.getClass();

        if (Settler.class.equals(type)) {
            this.settlers.values().remove(steppable);
            this.steppableMap.values().remove(steppable);
            this.characterMap.values().remove(steppable);

            super.RemoveSteppable(steppable);
            super.RemoveSettler((Settler)steppable);
        }
        if (Robot.class.equals(type)) {
            this.robots.values().remove(steppable);
            this.steppableMap.values().remove(steppable);
            this.characterMap.values().remove(steppable);

            super.RemoveSteppable(steppable);
        }
        if (UFO.class.equals(type)) {
            this.ufos.values().remove(steppable);
            this.steppableMap.values().remove(steppable);
            this.characterMap.values().remove(steppable);

            super.RemoveSteppable(steppable);
        }
        if (TeleportGate.class.equals(type)) {
            this.teleportgates.values().remove(steppable);
            this.steppableMap.values().remove(steppable);

            super.RemoveSteppable(steppable);
        }
    }

    public void CraftTeleportGateHelper(Robot robot) {
        robots.put("tempKey", robot);
    }

    public void RemoveAsteroid(Asteroid asteroid){
        this.asteroids.values().remove(asteroid);
    }

    private void SetKonzolos(boolean bool){
        this.konzolos=bool;
    }

    public static void main(String[] args) {
        TestGame.getInstance();

        boolean running = true;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Fájlból történő tesztelés(0), vagy konzolból történő tesztelés(1): ");
        int inp = scanner.nextInt();
        if (inp==0) {
            String in_txt = scanner.nextLine();
            System.out.println("Adja meg a bemeneti fájl nevét: ");
            in_txt = scanner.nextLine();
            System.out.println("Adja meg a kimeneti fájl nevét: ");
            String out_txt = scanner.nextLine();

            TestGame.getInstance().konzolos=false;
            File out = new File(out_txt); // Valahogy teljesen máshogy

            InputParser.executeCommand("load "+in_txt);
            InputParser.executeCommand("end");
        } else {
            TestGame.getInstance().konzolos=true;
            while (running) {
                if (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (line.equalsIgnoreCase("end")) {
                        running = false;
                    } else {
                        InputParser.executeCommand(line);
                    }
                }
            }
        }
    }
}
