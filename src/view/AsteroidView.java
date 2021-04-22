package view;

import characters.Character;
import places.Asteroid;

import java.util.ArrayList;
import java.util.List;

public class AsteroidView extends Drawable {

    private final Asteroid asteroid;
    private final Position pos;
    private final List<DrawableCharacter> drawableCharacterList;

    public AsteroidView(Asteroid a, Position pos, int z){
        this.asteroid = a;
        this.pos = pos;
        this.zIndex = z;
        this.drawableCharacterList = new ArrayList<>();
    }

    @Override
    public void Draw() {
        //aszteroida magat kirajzolja

        //majd a karakterjeit is
        for(DrawableCharacter dc : drawableCharacterList){
            Position p = this.pos; //vmi nagyon kimatekolt hely
            dc.Draw(p);
        }
    }

    public Asteroid GetAsteroid(){
        return this.asteroid;
    }

    public void RemoveDrawableCharacter(DrawableCharacter dc){
        this.drawableCharacterList.remove(dc);
    }

    public void AddDrawableCharacter(DrawableCharacter dc){
        this.drawableCharacterList.add(dc);
    }

    public void AsteroidExploded(){
        View.getInstance().AsteroidExploded(this);
    }

}
