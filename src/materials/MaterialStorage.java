package materials;

import Skeleton.Skeleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class MaterialStorage {
    protected List<Material> materials;

    public MaterialStorage(){
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.tabIncrement();
        skeleton.Print(this, "create(" + MaterialStorage.class.getSimpleName() + ")");

        this.materials = new ArrayList<>();

        skeleton.tabDecrement();
    }

    public boolean AddMaterial(Material material) {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.tabIncrement();
        skeleton.Print(this, "AddMaterial(" + material.getClass().getSimpleName() + ")");

        materials.add(material);

        skeleton.tabDecrement();
        return true;
    }

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

    public List<Material> GetMaterials(){
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.tabIncrement();
        skeleton.Print(this, "GetMaterials()");

        skeleton.tabDecrement();
        return this.materials;
    }
}
