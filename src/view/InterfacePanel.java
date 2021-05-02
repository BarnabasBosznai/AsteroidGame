package view;

import characters.Settler;
import materials.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InterfacePanel extends Drawable {

    boolean craft, place, allMaterial;
    String output;
    Settler waitingSettler;
    Image CoalImg, IronImg, WaterIceImg, UraniumImg;
    Color textbox;
    Color brown = new Color(128,64,0);
    boolean stepped = false;

    private final Map<String, Button> buttonMap;

    public InterfacePanel(){
        this.zIndex = 100;
        craft = false;
        place = false;
        allMaterial = false;
        output = "";
        textbox = brown;

        this.buttonMap = new HashMap<>();
        /*this.buttonMap.put("Robot", new Button(0,434,120,43,5,439,110,33,"Robot",26,466,26));
        this.buttonMap.put("CraftTeleport", new Button(0,477,120,43,5,482,110,33,"Teleport",12,508,26));
        this.buttonMap.put("PlaceTeleport", new Button(880,305,120,43,885,310,110,33,"Teleport",893,337,26));
        this.buttonMap.put("Craft", new Button(0,520,120,43,5,525,110,33,"Craft",29,552,26));
        this.buttonMap.put("Drill", new Button(120,520,120,43,125,525,110,33,"Drill",155,552,26));
        this.buttonMap.put("Mine", new Button(760,520,120,43,765,525,110,33,"Mine",790,552,26));
        this.buttonMap.put("Place", new Button(880,520,120,43,885,525,110,33,"Place",905,552,26));
        this.buttonMap.put("Iron", new Button(880,391,120,43,885,396,110,33,"Iron",919,423,26));
        this.buttonMap.put("Coal", new Button(880,348,120,43,885,353,110,33,"Coal",914,380,26));
        this.buttonMap.put("Waterice", new Button(880,434,120,43,885,439,110,33,"Waterice",889,466,26));
        this.buttonMap.put("Uranium", new Button(880,477,120,43,885,482,110,33,"Uranium",892,508,26));
        this.buttonMap.put("Output", new Button(240,520,520,43,245,525,510,33,output,250,552,29));*/

        this.buttonMap.put("Robot", new Button(new Rectangle(0,434,120,43), new Rectangle(5,439,110,33),"Robot",26));
        this.buttonMap.put("CraftTeleport", new Button(new Rectangle(0,477,120,43), new Rectangle(5,482,110,33),"Teleport",26));
        this.buttonMap.put("PlaceTeleport", new Button(new Rectangle(880,305,120,43), new Rectangle(885,310,110,33),"Teleport",26));
        this.buttonMap.put("Craft", new Button(new Rectangle(0,520,120,43), new Rectangle(5,525,110,33),"Craft",26));
        this.buttonMap.put("Drill", new Button(new Rectangle(120,520,120,43), new Rectangle(125,525,110,33),"Drill",26));
        this.buttonMap.put("Mine", new Button(new Rectangle(760,520,120,43), new Rectangle(765,525,110,33),"Mine",26));
        this.buttonMap.put("Place", new Button(new Rectangle(880,520,120,43), new Rectangle(885,525,110,33),"Place",26));
        this.buttonMap.put("Iron", new Button(new Rectangle(880,391,120,43), new Rectangle(885,396,110,33),"Iron",26));
        this.buttonMap.put("Coal", new Button(new Rectangle(880,348,120,43), new Rectangle(885,353,110,33),"Coal",26));
        this.buttonMap.put("Waterice", new Button(new Rectangle(880,434,120,43), new Rectangle(885,439,110,33),"Waterice",26));
        this.buttonMap.put("Uranium", new Button(new Rectangle(880,477,120,43), new Rectangle(885,482,110,33),"Uranium",26));
        this.buttonMap.put("Output", new Button(new Rectangle(240,520,520,43), new Rectangle(245,525,510,33),output,29));

        this.buttonMap.get("Drill").setCallback(() -> {
            if (waitingSettler.Drill()){
                output = "Sikerült fúrni!";
                stepped = true;
                Controller.getInstance().SettlerStepped();
            } else {
                output = "Nem sikerült fúrni!";
            }
        });
        this.buttonMap.get("Mine").setCallback(() -> {
            if (waitingSettler.Mine()){
                output = "Sikerült bányászni!";
                stepped = true;
                Controller.getInstance().SettlerStepped();
            } else {
                output = "Nem sikerült bányászni!";
            }
        });
        this.buttonMap.get("Robot").setCallback(() -> {
            if(buttonMap.get("Craft").getState()) {
                if (waitingSettler.CraftRobot()) {
                    output = "Sikerült a Robot craftolása!";
                    stepped = true;
                    Controller.getInstance().SettlerStepped();
                } else {
                    output = "Nem sikerült a Robot Craftolása!";
                }
            }
        });
        this.buttonMap.get("CraftTeleport").setCallback(() -> {
            if(buttonMap.get("Craft").getState()) {
                if (waitingSettler.CraftTeleportGates()) {
                    output = "Sikerült a Teleport craftolása!";
                    stepped = true;
                    Controller.getInstance().SettlerStepped();
                } else {
                    output = "Nem sikerült a Teleport Craftolása!";
                }
            }
        });
        this.buttonMap.get("Iron").setCallback(() -> {
            if(this.buttonMap.get("Place").getState()) {
                if (waitingSettler.PlaceMaterial(new Iron())) {
                    output = "Sikerült letenni a Vasat!";
                    stepped=true;
                    Controller.getInstance().SettlerStepped();
                } else {
                    output = "Nem sikerült letenni a Vasat!";
                }
            }
        });
        this.buttonMap.get("Coal").setCallback(() -> {
            if(this.buttonMap.get("Place").getState()) {
                if (waitingSettler.PlaceMaterial(new Coal())) {
                    output = "Sikerült letenni a Szenet!";
                    stepped=true;
                    Controller.getInstance().SettlerStepped();
                } else {
                    output = "Nem sikerült letenni a Szenet!";
                }
            }
        });
        this.buttonMap.get("Waterice").setCallback(() -> {
            if(this.buttonMap.get("Place").getState()) {
                if (waitingSettler.PlaceMaterial(new WaterIce())) {
                    output = "Sikerült letenni a Vízjeget!";
                    stepped=true;
                    Controller.getInstance().SettlerStepped();
                } else {
                    output = "Nem sikerült letenni a Vízjeget!";
                }
            }
        });
        this.buttonMap.get("Uranium").setCallback(() -> {
            if(this.buttonMap.get("Place").getState()) {
                if (waitingSettler.PlaceMaterial(new Uranium())) {
                    output = "Sikerült letenni az Uránt!";
                    stepped=true;
                    Controller.getInstance().SettlerStepped();
                } else {
                    output = "Nem sikerült letenni az Uránt!";
                }
            }
        });
        this.buttonMap.get("PlaceTeleport").setCallback(() -> {
            if(this.buttonMap.get("Place").getState()) {
                if (waitingSettler.PlaceTeleportGate()) {
                    output = "Sikerült letenni a Teleportkaput!";
                    stepped = true;
                    Controller.getInstance().SettlerStepped();
                } else {
                    output = "Nem sikerült letenni a Teleportkaput!";
                }
            }
        });
        this.buttonMap.get("Output").setCallback(() -> {
            // :woozy_face:
            output = "Itt meg mit szeretnél?";
        });


        try{
            //Beolvasas utan automatikusan bezarodnak a fajlok az ImageIO-nal
            CoalImg = ImageIO.read(new File("Textures/szén.png"));
            IronImg = ImageIO.read(new File("Textures/vas.png"));
            WaterIceImg = ImageIO.read(new File("Textures/vízjég.png"));
            UraniumImg = ImageIO.read(new File("Textures/urán.png"));
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void Draw(Graphics2D graphics, Position cameraPos) { // boolean-eket itt nagyban használnám
        if (buttonMap.get("Craft").getState()){ // mágikus számok hada 1.
            // Robot craftoló gomb
            this.buttonMap.get("Robot").Draw(graphics);

            // Teleport craftoló gomb
            this.buttonMap.get("CraftTeleport").Draw(graphics);

            //Craft gomb meg van nyomva
            this.buttonMap.get("Craft").Draw(graphics);

        } else {  // mágikus számok hada 2.

            //Craft gomb nincs megnyomva
            this.buttonMap.get("Craft").Draw(graphics);
        }

        if (buttonMap.get("Place").getState()) { // mágikus számok hada 3.

            /// Teleportkaput letevő gomb
            this.buttonMap.get("PlaceTeleport").Draw(graphics);

            // Szenet letevő gomb
            this.buttonMap.get("Coal").Draw(graphics);

            // Vasat letevő gomb
            this.buttonMap.get("Iron").Draw(graphics);

            // Vízjeget letevő gomb // emiatt lett 120 a szélessége minden gombnak
            this.buttonMap.get("Waterice").Draw(graphics);

            // Uránt letevő gomb
            this.buttonMap.get("Uranium").Draw(graphics);

            //Place gomb meg van nyomva
            this.buttonMap.get("Place").Draw(graphics);

        } else {    // mágikus számok hada 4.

            //Place gomb nincs megnyomva
            this.buttonMap.get("Place").Draw(graphics);
        }

        // Nyersanyagok kijelzése

        graphics.setColor(brown);
        graphics.fillRect(240,0,520,43);
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(245,5,510,33);
        graphics.setFont(new Font("Dialog",Font.PLAIN,29));
        graphics.setColor(Color.BLACK);
        graphics.drawImage(CoalImg,245,2,40,40,null,null);
        graphics.drawImage(IronImg,340,2,40,40,null,null);
        graphics.drawImage(WaterIceImg,435,2,40,40,null,null);
        graphics.drawImage(UraniumImg,530,2,40,40,null,null);

        if (waitingSettler!=null) {

            int coal = 0;
            int iron = 0;
            int waterice = 0;
            int uranium = 0;
            var materials = waitingSettler.GetInventory().GetMaterials();
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


            graphics.drawString(coal+"",295,32);
            graphics.drawString(iron+"",390,32);
            graphics.drawString(waterice+"",485,32);
            graphics.drawString(uranium+"",580,32);
        } else {
            graphics.drawString("0",295,32);
            graphics.drawString("0",390,32);
            graphics.drawString("0",485,32);
            graphics.drawString("0",580,32);
        }
        // Ast_Infobox?

        // Szöveges visszacsatolás
        if (waitingSettler!=null)
            textbox = Controller.getInstance().GetSettlerView(waitingSettler).GetColor();

        Button outputButton = this.buttonMap.get("Output");
        outputButton.SetBackGroundColor(this.textbox);
        outputButton.SetString(this.output);
        outputButton.Draw(graphics);

        // Drill gomb
        this.buttonMap.get("Drill").Draw(graphics);

        // Mine gomb
        this.buttonMap.get("Mine").Draw(graphics);
    }


    public void SetCurrentWaitingSettler(Settler currentWaitingSettler) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        waitingSettler=currentWaitingSettler;
        stepped=false;

        output = "Te következel!";
    }

    public boolean HandleClick(Position clickPos){
        if(waitingSettler == null){
            output = "Ilyen nincs, nem jön semelyik Settler!";
            return true;
        }
        if (stepped){
            output = "Már jöttél, ne siess!";
            return true;
        }
        boolean clickedOnInterface = false;

        var res = buttonMap.entrySet().stream()
                .filter(stringButtonEntry -> stringButtonEntry.getValue().ClickedCheck(clickPos, null)).findAny();
        if(res.isPresent()) {
            res.get().getValue().Clicked(clickPos, null);
            if(buttonMap.get("Place").getState() && buttonMap.get("Craft").getState())
                clickedOnInterface = true;

            buttonMap.entrySet().forEach(stringButtonEntry -> {
                if(!stringButtonEntry.equals(res.get()))
                    stringButtonEntry.getValue().UnClicked();
            });
        } else {
            buttonMap.forEach((s, button) -> button.UnClicked());
        }

        /*if (craft){
            if (clickPos.x<120 && clickPos.y>434){
                clickedOnInterface = true;
                if (clickPos.y>434 && clickPos.y<477) {
                    if (waitingSettler.CraftRobot()) {
                        output = "Sikerült a Robot craftolása!";
                        stepped=true;
                        Controller.getInstance().SettlerStepped();
                    } else {
                        output = "Nem sikerült a Robot Craftolása!";
                    }
                }
                else if (clickPos.y>477 && clickPos.y<520) {
                    if (waitingSettler.CraftTeleportGates()) {
                        output = "Sikerült a Teleport craftolása!";
                        stepped=true;
                        Controller.getInstance().SettlerStepped();
                    } else {
                        output = "Nem sikerült a Teleport Craftolása!";
                    }
                }
                else if (clickPos.y>520 && clickPos.y<563) {
                    craft = false;
                }
            }
        }*/

        /*if (place){
            if (clickPos.x>880 && clickPos.y>305){
                clickedOnInterface = true;
                if (clickPos.y>305 && clickPos.y<348) {
                    if (waitingSettler.PlaceTeleportGate()) {
                        output = "Sikerült letenni a Teleportkaput!";
                        stepped=true;
                        Controller.getInstance().SettlerStepped();
                    } else {
                        output = "Nem sikerült letenni a Teleportkaput!";
                    }
                }
                else if (clickPos.y>348 && clickPos.y<391) {
                    if (waitingSettler.PlaceMaterial(new Coal())) {
                        output = "Sikerült letenni a Szenet!";
                        stepped=true;
                        Controller.getInstance().SettlerStepped();
                    } else {
                        output = "Nem sikerült letenni a Szenet!";
                    }
                }
                else if (clickPos.y>391 && clickPos.y<434) {
                    if (waitingSettler.PlaceMaterial(new Iron())) {
                        output = "Sikerült letenni a Vasat!";
                        stepped=true;
                        Controller.getInstance().SettlerStepped();
                    } else {
                        output = "Nem sikerült letenni a Vasat!";
                    }
                }
                else if (clickPos.y>434 && clickPos.y<477) {
                    if (waitingSettler.PlaceMaterial(new WaterIce())) {
                        output = "Sikerült letenni a Vízjeget!";
                        stepped=true;
                        Controller.getInstance().SettlerStepped();
                    } else {
                        output = "Nem sikerült letenni a Vízjeget!";
                    }
                }
                else if (clickPos.y>477 && clickPos.y<520) {
                    if (waitingSettler.PlaceMaterial(new Uranium())) {
                        output = "Sikerült letenni az Uránt!";
                        stepped=true;
                        Controller.getInstance().SettlerStepped();
                    } else {
                        output = "Nem sikerült letenni az Uránt!";
                    }
                }
                else if (clickPos.y>520 && clickPos.y<563) {
                    place = false;
                }
            }
        }

        craft = false; place = false; allMaterial = false;*/
        //lekezelni, hogy volt e valamelyik gombra/akarmire kattintas clickPos alapjan

        /*if (clickPos.y>520){
            clickedOnInterface=true;
            if (clickPos.x<120)
                craft=true;
            else if (clickPos.x<240) {
                if (waitingSettler.Drill()){
                    output = "Sikerült fúrni!";
                    stepped=true;
                    Controller.getInstance().SettlerStepped();
                } else {
                    output = "Nem sikerült fúrni!";
                }
            } else if (clickPos.x<760){
                output = "Itt meg mit szeretnél?";
            } else if (clickPos.x<880){
                if (waitingSettler.Mine()){
                    output = "Sikerült bányászni!";
                    stepped=true;
                    Controller.getInstance().SettlerStepped();
                } else {
                    output = "Nem sikerült bányászni!";
                }
            } else {
                place=true;
            }
        }*/

        /*if (clickPos.y<43){
            if (clickPos.x>240 && clickPos.y<760)
                clickedOnInterface=true;
        }*/

        return !clickedOnInterface;
    }
}
