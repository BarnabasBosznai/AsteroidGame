package items;

import characters.Robot;
import characters.Settler;
import interfaces.Item;
import main.Game;
import materials.Material;
import places.Asteroid;
import places.TeleportGate;

import java.util.HashMap;
import java.util.Map;

public class CraftingTable {
    private static CraftingTable instance;

    private final Map<Class<? extends Item>, Recipe> recipes;

    public static CraftingTable getInstance() {
        if(instance == null)
            instance = new CraftingTable();

        return instance;
    }

    private CraftingTable(){
        this.recipes = new HashMap<>();
    }

    public boolean craft(Class<? extends Item> itemType, Settler settler) {
        Inventory inventory = settler.getInventory();
        Recipe recipe = this.recipes.get(itemType);

        boolean hasEnoughMaterial = this.hasEnoughMaterial(inventory, recipe);

        if(!hasEnoughMaterial)
            return false;
        else{
            if(itemType.equals(Robot.class)){
                Robot robot = new Robot();

                Game.getInstance().addSteppable(robot);

                Asteroid asteroid = settler.getAsteroid();
                asteroid.move(robot);

                return true;
            }
            else if(itemType.equals(TeleportGate.class)){
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

        for(var materialType : recipeAmounts.keySet()){
            if(!inventoryAmounts.containsKey(materialType))
                return false;
            if(inventoryAmounts.get(materialType) < recipeAmounts.get(materialType))
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
        this.recipes.put(recipe.getItemType(), recipe);
    }
}
