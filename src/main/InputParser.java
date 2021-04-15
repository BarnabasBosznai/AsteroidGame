package main;

import characters.Robot;
import characters.Settler;
import characters.UFO;
import places.Asteroid;
import places.TeleportGate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputParser {
    private static StringBuilder builder;
    private static StringBuilder listCommandsBuilder;
    static {
        builder = new StringBuilder();
        listCommandsBuilder = new StringBuilder();
    }

    public static void executeCommand(String line) {
        String[] split = line.split(" ");
        if(split.length > 0) {
            switch(split[0]) {
                case "load":
                    loadCommand(split);
                    break;
                case "save":
                    saveCommand(split);
                    break;
                case "clear":
                    builder = new StringBuilder();
                    listCommandsBuilder = new StringBuilder();
                    break;
                case "create":
                    TestGame.getInstance().Create();
                    builder.append("create").append(System.getProperty("line.separator"));
                    break;
                case "add":
                    addCommand(split);
                    break;
                case "land":
                    landCommand(split);
                    break;
                case "addteleportgate":
                    addeleportgateCommand(split);
                    break;
                case "addmaterial":
                    addmaterialCommand(split);
                    break;
                case "set":
                    setCommand(split);
                    break;
                case "pair":
                    pairCommand(split);
                    break;
                case "placeteleport":
                    placeteleportCommand(split);
                    break;
                case "setneighbours":
                    setneighboursCommand(split);
                    break;
                case "step":
                    stepCommand(split);
                    break;
                case "listasteroids":
                    builder.append("listasteroids").append(System.getProperty("line.separator"));
                    listCommandsBuilder.append("listasteroids ");
                    break;
                case "listsettlers":
                    builder.append("listsettlers").append(System.getProperty("line.separator"));
                    listCommandsBuilder.append("listsettlers ");
                    break;
                case "listrobots":
                    builder.append("listrobots").append(System.getProperty("line.separator"));
                    listCommandsBuilder.append("listrobots ");
                    break;
                case "listufos":
                    builder.append("listufos").append(System.getProperty("line.separator"));
                    listCommandsBuilder.append("listufos ");
                    break;
                case "listteleportgates":
                    builder.append("listteleportgates").append(System.getProperty("line.separator"));
                    listCommandsBuilder.append("listteleportgates ");
                    break;
                case "gamestatus":
                    builder.append("gamestatus").append(System.getProperty("line.separator"));
                    listCommandsBuilder.append("gamestatus ");
                    break;
                case "exit": // ez nem kell
                    {
                        for(String str : listCommandsBuilder.toString().split(" ")) {
                            switch(str) {
                                case "listasteroids":
                                    TestGame.getInstance().ListAsteroids();
                                    break;
                                case "listsettlers":
                                    TestGame.getInstance().ListSettlers();
                                    break;
                                case "listrobots":
                                    TestGame.getInstance().ListRobots();
                                    break;
                                case "listufos":
                                    TestGame.getInstance().ListUFOs();
                                    break;
                                case "listteleportgates":
                                    TestGame.getInstance().ListTeleportGates();
                                    break;
                                case "gamestatus":
                                    TestGame.getInstance().GameStatus();
                                    break;
                            }
                        }
                        listCommandsBuilder = new StringBuilder();
                    }
                    break;
            }
        }
    }

    private static void loadCommand(String[] params) {
        if(params.length == 2) {
            try(BufferedReader reader = new BufferedReader(new FileReader(params[1]))) {
                builder.append("load ").append(params[0]).append(System.getProperty("line.separator"));
                String line;
                while((line = reader.readLine()) != null) {
                    executeCommand(line);
                }

            } catch (Exception e) {
                // Egyenlőre
                e.printStackTrace();
            }
        }
    }

    private static void saveCommand(String[] params) {
        if(params.length == 2) {
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(params[1]))) {
                writer.write(builder.toString());
                builder = new StringBuilder();
                listCommandsBuilder = new StringBuilder();
            } catch (Exception e) {
                // Egyenlőre
                e.printStackTrace();
            }
        }
    }

    private static void addCommand(String[] params) {
        if(params.length == 3) {
            switch (params[1]) {
                case "asteroid":
                    TestGame.getInstance().Add(Asteroid.class, params[2]);
                    builder.append("add asteroid ").append(params[2]).append(System.getProperty("line.separator"));
                    break;
                case "settler":
                    TestGame.getInstance().Add(Settler.class, params[2]);
                    builder.append("add settler ").append(params[2]).append(System.getProperty("line.separator"));
                    break;
                case "teleportgate":
                    TestGame.getInstance().Add(TeleportGate.class, params[2]);
                    builder.append("add teleportgate ").append(params[2]).append(System.getProperty("line.separator"));
                    break;
                case "robot":
                    TestGame.getInstance().Add(Robot.class, params[2]);
                    builder.append("add robot ").append(params[2]).append(System.getProperty("line.separator"));
                    break;
                case "ufo":
                    TestGame.getInstance().Add(UFO.class, params[2]);
                    builder.append("add ufo ").append(params[2]).append(System.getProperty("line.separator"));
                    break;
            }
        }
    }

    private static void landCommand(String[] params) {
        if(params.length == 3) {
            TestGame.getInstance().Land(params[1], params[2]);
            builder.append("land ").append(params[1]).append(" ").append(params[2]).append(System.getProperty("line.separator"));
        }
    }

    private static void addeleportgateCommand(String[] params) {
        if(params.length == 3) {
            TestGame.getInstance().AddTeleportGate(params[1], params[2]);
            builder.append("addteleportgate ").append(params[1]).append(" ").append(params[2]).append(System.getProperty("line.separator"));
        }
    }

    private static void addmaterialCommand(String[] params) {
        if(params.length == 3) {
            TestGame.getInstance().AddMaterial(params[1], params[2]);
            builder.append("addmaterial ").append(params[1]).append(" ").append(params[2]).append(System.getProperty("line.separator"));
        }
    }

    private static void setCommand(String[] params) {
        if(params.length == 4) {
            TestGame.getInstance().Set(params[1], params[2], params[3]);
            builder.append("set ").append(params[1]).append(" ").append(params[2]).append(" ").append(params[3]).append(System.getProperty("line.separator"));
        }
    }

    private static void pairCommand(String[] params) {
        if(params.length == 3) {
            TestGame.getInstance().Pair(params[1], params[2]);
        }
    }

    private static void placeteleportCommand(String[] params) {
        if(params.length == 3) {
            TestGame.getInstance().PlaceTeleport(params[1], params[2]);
            builder.append("placeteleport ").append(params[1]).append(" ").append(params[2]).append(System.getProperty("line.separator"));
        }
    }

    private static void setneighboursCommand(String[] params) {
        if(params.length == 3) {
            TestGame.getInstance().SetNeighbours(params[1], params[2]);
            builder.append("setneighbours ").append(params[1]).append(" ").append(params[2]).append(System.getProperty("line.separator"));
        }
    }

    private static void stepCommand(String[] params) {
        if(params.length >= 2) {
            List<String> temp = new ArrayList<>(Arrays.asList(params).subList(2, params.length));
            TestGame.getInstance().Step(params[1], temp);

            builder.append("step ");
            for(String str : temp)
                builder.append(str).append(" ");

            builder.append(System.getProperty("line.separator"));
        }
    }
}
