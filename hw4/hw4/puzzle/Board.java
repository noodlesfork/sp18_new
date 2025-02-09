package hw4.puzzle;
import edu.princeton.cs.algs4.Queue;
import java.util.Arrays;

public class Board implements WorldState {
    private int[][] tiles;
    private int N;
    private int BLANK = 0;

    public Board(int[][] tiles) {
        N = tiles.length;
        this.tiles = new int[N][N];
        for (int i = 0; i < N; i += 1) {
            System.arraycopy(tiles[i], 0, this.tiles[i], 0, N);
        }
    }

    public int tileAt(int i, int j) {
        return tiles[i][j];
    }

    public int size() {
        return N;
    }

    private boolean outOfBound(int i, int j) {
        return (i < 0 || i > N - 1 || j < 0 || j > N - 1);
    }

    /*
       Reference: https://joshh.ug/neighbors.html
     */
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }

    public int hamming() {
        int wrong = 0;
        for (int i = 0; i < N; i += 1) {
            for (int j = 0; j < N; j += 1) {
                if (tileAt(i, j) != 0 && tileAt(i, j) != N * i + j + 1) {
                    wrong += 1;
                }
            }
        }
        return wrong;
    }
    public int manhattan() {
        int manDistance = 0;
        for (int i = 0; i < N; i += 1) {
            for (int j = 0; j < N; j += 1) {
                if (tileAt(i, j) != BLANK) {
                    int xShouldBe = (tileAt(i, j) - 1) / N;
                    int yShouldBe = (tileAt(i, j) - 1) % N;
                    manDistance += Math.abs(xShouldBe - i) + Math.abs(yShouldBe - j);
                }
            }
        }
        return manDistance;
    }

    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    @Override
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }

        if (y == null || getClass() != y.getClass()) {
            return false;
        }

        Board Y = (Board) y;

        if (size() != Y.size()) {
            return false;
        }

        return Arrays.deepEquals(this.tiles, Y.tiles);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(this.tiles);
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }
}
