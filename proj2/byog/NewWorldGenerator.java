package byog;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.TileEngine.TERenderer;
import java.util.Random;
import java.util.HashMap;

public class NewWorldGenerator {
    private static final int WIDTH = 100;
    private static final int HEIGHT = 40;
    private static Random r;
    public static Position playerPosition = new Position(0, 0);
    public static TETile[][] PRandomWorld;
    public static TERenderer ter = new TERenderer();

    public static class Position {
        public int x;
        public int y;

        public Position(int X, int Y) {
            x = X;
            y = Y;
        }
    }

    public static class Direction {
        int x;
        int y;

        public Direction(int X, int Y) {
            x = X;
            y = Y;
        }
    }

    public static void InitializeWorld(TETile[][] tiles) {
        for (int i = 0; i < WIDTH; i += 1) {
            for (int j = 0; j < HEIGHT; j += 1) {
                tiles[i][j] = Tileset.NOTHING;
            }
        }
    }




    public static int[] getRandomDirection() {
        // 随机选择操作轴（0=x轴，1=y轴）
        boolean isXAxis = r.nextBoolean();

        // 随机选择正负
        int value = r.nextBoolean() ? 1 : -1;

        return isXAxis
                ? new int[] {value, 0}   // x轴方向
                : new int[] {0, value};  // y轴方向
    }

    public static void generateWorld(int randomSeed) {
        r = new Random(randomSeed);
        PRandomWorld = new TETile[WIDTH][HEIGHT];
        InitializeWorld(PRandomWorld);

        // 随机生成初始点
        int StartX = r.nextInt(WIDTH - 4) + 2;
        int StartY = r.nextInt(HEIGHT - 4) + 2;
        Position StartP = new Position(StartX, StartY);

        // 随机生成初始方向
        int[] RandomDirection = getRandomDirection();
        Direction StartD = new Direction(RandomDirection[0], RandomDirection[1]);

        Build.GenerateStuff(PRandomWorld, StartP, StartD, r);
        PRandomWorld[StartP.x][StartP.y] = Tileset.LOCKED_DOOR;
        playerPosition.x = StartP.x + StartD.x;
        playerPosition.y = StartP.y + StartD.y;
        PRandomWorld[playerPosition.x][playerPosition.y] = Tileset.PLAYER;

    }

    public static void getPlayerPosition() {
        for (int i = 0; i < WIDTH; i += 1) {
            for (int j = 0; j < HEIGHT; j += 1) {
                System.out.println("111");
                if (PRandomWorld[i][j] == Tileset.PLAYER) {

                    playerPosition.x = i;
                    playerPosition.y = j;
                    break;
                }
            }
        }
        System.out.println(playerPosition.x);
        PRandomWorld[playerPosition.x][playerPosition.y] = Tileset.PLAYER;
    }

    public static void letsBegin() {
        ter.initialize(WIDTH, HEIGHT);
        while (true) {
            ter.renderFrame(PRandomWorld);
        }
    }
}
