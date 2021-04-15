package items;

import interfaces.Item;
import materials.*;
import places.TeleportGate;

import java.util.ArrayList;
import java.util.List;

/**
 * A telepes itt tárolja a nála lévő nyersanyagokat, illetve megcraftolt eszközöket. Maximum 10
 * db nyersanyagot képes eltárolni
 */
public class Inventory extends MaterialStorage {

    /**
     * Az inventoryban tárolt craftolt eszközök
     */
    private List<Item> items;

    public Inventory(){
        super();
        this.items = new ArrayList<>();
    }

    /**
     * Megpróbálja hozzáadni a paraméterként
     * kapott nyersanyagot az inventoryhoz. Ha nincs elég hely, akkor a visszatérési érték
     * False, ha sikerült a hozzáadás, akkor True
     * @param material
     * @return
     */
    @Override
    public boolean AddMaterial(Material material) {

        if(materials.size() < 10) {
            super.AddMaterial(material);
            return true;
        }
        else
            return false;
    }

    /**
     * Visszatér a paraméternek megfelelő
     * nyersanyaggal, ha megtalálja azt az inventory-ban, egyébként null-al
     * @param material
     * @return
     */
    public Material RemoveMaterial(Material material) {
        for(Material m : materials){
            if(material.CompatibleWith(m)){
                materials.remove(m);
                return m;
            }
        }
        return null;
    }

    /**
     * Felveszi a paraméterként kapott craftolt tárgyat(ami nem
     * nyersanyag) az inventoryba
     * @param item
     */
    public void AddItem(Item item) {
        items.add(item);
    }

    /**
     * Eltávolítja a paraméterként kapott craftolt tárgyat(ami
     * nem nyersanyag) az inventoryból
     * @param item
     * @return
     */
    public void RemoveItem(Item item) {
        items.remove(item);
    }

    /**
     * Megkeresi és visszatér a paraméterként kapott típusnak megfelelő Item-el az
     * inventory-ból. Ha nem talált ilyet, akkor null-al.
     * @param itemType
     * @return
     */
    public Item GetItem(Class<? extends Item> itemType){
        Item searchHelperItem;
        if(itemType == TeleportGate.class)
            searchHelperItem = new TeleportGate();
        else {
            return null;
        }

        for(Item item : items){
            if(item.CompatibleWith(searchHelperItem)) {
                return item;
            }
        }

        return null;
    }

    public int GetNumberOfItems(Class<? extends Item> itemType) {

        Item searchHelperItem;
        if(itemType == TeleportGate.class)
            searchHelperItem = new TeleportGate();
        else
            return 0;

        int count = 0;
        for(Item item : items) {
            if(item.CompatibleWith(searchHelperItem)) {
               ++count;
            }
        }

        return count;
    }

    public List<TeleportGate> GetTeleportGates() {
        List<TeleportGate> teleportGates = new ArrayList<>();
        TeleportGate tg = new TeleportGate();
        for(Item item : items) {
            if(item.CompatibleWith(tg))
                teleportGates.add((TeleportGate)item);
        }

        return teleportGates;
    }
}
