package materials;

import places.Asteroid;

/**
 * A játékban található nyersanyagok belőle származnak le. Ezeket tudják kibányászni a telepesek, ebből tudnak majd
 * craftolni itemeket,illetve ezek összegyűjtésével lehet majd felépíteni a bázist,ami a játék megnyeréséhez szükséges.
 */
public abstract class Material {
    /**
     * Lereagálja, hogy a nyersanyagot tartalmazó aszteroida napközelbe került.
     * @param asteroid
     */
    public void OnNearSun(Asteroid asteroid) {
    }

    /**
     * Növeli a paraméterként kapott számlálóban a típusához tartozó értéket.
     * @param counter
     */
    public abstract void Count(MaterialCounter counter);

    /**
     * Igazzal tér vissza, ha a paraméterként kapott material ugyanolyan típusú, mint ő, egyébként hamis.
     * @param material
     * @return
     */
    public boolean CompatibleWith(Material material) {
        return this.getClass() == material.getClass();
    }
}
