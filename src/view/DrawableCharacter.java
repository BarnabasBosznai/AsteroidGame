package view;

import characters.Character;
import places.Asteroid;

public abstract class DrawableCharacter implements Clickable {

    protected boolean clicked;
    protected double radius;
    protected Position pos;

    public abstract void Draw();

    public Asteroid GetAsteroid(){
        return this.GetCharacter().GetAsteroid();
    }

    public abstract Character GetCharacter();

    public void CharacterDied(){
        Controller.getInstance().CharacterDied(this);
    }

    public void CharacterMoved(Asteroid oldAsteroid, Asteroid newAsteroid){
        Controller.getInstance().CharacterMoved(this, oldAsteroid, newAsteroid);
    }

    public void SetPosition(Position pos){
        this.pos = pos;
    }

    @Override
    public void Clicked(Position pos) {
        this.clicked = true;
    }

    @Override
    public void UnClicked() {
        this.clicked = false;
    }

    @Override
    public BoundingCircle GetBoundingCircle() {
        return new BoundingCircle(this.pos, this.radius);
    }
}
