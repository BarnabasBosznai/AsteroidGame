package main;

import characters.Settler;
import interfaces.Steppable;

import java.util.List;

public class Game {
    private static Game instance;

    private List<Settler> settlers;
    private List<Steppable> steppables;

    public static Game getInstance() {
        if(instance == null)
            instance = new Game();

        return instance;
    }

    private Game(){

    }

    public void start() {

    }

    public boolean checkGameOver() {

    }

   public void addSteppable(Steppable steppable) {

   }

    public void removeSteppable(Steppable steppable) {

    }

    public void addSettler(Settler settler) {

    }
}
