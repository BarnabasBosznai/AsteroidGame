package main;

import characters.Robot;
import characters.Settler;
import characters.UFO;
import places.Asteroid;
import view.AsteroidView;

public class Main {

    public static void main(String[] args){
        Frame frame = new Frame();
        Asteroid ast=new Asteroid();
        Asteroid ast2 = new Asteroid();
        Asteroid ast3=new Asteroid();
        Asteroid ast4 = new Asteroid();
        Asteroid ast5=new Asteroid();
        Asteroid ast6 = new Asteroid();
        UFO ufo = new UFO(ast2);
        UFO ufo1 = new UFO(ast3);
        UFO ufo2 = new UFO(ast4);
        UFO ufo3 = new UFO(ast);
        UFO ufo4 = new UFO(ast);
        UFO ufo5 = new UFO(ast);
        Settler set = new Settler(ast5);
        Settler set2 = new Settler(ast4);
        Settler set3 = new Settler(ast5);
        Settler set4 = new Settler(ast2);
        Settler set5 = new Settler(ast5);
        Settler set6 = new Settler(ast2);
        Settler set7 = new Settler(ast5);
        Settler set8 = new Settler(ast3);
        Robot rob = new Robot(ast6);
        frame.StartThread();
    }
}
