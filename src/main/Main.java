package main;

import characters.UFO;
import places.Asteroid;
import view.AsteroidView;

public class Main {

    public static void main(String[] args){
        Frame frame = new Frame();
        Asteroid ast=new Asteroid();
        UFO ufo = new UFO(ast);
        UFO ufo1 = new UFO(ast);
        UFO ufo2 = new UFO(ast);
        UFO ufo3 = new UFO(ast);
        UFO ufo4 = new UFO(ast);
        UFO ufo5 = new UFO(ast);
        frame.StartThread();
    }
}
