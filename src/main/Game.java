package main;

import Skeleton.Skeleton;
import characters.Settler;
import interfaces.Steppable;
import materials.Coal;
import materials.Iron;
import materials.Material;
import materials.Uranium;
import materials.WaterIce;
import places.Asteroid;
import places.AsteroidBelt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private static Game instance;

    private final List<Settler> settlers;
    private final List<Steppable> steppables;

    public static Game getInstance() {
        if(instance == null)
            instance = new Game();

        return instance;
    }

    private Game(){

        Skeleton skeleton = Skeleton.getInstance();
        skeleton.tabIncrement();
        skeleton.Print(this, "create(" + AsteroidBelt.class.getSimpleName() + ")");
        this.settlers = new ArrayList<>();
        this.steppables = new ArrayList<>();

        skeleton.tabDecrement();
    }

    public void Start() {

    }

    public boolean CheckGameOver() {
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
                return true;
        }

        return false;
    }

   public void AddSteppable(Steppable steppable) {
        this.steppables.add(steppable);
   }

    public void RemoveSteppable(Steppable steppable) {
        this.steppables.remove(steppable);
    }

    public void AddSettler(Settler settler) {
        this.settlers.add(settler);
    }

    public void RemoveSettler(Settler settler) {
        this.settlers.remove(settler);
    }
}
