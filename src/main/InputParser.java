package main;

import characters.Robot;
import characters.Settler;
import characters.UFO;
import places.Asteroid;
import places.TeleportGate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class InputParser {
    private static Scanner scanner;
    static {
        scanner = new Scanner(System.in);
    }

    // Még nem biztos hogy ez lesz
    public static void executeFileCommands(String filepath) {
        try(BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while((line = reader.readLine()) != null) {
                if(line.split(" ").length < 1)
                    break;

            }

        } catch (Exception e) {

        }
    }

    public static void executeCommand() {
        String[] split = scanner.nextLine().split(" ");
        if(split.length > 0) {
            switch(split[0]) {
                case "add":
                    addCommand(split);
                    break;
                case "land":
                    landCommand(split);
                    break;
                case "addteleportgate":
                    break;
                case "addmaterial":
                    break;
                case "set":
                    break;
                case "pair":
                    break;
                case "placeteleport":
                    break;
                case "setneighbours":
                    break;
                case "step":
                    break;
                case "listasteroids":
                    break;
                case "listsettlers":
                    break;
                case "listrobots":
                    break;
                case "listufos":
                    break;
                case "listteleportgates":
                    break;
                case "gamestatus":
                    break;
            }
        }
    }

    private static void createCommand() {

    }

    private static void addCommand(String[] params) {
        if(params.length == 3) {
            switch (params[1]) {
                case "asteroid":
                    TestGame.getInstance().Add(Asteroid.class, params[2]);
                    break;
                case "settler":
                    TestGame.getInstance().Add(Settler.class, params[2]);
                    break;
                case "teleportgate":
                    TestGame.getInstance().Add(TeleportGate.class, params[2]);
                    break;
                case "robot":
                    TestGame.getInstance().Add(Robot.class, params[2]);
                    break;
                case "ufo":
                    TestGame.getInstance().Add(UFO.class, params[2]);
                    break;
            }
        }
    }

    private static void landCommand(String[] params) {
        if(params.length == 3) {
            TestGame.getInstance().Land(params[1], params[2]);
        }
    }
}
