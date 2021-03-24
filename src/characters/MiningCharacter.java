package characters;

import Skeleton.Skeleton;
import materials.Material;
import materials.MaterialStorage;

public abstract class MiningCharacter extends Character {

    /**
     * A telepes megpróbálja kinyeri az aszteroidában található
     * nyersanyagot. Ha az aszteroida magja üres, akkor False a visszatérési érték, ha sikerült
     * a bányászás, akkor True
     * @return
     */
    public boolean Mine(MaterialStorage inventory) {
        Skeleton.getInstance().tabIncrement();
        Skeleton.getInstance().Print(this, "Mine()");

        Material material = asteroid.RemoveMaterial();

        if (material!=null){

            if (inventory.AddMaterial(material)) {

                asteroid.PlaceMaterial(material);
            }

            else {

                Skeleton.getInstance().tabDecrement();
                return true;
            }
        }

        Skeleton.getInstance().tabDecrement();
        return false;
    }
}
