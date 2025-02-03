package byog.Core;

import byog.NewWorldGenerator;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.*;


public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    public static HashMap<Character, int[]> directionMap = new HashMap<>();
    public static final char[] options= "NLQ".toCharArray();
    public static int randomSeed;
    public static boolean keyboard;

    public Game() {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        InitializeDirectionMap();
    }

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */

    public static void InitializeDirectionMap() {
        directionMap.put('W', new int[]{0, 1});
        directionMap.put('A', new int[]{-1, 0});
        directionMap.put('S', new int[]{0, -1});
        directionMap.put('D', new int[]{1, 0});
    }

    public void showMenu() {
        StdDraw.clear();

        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.text(WIDTH * 0.5, HEIGHT * 0.8, "CS61B: THE GAME");

        Font optionsFont = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(optionsFont);
        StdDraw.text(WIDTH * 0.5, HEIGHT * 0.5, "NEW GAME (N)");
        StdDraw.text(WIDTH * 0.5, HEIGHT * 0.5 - 2, "LOAD GAME (L)");
        StdDraw.text(WIDTH * 0.5, HEIGHT * 0.5 - 4, "QUIT (Q)");
        StdDraw.show();
    }

    public void pleaseEnterASeed() {
        StdDraw.clear();

        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.text(WIDTH * 0.5, HEIGHT * 0.5, "PLEASE ENTER A SEED");
        StdDraw.text(WIDTH * 0.5, HEIGHT * 0.5 - 2, "ENDS WITH 'S'");
        StdDraw.show();
    }

    public char userOption() {
        while(true) {
            if (StdDraw.hasNextKeyTyped()) {
                char temp = Character.toUpperCase(StdDraw.nextKeyTyped());
                for (char option : options) {
                    if (temp == option) {
                        return temp;
                    }
                }
            }
        }
    }

    public int userSeed() {
        char stop = 'S';
        StringBuilder randomSeedTemp = new StringBuilder();
        pleaseEnterASeed();
        while(true) {
            if (StdDraw.hasNextKeyTyped()) {
                char temp = StdDraw.nextKeyTyped();

                if (Character.toUpperCase(temp) == stop){
                    String randomSeed = randomSeedTemp.toString();
                    return Integer.parseInt(randomSeed);
                }

                randomSeedTemp.append(temp);
            }
        }
    }

    public void playWithKeyboard() {
        showMenu();
        keyboard = true;
        char Option = userOption();
        gameOption(Option);
    }

    public void gameOption(char option) {
        switch (option) {
            case 'N':
                if (keyboard) {
                    randomSeed = userSeed();
                }
                NewWorldGenerator.generateWorld(randomSeed);
                NewWorldGenerator.letsBegin(keyboard);
                break;
            case 'L':
                loadProject();
                NewWorldGenerator.letsBegin(keyboard);
                break;
            case 'Q':
                saveProject(NewWorldGenerator.PRandomWorld);
                System.exit(0);
                break;
        }
    }



    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */

    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        char[] newInput = input.toCharArray();
        keyboard = false;

        if (newInput[0] == 'N') {
            Pattern pattern = Pattern.compile("^N(\\d+)S([WASD]+)(:Q)?$");
            Matcher matcher = pattern.matcher(input);
            if (matcher.matches()) {
                String numStr = matcher.group(1); // 获取匹配的字符串
                randomSeed = Integer.parseInt(numStr);
                NewWorldGenerator.generateWorld(randomSeed);
                letsMove(matcher.group(2));
                NewWorldGenerator.letsBegin(keyboard);
            } else {
                System.out.println("未找到数字！");
            }
        } else if (newInput[0] == 'L') {
            gameOption('L');
            Pattern r = Pattern.compile("[WASD]+");
            Matcher matcher = r.matcher(input);
            if (matcher.find()) {
                letsMove(matcher.group());
            }
            NewWorldGenerator.letsBegin(keyboard);
        }

        Pattern r = Pattern.compile(":Q");
        Matcher matcher = r.matcher(input);
        if (matcher.find()) {
            return NewWorldGenerator.PRandomWorld;
        }
        return null;
    }

    public static void letsMove(String move) {
        System.out.println(move);
        for (char c: move.toCharArray()) {
            int[] change = directionMap.get(c);
            NewWorldGenerator.Position temp = new NewWorldGenerator.Position(NewWorldGenerator.playerPosition.x, NewWorldGenerator.playerPosition.y);
            System.out.println(Arrays.toString(change));
            if (change != null) {
                System.out.println("moving");
                if (NewWorldGenerator.PRandomWorld[temp.x + change[0]][temp.y + change[1]] != Tileset.WALL && NewWorldGenerator.PRandomWorld[temp.x + change[0]][temp.y + change[1]] != Tileset.LOCKED_DOOR) {

                    NewWorldGenerator.playerPosition.x += change[0];
                    NewWorldGenerator.playerPosition.y += change[1];
                }
            }
            NewWorldGenerator.PRandomWorld[temp.x][temp.y] = Tileset.FLOOR;
            NewWorldGenerator.PRandomWorld[NewWorldGenerator.playerPosition.x][NewWorldGenerator.playerPosition.y] = Tileset.PLAYER;
        }
    }


    public static void saveProject(TETile[][] world) {
        if (world != null) {
            String filePath = "myWorld.ser";
            try (ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(filePath))) {
                oos.writeObject(world);
                System.out.println("项目保存成功！");
                System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void loadProject() {
        String filePath = "myWorld.ser";
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(filePath))) {
            NewWorldGenerator.PRandomWorld = (TETile[][]) ois.readObject();
            System.out.println("项目加载成功！");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        Game game = new Game();
//        game.playWithKeyboard();
        TETile[][] world = game.playWithInputString("LWWWDDD");
        saveProject(world);
    }
}
