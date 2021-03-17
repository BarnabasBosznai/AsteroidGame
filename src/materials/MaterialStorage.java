package materials;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class MaterialStorage {
    protected List<Material> materials;

    public MaterialStorage(){
        this.materials = new ArrayList<>();
    }

    public boolean addMaterial(Material material) {
        materials.add(material);
        return true;
    }

    public HashMap<String, Integer> getAmountOfMaterials(){
        MaterialCounter materialCounter = new MaterialCounter();

        for(Material material : materials){
            material.count(materialCounter);
        }

        return materialCounter.getCountedMaterials();
    }

    public List<Material> getMaterials(){
        return this.materials;
    }
}
