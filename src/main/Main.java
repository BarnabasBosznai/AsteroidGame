package main;

import characters.Robot;
import characters.Settler;
import characters.UFO;
import places.Asteroid;
import view.AsteroidView;
import view.Controller;

public class Main {

    public static void main(String[] args){
        Frame frame = new Frame();
        Controller.StartGame();
        frame.StartGame();
    }
}
