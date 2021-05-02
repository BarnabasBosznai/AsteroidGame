package view;

import characters.Settler;
import places.Asteroid;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AsteroidView extends Drawable implements Clickable {

    private final Asteroid asteroid;
    private final List<DrawableCharacter> drawableCharacterList;

    private final Position pos;
    private static final int asteroidRadius = 42;
    private boolean clicked;

    private double angle;
    public AsteroidView(Asteroid a, Position pos, int z){
        this.asteroid = a;
        this.pos = pos;
        this.zIndex = z;
        this.clicked = false;
        this.drawableCharacterList = new ArrayList<>();
        this.angle=new Random().nextDouble()*2*Math.PI;
        try{
            //Beolvasas utan automatikusan bezarodnak a fajlok az ImageIO-nal
            this.img= ImageIO.read(new File("aszteroida.png"));
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public Position GetPos(){
        return pos;
    }


    public void Draw_Neighbours_and_Teleports(Graphics2D graphics, Position cameraPos){
        var asteroidviews = new ArrayList<AsteroidView>();
        var asteroids = this.GetAsteroid().GetNeighboringAsteroids();
        for (Asteroid ast: asteroids) {
            asteroidviews.add(Controller.getInstance().GetAsteroidView(ast));
        }
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.setStroke(new BasicStroke(3));
        if (asteroidviews==null)
            System.out.println("A1");
        if (asteroidviews.isEmpty())
            System.out.println("A2");
        for (var asteroidv: asteroidviews) {
            if (pos==null)
                System.out.println("A3");
            if (cameraPos==null)
                System.out.println("A4");
            if (asteroidv==null) {
                System.out.println("A5"+pos.x+" "+pos.y);
            }
            if (asteroidv.pos==null)
                System.out.println("A6");
            if (asteroidv!=null)
                graphics.drawLine(pos.x- cameraPos.x + 50, pos.y -cameraPos.y + 50, asteroidv.pos.x - cameraPos.x + 50,  asteroidv.pos.y - cameraPos.y + 50);
        }
    }

    @Override
    public void Draw(Graphics2D graphics, Position cameraPos) {
        if (!(pos.x >cameraPos.x-100 && pos.x < cameraPos.x+1000))
            return;
        if (!(pos.y >cameraPos.y-100 && pos.y < cameraPos.y+600))
            return;
        //aszteroida magat kirajzolja

        //graphics.setColor(Color.RED);
        //graphics.fillOval(pos.x, pos.y,asteroidRadius*2,asteroidRadius*2);


        graphics.drawImage(rotate(angle),pos.x - cameraPos.x , pos.y  - cameraPos.y ,asteroidRadius*2,asteroidRadius*2,null);
        //majd a karakterjeit is
        for(int i = 0; i < drawableCharacterList.size(); i++){

            double phi = i * 2 * Math.PI/drawableCharacterList.size();
            phi -= Math.PI/2;
            Position p = new Position(this.pos.x  - cameraPos.x  + asteroidRadius + (int) ((asteroidRadius +12) * Math.cos(phi))  ,this.pos.y  - cameraPos.y  + asteroidRadius +(int) ((asteroidRadius +12) * Math.sin(phi)));

            drawableCharacterList.get(i).SetPosition(p,phi + Math.PI/2);
            drawableCharacterList.get(i).Draw(graphics);
        }

        if (clicked){
            Color faded = new Color(255,255,255,220);
            graphics.setColor(faded);
            graphics.fillRect(pos.x-cameraPos.x-70, pos.y-cameraPos.y, 70,65);
            graphics.setColor(Color.WHITE);
            graphics.drawRect(pos.x-cameraPos.x-70, pos.y-cameraPos.y, 70,65);

            graphics.setColor(Color.BLACK);

            graphics.drawString("Layers: "+asteroid.GetThickness(),pos.x-cameraPos.x-64,pos.y-cameraPos.y+18);

            graphics.drawString("Material:",pos.x-cameraPos.x-64,pos.y-cameraPos.y+38);
            if (asteroid.GetThickness()==0) {
                if (asteroid.GetMaterial() != null)
                    graphics.drawString(asteroid.GetMaterial().getClass().getSimpleName(), pos.x - cameraPos.x - 64, pos.y - cameraPos.y + 58);
                else
                    graphics.drawString("hollow", pos.x - cameraPos.x - 64, pos.y - cameraPos.y + 58);
            } else {
                graphics.drawString("???", pos.x - cameraPos.x - 64, pos.y - cameraPos.y + 58);
            }
            boolean teleportabel = false;
            if (Controller.getInstance().GetCurrentSettlerWaitingForInput()!=null) {
                var teleportGates = Controller.getInstance().GetCurrentSettlerWaitingForInput().GetAsteroid().GetTeleportGates();

                for (var teleport : teleportGates
                ) {
                    if (teleport.GetPair().GetAsteroid() == this.asteroid)
                        teleportabel = true;
                }
            }
            if (Controller.getInstance().GetCurrentSettlerWaitingForInput()!=null &&
                    (Controller.getInstance().GetCurrentSettlerWaitingForInput().GetAsteroid().GetNeighboringAsteroids().contains(this.asteroid)
                    || teleportabel)) {
                graphics.setColor(faded);
                graphics.fillRect(pos.x - cameraPos.x - 70, pos.y - cameraPos.y + 65, 70, 30);
                graphics.setColor(Color.GRAY);
                graphics.drawRect(pos.x - cameraPos.x - 70, pos.y - cameraPos.y + 65, 70, 30);
                graphics.setColor(Color.BLACK);
                graphics.setFont(new Font("Dialog", Font.PLAIN, 22));
                graphics.drawString("Move", pos.x - cameraPos.x - 63, pos.y - cameraPos.y + 90);
            }
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
    public void Clicked(Position clickPos, Position cameraPos) {
        if (clicked) {
            if ((clickPos.x > pos.x-cameraPos.x-70) && (clickPos.x < pos.x-cameraPos.x-70 + 70) &&
                    (clickPos.y > pos.y-cameraPos.y + 65) && (clickPos.y < pos.y-cameraPos.y + 65 + 30)){

                Controller.getInstance().GetCurrentSettlerWaitingForInput().Move(this.asteroid);
                Controller.getInstance().SettlerStepped();
            }
        }
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
        double tes = Math.sqrt(Math.pow((clickPos.x - (pos.x + 45 - cameraPos.x)), 2) + Math.pow(clickPos.y - (pos.y + 40 - cameraPos.y), 2));
        if (tes <= 35) {
            return true;
        }
        if (clicked){
            if (clickPos.x > pos.x-cameraPos.x-70 && clickPos.x < pos.x-cameraPos.x-70 + 70 &&
                    clickPos.y > pos.y-cameraPos.y && clickPos.y < pos.y-cameraPos.y + 65 + 30){
                var teleportGates = Controller.getInstance().GetCurrentSettlerWaitingForInput().GetAsteroid().GetTeleportGates();
                boolean teleportabel = false;
                for (var teleport: teleportGates
                     ) {
                    if (teleport.GetPair().GetAsteroid()==this.asteroid)
                        teleportabel=true;
                }

                if (Controller.getInstance().GetCurrentSettlerWaitingForInput()!=null &&
                        clickPos.y > pos.y-cameraPos.y + 65 &&
                        (!Controller.getInstance().GetCurrentSettlerWaitingForInput().GetAsteroid().GetNeighboringAsteroids().contains(this.asteroid)
                                && !teleportabel)) {
                    return false;
                }
                return true;
            }
        }

        return false;
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
