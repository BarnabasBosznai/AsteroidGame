package view;

import characters.Settler;

import java.awt.*;

public class InterfacePanel extends Drawable {

    boolean craft, place, allMaterial;

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
        //lekezelni, hogy volt e valamelyik gombra/akarmire kattintas clickPos alapjan

        //ezt majd lekezeljuk, egyelore false
        boolean clickedOnInterface = false;

        if(clickedOnInterface){
            if(currentWaitingSettler != null){

                //megfelelo fv meghivasa

                //kicsit kokler, majd lehet ra kulon fv akar
                Controller.getInstance().CurrentSettlerWaitingForInput(null);
            }
            return true;
        }
        else
            return false;
    }
}
