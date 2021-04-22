package view;

import characters.Character;
import characters.Settler;

public class SettlerView extends DrawableCharacter {

    private final Settler settler;

    public SettlerView(Settler s){
        this.settler = s;
    }

    @Override
    public void Draw(Position pos) {

    }

    @Override
    public Character GetCharacter() {
        return this.settler;
    }
}
