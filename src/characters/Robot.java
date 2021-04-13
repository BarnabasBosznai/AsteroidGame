package characters;

import interfaces.Item;

/**
 * A robotokat reprezentáló osztály. A robotokat a Controller irányítja. Csak fúrásra és
 * szomszédos aszteroidák közötti mozgásra képesek. A telepesek tudják őket craftolni megfelelő
 * mennyiségű nyersanyagból.
 */
public class Robot extends Character implements Item {
    /**
     * A robot lépésének végrehajtása
     */
    @Override
    public void Step() {
        ControlRobot();
    }

    /**
     *  Az aszteroida, amin a robot éppen tartózkodik felrobbant,
     * ezáltal a robot átrobban egy szomszédos aszteroidára
     */
    public void HitByExplosion() {
        while(!Move());
    }

    /**
     *  Irányítja a robotot.Mindaddig fúr amíg el nem éri a magot, ha
     * elérte átmegy egy másik aszteroidára.
     */
    private void ControlRobot() {
        if (asteroid.Drilled()){
            while(!Move());
        }
    }

    /**
     * Igazzal tér vissza, ha a paraméterként kapott item ugyanolyan típusú, mint ő, egyébként hamis.
     * @param item
     * @return
     */
    @Override
    public boolean CompatibleWith(Item item) {
        return this.getClass() == item.getClass();
    }
}
