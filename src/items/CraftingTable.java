package items;

import characters.Robot;
import characters.Settler;
import materials.Material;
import places.Asteroid;
import places.TeleportGate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CraftingTable {
    private Map<String, Recipe> recipes;

    public CraftingTable(){
        this.recipes = new HashMap<>();
    }

    public boolean craft(String itemName, Settler settler) {
        Inventory inventory = settler.getInventory();
        Recipe recipe = this.recipes.get(itemName);

        boolean hasEnoughMaterial = this.hasEnoughMaterial(inventory, recipe);

        if(!hasEnoughMaterial)
            return false;
        else{
            if(itemName.equals("robot")){
                Robot robot = new Robot();

                game.addSteppable(robot);

                Asteroid asteroid = settler.getAsteroid();
                asteroid.move(robot);

                return true;
            }
            else if(itemName.equals("teleportgates")){
                TeleportGate teleportGate1 = new TeleportGate();
                TeleportGate teleportGate2 = new TeleportGate();

                inventory.addItem(teleportGate1);
                inventory.addItem(teleportGate2);

                teleportGate1.setPair(teleportGate2);
                teleportGate2.setPair(teleportGate1);

                return true;
            }
            else{
                return false;
            }
        }
    }

    public boolean hasEnoughMaterial(Inventory inventory, Recipe recipe) {
        var inventoryAmounts = inventory.getAmountOfMaterials();
        var recipeAmounts = recipe.getAmountOfMaterials();

        for(var materialName : recipeAmounts.keySet()){
            if(!inventoryAmounts.containsKey(materialName))
                return false;
            if(inventoryAmounts.get(materialName) < recipeAmounts.get(materialName))
                return false;
        }
        //has enough materials in the inventory if didnt't return

        var recipeMaterials = recipe.getMaterials();

        for(Material material : recipeMaterials){
            inventory.removeMaterial(material);
        }

        return true;
    }

    public void addRecipe(Recipe recipe) {
        this.recipes.put(recipe.getItemName(), recipe);
    }
}
