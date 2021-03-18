package materials;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class MaterialStorage {
    protected List<Material> materials;

    public MaterialStorage(){
        this.materials = new ArrayList<>();
    }

    public boolean AddMaterial(Material material) {
        materials.add(material);
        return true;
    }

    public Map<Class<? extends Material>, Integer> GetAmountOfMaterials(){
        MaterialCounter materialCounter = new MaterialCounter();

        for(Material material : materials){
            material.Count(materialCounter);
        }

        return materialCounter.GetCountedMaterials();
    }

    public List<Material> GetMaterials(){
        return this.materials;
    }
}
