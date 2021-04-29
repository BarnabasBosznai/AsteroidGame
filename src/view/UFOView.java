package view;

import characters.Character;
import characters.UFO;

import java.awt.*;

public class UFOView extends DrawableCharacter {

    private final UFO ufo;
    private static final double ufoRadius = 2.0;

    public UFOView(UFO u){
        this.ufo = u;
        this.radius = ufoRadius;
    }

    @Override
    public void Draw(Graphics2D graphics) {

    }

    @Override
    public Character GetCharacter() {
        return this.ufo;
    }

    @Override
    public void Clicked(Position pos){
        super.Clicked(pos);

        //TODO
    }
}
