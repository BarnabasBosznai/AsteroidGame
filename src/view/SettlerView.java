package view;

import characters.Character;
import characters.Settler;

public class SettlerView extends DrawableCharacter {

    private final Settler settler;
    private static final double settlerRadius = 2.0;

    public SettlerView(Settler s){
        this.settler = s;
        this.radius = settlerRadius;
    }

    @Override
    public void Draw() {

    }

    @Override
    public Character GetCharacter() {
        return this.settler;
    }

    public void WaitingForInput(){
        Controller.getInstance().CurrentSettlerWaitingForInput(this.settler);
    }
}
