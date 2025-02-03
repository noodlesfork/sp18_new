package byog.lab5;
import org.junit.Test;


import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 30;
    private static int s = 3;
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);
//    private static TETile t = Tileset.FLOWER;

    public static class Position {
        int x;
        int y;
        public Position(int positionW, int positionH) {
            x = positionW;
            y = positionH;
        }
    }

    public static void DrawNextLine(TETile[][] tiles, int length, int positionW, int positionH, TETile t) {
        for (int i = 0; i < length; i += 1) {
            if (! tiles[positionW + i][positionH].equals(t)) {
                tiles[positionW + i][positionH] = TETile.colorVariant(t, 32, 123, 123, RANDOM);
            }
        }
    }

    public static int[] GetChanges(int s) {

        int[] changes = new int[2 * s];
        for (int i = 0; i < 2 * s; i += 1) {
            if (i == 0 || i == s) {
                changes[i] = 0;
            } else if (i < s) {
                changes[i] = 1;
            } else {
                changes[i] = -1;
            }
        }
        return changes;
    }

    public static void addHexToTile(TETile[][] tiles, int positionW, int positionH, TETile t) {
        // where is the given position? 要不先认为是左上角？
        if (positionW + s >= WIDTH || positionW - s < 0 || positionH - 2 * s < 0) {
            throw new IndexOutOfBoundsException(("You can not place a hex here. It's out of bound."));
        }

        int[] changes = GetChanges(s);
        int length = s;

        for (int i = 0; i < 2 * s; i += 1) {
            int change = changes[i];
            positionW -= change;
            if (i > 0) {
                positionH -= 1;
            }
            length += 2 * change;
            DrawNextLine(tiles, length, positionW, positionH, t);
        }


    }

    public static void addHexToWholeWorld(TETile[][] tiles, int positionW, int positionH, TETile t) {
        if (positionW + s >= WIDTH || positionW - s < 0 || positionH - 2 * s < 0) {
            throw new IndexOutOfBoundsException(("You can not place a hex here. It's out of bound."));
        }

        int[] changes = GetChanges(s);
        int length = s;

        for (int i = 0; i < 2 * s; i += 1) {
            int change = changes[i];
            positionW -= change;
            if (i > 0) {
                positionH -= 1;
            }
            length += 2 * change;
            DrawNextLine(tiles, length, positionW, positionH, t);

            if (i == s) {
                Position p1 = new Position(positionW - s, positionH);
                try {
                    addHexToWholeWorld(tiles, p1.x, p1.y, t);
                } catch (IndexOutOfBoundsException e) {
                    // 处理异常，例如打印日志或恢复操作
                    System.err.println("递归调用失败: " + e.getMessage());
                }
                Position p2 = new Position(positionW + 3 * s - 2, positionH);
                try {
                    addHexToWholeWorld(tiles, p2.x, p2.y, t);
                } catch (IndexOutOfBoundsException e) {
                    // 处理异常，例如打印日志或恢复操作
                    System.err.println("递归调用失败: " + e.getMessage());
                }
            }

            if (i == 2 * s - 1) {
                Position p3 = new Position(positionW, positionH - 1);
                try {
                    addHexToWholeWorld(tiles, p3.x, p3.y, t);
                } catch (IndexOutOfBoundsException e) {
                    // 处理异常，例如打印日志或恢复操作
                    System.err.println("递归调用失败: " + e.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] HexRegion = new TETile[WIDTH][HEIGHT];


        // Initialize the world
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                HexRegion[x][y] = Tileset.NOTHING;
            }
        }

        Position p = new Position(9, 29);
        TETile t = Tileset.FLOWER;
//        addHexToTile(HexRegion, p.x, p.y, t);
        addHexToWholeWorld(HexRegion, p.x, p.y, t);


        ter.renderFrame(HexRegion);
    }

}
