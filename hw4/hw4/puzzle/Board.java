package hw4.puzzle;
import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState{
    private int[][] tiles;
    private int N;
    private int BLANK = 0;

    public Board(int[][] tiles) {
        N = tiles.length;
        this.tiles = new int[N][N];
        for (int i = 0; i < N; i += 1) {
            for (int j = 0; j < N; j += 1) {
                this.tiles[i][j] = tiles[i][j];
                }
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
                if (tileAt(i, j) != 3 * i + j + 1) {
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
                    int YShouldBe = (tileAt(i, j) - 1) % N;
                    manDistance += Math.abs(xShouldBe - i) + Math.abs(YShouldBe - j);
                }
            }
        }
        return manDistance;
    }

    public int estimatedDistanceToGoal() {
        return manhattan();
    }
    public boolean equals(Object y) {
        for (int i = 0; i < N; i += 1) {
            for (int j = 0; j < N; j += 1) {
                if (this.tileAt(i, j) != ((Board) y).tileAt(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }
}
