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

    private Asteroid from;
    private int animationTime;

    public DrawableCharacter(){
        this.pos = new Position(0,0);
        animationTime = 1;
    }

    public abstract void Draw(Graphics2D graphics);

    public Asteroid GetAsteroid(){
        return this.GetCharacter().GetAsteroid();
    }

    public Asteroid GetLastAsteroid() {
        return from;
    }

    public abstract Character GetCharacter();

    public void CharacterDied(){
        Controller.getInstance().CharacterDied(this);
    }

    public void CharacterMoved(Asteroid oldAsteroid, Asteroid newAsteroid){
        Controller.getInstance().CharacterMoved(this, oldAsteroid, newAsteroid);
        from = oldAsteroid;
    }

    public boolean PlayMoveAnimation(Graphics2D g, Position cameraPos, AsteroidView lastPos, AsteroidView newPos) {
        if(animationTime < 120) {
            Position last = new Position(lastPos.GetPos().x, lastPos.GetPos().y);
            Position next = new Position(newPos.GetPos().x, newPos.GetPos().y);

            Position temp = new Position(0, 0);
            temp.x = next.x - last.x;
            temp.y = next.y - last.y;

            float tempX = temp.x;
            float tempY = temp.y;

            temp.x = Math.round(tempX * ((float)animationTime / (float)120));
            temp.y = Math.round(tempY * ((float)animationTime / (float)120));

            temp.x = temp.x + last.x - cameraPos.x;
            temp.y = temp.y + last.y - cameraPos.y;

            animationTime++;
            g.drawImage(img, temp.x, temp.y, 15,15,null);
            return true;
        }
        animationTime = 1;
        newPos.AddDrawableCharacter(this);
        return false;
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
        graphic.rotate(angle, (double)w/(double)2, (double)h/(double)2);
        graphic.drawImage(this.img, null, 0, 0);
        graphic.dispose();
        return rotated;
    }
}
