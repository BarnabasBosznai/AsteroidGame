package Skeleton;


import characters.Settler;
import items.CraftingTable;
import items.Inventory;
import main.Game;
import materials.Material;
import places.Asteroid;
import places.Place;
import places.TeleportGate;
import java.util.Scanner;

import java.util.HashMap;

public class Skeleton {

    private static Skeleton instance=null;

    private int hol_van;

    private static int tabValue;

    public static Skeleton getInstance() {
        if(instance == null)
            instance = new Skeleton();

        return instance;
    }

    public void Print(Object o, String s) {
        for (int i = 0; i < tabValue; ++i)
        {
            System.out.print(" | ");
        }
        System.out.print(s);


        //o.getClass().getSimpleName()
    }

    public String GetInput(String s) {
        for (int i = 0; i < tabValue; ++i)
        {
            System.out.print(" | ");
        }
        System.out.print(s);

        Scanner in = new Scanner(System.in);

        return in.nextLine();

    }

    private void mine_menupont() {
        Settler telepes = new Settler();
        Asteroid asteroid = new Asteroid();

        telepes.SetAsteroid(asteroid);

        telepes.Mine();
    }

    //Tabulálás növelése
    public void tabIncrement()
    {
        tabValue++;
    }

    //Tabulálás csökkentése
    public void tabDecrement()
    {
        if(tabValue==0)
        {
            return;
        }
        tabValue--;
    }


}

