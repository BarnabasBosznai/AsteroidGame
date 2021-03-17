package materials;

import java.util.HashMap;
import java.util.Map;

public class MaterialCounter {
    private Map<String, Integer> amountOfMaterials;

    public MaterialCounter(){
        this.amountOfMaterials = new HashMap<>();
    }

    public void count(String materialType){
        if(amountOfMaterials.containsKey(materialType)){

            Integer amount = amountOfMaterials.get(materialType);
            amountOfMaterials.put(materialType, amount + 1);
        }
        else{
            amountOfMaterials.put(materialType, 1);
        }
    }

    public Map<String, Integer> getCountedMaterials(){
        return this.amountOfMaterials;
    }
}
