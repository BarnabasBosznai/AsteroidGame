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
    public void Draw(Graphics2D graphics) { // boolean-eket itt nagyban használnám

    }

    public boolean HandleClick(Position clickPos, Settler currentWaitingSettler){

        if(currentWaitingSettler == null){
            output = "Ilyen nincs, nem jön semelyik Settler!";
            return false;
        }

        boolean clickedOnInterface = false;

        if (craft){
            if (clickPos.x<110 && clickPos.y>365){
                clickedOnInterface = true;
                if (clickPos.y>365 && clickPos.y<410) {
                    if (currentWaitingSettler.CraftRobot()) {
                        output = "Sikerült a Robot craftolása!";
                        Controller.getInstance().SettlerStepped();
                    } else {
                        output = "Nem sikerült a Robot Craftolása!";
                    }
                }
                else if (clickPos.y>410 && clickPos.y<455) {
                    if (currentWaitingSettler.CraftTeleportGates()) {
                        output = "Sikerült a Teleport craftolása!";
                        Controller.getInstance().SettlerStepped();
                    } else {
                        output = "Nem sikerült a Teleport Craftolása!";
                    }
                }
                else if (clickPos.y>455 && clickPos.y<500) {
                    craft = false;
                }
            }
        }

        if (place){
            if (clickPos.x>690 && clickPos.y>230){
                clickedOnInterface = true;
                if (clickPos.y>230 && clickPos.y<275) {
                    if (currentWaitingSettler.PlaceTeleportGate()) {
                        output = "Sikerült letenni a Teleportkaput!";
                        Controller.getInstance().SettlerStepped();
                    } else {
                        output = "Nem sikerült letenni a Teleportkaput!";
                    }
                }
                else if (clickPos.y>275 && clickPos.y<320) {
                    if (currentWaitingSettler.PlaceMaterial(new Coal())) {
                        output = "Sikerült letenni a Szenet!";
                        Controller.getInstance().SettlerStepped();
                    } else {
                        output = "Nem sikerült letenni a Szenet!";
                    }
                }
                else if (clickPos.y>320 && clickPos.y<365) {
                    if (currentWaitingSettler.PlaceMaterial(new Iron())) {
                        output = "Sikerült letenni a Vasat!";
                        Controller.getInstance().SettlerStepped();
                    } else {
                        output = "Nem sikerült letenni a Vasat!";
                    }
                }
                else if (clickPos.y>365 && clickPos.y<410) {
                    if (currentWaitingSettler.PlaceMaterial(new WaterIce())) {
                        output = "Sikerült letenni a Vízjeget!";
                        Controller.getInstance().SettlerStepped();
                    } else {
                        output = "Nem sikerült letenni aa Vízjeget!";
                    }
                }
                else if (clickPos.y>410 && clickPos.y<455) {
                    if (currentWaitingSettler.PlaceMaterial(new Uranium())) {
                        output = "Sikerült letenni az Uránt!";
                        Controller.getInstance().SettlerStepped();
                    } else {
                        output = "Nem sikerült letenni az Uránt!";
                    }
                }
                else if (clickPos.y>455 && clickPos.y<500) {
                    place = false;
                }
            }
        }

        craft = false; place = false; allMaterial = false;
        //lekezelni, hogy volt e valamelyik gombra/akarmire kattintas clickPos alapjan

        if (clickPos.y>455){
            clickedOnInterface=true;
            if (clickPos.x<110)
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
