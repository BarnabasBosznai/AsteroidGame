package view;

import characters.Settler;
import places.Asteroid;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AsteroidView extends Drawable implements Clickable {

    private final Asteroid asteroid;
    private final List<DrawableCharacter> drawableCharacterList;

    private final Position pos;
    private static final int asteroidRadius = 50;
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
        graphics.setColor(Color.RED);
        graphics.fillOval(pos.x, pos.y,asteroidRadius,asteroidRadius);
        //majd a karakterjeit is
        for(DrawableCharacter dc : drawableCharacterList){
            Position p = this.pos; //vmi nagyon kimatekolt hely
            dc.SetPosition(p);
            dc.Draw(graphics);
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
    public boolean ClickedCheck(Position clickPos, Position cameraPos) {
        if (clicked){

        } else {
            double tes = Math.sqrt(Math.pow((clickPos.x - (pos.x - cameraPos.x)),2) + Math.pow(clickPos.y - (pos.y - cameraPos.y),2));
            if (tes<=30){
                return true;
            }
            return false;
        }

        return false;
    }

}
