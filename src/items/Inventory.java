package items;

import Skeleton.Skeleton;
import interfaces.Item;
import materials.Material;
import materials.MaterialStorage;
import places.TeleportGate;

import java.util.ArrayList;
import java.util.List;

public class Inventory extends MaterialStorage {
    private List<Item> items;

    public Inventory(){
        super();

        Skeleton skeleton = Skeleton.getInstance();
        skeleton.tabIncrement();
        skeleton.Print(this, "create(" + MaterialStorage.class.getSimpleName() + ")");

        this.items = new ArrayList<>();

        skeleton.tabDecrement();
    }

    @Override
    public boolean AddMaterial(Material material) {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.tabIncrement();
        skeleton.Print(this, "AddMaterial(" + material.getClass().getSimpleName() + ")");

        String input = skeleton.GetInput("van elég hely? (igen / nem)");
        if(input.equals("igen")) {
            materials.add(material);

            skeleton.tabDecrement();
            return true;
        }
        else{
            skeleton.tabDecrement();
            return false;
        }
    }

    public Material RemoveMaterial(Material material) {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.tabIncrement();
        skeleton.Print(this, "RemoveMaterial(" + material.getClass().getSimpleName() + ")");

        for(Material m : materials){
            if(material.CompatibleWith(m)){
                materials.remove(m);

                skeleton.tabDecrement();
                return m;
            }
        }
        skeleton.tabDecrement();
        return null;
    }

    public void AddItem(Item item) {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.tabIncrement();
        skeleton.Print(this, "AddItem(" + item.getClass().getSimpleName() + ")");

        items.add(item);

        skeleton.tabDecrement();
    }

    public void RemoveItem(Item item) {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.tabIncrement();
        skeleton.Print(this, "RemoveItem(" + item.getClass().getSimpleName() + ")");

        items.remove(item);

        skeleton.tabDecrement();
    }

    public Item GetItem(Class<? extends Item> itemType){
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.tabIncrement();
        skeleton.Print(this, "GetItem(" + itemType.getSimpleName() + ")");

        Item searchHelperItem;
        if(itemType == TeleportGate.class)
            searchHelperItem = new TeleportGate();
        else {
            skeleton.tabDecrement();
            return null;
        }

        for(Item item : items){
            if(item.CompatibleWith(searchHelperItem)) {
                skeleton.tabDecrement();
                return item;
            }
        }

        skeleton.tabDecrement();
        return null;
    }
}
