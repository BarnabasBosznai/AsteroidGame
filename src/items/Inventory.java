package items;

import interfaces.Item;
import materials.Material;
import materials.MaterialStorage;
import places.TeleportGate;

import java.util.ArrayList;
import java.util.List;

public class Inventory extends MaterialStorage {
    private int capacity;
    private final int maxCapacity;
    private List<Item> items;

    public Inventory(){
        super();

        this.capacity = 0;
        this.maxCapacity = 10;
        this.items = new ArrayList<>();
    }

    @Override
    public boolean AddMaterial(Material material) {
        if(capacity < maxCapacity) {
            materials.add(material);
            ++capacity;
            return true;
        }
        else
            return false;
    }

    public Material RemoveMaterial(Material material) {
        for(Material m : materials){
            if(material.CompatibleWith(m)){
                materials.remove(m);
                --capacity;
                return m;
            }
        }
        return null;
    }

    public void AddItem(Item item) {
        items.add(item);
    }

    public void RemoveItem(Item item) {
        items.remove(item);
    }

    public Item GetItem(Class<? extends Item> itemType){
        Item searchHelperItem;
        if(itemType == TeleportGate.class)
            searchHelperItem = new TeleportGate();
        else
            return null;

        for(Item item : items){
            if(item.CompatibleWith(searchHelperItem)) {
                return item;
            }
        }

        return null;
    }
}
