package materials;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A nyersanyagok tárolására szolgáló absztrakt osztály
 */
public class MaterialStorage {

    /**
     * A tárolóban lévő nyersanyagok
     */
    protected List<Material> materials;

    public MaterialStorage(){
        this.materials = new ArrayList<>();
    }

    /**
     * Hozzáadja a tárolóhoz a paraméterként
     * kapott nyersanyagot
     * @param material
     * @return
     */
    public boolean AddMaterial(Material material) {
        materials.add(material);
        return false;
    }

    /**
     * Visszaad egy listát arról, hogy
     * melyik nyersanyagból mennyi található a tárolóban
     * @return
     */
    public Map<Class<? extends Material>, Integer> GetAmountOfMaterials(){
        MaterialCounter materialCounter = new MaterialCounter();

        for(Material material : materials){
            material.Count(materialCounter);
        }

        return materialCounter.GetCountedMaterials();
    }

    /**
     *  Visszatér a tárolt nyersanyagok listájával
     * @return
     */
    public List<Material> GetMaterials(){
        return this.materials;
    }
}
