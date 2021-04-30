package main;

import characters.Settler;
import interfaces.Steppable;
import materials.Coal;
import materials.Iron;
import materials.Material;
import materials.Uranium;
import materials.WaterIce;
import places.Asteroid;
import places.AsteroidBelt;
import view.Controller;
import view.Position;

import java.util.*;
import java.util.stream.Collectors;

/**
 *A Game osztály felelős a játék működéséért, ő tárolja kollektíven a játékban résztvevő
 * entitásokat, valamint ő kérdezgeti a lépésre képes résztvevőit a játéknak, hogy mit csinálnak a
 * jelenlegi körben.
 */
public class Game {
    /**
     * A tesztelhető Game osztály egyetlen példánya
     */
    private static Game instance;

    /**
     * A játékban található összes telepes (settler).
     */
    private final List<Settler> settlers;

    /**
     * A játékban található lépésre képes (steppable) entitások.
     */
    private final Queue<Steppable> steppables;

    /**
     * Visszatér a tesztelhető Game osztály egyetlen objetumával
     * @return testgame: az egyetlen TestGame objektum
     */
    
    public static Game getInstance() {
        if(instance == null)
            instance = new Game();

        return instance;
    }

    /**
     * Konstruktor
     */
    public Game(){
        this.settlers = new ArrayList<>();
        this.steppables = new ArrayDeque<>();
    }

    /**
     * Elvégzi a játék inicializálását, majd elindítja a játékot.
     */
    public void Start() {
        this.Init();

        this.NextStep();
    }

    private void Init(){
        Random random = new Random();
        List<Position> positions = new ArrayList<>();
        List<Asteroid> asteroids = new ArrayList<>();

        int[] p = new int[2];
        for(int i = 0; i < 50; i++) {
            do {
                p[0] = random.nextInt(2000 + 2000) - 2000;
                p[1] = random.nextInt(2000 + 2000) - 2000;

                // Magic constant a sugarhoz, azert nem 42 + 42 hogy kicsit tavolabb legyenek egymastol
            } while (positions.stream().anyMatch(position -> Math.pow(p[0] - position.x, 2) + Math.pow(p[1] - position.y, 2) <= Math.pow(42 + 50, 2)));

            Position pos = new Position(p[0], p[1]);
            Asteroid ast = new Asteroid();

            positions.add(pos);
            asteroids.add(ast);
            AsteroidBelt.getInstance().AddAsteroid(ast);

            Controller.getInstance().AddAsteroidView(ast, pos);
        }

        for(Asteroid ast : asteroids) {
            List<Double> distances = asteroids.stream()
                    .map(asteroid ->
                            Math.sqrt(Math.pow(positions.get(asteroids.indexOf(ast)).x + positions.get(asteroids.indexOf(asteroid)).x, 2) + Math.pow(positions.get(asteroids.indexOf(ast)).y + positions.get(asteroids.indexOf(asteroid)).y, 2))).collect(Collectors.toList());

            for(int i = 0; i < 5; i++) {
                Double min = distances.stream().mapToDouble(d -> d).min().getAsDouble();
                    Asteroid neighbor = asteroids.get(distances.indexOf(min));
                    if(ast.GetNeighboringAsteroids().size() < 5 && neighbor.GetNeighboringAsteroids().size() < 5) {
                        ast.AddNeighbor(neighbor);
                        neighbor.AddNeighbor(ast);
                    }
                distances.remove(min);
            }
        }


       // UFO ufo = new UFO(ast2);
       // UFO ufo1 = new UFO(ast3);
       // UFO ufo2 = new UFO(ast);
        //UFO ufo3 = new UFO(ast);
        //UFO ufo4 = new UFO(ast3);
        //UFO ufo5 = new UFO(ast2);

        //this.AddSteppable(ufo);
        //this.AddSteppable(ufo1);
        //this.AddSteppable(ufo2);
        //this.AddSteppable(ufo3);
        //this.AddSteppable(ufo4);
        //this.AddSteppable(ufo5);

        /*Settler set = new Settler(ast5);
        Settler set2 = new Settler(ast4);
        Settler set3 = new Settler(ast5);
        Settler set4 = new Settler(ast2);
        Settler set5 = new Settler(ast5);
        Settler set6 = new Settler(ast2);
        Settler set7 = new Settler(ast5);
        Settler set8 = new Settler(ast3);*/


        //Robot rob = new Robot(ast);
        //this.AddSteppable(rob);
    }

    public double findTheNthSmallestElement(double[] arr, int nThSmallest, int low, int high) {
        if (low < high) {
            int pivot = lomutoPartition(low, high, arr);
            if (pivot == nThSmallest) {
                return arr[pivot];
            }
            if (nThSmallest > pivot) {
                return findTheNthSmallestElement(arr, nThSmallest, pivot + 1, high);
            }
            return findTheNthSmallestElement(arr, nThSmallest, low, pivot - 1);
        }
        return -1;
    }

    public int lomutoPartition(int low, int high, double[] arr) {
        double pivot = arr[high];
        int j = low;
        for (int i = low; i < high; i++) {
            if (arr[i] < pivot) {
                double temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                ++j;
            }
        }
        double temp = arr[high];
        arr[high] = arr[j];
        arr[j] = temp;
        return j;
    }

    public void NextStep(){
        //lehet felesleges, elfer
        if(steppables.size() == 0) {
            Controller.getInstance().GameEnded(GameState.SETTLERSLOST);
            return;
        }

        Steppable currentSteppable = this.steppables.poll();
        this.steppables.add(currentSteppable);
        currentSteppable.Step();

        //check
        GameState currentGameState = this.CheckGameStatus();
        if(currentGameState != GameState.NOTENDED){
            Controller.getInstance().GameEnded(currentGameState);
        }

        Controller.getInstance().StepEnded();
    }

    /**
     * Ellenőrzi, hogy véget ért-e a játék. Ha a telepesek
     * nyertek (felépült a bázis), vesztetettek (minden
     * telepes meghalt, vagy megnyerhetetlenné vált a játék),
     * vagy még nem ért véget
     * @return SETTLERSLOST: a telepesek vesztettek, SETTLERSWON: a telepesek nyertek, NOTENDED: még nem ért véget
     */
    public GameState CheckGameStatus() {
        if(this.settlers.size() == 0)
            return GameState.SETTLERSLOST;
        Map<Asteroid, Map<Class<? extends Material>, Integer>> asteroidMaterialAmounts = new HashMap<>();

        //counting the current materialAmounts of all asteroids in asteroidbelt
        for(Settler settler : settlers){
            var asteroid = settler.GetAsteroid();
            var settlerAmounts = settler.GetInventory().GetAmountOfMaterials();

            if(!asteroidMaterialAmounts.containsKey(asteroid)){
                asteroidMaterialAmounts.put(asteroid, settlerAmounts);
            }
            else{
                var alreadyCountedMaterials = asteroidMaterialAmounts.get(asteroid);

                //merge alreadyCountedMaterials and settlerAmounts
                for(var materialType : settlerAmounts.keySet()){
                    Integer settlerAmount = settlerAmounts.get(materialType);

                    if(!alreadyCountedMaterials.containsKey(materialType)){
                        alreadyCountedMaterials.put(materialType, settlerAmount);
                    }
                    else{
                        Integer alreadyCountedAmount = alreadyCountedMaterials.get(materialType);
                        alreadyCountedMaterials.put(materialType, settlerAmount + alreadyCountedAmount);
                    }
                }

                asteroidMaterialAmounts.put(asteroid, alreadyCountedMaterials);
            }
        }

        //iterate through asteroids, decide if one has enough materials

        Map<Class<? extends Material>, Integer> goalAmount = new HashMap<>();
        goalAmount.put(Coal.class, 3);
        goalAmount.put(Iron.class, 3);
        goalAmount.put(Uranium.class, 3);
        goalAmount.put(WaterIce.class, 3);

        for(Asteroid asteroid : asteroidMaterialAmounts.keySet()) {
            var currentAmounts = asteroidMaterialAmounts.get(asteroid);

            boolean hasEnough = true;

            for (var materialType : goalAmount.keySet()) {
                if (!currentAmounts.containsKey(materialType)) {
                    hasEnough = false;
                    break;
                }
                if (currentAmounts.get(materialType) < goalAmount.get(materialType)) {
                    hasEnough = false;
                    break;
                }
            }

            if(hasEnough)
                return GameState.SETTLERSWON;
        }

        return GameState.NOTENDED;
    }

    /**
     * Felveszi a paraméterként kapott
     * steppablet a nyilvántartásba.
     * @param steppable: a hozzáadandó steppable
     */
    public void AddSteppable(Steppable steppable) {
        this.steppables.add(steppable);
    }

    /**
     *  Eltávolítja a paraméterként kapott
     * steppable-t a nyilvántartásból.
     * @param steppable: az eltávolítandó steppable
     */
    public void RemoveSteppable(Steppable steppable) {
        this.steppables.remove(steppable);
    }

    /**
     * Hozzáad egy settlert a nyilvántartásba.
     * @param settler: a hozzáadandó settler
     */
    public void AddSettler(Settler settler) {
        this.settlers.add(settler);
    }

    /**
     *  Eltávolítja a paraméterként kapott settler-t a
     * settlerek nyilvántartásából.
     * @param settler: az eltávolítandó settler
     */
    public void RemoveSettler(Settler settler) {
        this.settlers.remove(settler);
    }
}
