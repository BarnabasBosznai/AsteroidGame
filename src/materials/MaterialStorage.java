package materials;

import Skeleton.Skeleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A nyersanyagok tárolására szolgáló absztrakt osztály
 */
public abstract class MaterialStorage {

    /**
     * A tárolóban lévő nyersanyagok
     */
    protected List<Material> materials;

    public MaterialStorage(){
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.tabIncrement();
        skeleton.Print(this, "create(" + MaterialStorage.class.getSimpleName() + ")");

        this.materials = new ArrayList<>();

        skeleton.tabDecrement();
    }

    /**
     * Hozzáadja a tárolóhoz a paraméterként
     * kapott nyersanyagot
     * @param material
     * @return
     */
    public boolean AddMaterial(Material material) {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.tabIncrement();
        skeleton.Print(this, "AddMaterial(" + material.getClass().getSimpleName() + ")");

        materials.add(material);

        skeleton.tabDecrement();
        return true;
    }

    /**
     * Visszaad egy listát arról, hogy
     * melyik nyersanyagból mennyi található a tárolóban
     * @return
     */
    public Map<Class<? extends Material>, Integer> GetAmountOfMaterials(){
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.tabIncrement();
        skeleton.Print(this, "GetAmountOfMaterials()");

        MaterialCounter materialCounter = new MaterialCounter();

        for(Material material : materials){
            material.Count(materialCounter);
        }

        skeleton.tabDecrement();
        return materialCounter.GetCountedMaterials();
    }

    /**
     *  Visszatér a tárolt nyersanyagok listájával
     * @return
     */
    public List<Material> GetMaterials(){
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.tabIncrement();
        skeleton.Print(this, "GetMaterials()");

        skeleton.tabDecrement();
        return this.materials;
    }
}
