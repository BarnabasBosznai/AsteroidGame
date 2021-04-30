package view;

import characters.Settler;
import materials.Coal;
import materials.Iron;
import materials.Uranium;
import materials.WaterIce;

import java.awt.*;

public class InterfacePanel extends Drawable {

    boolean craft, place, allMaterial;
    String output;

    public InterfacePanel(){
        this.zIndex = 100;
        craft = false;
        place = false;
        allMaterial = false;
    }

    @Override
    public void Draw(Graphics2D graphics, Position cameraPos) { // boolean-eket itt nagyban használnám
        if (craft){ // mágikus számok hada 1.

            // Robot craftoló gomb
            graphics.setColor(Color.GRAY);
            graphics.fillRect(0,434,120,43);
            graphics.setColor(Color.LIGHT_GRAY);
            graphics.fillRect(5,439,110,33);
            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font("Dialog",Font.PLAIN,26));
            graphics.drawString("Robot",26,466);

            // Teleport craftoló gomb
            graphics.setColor(Color.GRAY);
            graphics.fillRect(0,477,120,43);
            graphics.setColor(Color.LIGHT_GRAY);
            graphics.fillRect(5,482,110,33);
            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font("Dialog",Font.PLAIN,26));
            graphics.drawString("Teleport",12,508);

            //Craft gomb meg van nyomva
            graphics.setColor(Color.LIGHT_GRAY);
            graphics.fillRect(0,520,120,43);
            graphics.setColor(Color.cyan);
            graphics.fillRect(5,525,110,33);
            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font("Dialog",Font.PLAIN,29));
            graphics.drawString("Craft",29,552);
        } else {  // mágikus számok hada 2.

            //Craft gomb nincs megnyomva
            graphics.setColor(Color.GRAY);
            graphics.fillRect(0,520,120,43);
            graphics.setColor(Color.LIGHT_GRAY);
            graphics.fillRect(5,525,110,33);
            graphics.setFont(new Font("Dialog",Font.PLAIN,29));
            graphics.setColor(Color.BLACK);
            graphics.drawString("Craft",29,552);
        }

        if (place){ // mágikus számok hada 3.

            // Vízjeget letevő gomb
            graphics.setColor(Color.GRAY);
            graphics.fillRect(880,348,120,43);
            graphics.setColor(Color.LIGHT_GRAY);
            graphics.fillRect(885,353,110,33);
            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font("Dialog",Font.PLAIN,26));
            graphics.drawString("Coal",914,380);

            // Vízjeget letevő gomb
            graphics.setColor(Color.GRAY);
            graphics.fillRect(880,391,120,43);
            graphics.setColor(Color.LIGHT_GRAY);
            graphics.fillRect(885,396,110,33);
            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font("Dialog",Font.PLAIN,26));
            graphics.drawString("Iron",919,423);

            // Vízjeget letevő gomb // emiatt lett 120 a szélessége minden gombnak
            graphics.setColor(Color.GRAY);
            graphics.fillRect(880,434,120,43);
            graphics.setColor(Color.LIGHT_GRAY);
            graphics.fillRect(885,439,110,33);
            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font("Dialog",Font.PLAIN,26));
            graphics.drawString("Waterice",889,466);

            // Uránt letevő gomb
            graphics.setColor(Color.GRAY);
            graphics.fillRect(880,477,120,43);
            graphics.setColor(Color.LIGHT_GRAY);
            graphics.fillRect(885,482,110,33);
            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font("Dialog",Font.PLAIN,26));
            graphics.drawString("Uranium",892,508);

            //Place gomb meg van nyomva
            graphics.setColor(Color.LIGHT_GRAY);
            graphics.fillRect(880,520,120,43);
            graphics.setColor(Color.cyan);
            graphics.fillRect(885,525,110,33);
            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font("Dialog",Font.PLAIN,29));
            graphics.drawString("Place",905,552);

        } else {    // mágikus számok hada 4.

            //Place gomb nincs megnyomva
            graphics.setColor(Color.GRAY);
            graphics.fillRect(880,520,120,43);
            graphics.setColor(Color.LIGHT_GRAY);
            graphics.fillRect(885,525,110,33);
            graphics.setFont(new Font("Dialog",Font.PLAIN,29));
            graphics.setColor(Color.BLACK);
            graphics.drawString("Place",905,552);
        }
    }




    public boolean HandleClick(Position clickPos, Settler currentWaitingSettler){

        /*if(currentWaitingSettler == null){
            output = "Ilyen nincs, nem jön semelyik Settler!";
            return false;
        }*/

        boolean clickedOnInterface = false;

        if (craft){
            if (clickPos.x<120 && clickPos.y>434){
                clickedOnInterface = true;
                if (clickPos.y>434 && clickPos.y<477) {
                    if (currentWaitingSettler.CraftRobot()) {
                        output = "Sikerült a Robot craftolása!";
                        Controller.getInstance().SettlerStepped();
                    } else {
                        output = "Nem sikerült a Robot Craftolása!";
                    }
                }
                else if (clickPos.y>477 && clickPos.y<520) {
                    if (currentWaitingSettler.CraftTeleportGates()) {
                        output = "Sikerült a Teleport craftolása!";
                        Controller.getInstance().SettlerStepped();
                    } else {
                        output = "Nem sikerült a Teleport Craftolása!";
                    }
                }
                else if (clickPos.y>520 && clickPos.y<563) {
                    craft = false;
                }
            }
        }

        if (place){
            if (clickPos.x>880 && clickPos.y>348){
                clickedOnInterface = true;
                if (clickPos.y>305 && clickPos.y<348) {
                    if (currentWaitingSettler.PlaceTeleportGate()) {
                        output = "Sikerült letenni a Teleportkaput!";
                        Controller.getInstance().SettlerStepped();
                    } else {
                        output = "Nem sikerült letenni a Teleportkaput!";
                    }
                }
                else if (clickPos.y>348 && clickPos.y<391) {
                    if (currentWaitingSettler.PlaceMaterial(new Coal())) {
                        output = "Sikerült letenni a Szenet!";
                        Controller.getInstance().SettlerStepped();
                    } else {
                        output = "Nem sikerült letenni a Szenet!";
                    }
                }
                else if (clickPos.y>391 && clickPos.y<434) {
                    if (currentWaitingSettler.PlaceMaterial(new Iron())) {
                        output = "Sikerült letenni a Vasat!";
                        Controller.getInstance().SettlerStepped();
                    } else {
                        output = "Nem sikerült letenni a Vasat!";
                    }
                }
                else if (clickPos.y>434 && clickPos.y<477) {
                    if (currentWaitingSettler.PlaceMaterial(new WaterIce())) {
                        output = "Sikerült letenni a Vízjeget!";
                        Controller.getInstance().SettlerStepped();
                    } else {
                        output = "Nem sikerült letenni aa Vízjeget!";
                    }
                }
                else if (clickPos.y>477 && clickPos.y<520) {
                    if (currentWaitingSettler.PlaceMaterial(new Uranium())) {
                        output = "Sikerült letenni az Uránt!";
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

        craft = false; place = false; allMaterial = false;
        //lekezelni, hogy volt e valamelyik gombra/akarmire kattintas clickPos alapjan

        if (clickPos.y>520){
            clickedOnInterface=true;
            if (clickPos.x<120)
                craft=true;
            else if (clickPos.x<220) {
                if (currentWaitingSettler.Drill()){
                    output = "Sikerült fúrni!";
                    Controller.getInstance().SettlerStepped();
                } else {
                    output = "Nem sikerült fúrni!";
                }
            } else if (clickPos.x<580){
                output = "Itt meg mit szeretnél?";
                clickedOnInterface=false;
            } else if (clickPos.x<690){
                if (currentWaitingSettler.Mine()){
                    output = "Sikerült bányászni!";
                    Controller.getInstance().SettlerStepped();
                } else {
                    output = "Nem sikerült bányászni!";
                }
            } else {
                place=true;
            }
        }
        return clickedOnInterface;
    }
}
