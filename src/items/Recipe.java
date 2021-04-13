package items;

import interfaces.Item;
import materials.Material;
import materials.MaterialStorage;

import java.util.List;

/**
 * Egy készíthető Itemhez szükséges alapanyagok mennyiségének feljegyzése
 */
public class Recipe extends MaterialStorage {
    /**
     * A recepthez tartozó Item típusa
     */
    private final Class<? extends Item> itemType;

    public Recipe(Class<? extends Item> itemType, List<Material> materialList){
        super();
        this.itemType = itemType;
        materials = materialList;
    }

    /**
     * Visszatér a recepthez tartozó Item típusával
     * @return
     */
    public Class<? extends Item> GetItemType(){
        return this.itemType;
    }
}
