package view;

import characters.Character;
import characters.Settler;

import java.awt.*;

public class SettlerView extends DrawableCharacter {

    private final Settler settler;
    private static final double settlerRadius = 2.0;

    public SettlerView(Settler s){
        this.settler = s;
        this.radius = settlerRadius;
    }

    @Override
    public void Draw(Graphics2D graphics) {

    }

    @Override
    public Character GetCharacter() {
        return this.settler;
    }

    public void WaitingForInput(){
        Controller.getInstance().CurrentSettlerWaitingForInput(this.settler);
    }

}
