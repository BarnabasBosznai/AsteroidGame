package view;

import characters.Character;
import characters.UFO;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class UFOView extends DrawableCharacter {

    private final UFO ufo;
    private static final int ufoRadius = 20;

    public UFOView(UFO u){
        super();

        this.ufo = u;
        this.radius = ufoRadius;
        try{
            //Beolvasas utan automatikusan bezarodnak a fajlok az ImageIO-nal
            this.img= ImageIO.read(new File("ufo.png"));
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void Draw(Graphics2D graphics) {
        graphics.drawImage(img,this.pos.x,this.pos.y,ufoRadius,ufoRadius,null);
    }

    @Override
    public Character GetCharacter() {
        return this.ufo;
    }

}
