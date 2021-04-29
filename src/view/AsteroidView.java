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
    private static final int asteroidRadius = 42;
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
        graphics.fillOval(pos.x, pos.y,asteroidRadius*2,asteroidRadius*2);
        //majd a karakterjeit is
        for(int i = 0; i < drawableCharacterList.size(); i++){

            double phi = i * 2 * Math.PI/drawableCharacterList.size();
            Position p = new Position(this.pos.x  + asteroidRadius + (int) (asteroidRadius * Math.cos(phi))  ,this.pos.y + asteroidRadius +(int) (asteroidRadius * Math.sin(phi)));

            drawableCharacterList.get(i).SetPosition(p);
            drawableCharacterList.get(i).Draw(graphics);
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
