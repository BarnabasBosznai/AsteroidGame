package items;

import Skeleton.Skeleton;
import interfaces.Item;
import materials.Material;
import materials.MaterialStorage;

import java.util.List;

public class Recipe extends MaterialStorage {
    private final Class<? extends Item> itemType;

    public Recipe(Class<? extends Item> itemType, List<Material> materialList){
        super();

        Skeleton skeleton = Skeleton.getInstance();
        skeleton.tabIncrement();
        skeleton.Print(this, "create(" + MaterialStorage.class.getSimpleName() + ")");

        this.itemType = itemType;
        materials = materialList;

        skeleton.tabDecrement();
    }

    public Class<? extends Item> GetItemType(){
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.tabIncrement();
        skeleton.Print(this, "GetItemType()");

        skeleton.tabDecrement();
        return this.itemType;
    }
}
