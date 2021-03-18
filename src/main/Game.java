package main;

import characters.Settler;
import interfaces.Steppable;
import materials.Material;
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
        this.settlers = new ArrayList<>();
        this.steppables = new ArrayList<>();
    }

    public void Start() {

    }

    public boolean CheckGameOver() {
        Map<Asteroid, Map<Class<? extends Material>, Integer>> asteroidMaterialAmounts = new HashMap<>();

        for(Settler settler : settlers){
            var asteroid = settler.GetAsteroid();
            var materials = settler.GetInventory().GetAmountOfMaterials();

            if(!asteroidMaterialAmounts.containsKey(asteroid)){
                asteroidMaterialAmounts.put(asteroid, materials);
            }
            else{
                var alreadyCountedMaterials = asteroidMaterialAmounts.get(asteroid);

                //TODO
            }
        }
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
}
