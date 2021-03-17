package items;

import materials.Material;
import materials.MaterialStorage;

import java.util.List;

public class Recipe extends MaterialStorage {
    private final String itemName;

    public Recipe(String itemName, List<Material> materialList){
        super();

        this.itemName = itemName;
        materials = materialList;
    }

    public String getItemName(){
        return this.itemName;
    }
}
