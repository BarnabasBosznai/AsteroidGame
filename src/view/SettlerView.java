package view;

import characters.Character;
import characters.Settler;
import items.Inventory;
import materials.Coal;
import materials.Iron;
import materials.WaterIce;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class SettlerView extends DrawableCharacter implements Clickable {

    private final Settler settler;
    private static final int settlerRadius = 10;
    private final Color color;
    Image CoalImg, IronImg, WaterIceImg, UraniumImg;

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

        if (clicked) {
            graphics.setColor(new Color(255,255,255,100));
            graphics.fillRect(2,46,68,145);
            graphics.setColor(new Color(255,255,255,220));
            graphics.fillRect(2,48,65,140);
            graphics.setColor(color);
            graphics.setStroke(new BasicStroke(3));
            graphics.drawRect(2,48,65,140);
            graphics.setColor(Color.BLACK);
            try {
                CoalImg = ImageIO.read(new File("Textures/szén.png"));
                IronImg = ImageIO.read(new File("Textures/vas.png"));
                WaterIceImg = ImageIO.read(new File("Textures/vízjég.png"));
                UraniumImg = ImageIO.read(new File("Textures/urán.png"));
            } catch ( IOException e){

            }
            int coal = 0;
            int iron = 0;
            int waterice = 0;
            int uranium = 0;
            var materials = this.settler.GetInventory().GetMaterials();

            for ( var material: materials
            ) {
                if (material.CompatibleWith(new Coal()))
                    coal++;
                else if (material.CompatibleWith(new Iron()))
                    iron++;
                else if (material.CompatibleWith(new WaterIce()))
                    waterice++;
                else
                    uranium++;
            }
            graphics.drawImage(CoalImg,10,55,30,30,null,null);
            graphics.drawImage(IronImg,10,86,30,30,null,null);
            graphics.drawImage(WaterIceImg,10,117,30,30,null,null);
            graphics.drawImage(UraniumImg,10,148,30,30,null,null);
            graphics.setFont(new Font("Dialog",Font.PLAIN,15));
            graphics.drawString(coal+"" ,45,55 + 20);
            graphics.drawString(iron+"" ,45,86+ 20);
            graphics.drawString(waterice+"" ,45,117+ 20);
            graphics.drawString(uranium+"" ,45,148+ 20);
        }
    }

    public void Draw_Inventory(Graphics2D graphics){

    }

    @Override
    public Character GetCharacter() {
        return this.settler;
    }

    @Override
    public void CharacterDied(){
        Controller.getInstance().SettlerDied(this);
    }

    @Override
    public void Clicked(Position pos, Position cameraPos) {
        clicked=true;
    }

    @Override
    public void UnClicked() {
        clicked=false;
    }

    @Override
    public boolean ClickedCheck(Position clickPos, Position cameraPos) {
        if (pos.x==0)   //gyenge szűrés
            return false;

        if (!(pos.x - 12 < clickPos.x && pos.x - 12 + 20 > clickPos.x &&
                pos.y - 12 < clickPos.y && pos.y - 12 + 20 > clickPos.y))
            return false;
        Clicked(clickPos,cameraPos);
        return true;
    }
}
