package materials;

import Skeleton.Skeleton;

import java.util.HashMap;
import java.util.Map;

public class MaterialCounter {
    private Map<Class<? extends Material>, Integer> amountOfMaterials;

    public MaterialCounter(){
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.tabIncrement();
        skeleton.Print(this, "create(" + MaterialStorage.class.getSimpleName() + ")");

        this.amountOfMaterials = new HashMap<>();

        skeleton.tabDecrement();
    }

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

    public Map<Class<? extends Material>, Integer> GetCountedMaterials(){
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.tabIncrement();
        skeleton.Print(this, "GetCountedMaterials()");

        skeleton.tabDecrement();
        return this.amountOfMaterials;
    }
}
