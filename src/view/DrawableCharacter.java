package view;

import characters.Character;
import places.Asteroid;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public abstract class DrawableCharacter {

    protected BufferedImage img;
    protected boolean clicked;
    protected int radius;
    protected Position pos;
    protected double angle;

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

    public void SetPosition(Position pos, double ang){
        this.pos.x = pos.x;
        this.pos.y = pos.y;
        this.angle = ang;
    }

    public BufferedImage rotate(double angle) {

        int w = this.img.getWidth();
        int h = this.img.getHeight();

        BufferedImage rotated = new BufferedImage(w, h, this.img.getType());
        Graphics2D graphic = rotated.createGraphics();
        graphic.rotate(angle, w/2, h/2);
        graphic.drawImage(this.img, null, 0, 0);
        graphic.dispose();
        return rotated;
    }
}
