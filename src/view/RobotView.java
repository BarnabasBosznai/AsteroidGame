package view;

import characters.Character;
import characters.Robot;

public class RobotView extends DrawableCharacter {

    private final Robot robot;

    public RobotView(Robot r){
        this.robot = r;
    }

    @Override
    public void Draw(Position pos) {

    }

    @Override
    public Character GetCharacter() {
        return this.robot;
    }
}
