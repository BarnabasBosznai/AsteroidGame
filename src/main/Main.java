package main;

import characters.UFO;
import places.Asteroid;
import view.AsteroidView;

public class Main {

    public static void main(String[] args){
        Frame frame = new Frame();
        Asteroid ast=new Asteroid();
        UFO ufo = new UFO(ast);

        frame.StartThread();
    }
}
