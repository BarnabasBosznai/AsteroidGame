package characters;

import main.Game;
import materials.MaterialStorage;
import places.Place;

import java.util.Random;

public class UFO extends MiningCharacter {

    protected MaterialStorage inventory;
    public UFO(){
        this.inventory = new MaterialStorage();
    }
    @Override
    public void HitByExplosion() {
        Game.getInstance().RemoveSteppable(this);
        asteroid.TakeOff(this);
    }

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
