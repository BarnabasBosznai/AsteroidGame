package view;

import characters.Character;
import places.Asteroid;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class DrawableCharacter {

    protected BufferedImage img;
    protected boolean clicked;
    protected int radius;
    protected Position pos;

    public DrawableCharacter(){
        this.pos = new Position(0,0);
    }

    public abstract void Draw(Graphics2D graphics);

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
        this.pos.x = pos.x;
        this.pos.y = pos.y;
    }
}
