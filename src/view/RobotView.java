package view;

import characters.Robot;

public class RobotView extends Drawable {

    private final Robot robot;

    public RobotView(Robot r, Position pos, int z){
        this.robot = r;
        this.pos = pos;
        this.zIndex = z;
    }

    @Override
    public void Draw() {

    }
}
