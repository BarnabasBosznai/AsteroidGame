package main;

import characters.Settler;
import interfaces.Steppable;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private static Game instance;

    private final List<Settler> settlers;
    private final List<Steppable> steppables;

    public static Game getInstance() {
        if(instance == null)
            instance = new Game();

        return instance;
    }

    private Game(){
        this.settlers = new ArrayList<>();
        this.steppables = new ArrayList<>();
    }

    public void start() {

    }

    public boolean checkGameOver() {

    }

   public void addSteppable(Steppable steppable) {
        this.steppables.add(steppable);
   }

    public void removeSteppable(Steppable steppable) {
        this.steppables.remove(steppable);
    }

    public void addSettler(Settler settler) {
        this.settlers.add(settler);
    }
}
