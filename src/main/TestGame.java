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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Tesztelést támogató Game osztály
 */
public class TestGame extends Game {

    /**
     * A játékban lévő aszteroidák id szerint kereshetően
     */
    private final Map<String, Asteroid> asteroids;

    /**
     * A játékban lévő telepesek id szerint kereshetően
     */
    private final Map<String, Settler> settlers;

    /**
     * A játékban lévő robotok id szerint kereshetően
     */
    private final Map<String, Robot> robots;

    /**
     * A játékban lévő ufok id szerint kereshetően
     */
    private final Map<String, UFO> ufos;

    /**
     * A játékban lévő teleportkapuk id szerint kereshetően
     */
    private final Map<String, TeleportGate> teleportgates;

    /**
     * A játékban lévő karakterek id szerint kereshetően
     */
    private final Map<String, Character> characterMap;

    /**
     * Konstruktor
     */
    public TestGame() {
        super();

        CraftingTable craftingTable = CraftingTable.getInstance();
        craftingTable.AddRecipe(new Recipe(Robot.class,
                Stream.of(new Coal(), new Iron(), new Uranium()).collect(Collectors.toList())));
        craftingTable.AddRecipe(new Recipe(TeleportGate.class,
                Stream.of(new Iron(), new Iron(), new WaterIce(), new Uranium()).collect(Collectors.toList())));

        this.asteroids = new HashMap<>();
        this.settlers = new HashMap<>();
        this.robots = new HashMap<>();
        this.ufos = new HashMap<>();
        this.teleportgates = new HashMap<>();

        this.characterMap = new HashMap<>();
    }

    /**
     * Inicializálás
     */
    public void Create(){
        settlers.forEach((key, value) -> super.RemoveSettler(value));

        asteroids.clear();
        settlers.clear();
        robots.clear();
        ufos.clear();
        teleportgates.clear();
    }

    /**
     * Hozzáad a játékhoz az objectType-al egyező típusú, objectID id-jű objektumot
     * @param objectType: az objektum típusa
     * @param objectID: az objektum id-je
     */
    public void Add(Class<?> objectType, String objectID){

        if(objectType == Asteroid.class){
            Asteroid asteroid = new Asteroid();

            AsteroidBelt.getInstance().AddAsteroid(asteroid);
            this.asteroids.put(objectID, asteroid);
        }
        else if(objectType == Settler.class){
            Settler settler = new Settler();

            this.settlers.put(objectID, settler);
            this.characterMap.put(objectID, settler);

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
            this.characterMap.put(objectID, robot);

            this.AddSteppable(robot);
        }
        else if(objectType == UFO.class){
            UFO ufo = new UFO();

            this.ufos.put(objectID, ufo);
            this.characterMap.put(objectID, ufo);

            this.AddSteppable(ufo);
        }
    }

    /**
     * Egy karakter rámozog egy aszteroidára
     * @param asteroidID: az aszteroida id-je
     * @param characterID: a karakter id-je
     */
    public void Land(String asteroidID, String characterID){
        Asteroid asteroid = this.asteroids.get(asteroidID);

        Character character = this.characterMap.get(characterID);

        asteroid.Move(character);
    }

    /**
     * Hozzáad egy telepeshez egy teleportkaput
     * @param settlerID: telepes id-je
     * @param teleportID: teleportkapu id-je
     */
    public void AddTeleportGate(String settlerID, String teleportID){
        Settler settler = this.settlers.get(settlerID);
        TeleportGate teleportGate = this.teleportgates.get(teleportID);

        settler.AddItem(teleportGate);
        super.AddSteppable(teleportGate);
    }

    /**
     * Hozzáad egy telepeshez egy nyersanyagot
     * @param settlerID: telepes id-je
     * @param materialType: nyersanyag id-je
     */
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

    /**
     * Beállítja egy ojektum egy attribútumának értékét
     * @param objectID: objketum id-je
     * @param variable: attribútum
     * @param value: érték
     */
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

    /**
     * Összepárosít két teleportkaput
     * @param teleportID1: egyik teleportkapu id-je
     * @param teleportID2: másik teleportkapu id-je
     */
    public void Pair(String teleportID1, String teleportID2){
        TeleportGate teleportGate1 = this.teleportgates.get(teleportID1);
        TeleportGate teleportGate2 = this.teleportgates.get(teleportID2);

        teleportGate1.SetPair(teleportGate2);
        teleportGate2.SetPair(teleportGate1);
    }

    /**
     * Elhelyez egy teleportkaput egy aszteroidán
     * @param teleportID: teleportkapu id-je
     * @param asteroidID: aszteroida id-je
     */
    public void PlaceTeleport(String teleportID, String asteroidID){
        TeleportGate teleportGate = this.teleportgates.get(teleportID);
        Asteroid asteroid = this.asteroids.get(asteroidID);

        asteroid.PlaceTeleport(teleportGate);
    }

    /**
     * Két aszteroidából szomszédokat csinál
     * @param asteroidID1: egyik aszteroida id-je
     * @param asteroidID2: másik aszteroida id-je
     */
    public void SetNeighbours(String asteroidID1, String asteroidID2){
        Asteroid asteroid1 = this.asteroids.get(asteroidID1);
        Asteroid asteroid2 = this.asteroids.get(asteroidID2);

        asteroid1.AddNeighbor(asteroid2);
        asteroid2.AddNeighbor(asteroid1);
    }

    /**
     * Léptet egy objektumot a játékban
     * @param command: parancs (vagy objektum id-je, ha nincsenek paraméterek)
     * @param parameters: paraméterek
     */
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

    /**
     * Kilistázza a játékban lévő aszteroidákat, azok tulajdonságaival
     */
    public void ListAsteroids() {
        int[] counter = {0};
        asteroids.forEach((key, value) -> {
            counter[0]=0;
            if (value.GetMaterial() == null) {
                InputParser.Log("asteroid " + key + " " + value.GetThickness() + " null" + System.getProperty("line.separator"));
            }
            else {
                InputParser.Log("asteroid " + key + " " + value.GetThickness() + " " + value.GetMaterial().Print() + System.getProperty("line.separator"));
            }
            InputParser.Log("neighbor ids:" + System.getProperty("line.separator"));

            asteroids.forEach((key2, value2) -> {
                if(!key.equals(key2) && value2.GetNeighbors().stream().filter(place -> place.equals(value)).findFirst().orElse(null) != null) {
                    InputParser.Log(key2 + " ");
                    counter[0]++;
                }
            });
            if(counter[0] == 0) {
                InputParser.Log("-");
            }
            InputParser.Log(System.getProperty("line.separator"));

            counter[0] = 0;
            InputParser.Log("teleportgates’s pair’s asteroid’s id: " + System.getProperty("line.separator"));
            teleportgates.forEach((key2, value2) -> {
                value.GetTeleportGates().forEach(tg -> {
                    if(value2.equals(tg)) {
                        if(tg.GetPair() != null && tg.GetPair().GetAsteroid() != null) {
                            asteroids.entrySet().stream().filter(entry -> tg.GetPair().GetAsteroid().equals(entry.getValue())).map(Map.Entry::getKey).findFirst().ifPresent(InputParser::Log);
                            counter[0]++;
                        }
                    }
                });
            });
            if(counter[0] == 0) {
                InputParser.Log("-");
            }
            InputParser.Log(System.getProperty("line.separator"));

            counter[0] = 0;
            InputParser.Log("character ids:" + System.getProperty("line.separator"));
            characterMap.forEach((key2, value2) -> {
                value.GetCharacters().forEach(character -> {
                    if(value2.equals(character)) {
                        InputParser.Log(key2+" ");
                        counter[0]++;
                    }
                });
            });
            if(counter[0] == 0) {
                InputParser.Log("-");
            }
            InputParser.Log(System.getProperty("line.separator"));

            Map<Class<? extends Material>, Integer> matsOnAsteroid = new HashMap<>();
            InputParser.Log("total amount of materials by material on asteroid:" + System.getProperty("line.separator"));
            settlers.forEach((key2, value2) -> {
                if(value.GetCharacters().stream().filter(character -> character.equals(value2)).findFirst().orElse(null) != null)
                    value2.GetInventory().GetAmountOfMaterials().forEach(matsOnAsteroid::put);
            });
            if(matsOnAsteroid.containsKey(Coal.class)) {
                InputParser.Log(matsOnAsteroid.get(Coal.class) + " ");
            } else {
                InputParser.Log("0 ");
            }

            if(matsOnAsteroid.containsKey(Iron.class)) {
                InputParser.Log(matsOnAsteroid.get(Iron.class) + " ");
            } else {
                InputParser.Log("0 ");
            }

            if(matsOnAsteroid.containsKey(WaterIce.class)) {
                InputParser.Log(matsOnAsteroid.get(WaterIce.class) + " ");
            } else {
                InputParser.Log("0 ");
            }

            if(matsOnAsteroid.containsKey(Uranium.class)) {
                InputParser.Log(matsOnAsteroid.get(Uranium.class) + System.getProperty("line.separator"));
            } else {
                InputParser.Log("0 " + System.getProperty("line.separator"));
            }
            InputParser.Log(System.getProperty("line.separator"));
        });
    }

    /**
     * Kilistázza a játékban lévő telepeseket, azok tulajdonságaival
     */
    public void ListSettlers(){
        settlers.forEach((key, value) -> {
            InputParser.Log("settler " + key + " " //+ System.getProperty("line.separator")
                     );
            asteroids.entrySet().stream().filter(entry -> value.GetAsteroid().equals(entry.getValue())).map(Map.Entry::getKey).findFirst().ifPresent(InputParser::Log);
            InputParser.Log(System.getProperty("line.separator"));

            InputParser.Log("materials:" + System.getProperty("line.separator"));
            var mats = value.GetInventory().GetAmountOfMaterials();
            if(mats.containsKey(Coal.class)) {
                InputParser.Log(mats.get(Coal.class) + " ");
            } else {
                InputParser.Log("0 ");
            }

            if(mats.containsKey(Iron.class)) {
                InputParser.Log(mats.get(Iron.class) + " ");
            } else {
                InputParser.Log("0 ");
            }

            if(mats.containsKey(WaterIce.class)) {
                InputParser.Log(mats.get(WaterIce.class) + " ");
            } else {
                InputParser.Log("0 ");
            }

            if(mats.containsKey(Uranium.class)) {
                InputParser.Log(mats.get(Uranium.class) + " ");
            } else {
                InputParser.Log("0 ");
            }
            InputParser.Log(System.getProperty("line.separator"));

            InputParser.Log("teleportgate ids:" + System.getProperty("line.separator"));
            int[] counter = {0};
            teleportgates.forEach((key2, value2) -> {
                value.GetInventory().GetTeleportGates().forEach(teleportGate -> {
                    if(teleportGate.equals(value2)) {
                        InputParser.Log(key2 + " ");
                    }
                    counter[0]++;
                });
            });
            if (counter[0] == 0) {
                InputParser.Log("-" + System.getProperty("line.separator"));
            }
            InputParser.Log(System.getProperty("line.separator"));
        });
    }

    /**
     * Kilistázza a játékban lévő robotokat, azok tulajdonságaival
     */
    public void ListRobots(){
        if(this.robots.size() == 0){
            InputParser.Log("robot - -" + System.getProperty("line.separator"));
            InputParser.Log(System.getProperty("line.separator"));
            return;
        }
        for(String robotID : robots.keySet()){
            Asteroid asteroid = this.robots.get(robotID).GetAsteroid();
            String asteroidID = "";

            for(var entry : asteroids.entrySet()){
                if(entry.getValue().equals(asteroid))
                    asteroidID = entry.getKey();
            }
            InputParser.Log("robot " + robotID + " " + asteroidID + System.getProperty("line.separator"));

        }
        InputParser.Log(System.getProperty("line.separator"));
    }

    /**
     * Kilistázza a játékban lévő ufokat, azok tulajdonságaival
     */
    public void ListUFOs(){
        if(this.ufos.size() == 0){
            InputParser.Log("ufo - -");
            InputParser.Log(System.getProperty("line.separator"));
            return;
        }
        for(String ufoID : ufos.keySet()){
            Asteroid asteroid = this.ufos.get(ufoID).GetAsteroid();
            String asteroidID = "";

            for(var entry : asteroids.entrySet()){
                if(entry.getValue().equals(asteroid))
                    asteroidID = entry.getKey();
            }
            InputParser.Log("ufo " + ufoID + " " + asteroidID + System.getProperty("line.separator"));

        }
        InputParser.Log(System.getProperty("line.separator"));
    }

    /**
     * Kilistázza a játékban lévő teleportkapukat, azok tulajdonságaival
     */
    public void ListTeleportGates(){
        if(this.teleportgates.size() == 0){
            InputParser.Log("teleportgate - -" + System.getProperty("line.separator"));
            InputParser.Log(System.getProperty("line.separator"));
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
            InputParser.Log("teleportgate " + teleportgateID + " " + asteroidID + " " + crazy + System.getProperty("line.separator"));
        }
        InputParser.Log(System.getProperty("line.separator"));
    }

    /**
     * Kirja a játék állapotát
     */
    public void GameStatus(){
        GameState gameStatus = this.CheckGameStatus();

        String gameStatusString;
        switch (gameStatus) {
            case SETTLERSLOST:
                gameStatusString = "settlers lost";
                break;
            case SETTLERSWON:
                gameStatusString = "settlers won";
                break;
            default:
                gameStatusString = "not ended";
                break;
        }
        InputParser.Log("game " + gameStatusString + System.getProperty("line.separator"));
    }

    /**
     * Eltávolít egy steppable-t a játékból
     * @param steppable: az eltávolítandó steppable
     */
    @Override
    public void RemoveSteppable(Steppable steppable){
        Class<? extends Steppable> type = steppable.getClass();

        if (Settler.class.equals(type)) {
            this.settlers.values().remove(steppable);
            this.characterMap.values().remove(steppable);

            super.RemoveSteppable(steppable);
            super.RemoveSettler((Settler)steppable);
        }
        if (Robot.class.equals(type)) {
            this.robots.values().remove(steppable);
            this.characterMap.values().remove(steppable);

            super.RemoveSteppable(steppable);
        }
        if (UFO.class.equals(type)) {
            this.ufos.values().remove(steppable);
            this.characterMap.values().remove(steppable);

            super.RemoveSteppable(steppable);
        }
        if (TeleportGate.class.equals(type)) {
            this.teleportgates.values().remove(steppable);

            super.RemoveSteppable(steppable);
        }
    }

    public void CraftTeleportGateHelper(Robot robot) {
        robots.put("tempKey", robot);
    }

    /**
     * Eltávolít egy aszteroidát a nyilvántartásból
     * @param asteroid: aszteroida
     */
    public void RemoveAsteroid(Asteroid asteroid){
        this.asteroids.values().remove(asteroid);
    }

    /**
     * Main
     * @param args: args
     */
    public static void main(String[] args) {
        TestGame.getInstance();
        boolean running = true;
        Scanner scanner = new Scanner(System.in);

        while(running) {
            System.out.println("Fájlból történő tesztelés(0), konzolból történő tesztelés(1) vagy kilépés(2): ");
            try {
                int input = Integer.parseInt(scanner.nextLine());
                if(input == 0) {
                    InputParser.SetConsoleLog(false);
                    System.out.println("Adja meg a bemeneti fájl nevét: ");
                    String in_txt = scanner.nextLine();
                    System.out.println("Adja meg a kimeneti fájl nevét: ");
                    InputParser.SetOutputFile(scanner.nextLine());
                    InputParser.executeCommand("load " + in_txt);
                    System.out.println("Teszt befejeződött!");
                } else if(input == 1) {
                    InputParser.SetConsoleLog(true);
                    System.out.println("Adja meg a parancsokat!");
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        if (!line.equalsIgnoreCase("end")) {
                            InputParser.executeCommand(line);
                        } else {
                            break;
                        }
                    }
                } else if(input == 2) {
                    running = false;
                } else {
                    System.out.println("Hibás választás! Próbáld újra!");
                }
            } catch (Exception e) {
                System.out.println("Hibás választás! Próbáld újra!");
            }
        }
    }
}
