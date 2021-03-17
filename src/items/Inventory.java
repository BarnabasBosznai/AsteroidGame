package items;

import interfaces.Item;
import materials.Material;
import materials.MaterialStorage;

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
    public boolean addMaterial(Material material) {
        if(capacity < maxCapacity) {
            materials.add(material);
            ++capacity;
            return true;
        }
        else
            return false;
    }

    public Material removeMaterial(Material material) {
        for(Material m : materials){
            if(material.compatibleWith(m)){
                materials.remove(m);
                return m;
            }
        }
        return null;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }
}
