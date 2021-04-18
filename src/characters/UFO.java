package characters;

import main.Game;
import materials.MaterialStorage;
import places.Place;

import java.util.Random;

public class UFO extends MiningCharacter {

    /**
     * Az ufo inventory-ja, végtelen kapacitású, csak nyersanyagot tud tárolni
     */
    protected MaterialStorage inventory;

    /**
     * Az UFO osztály konstruktora
     */
    public UFO(){
        this.inventory = new MaterialStorage();
    }

    /**
     * Lereagálja, hogy felrobbant az aszteroidája
     */
    @Override
    public void HitByExplosion() {
        Game.getInstance().RemoveSteppable(this);
        asteroid.TakeOff(this);
    }

    /**
     * Az ufo lépésének végrehajtása
     * Ha tud, kilopja az aszteroidában található nyersanyagot, ha nem, megprobál elmenni az aszteroidáról
     */
    @Override
    public void Step() {
        if (!this.Mine(inventory)){
            for(Place place : asteroid.GetNeighbors()){
                if(Move(place))
                    return;
            }
        }
    }
}
