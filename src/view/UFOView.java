package view;

import characters.Character;
import characters.UFO;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class UFOView extends DrawableCharacter {

    private final UFO ufo;
    private static final int ufoRadius = 10;

    public UFOView(UFO u){
        super();

        this.ufo = u;
        this.radius = ufoRadius*2;
        try{
            //Beolvasas utan automatikusan bezarodnak a fajlok az ImageIO-nal
            this.img = ImageIO.read(new File("Textures/ufo.png"));
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void Draw(Graphics2D graphics) {
        graphics.drawImage(rotate(angle),this.pos.x-ufoRadius,this.pos.y-ufoRadius,2*ufoRadius,2*ufoRadius,null);
    }

    @Override
    public Character GetCharacter() {
        return this.ufo;
    }

}
