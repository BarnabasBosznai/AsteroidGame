package view;

import characters.Character;
import places.Asteroid;

public abstract class DrawableCharacter {

    public abstract void Draw(Position pos);

    public Asteroid GetAsteroid(){
        return this.GetCharacter().GetAsteroid();
    }

    public abstract Character GetCharacter();

    public void CharacterDied(){
        View.getInstance().CharacterDied(this);
    }

    public void CharacterMoved(Asteroid oldAsteroid, Asteroid newAsteroid){
        View.getInstance().CharacterMoved(this, oldAsteroid, newAsteroid);
    }
}
