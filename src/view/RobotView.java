package view;

import characters.Character;
import characters.Robot;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class RobotView extends DrawableCharacter {

    private final Robot robot;
    private static final int robotRadius = 2;

    public RobotView(Robot r){
        super();

        this.robot = r;
        this.radius = robotRadius;
        try{
            //Beolvasas utan automatikusan bezarodnak a fajlok az ImageIO-nal
            this.img= ImageIO.read(new File("robot.png"));
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void Draw(Graphics2D graphics) {

    }

    @Override
    public Character GetCharacter() {
        return this.robot;
    }


}
