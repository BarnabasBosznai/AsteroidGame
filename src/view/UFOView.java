package view;

import characters.Character;
import characters.UFO;

public class UFOView extends DrawableCharacter {

    private final UFO ufo;

    public UFOView(UFO u){
        this.ufo = u;
    }

    @Override
    public void Draw(Position pos) {

    }

    @Override
    public Character GetCharacter() {
        return this.ufo;
    }
}
