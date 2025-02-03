package byog;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Build {
    public static void LayTheFloor(TETile[][] tiles, int height, int left, int right, NewWorldGenerator.Position p, NewWorldGenerator.Direction d) {
        int XHeightChange;
        int YHeightChange;
        int XChange;
        int YChange;

        TETile t = Tileset.FLOOR;

        for (int i = 0; i < height - 1; i += 1) {
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


    public static void BuildUpAndDownWall(TETile[][] tiles, int height, int left, int right, NewWorldGenerator.Position p, NewWorldGenerator.Direction d, boolean Generate, Random r) {
        // 我在想砌墙是不是只是关于方向的事情，也就是这几个参数的事情int height, int left, int right, Position p, Direction d
        // 我只需要计算这个方向上的height left right 是什么，以及要不要生成NewStuff就可以吧
        // 应该只有top down 可以这么做

        int XChangeInHeight = d.x * (height - 1);
        int YChangeInHeight = d.y * (height - 1);
        int XChangeInWidth;
        int YChangeInWidth;

        TETile t = Tileset.WALL;

        NewWorldGenerator.Position newP = new NewWorldGenerator.Position(p.x + XChangeInHeight, p.y + YChangeInHeight);

        for (int i = 0; i < left; i += 1) {
            XChangeInWidth = i * (d.x * d.x - 1);
            YChangeInWidth = i * (d.y * d.y - 1);
            tiles[newP.x + XChangeInWidth][newP.y + YChangeInWidth] = t;
        }

        for (int i = 0; i < right; i += 1) {
            XChangeInWidth = i * (1 - d.x * d.x);
            YChangeInWidth = i * (1 - d.y * d.y);
            tiles[newP.x + XChangeInWidth][newP.y + YChangeInWidth] = t;
        }

        if (Generate) {
            if (r.nextBoolean()) {
                int ExpansionPoint;
                int LeftOrRight = r.nextInt(3);
                NewWorldGenerator.Position ToCreate;
                NewWorldGenerator.Position ToExpand;

                if (LeftOrRight == 0) {
                    if (left != 2) {
                        ExpansionPoint = r.nextInt(left - 2);
                        ToExpand = new NewWorldGenerator.Position(newP.x + ExpansionPoint * (d.x * d.x - 1), newP.y + ExpansionPoint * (d.y * d.y - 1));
                        ToCreate = new NewWorldGenerator.Position(ToExpand.x + d.x, ToExpand.y + d.y);
                        boolean success = GenerateStuff(tiles, ToCreate, d, r);
                        if (success) {
                            tiles[ToExpand.x][ToExpand.y] = Tileset.FLOOR;
                        }
                    }
                } else {
                    if (right != 2) {
                        ExpansionPoint = r.nextInt(right - 1);
                        ToExpand = new NewWorldGenerator.Position(newP.x + ExpansionPoint * (1 - d.x * d.x), newP.y + ExpansionPoint * (1 - d.y * d.y));
                        ToCreate = new NewWorldGenerator.Position(ToExpand.x + d.x, ToExpand.y + d.y);
                        boolean success = GenerateStuff(tiles, ToCreate, d, r);
                        if (success) {
                            tiles[ToExpand.x][ToExpand.y] = Tileset.FLOOR;
                        }
                    }
                }



            }
        }
    }

    public static void BuildSideWall(TETile[][] tiles, int height, int left, int right, NewWorldGenerator.Position p, NewWorldGenerator.Direction d, Random r){
        int XFarLeft = p.x + (d.x * d.x - 1) * (left - 1);
        int XFarRight = p.x + (1 - d.x * d.x) * (right - 1);
        int YFarLeft = p.y + (d.y * d.y - 1) * (left - 1);
        int YFarRight = p.y + (1 - d.y * d.y) * (right - 1);

        TETile t = Tileset.WALL;

        for (int i = 1; i < height; i += 1) {
            tiles[XFarLeft + i * d.x][YFarLeft + i * d.y] = t;
            tiles[XFarRight + i * d.x][YFarRight + i * d.y] = t;
        }

        // 左边的墙要不要生成新东西
        if (r.nextBoolean()) {
            int ExpansionPoint = r.nextInt(height - 2) + 1;
            NewWorldGenerator.Position ToExpand = new NewWorldGenerator.Position(XFarLeft + ExpansionPoint * d.x, YFarLeft + ExpansionPoint * d.y);
            NewWorldGenerator.Position ToCreate = new NewWorldGenerator.Position(ToExpand.x + d.x * d.x - 1, ToExpand.y + d.y * d.y - 1);
            NewWorldGenerator.Direction NewD = new NewWorldGenerator.Direction(d.x * d.x - 1, d.y * d.y - 1);
            boolean success = GenerateStuff(tiles, ToCreate, NewD, r);
            if (success) {
                tiles[ToExpand.x][ToExpand.y] = Tileset.FLOOR;
            }
        }

        // 右边的墙要不要生成新东西
        if (r.nextBoolean()) {
            int ExpansionPoint = r.nextInt(height - 2) + 1;
            NewWorldGenerator.Position ToExpand = new NewWorldGenerator.Position(XFarRight + ExpansionPoint * d.x, YFarRight + ExpansionPoint * d.y);
            NewWorldGenerator.Position ToCreate = new NewWorldGenerator.Position(ToExpand.x + 1 - d.x * d.x, ToExpand.y + 1 - d.y * d.y);
            NewWorldGenerator.Direction NewD = new NewWorldGenerator.Direction(1 - d.x * d.x, 1 - d.y * d.y);
            boolean success = GenerateStuff(tiles, ToCreate, NewD, r);
            if (success) {
                tiles[ToExpand.x][ToExpand.y] = Tileset.FLOOR;
            }
        }
    }

    public static boolean check(TETile[][] tiles, int height, int left, int right, NewWorldGenerator.Position p, NewWorldGenerator.Direction d) {
        int XHeightChange;
        int YHeightChange;
        int XChange;
        int YChange;

        TETile t = Tileset.WALL;
        for (int i = 0; i < height; i += 1) {
            XHeightChange = i * d.x;
            YHeightChange = i * d.y;
            for (int j = 0; j < left; j += 1) {
                XChange = j * (d.x * d.x - 1);
                YChange = j * (d.y * d.y - 1);
                try {
                    if (tiles[p.x + XHeightChange + XChange][p.y + YHeightChange + YChange] == t) {
                        return false;
                    }
                } catch (IndexOutOfBoundsException e) {
                    return false;
                }

            }
            for (int j = 0; j < right; j += 1) {
                XChange = j * (d.x * d.x - 1);
                YChange = j * (d.y * d.y - 1);
                try {
                    if (tiles[p.x + XHeightChange - XChange][p.y + YHeightChange - YChange] == t) {
                        return false;
                    }
                } catch (IndexOutOfBoundsException e) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean GenerateStuff(TETile[][] tiles, NewWorldGenerator.Position p, NewWorldGenerator.Direction d, Random r) {
        int MaxAttempts = 10;
        int attempt = 0;

        while (attempt < MaxAttempts) {
            int height = r.nextInt(8) + 3;
            int left = r.nextInt(6) + 2;
            int right = r.nextInt(6) + 2;

            if (check(tiles, height, left, right, p, d)) {
                LayTheFloor(tiles, height, left, right, p, d);
                BuildUpAndDownWall(tiles, height, left, right, p, d, true, r); // 正向
                NewWorldGenerator.Direction DownD = new NewWorldGenerator.Direction(-d.x, -d.y);
                BuildUpAndDownWall(tiles, 1, left, right, p, DownD, false, r); // 反向
                tiles[p.x][p.y] = Tileset.FLOOR;
                BuildSideWall(tiles, height, left, right, p, d, r);
                return true;
            }

            attempt += 1;
        }
        return false;
    }
}
