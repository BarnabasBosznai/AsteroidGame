package view;

import characters.Settler;
import places.Asteroid;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AsteroidView extends Drawable implements Clickable {

    private final Asteroid asteroid;
    private final List<DrawableCharacter> drawableCharacterList;

    private final Position pos;
    private static final double asteroidRadius = 5.0;
    private boolean clicked;

    public AsteroidView(Asteroid a, Position pos, int z){
        this.asteroid = a;
        this.pos = pos;
        this.zIndex = z;
        this.clicked = false;
        this.drawableCharacterList = new ArrayList<>();
    }

    @Override
    public void Draw(Graphics2D graphics) {
        //aszteroida magat kirajzolja

        //majd a karakterjeit is
        for(DrawableCharacter dc : drawableCharacterList){
            Position p = this.pos; //vmi nagyon kimatekolt hely
            dc.SetPosition(p);
            dc.Draw();
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
        Controller.getInstance().AsteroidExploded(this);
    }

    @Override
    public void Clicked(Position pos) {
        this.clicked = true;

        //ha a gombra kattintottak
        /*Settler settler = Controller.getInstance().GetCurrentSettlerWaitingForInput();
        Asteroid settlerAsteroid = settler.GetAsteroid();

        if(settlerAsteroid.GetNeighboringAsteroids().contains(this.asteroid)){
            settler.Move(this.asteroid);
            Controller.getInstance().CurrentSettlerWaitingForInput(null);
        }*/
    }

    @Override
    public void UnClicked() {
        this.clicked = false;
    }

    @Override
    public BoundingCircle GetBoundingCircle() {
        return new BoundingCircle(this.pos, AsteroidView.asteroidRadius);
    }

    public List<Clickable> GetClickables(){
        List<Clickable> list = new ArrayList<>(drawableCharacterList);
        list.add(this);

        return list;
    }
}
