package materials;

import java.util.HashMap;

public class MaterialCounter {
    private HashMap<String, Integer> amountOfMaterials;

    public void count(String materialType){
        if(amountOfMaterials.containsKey(materialType)){

            Integer amount = amountOfMaterials.get(materialType);
            amountOfMaterials.put(materialType, amount + 1);
        }
        else{
            amountOfMaterials.put(materialType, 1);
        }
    }

    public HashMap<String, Integer> getCountedMaterials(){
        return this.amountOfMaterials;
    }
}
