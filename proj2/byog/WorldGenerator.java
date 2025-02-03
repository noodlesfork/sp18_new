package byog;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.TileEngine.TERenderer;

import java.util.Random;

public class WorldGenerator {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 40;
    private static final Random r = new Random();

    public static void InitializeWorld(TETile[][] tiles) {
        for (int i = 0; i < WIDTH; i += 1) {
            for (int j = 0; j < HEIGHT; j += 1) {
                tiles[i][j] = Tileset.NOTHING;
            }
        }
    }

    public static class Position {
        int x;
        int y;

        public Position(int PositionW, int PositionH) {
            x = PositionW;
            y = PositionH;
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


    public static void GenerateFloor(TETile[][] tiles, Position p, Direction d, int left, int right, int height) {
        int XHeightChange;
        int YHeightChange;
        int XChange;
        int YChange;

        TETile t = Tileset.FLOOR;
        System.out.println("正在铺地板");

        for (int i = 0; i < height; i += 1) {
            XHeightChange = i * d.x;
            YHeightChange = i * d.y;
            for (int j = 0; j < left; j += 1) {
                XChange = j * (d.x * d.x - 1);
                YChange = j * (d.y * d.y - 1);
                tiles[p.x + XHeightChange + XChange][p.y + YHeightChange + YChange] = t;

            }
            for (int j = 0; j < right; j += 1) {
                XChange = j * (d.x * d.x - 1);
                YChange = j * (d.y * d.y - 1);
                tiles[p.x + XHeightChange - XChange][p.y + YHeightChange - YChange] = t;

            }
        }
    }

    public static void GenerateTopWall(TETile[][] tiles, Position p, Direction d, int left, int right, int height, String LastGenerated) {
        int XHeightChange;
        int YHeightChange;
        int XChange;
        int YChange;

        TETile t = Tileset.WALL;
        System.out.println("正在砌墙top");

        XHeightChange = (height - 1) * d.x;
        YHeightChange = (height - 1) * d.y;

        for (int j = 0; j < left; j += 1) {
            XChange = j * (d.x * d.x - 1);
            YChange = j * (d.y * d.y - 1);
            tiles[p.x + XHeightChange + XChange][p.y + YHeightChange + YChange] = t;
        }

        for (int j = 0; j < right; j += 1) {
            XChange = j * (d.x * d.x - 1);
            YChange = j * (d.y * d.y - 1);
            tiles[p.x + XHeightChange - XChange][p.y + YHeightChange - YChange] = t;
        }

        boolean newStuff = r.nextBoolean();
        if (newStuff) {
            int where = r.nextInt(left + right - 1 - 2) + 1;
            int newX = p.x + XHeightChange + (left - 1 + where) * (d.x * d.x - 1);
            int newY = p.y + YHeightChange + (left - 1 + where) * (d.y * d.y - 1);

            tiles[newX][newY] = Tileset.FLOOR;

            Position newP = new Position(newX + d.x, newY + d.y);
            GenerateStuff(tiles, newP, d, LastGenerated);

        }
    }

    public static void GenerateDownWall(TETile[][] tiles, Position p, Direction d, int left, int right, String LastGenerated) {
        int XChange;
        int YChange;

        TETile t = Tileset.WALL;
        System.out.println("正在砌墙down");

        for (int j = 0; j < left; j += 1) {
            XChange = j * (d.x * d.x - 1);
            YChange = j * (d.y * d.y - 1);
            tiles[p.x + XChange][p.y + YChange] = t;
        }

        for (int j = 0; j < right; j += 1) {
            XChange = j * (d.x * d.x - 1);
            YChange = j * (d.y * d.y - 1);
            tiles[p.x - XChange][p.y - YChange] = t;
        }
    }

    public static void GenerateLeftWall(TETile[][] tiles, Position p, Direction d, int left, int height, String LastGenerated) {
        int XHeightChange;
        int YHeightChange;

        int XChange = (left - 1) * (d.x * d.x - 1);
        int YChange = (left - 1) * (d.y * d.y - 1);

        for (int i = 0; i < height; i += 1) {
            XHeightChange = i * d.x;
            YHeightChange = i * d.y;

            TETile t = Tileset.WALL;
            tiles[p.x + XHeightChange + XChange][p.y + YHeightChange + YChange] = t;
        }

        boolean newStuff = r.nextBoolean();
        if (newStuff) {
            int where = r.nextInt(height - 1);
            int newX = p.x + where * d.x + XChange;
            int newY = p.y + where * d.y + YChange;

            Position newP = new Position(newX + (d.x * d.x - 1), newY + (d.y * d.y - 1));
            Direction newD = new Direction(d.x * d.x - 1, d.y * d.y - 1);
            GenerateStuff(tiles, newP, newD, LastGenerated);
        }
    }

    public static void GenerateRightWall(TETile[][] tiles, Position p, Direction d, int right, int height, String LastGenerated) {
        int XHeightChange;
        int YHeightChange;

        int XChange = (right - 1) * (d.x * d.x - 1);
        int YChange = (right - 1) * (d.y * d.y - 1);
        System.out.println("正在砌墙right");



        for (int i = 0; i < height; i += 1) {
            XHeightChange = i * d.x;
            YHeightChange = i * d.y;
            TETile t = Tileset.WALL;
            tiles[p.x + XHeightChange - XChange][p.y + YHeightChange - YChange] = t;
        }

        boolean newStuff = r.nextBoolean();
        if (newStuff) {
            int where = r.nextInt(height - 1) + 1;
            int newX = p.x + where * d.x - XChange ;
            int newY = p.y + where * d.y - YChange ;

            tiles[newX][newY] = Tileset.FLOOR;

            Position newP = new Position(newX - (d.x * d.x - 1), newY - (d.y * d.y - 1));
            Direction newD = new Direction(1 - d.x * d.x, 1 - d.y * d.y);
            GenerateStuff(tiles, newP, newD, LastGenerated);
        }
    }


    public static void GenerateWall(TETile[][] tiles, Position p, Direction d, int left, int right, int height, String LastGenerated) {
        System.out.println("砌墙中");
        GenerateDownWall(tiles, p, d, left, right, LastGenerated);
        GenerateLeftWall(tiles, p, d, left, height, LastGenerated);
        GenerateRightWall(tiles, p, d, right, height, LastGenerated);
        GenerateTopWall(tiles, p, d, left, right, height, LastGenerated);
    }

    public static boolean Check(TETile[][] tiles, Position p, Direction d, int left, int right, int height) {
        int XHeightChange;
        int YHeightChange;
        int XChange;
        int YChange;

        TETile t = Tileset.WALL;

        System.out.println("正在检查是否可以生成");


        // 检查是否可以这么铺
        for (int i = 0; i < height; i += 1) {
            XHeightChange = i * d.x;
            YHeightChange = i * d.y;
            for (int j = 0; j < left; j += 1) {
                XChange = j * (d.x * d.x - 1);
                YChange = j * (d.y * d.y - 1);
                if (p.y + YHeightChange + YChange >= HEIGHT || p.y + YHeightChange + YChange < 0) {
                    return false;
                }
                if (p.x + XHeightChange + XChange >= WIDTH || p.x + XHeightChange + XChange < 0) {
                    return false;
                }
                if (tiles[p.x + XHeightChange + XChange][p.y + YHeightChange + YChange] == t) {
                    return false;
                }


            }
            for (int j = 0; j < right; j += 1) {
                XChange = j * (d.x * d.x - 1);
                YChange = j * (d.y * d.y - 1);

                if (p.y + YHeightChange + YChange >= HEIGHT || p.y + YHeightChange + YChange < 0) {
                    return false;
                }
                if (p.x + XHeightChange - XChange >= WIDTH || p.x - XHeightChange + XChange < 0) {
                    return false;
                }
                if (tiles[p.x + XHeightChange - XChange][p.y + YHeightChange - YChange] == t) {
                    return false;
                }

            }
        }
        return true;
    }

    public static boolean GenerateRoom(TETile[][] tiles, Position p, Direction d) {
        // 如果最开始就算出来可以使用的空间有多少是不是就可以？但是还有已经使用的空间
         // 只需初始化一次
        int maxAttempts = 10;
        int attempts = 0;
        String ThisGenerated = "Room";
        while (attempts < maxAttempts) {
            // 生成随机参数
            int left = r.nextInt(5) + 2;
            int right = r.nextInt(5) + 2;
            int height = r.nextInt(5) + 4;

            // 检查参数是否合法
            if (Check(tiles, p, d, left, right, height)) {
                // 生成房间

                GenerateFloor(tiles, p, d, left, right, height);
                GenerateWall(tiles, p, d, left, right, height, ThisGenerated);
                tiles[p.x][p.y] = Tileset.FLOWER;

                System.out.println("成功生成一个房间");
                return true; // 生成成功

            }

            attempts += 1;
        }
        System.out.println("生成房间失败");
        return false;
    }

    public static boolean GenerateHall(TETile[][] tiles, Position p, Direction d) {
        int maxAttempts = 10;
        int attempts = 0;
        String ThisGenerated = "Hall";

        while (attempts < maxAttempts) {
            int height = r.nextInt(8) + 2;
            int left = 2;
            int right = 2;

            if (Check(tiles, p, d, left, right, height)) {
                // 这里应该先往前走一格

                GenerateFloor(tiles, p, d, left, right, height);
                GenerateWall(tiles, p, d, left, right, height, ThisGenerated);
                tiles[p.x][p.y] = Tileset.FLOWER;

                System.out.println("成功生成一个走廊");
                return true; // 生成成功
            }
            attempts += 1;
        }
        System.out.println("生成走廊失败");
        return false;
    }

    public static void GenerateStuff(TETile[][] tiles, Position p, Direction d, String LastGenerated) {

        if (LastGenerated.equals("Room")) {
            GenerateHall(tiles, p, d);
        } else {
            int i = r.nextInt(2);
            if (i == 0) {
                GenerateHall(tiles, p, d);
            } else {
                GenerateRoom(tiles, p, d);
            }
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();

        TETile[][] SimpleWorld = new TETile[WIDTH][HEIGHT];
        InitializeWorld(SimpleWorld);

        int StartX = 40;
        int StartY = 20;
        Position StartPosition = new Position(StartX, StartY);
        Direction StartDirection = new Direction(0, 1);

        GenerateRoom(SimpleWorld, StartPosition, StartDirection);
        SimpleWorld[StartPosition.x][StartPosition.y] = Tileset.LOCKED_DOOR;
        ter.renderFrame(SimpleWorld);

    }

}
