package characters;

import main.Game;
import materials.MaterialStorage;

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
        if (this.Mine(inventory)){
            // TODO: woozy_face
            while(!Move(this.asteroid.GetNeighbors().get(new Random().nextInt(this.asteroid.GetNeighbors().size() - 1))));
        }
    }
}
