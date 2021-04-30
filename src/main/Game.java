package main;

import characters.Robot;
import characters.Settler;
import characters.UFO;
import interfaces.Steppable;
import materials.Coal;
import materials.Iron;
import materials.Material;
import materials.Uranium;
import materials.WaterIce;
import places.Asteroid;
import view.Controller;

import java.util.*;

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
        //TODO

        Asteroid ast=new Asteroid();
        Asteroid ast2 = new Asteroid();
        Asteroid ast3=new Asteroid();

        ast.AddNeighbor(ast2);
        ast2.AddNeighbor(ast3);
        ast3.AddNeighbor(ast);
        UFO ufo = new UFO(ast2);
        UFO ufo1 = new UFO(ast3);
        UFO ufo2 = new UFO(ast);
        UFO ufo3 = new UFO(ast);
        UFO ufo4 = new UFO(ast3);
        UFO ufo5 = new UFO(ast2);

        this.AddSteppable(ufo);
        this.AddSteppable(ufo1);
        this.AddSteppable(ufo2);
        this.AddSteppable(ufo3);
        this.AddSteppable(ufo4);
        this.AddSteppable(ufo5);

        /*Settler set = new Settler(ast5);
        Settler set2 = new Settler(ast4);
        Settler set3 = new Settler(ast5);
        Settler set4 = new Settler(ast2);
        Settler set5 = new Settler(ast5);
        Settler set6 = new Settler(ast2);
        Settler set7 = new Settler(ast5);
        Settler set8 = new Settler(ast3);*/
        Robot rob = new Robot(ast);

        this.AddSteppable(rob);
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
