package view;

import characters.Character;
import characters.Robot;

public class RobotView extends DrawableCharacter {

    private final Robot robot;
    private static final double robotRadius = 2.0;

    public RobotView(Robot r){
        this.robot = r;
        this.radius = robotRadius;
    }

    @Override
    public void Draw() {

    }

    @Override
    public Character GetCharacter() {
        return this.robot;
    }

    @Override
    public void Clicked(Position pos){
        super.Clicked(pos);

        //TODO
    }
}
