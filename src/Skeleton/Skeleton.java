package Skeleton;

import characters.Settler;
import places.Asteroid;
import places.AsteroidBelt;
import java.util.Scanner;

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
        System.out.println(s);


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

    public static void main(String[] args){
        // Ez a menü
        boolean run=true;
        while (run) {
            System.out.println("Válassz egy funkciót!");
            System.out.println("(1) Settler Move");
            System.out.println("(2) Settler Mine");
            System.out.println("(3) Settler Drill");
            System.out.println("(4) Build Robot");
            System.out.println("(5) Build Teleport");
            System.out.println("(6) Place Material");
            System.out.println("(7) Install Teleport");
            System.out.println("(8) NearSun");
            System.out.println("(9) SolarFlare");
            System.out.println("(10) Control Robot");
            System.out.print("Add meg a sorszámát: ");

            Scanner in = new Scanner(System.in);
            int choosen_function= in.nextInt();

            switch (choosen_function){
                case (1):
                    moveMenu();
                    break;
                case (2):
                    mineMenu();
                    break;
                case (3):
                    drillMenu();
                    break;
            }


        }


    }

    private static void moveMenu() {
        Settler telepes = new Settler();
        Asteroid asteroid1 = new Asteroid();
        Asteroid asteroid2 = new Asteroid();
        Asteroid asteroid3 = new Asteroid();
        asteroid1.AddNeighbors(asteroid2);
        asteroid2.AddNeighbors(asteroid1);
        asteroid1.AddNeighbors(asteroid3);
        asteroid3.AddNeighbors(asteroid1);


        telepes.SetAsteroid(asteroid1);

        telepes.Move();
    }

    private static void mineMenu() {
        Settler telepes = new Settler();
        Asteroid asteroid = new Asteroid();

        telepes.SetAsteroid(asteroid);

        telepes.Mine();
    }

    private static void drillMenu() {
        Settler telepes = new Settler();
        Asteroid asteroid1 = new Asteroid();
        telepes.SetAsteroid(asteroid1);

        telepes.Drill();
    }



    private void nearSun() {
        AsteroidBelt.getInstance().NearSun();
    }

    private void solarFlare() {
        AsteroidBelt.getInstance().SolarFlare();
    }

    //Tabulálás növelése
    public void tabIncrement() {
        tabValue++;
    }

    //Tabulálás csökkentése
    public void tabDecrement() {
        if(tabValue == 0)
            return;
        tabValue--;
    }
}

