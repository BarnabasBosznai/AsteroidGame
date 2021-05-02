package view;

import characters.Character;
import characters.Settler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class SettlerView extends DrawableCharacter {

    private final Settler settler;
    private static final int settlerRadius = 10;
    private final Color color;

    private static final List<Color> settlerColors;
    private static int settlerViewCreationCounter;

    static{
        settlerViewCreationCounter = 0;
        settlerColors = new ArrayList<>(Arrays.asList(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.WHITE));
    }

    public Color GetColor(){ return this.color;}

    public Position GetPosition(){return this.pos;}

    public SettlerView(Settler s){
        super();

        this.settler = s;
        this.radius = settlerRadius;
        try{
            //Beolvasas utan automatikusan bezarodnak a fajlok az ImageIO-nal
            this.img= ImageIO.read(new File("Textures/settler.png"));
        }
        catch (IOException ex){
            ex.printStackTrace();
        }

        this.color = settlerColors.get(settlerViewCreationCounter);
        ++settlerViewCreationCounter;
    }

    @Override
    public void Draw(Graphics2D graphics) {
        graphics.setColor(color);
        graphics.setStroke(new BasicStroke(2));
        graphics.rotate(angle,this.pos.x,this.pos.y);
        graphics.drawRect((this.pos.x-settlerRadius-1),(this.pos.y-settlerRadius-2),2*(settlerRadius)+2,2*(settlerRadius)+4);
        graphics.rotate(-angle,this.pos.x,this.pos.y);
        graphics.drawImage(rotate(angle),this.pos.x-settlerRadius,this.pos.y-settlerRadius,2*settlerRadius,2*settlerRadius,null);
    }

    @Override
    public Character GetCharacter() {
        return this.settler;
    }

    @Override
    public void CharacterDied(){
        Controller.getInstance().SettlerDied(this);
    }
}
