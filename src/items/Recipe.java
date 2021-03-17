package items;

import interfaces.Item;
import materials.Material;
import materials.MaterialStorage;

import java.util.List;

public class Recipe extends MaterialStorage {
    private final Class<? extends Item> itemType;

    public Recipe(Class<? extends Item> itemType, List<Material> materialList){
        super();

        this.itemType = itemType;
        materials = materialList;
    }

    public Class<? extends Item> getItemType(){
        return this.itemType;
    }
}
