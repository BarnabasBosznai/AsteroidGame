package view;

import characters.Character;
import characters.Settler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SettlerView extends DrawableCharacter {

    private final Settler settler;
    private static final int settlerRadius = 10;

    public SettlerView(Settler s){
        super();

        this.settler = s;
        this.radius = settlerRadius;
        try{
            //Beolvasas utan automatikusan bezarodnak a fajlok az ImageIO-nal
            this.img= ImageIO.read(new File("settler.png"));
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void Draw(Graphics2D graphics) {
        graphics.setColor(Color.BLUE);
        graphics.rotate(angle,this.pos.x,this.pos.y);
        graphics.drawRect((this.pos.x-settlerRadius-1),(this.pos.y-settlerRadius-2),2*(settlerRadius)+2,2*(settlerRadius)+4);
        graphics.rotate(-angle,this.pos.x,this.pos.y);
        graphics.drawImage(rotate(angle),this.pos.x-settlerRadius,this.pos.y-settlerRadius,2*settlerRadius,2*settlerRadius,null);
    }

    @Override
    public Character GetCharacter() {
        return this.settler;
    }

    public void WaitingForInput(){
        Controller.getInstance().CurrentSettlerWaitingForInput(this.settler);
    }

}
