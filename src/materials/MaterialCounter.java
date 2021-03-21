package materials;

import Skeleton.Skeleton;

import java.util.HashMap;
import java.util.Map;

public class MaterialCounter {

    /**
     * A számláláskor az egyes nyersanyagok ezen
     * a listán növelik a hozzájuk tartozó előfordulási számot
     */
    private Map<Class<? extends Material>, Integer> amountOfMaterials;

    public MaterialCounter(){
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.tabIncrement();
        skeleton.Print(this, "create(" + MaterialStorage.class.getSimpleName() + ")");

        this.amountOfMaterials = new HashMap<>();

        skeleton.tabDecrement();
    }

    /**
     * Megnöveli az amountOfMaterials attribútum
     * materialType-hoz tartozó értékét
     * @param materialType
     */
    public void Count(Class<? extends Material> materialType){
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.tabIncrement();
        skeleton.Print(this, "Count(" + materialType.getSimpleName() + ")");

        if(amountOfMaterials.containsKey(materialType)){

            Integer amount = amountOfMaterials.get(materialType);
            amountOfMaterials.put(materialType, amount + 1);
        }
        else{
            amountOfMaterials.put(materialType, 1);
        }

        skeleton.tabDecrement();
    }

    /**
     * Visszaad egy listát arról, hogy melyik
     * nyersanyagból mennyi van
     * @return
     */
    public Map<Class<? extends Material>, Integer> GetCountedMaterials(){
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.tabIncrement();
        skeleton.Print(this, "GetCountedMaterials()");

        skeleton.tabDecrement();
        return this.amountOfMaterials;
    }
}
