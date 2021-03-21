package Skeleton;

import characters.Robot;
import characters.Settler;
import items.CraftingTable;
import items.Recipe;
import materials.*;
import places.*;

import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            System.out.print(" | ");
        System.out.println(s);
    }

    public String GetInput(String s) {
        for (int i = 0; i < tabValue; ++i)
            System.out.print(" | ");
        System.out.print(s);

        Scanner in = new Scanner(System.in);

        return in.nextLine();

    }

    public static void main(String[] args){
        CraftingTable craftingTable = CraftingTable.getInstance();
        craftingTable.AddRecipe(new Recipe(Robot.class,
                Stream.of(new Coal(), new Iron(), new Uranium()).collect(Collectors.toList())));
        craftingTable.AddRecipe(new Recipe(TeleportGate.class,
                Stream.of(new Iron(), new Iron(), new WaterIce(), new Uranium()).collect(Collectors.toList())));

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
                case (4):
                    buildRobotMenu();
                    break;
                case (5):
                    buildTeleportMenu();
                    break;
                case (6):
                    placeMaterialMenu();
                    break;
                case (7):
                    installTeleportMenu();
                    break;
                case (8):
                    nearSunMenu();
                    break;
                case (9):
                    solarFlareMenu();
                    break;
                case (10):
                    controlRobotMenu();
                    break;
            }
        }
    }
    
    private static void moveMenu() {
        Settler telepes = new Settler();
        Asteroid asteroid1 = new Asteroid();
        Asteroid asteroid2 = new Asteroid();
        Asteroid asteroid3 = new Asteroid();
        asteroid1.AddNeighbor(asteroid2);
        asteroid2.AddNeighbor(asteroid1);
        asteroid1.AddNeighbor(asteroid3);
        asteroid3.AddNeighbor(asteroid1);


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

    private static void buildRobotMenu() {
        Settler telepes = new Settler();
        Asteroid asteroid1 = new Asteroid();
        telepes.SetAsteroid(asteroid1);

        telepes.CraftRobot();
    }

    private static void buildTeleportMenu() {
        Settler telepes = new Settler();
        Asteroid asteroid1 = new Asteroid();
        telepes.SetAsteroid(asteroid1);

        telepes.CraftTeleportGates();
    }

    private static void placeMaterialMenu() {
        Settler telepes = new Settler();
        Asteroid asteroid1 = new Asteroid();
        telepes.SetAsteroid(asteroid1);

        Skeleton instance = Skeleton.getInstance();
        instance.tabIncrement();
        String res = instance.GetInput("Milyen nyersanyagot szeretnél lehelyezni? [V/J/U/S]: ");
        String res2 = instance.GetInput("Van ilyen nyersanyaga a Telepes-nek? [I/N]: ");
        if(res2.equalsIgnoreCase("i")) {
            Material material = null;
            if(res.equalsIgnoreCase("v")) {
                material = telepes.GetInventory().GetMaterials().stream().filter(m -> m.CompatibleWith(new Iron())).findFirst().get();
            } else if(res.equalsIgnoreCase("j")) {
                material = telepes.GetInventory().GetMaterials().stream().filter(m -> m.CompatibleWith(new WaterIce())).findFirst().get();
            } else if(res.equalsIgnoreCase("u")) {
                material = telepes.GetInventory().GetMaterials().stream().filter(m -> m.CompatibleWith(new Uranium())).findFirst().get();
            } else if(res.equalsIgnoreCase("s")) {
                material = telepes.GetInventory().GetMaterials().stream().filter(m -> m.CompatibleWith(new Coal())).findFirst().get();
            }
            telepes.PlaceMaterial(material);
            instance.tabDecrement();
        }

    }

    private static void installTeleportMenu() {
        Settler telepes = new Settler();
        Asteroid asteroid1 = new Asteroid();
        telepes.SetAsteroid(asteroid1);

        //telepes.AddItem(new TeleportGate()); itt még bármi lehet..., ugyanis egy pár kell
    }

    private static void nearSunMenu() {
        AsteroidBelt.getInstance().NearSun();
    }

    private static void solarFlareMenu() {
        AsteroidBelt.getInstance().SolarFlare();
    }

    private static void controlRobotMenu() {
        Robot robot = new Robot();
        Asteroid asteroid1 = new Asteroid();
        Asteroid asteroid2 = new Asteroid();
        asteroid1.AddNeighbor(asteroid2);
        asteroid2.AddNeighbor(asteroid1);

        robot.SetAsteroid(asteroid1);

        robot.Step();
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

