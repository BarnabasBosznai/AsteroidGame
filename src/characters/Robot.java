package characters;

import Skeleton.Skeleton;
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
        Skeleton.getInstance().tabIncrement();
        Skeleton.getInstance().Print(this, "Step()");

        ControlRobot();

        Skeleton.getInstance().tabDecrement();
    }

    /**
     *  Az aszteroida, amin a robot éppen tartózkodik felrobbant,
     * ezáltal a robot átrobban egy szomszédos aszteroidára
     */
    public void HitByExplosion() {
        Skeleton.getInstance().tabIncrement();
        Skeleton.getInstance().Print(this, "HitByExplosion()");

        while(!Move());

        Skeleton.getInstance().tabDecrement();
    }

    /**
     *  Irányítja a robotot.Mindaddig fúr amíg el nem éri a magot, ha
     * elérte átmegy egy másik aszteroidára.
     */
    private void ControlRobot() {
        Skeleton.getInstance().tabIncrement();
        Skeleton.getInstance().Print(this, "ControlRobot()");

        if (asteroid.Drilled()){
            while(!Move());
        }

        Skeleton.getInstance().tabDecrement();
    }

    /**
     * Igazzal tér vissza, ha a paraméterként kapott item ugyanolyan típusú, mint ő, egyébként hamis.
     * @param item
     * @return
     */
    @Override
    public boolean CompatibleWith(Item item) {
        Skeleton.getInstance().tabIncrement();
        Skeleton.getInstance().Print(this, "CompatibleWith("+item.getClass().getSimpleName()+")");

        Skeleton.getInstance().tabDecrement();
        return this.getClass() == item.getClass();
    }
}
