package view;

import characters.Settler;

public class InterfacePanel extends Drawable {

    public InterfacePanel(){
        this.zIndex = 100;
    }

    @Override
    public void Draw() {

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
